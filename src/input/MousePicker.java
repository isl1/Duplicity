package input;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import graphics.LaunchWindow;
import util.MathStuff;

public class MousePicker {
	
	private Vector3f currentRay;
	private Matrix4f viewMatrix;
	private Matrix4f projectionMatrix;
	private Camera camera;
	
	public MousePicker(Camera cam, Matrix4f projection){
		this.camera = cam;
		this.projectionMatrix = projection;
		this.viewMatrix = MathStuff.createViewMatrix(cam);
	}

	public Vector3f getCurrentRay() {
		return currentRay;
	}
	
	public void update(){
		viewMatrix = MathStuff.createViewMatrix(camera);
		currentRay = calculateMouseRay();
	}
	
	private Vector3f calculateMouseRay(){
		float mouseX = (float) MouseInput.getX();
		float mouseY = (float) MouseInput.getY();
		Vector2f normalizedCoords = getNormalizedDeviceCoords(mouseX, mouseY);
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1, 1);
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		Vector3f worldRay = toWorldCoords(eyeCoords);
		return worldRay;
	}
	
	private Vector3f toWorldCoords(Vector4f eyeCoords){
		Matrix4f invView = Matrix4f.invert(viewMatrix, null);
		Vector4f rayWorld = Matrix4f.transform(invView, eyeCoords, null);
		Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, -1);
		mouseRay.normalise();
		return mouseRay;
	}
	
	private Vector4f toEyeCoords(Vector4f clipCoords){
		Matrix4f invProjection = Matrix4f.invert(projectionMatrix, null);
		Vector4f eyeCoords = Matrix4f.transform(invProjection, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1, 0);
	}
	
	private Vector2f getNormalizedDeviceCoords(float mouseX, float mouseY){
		LaunchWindow launchWindow = new LaunchWindow();
		float x = (2f * mouseX) / launchWindow.width - 1;
		float y = (2f * mouseY) / launchWindow.height - 1;
		return new Vector2f(x, y);
	}
	
}
