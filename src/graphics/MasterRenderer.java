package graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import graphics.shaders.StaticShader;
import graphics.shaders.WorldShader;
import models.TexturedModel;
import world.Terrain;

public class MasterRenderer {
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.01f;
	private static final float FAR_PLANE = 20000;
	
	private static final float RED = 0.1f; //0.3
	private static final float GREEN = 0.1f; //0.5
	private static final float BLUE = 0.1f; //0.8
	
	private Matrix4f projectionMatrix;
	
	private StaticShader shader = new StaticShader();
	private EntityRender renderer;
	LaunchWindow launchWindow = new LaunchWindow();
	
	private WorldRender worldRenderer;
	private WorldShader worldShader = new WorldShader();
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	private SkyboxRender skyboxRenderer;
	
	public MasterRenderer(Loader loader){
		enableCulling();
		createProjectionMatrix();
		renderer = new EntityRender(shader, projectionMatrix);
		worldRenderer = new WorldRender(worldShader, projectionMatrix);
		skyboxRenderer = new SkyboxRender(loader, projectionMatrix);
	}
	
	public static void enableCulling(){
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableCulling(){
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public Matrix4f getProjectionMatrix(){
		return projectionMatrix;
	}
	
	public void render(List<Light> lights, Camera camera){
		prepare();
		shader.start();
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		worldShader.start();
		worldShader.loadSkyColor(RED, GREEN, BLUE);
		worldShader.loadLights(lights);
		worldShader.loadViewMatrix(camera);
		worldRenderer.renderWorld(terrains);
		worldShader.stop();
		skyboxRenderer.render(camera);
		terrains.clear();
		entities.clear();
	}
	
	public void processTerrain(Terrain terrain){
		terrains.add(terrain);
	}
	
	public void processEntity(Entity entity){
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch != null){
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	
	public void processEntity(List<Entity> entityList){
		for(int i = 0; i < entityList.size(); i++){
			TexturedModel entityModel = entityList.get(i).getModel();
			List<Entity> batch = entities.get(entityModel);
			entities.put(entityModel, entityList);
		}
	}
	
	public void prepare() {
		glEnable(GL11.GL_DEPTH_TEST);
		glClearColor(RED, GREEN, BLUE, 1.0f); //changes color of background space
		//glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void cleanUp(){
		shader.cleanUp();
		worldShader.cleanUp();
	}
	
	private void createProjectionMatrix(){
        int width = launchWindow.width;
        int height = launchWindow.height;
		float aspectRatio = (float) width / (float) height;
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV)/2f))) * aspectRatio;
		float x_scale = y_scale / aspectRatio;
		float frustrum_length = FAR_PLANE - NEAR_PLANE;
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustrum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * FAR_PLANE * NEAR_PLANE) / frustrum_length);
		projectionMatrix.m33 = 0;
	}
	
}
