package entities;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import graphics.LaunchWindow;
import input.KeyParser;
import input.KeyboardInput;
import input.MouseInput;
import input.MouseParser;

public class Camera extends KeyParser {
	
	LaunchWindow launchWindow = new LaunchWindow();
	KeyParser keyParser = new KeyParser();
	public Vector3f position = positionPass;
	public float pitch = getPassedPitch();
	public float yaw = getPassedYaw();
	public float roll;
	
	private float distanceFromPlayer = 0;
	private float angleAroundPlayer = 0;
	private float cameraPitch = 0;
	
	private Player player;
	
	public Camera(Player player){
		this.player = player;
		
	}
	
	
	public void moveCamera(){
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}
	
	public Vector2f moveOrientation(){
		if(KeyboardInput.isDown(KEY_F)){
			System.out.println(getPitch() + " " + getYaw());
		}
		if(true){
			this.pitch = (((float) MouseInput.getY() - (launchWindow.height / 2)) / MouseParser.sensitivityIndex);
			//this.yaw = (((float) MouseInput.getX() - (launchWindow.width / 2)) / MouseParser.sensitivityIndex);
			//System.out.println(pitch + " " + yaw);
		}
		return new Vector2f(pitch, yaw);
	}
		
	public Vector3f getPosition() {
		return position;
	}
	public float getPitch() {
		return this.pitch;
	}
	public float getYaw() {
		return this.yaw;
	}
	public float getRoll() {
		return roll;
	}
	
	private void calculateCameraPosition(float horizontalDistance, float verticalDistance){
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verticalDistance;
	}
	
	private float calculateHorizontalDistance(){
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(cameraPitch)));
	}
	
	private float calculateVerticalDistance(){
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(cameraPitch)));
	}
	
	private void calculateZoom(){
		float zoomLevel = (float) keyParser.getDistanceIncrement(); //(float) launchWindow.getScrollAmount();
		distanceFromPlayer -= zoomLevel;
	}
	
	private void calculatePitch(){
		if(MouseInput.isDown(MouseInput.MOUSE_BUTTON_RIGHT)){
			float pitchChange = -pitch;
			pitchChange--;
			System.out.println(pitchChange);
		}
	}
	
	public float calculateAngleAroundPlayer(){
		float angleChange = -yaw;
		if(MouseInput.isDown(MouseInput.MOUSE_BUTTON_LEFT)){
			angleChange--;
			System.out.println(angleChange);
		}
		return angleChange;
	}
	

}
