package input;

import graphics.LaunchWindow;

public class MouseParser extends MouseInput {
	
	public static int sensitivityIndex = 2;
	LaunchWindow launchWindow = new LaunchWindow();
	public float pitchPass;
	public float yawPass;
	
	public void aCameraOrientation(){
		if(true){
			pitchPass = (((float) MouseInput.getY() - (launchWindow.height / 2)) / sensitivityIndex);
			yawPass = (((float) MouseInput.getX() - (launchWindow.width / 2)) / sensitivityIndex);
		}
	}

	public long checkMouseState() {
		
		if(MouseInput.isClicked(MOUSE_BUTTON_LEFT) == true){
		}
		if(MouseInput.isClicked(MOUSE_BUTTON_RIGHT) == true){
		}
		if(MouseInput.isClicked(MOUSE_BUTTON_MIDDLE)){
		}
		
		return 0;
	}

	public float getPitchPass() {
		return pitchPass;
	}

	public float getYawPass() {
		return yawPass;
	}
	
	

}
