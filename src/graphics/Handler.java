package graphics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Icosohedron;
import entities.Lamp;
import entities.Light;
import entities.Player;
import gui.GuiTexture;
import input.KeyParser;
import models.Model1;
import models.OBJLoader;
import models.RawModel;
import models.TexturedModel;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toxi.geom.AABB;
import toxi.geom.Vec3D;
import util.Hitbox;
import world.Load;
import world.Terrain;

public class Handler extends LaunchWindow {
	
	Loader loader = new Loader();
	Model1 model1 = new Model1();
	Display display = new Display();
	
	List<Light> lights = new ArrayList<Light>();
	public List<Light> LightHandler(){
		
    	lights.add(new Light(new Vector3f(1024, 1000, 1500), new Vector3f(0.1f, 0.1f, 0.1f))); //white sunlight
    	lights.add(new Light(new Vector3f(512, 50, 100), new Vector3f(10, 10, 0), new Vector3f(1, 0.01f, 0.0075f))); //yellow
    	lights.add(new Light(new Vector3f(512, 50, 400), new Vector3f(0, 10, 10), new Vector3f(1, 0.01f, 0.0075f))); //cyan
    	lights.add(new Light(new Vector3f(1024, 50, 100), new Vector3f(10, 0, 10), new Vector3f(1, 0.01f, 0.0075f))); //magenta
    	lights.add(new Light(new Vector3f(1024, 50, 400), new Vector3f(10, 0, 0), new Vector3f(1, 0.01f, 0.0075f))); //red
    	lights.add(new Light(new Vector3f(215, 4.9f, 618), new Vector3f(3.9f, 3.9f, 3), new Vector3f(1, 5.5f, 0.75f))); //lamp
		
    	return lights;
	}
	
	public Terrain TerrainHandler(){
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("resources/textures/white.png"));
    	TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("resources/textures/bluewater.png"));
    	TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("resources/textures/white.png"));
    	TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("resources/textures/white.png"));
    	TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
    	TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("resources/blendmaps/blendmap1.png"));
    	
    	Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, "heightmap2");
		return terrain;
	}
	
	List<Entity> entities = new ArrayList<>();
	public List<Entity> EntityHandler(){
		Lamp lamp = new Lamp();
		entities.add(0, lamp.Lamp(new Vector3f(215, 3.1f, 618), 0.015f));
		Icosohedron icosohedron = new Icosohedron();
		entities.add(1, icosohedron.Icosohedron(new Vector3f(200, 5, 600), 0.2f));
		
		
		return entities;
	}
	
	Load load = new Load();
	public Player PlayerHandler() throws IOException {
		RawModel playerModel = OBJLoader.loadObjModel("icosohedron", loader);
		ModelTexture playerTexture = new ModelTexture(loader.loadTexture("resources/textures/white.png"));
		TexturedModel playerTexModel = new TexturedModel(playerModel, playerTexture);
		Player player = new Player(playerTexModel, new Vector3f(load.load().x, load.load().y, load.load().z), 
				load.getRotation().x, load.getRotation().y, 0, 0.000000000001f);
		return player;
		
	}
	
	public List<GuiTexture> GuiHandler(){
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
    	GuiTexture gui = new GuiTexture(loader.loadTexture("resources/gui/gui1.png"), 
    			new Vector2f(0, 0), new Vector2f(0.25f, 0.25f));
    	guis.add(gui);
    	return guis;
	}
	
	public boolean HitboxHandler() throws IOException {
		Hitbox hitbox = new Hitbox();
		AABB aabb = hitbox.Hitbox(PlayerHandler());
		if(aabb.intersectsBox(hitbox.Hitbox(entities))){
			return true;
		} else {
			return false;
		}
	}
	
	public void cleanUp(){
		entities.clear();
		lights.clear();
		
	}

}
