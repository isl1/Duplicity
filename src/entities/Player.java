package entities;


import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.DoubleBuffer;
import java.util.Date;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import graphics.LaunchWindow;
import input.KeyboardInput;
import input.MouseInput;
import input.MouseParser;
import models.TexturedModel;
import world.Load;
import world.Save;
import world.Terrain;

public class Player extends Entity {
	
	public static final float RUN_SPEED = 6.5f;
	public static final float TURN_SPEED = 50;
	public static final float GRAVITY = -9.80665f;
	public static final float JUMP_STRENGTH = 3f;
	public static final float TERRAIN_LEVEL = 0;
	
	public float currentSpeed = 0;
	public float currentStrafeSpeed = 0;
	public float currentTurnSpeed = 0;
	public float currentYSpeed = 0;
	double xTranslation;
	
	KeyboardInput keyboardInput = new KeyboardInput();
	LaunchWindow launchWindow = new LaunchWindow();
	MouseParser mouseParser = new MouseParser();

	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void move(Terrain terrain) throws IOException{
		getKeyStates(terrain);
		super.changeRotation(0, currentTurnSpeed * (LaunchWindow.getFrameTimeMilliseconds()/1000), 0);
		//super.changeRotation(0, camera.calculateAngleAroundPlayer() * (LaunchWindow.getFrameTimeMilliseconds()/1000), 0);
		float distance = currentSpeed * (LaunchWindow.getFrameTimeMilliseconds()/1000);
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.changePosition(dx, 0, dz);
		currentYSpeed += GRAVITY / Math.pow(((LaunchWindow.getFrameTimeMilliseconds() + 1)/160), -2);
		super.changePosition(0, currentYSpeed * (LaunchWindow.getFrameTimeMilliseconds()/1000), 0);
		float terrainHeight = terrain.getTerrainHeight(super.getPosition().x, super.getPosition().z) + 2;
		if(super.getPosition().y < terrainHeight){
			currentYSpeed = 0;
			super.getPosition().y = terrainHeight;
		}
	}
	
	private void jump(){
		this.currentYSpeed = JUMP_STRENGTH;
	}
	
	@SuppressWarnings("static-access")
	private void getKeyStates(Terrain terrain) throws IOException{
		if(keyboardInput.isDown(keyboardInput.KEY_W)){
			this.currentSpeed = RUN_SPEED;
		} else if(keyboardInput.isDown(keyboardInput.KEY_S)){
			this.currentSpeed = -RUN_SPEED;
		} else {
			this.currentSpeed = 0;
		}
		if(keyboardInput.isDown(keyboardInput.KEY_C)){
			float x = super.getPosition().x;
			float y = super.getPosition().y;
			float z = super.getPosition().z;
			String coords = (x + " " + y + " " + z);
			String falseCoords = "0.0 0.0 0.0";
			if(coords == falseCoords){
				System.out.print("");
			} else {
				System.out.println(coords);
			}
		}
		
		if(keyboardInput.isDown(keyboardInput.KEY_W) && keyboardInput.isDown(keyboardInput.KEY_LEFT_CONTROL)){
			this.currentSpeed = RUN_SPEED * 2.75f;
		} else if(keyboardInput.isDown(keyboardInput.KEY_S) && keyboardInput.isDown(keyboardInput.KEY_LEFT_CONTROL)){
			this.currentSpeed = -RUN_SPEED * 2.75f;
		}
		
		double storeOffset[] = {0, 0};
		
		if(MouseInput.getX() >= 0 && MouseInput.getX() <= launchWindow.width){
			double xOffset = -(MouseInput.getX() - (launchWindow.width / 2));
			if(xOffset != 0){
				this.currentTurnSpeed = (float) ((storeOffset[0] * 0.01) * TURN_SPEED);
			}
			storeOffset[0] = xOffset;
		}
		
		
		/*if(keyboardInput.isDown(keyboardInput.KEY_D)){
			this.currentTurnSpeed = -TURN_SPEED;
		} else if(keyboardInput.isDown(keyboardInput.KEY_A)){
			this.currentTurnSpeed = TURN_SPEED;
		} else {
			this.currentTurnSpeed = 0;
		}*/
		
		if(keyboardInput.isDown(keyboardInput.KEY_SPACE) && !(super.getPosition().y > 
							terrain.getTerrainHeight(super.getPosition().x, super.getPosition().z) + 2.1f)){
			jump();
		}
		if(keyboardInput.isDown(keyboardInput.KEY_SPACE) && keyboardInput.isDown(keyboardInput.KEY_U)
				&& keyboardInput.isDown(keyboardInput.KEY_P)){
			super.changePosition(0, 1, 0);
		}
	}
}
