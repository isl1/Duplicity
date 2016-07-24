package input;

import static org.lwjgl.glfw.GLFW.*;

import entities.Camera;
import entities.Entity;
import entities.Player;
import graphics.LaunchWindow;
import input.KeyboardInput;
import input.MouseInput;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioInputStream;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

import audio.Master;

//import audio.AudioMaster;
//import audio.Source;
@SuppressWarnings("unused")

public class KeyParser extends KeyboardInput {
	
	public boolean crouching;
	public Vector3f positionPass = new Vector3f(0, 0, 0);
	
	private MouseParser mouseParser = new MouseParser();
	public float pitch;
	public float yaw;
	private double distanceIncrement;
	
	public float getPassedPitch(){
		return mouseParser.pitchPass = pitch;
	}
	
	public float getPassedYaw(){
		return mouseParser.yawPass = yaw;
	}
	
	public void cameraOrientation(){
		mouseParser.aCameraOrientation();
	}
	

	public long checkKeyState() throws InterruptedException, IOException {
		
		if(KeyboardInput.isPressed(KEY_C)){
			Camera camera = new Camera(null);
			float x = camera.getPosition().x;
			float y = camera.getPosition().y;
			float z = camera.getPosition().z;
			System.out.println(x + " " + y + " " + z);
		}
		
		if(KeyboardInput.isPressed(KEY_UP)){
			this.distanceIncrement += 0.1;
			Thread.sleep(100);
		}
		if(KeyboardInput.isPressed(KEY_ESCAPE)){
			System.exit(0);
		}
		if(KeyboardInput.isDown(KEY_G)){
			System.out.println(MouseInput.getX() + " " + MouseInput.getY());
			Thread.sleep(100);
		}
		if(KeyboardInput.isDown(KEY_J)){
			new Master();
		}

		return fullscreen;
	}
	
	public double getDistanceIncrement(){
		return distanceIncrement;
	}
}
