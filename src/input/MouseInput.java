package input;

import graphics.LaunchWindow;
import input.KeyParser;
import input.KeyboardInput;

import java.awt.*;

import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;
import java.nio.ByteBuffer;
@SuppressWarnings("unused")
public class MouseInput {
	
	public static final int MOUSE_BUTTON_1 = 0x0, MOUSE_BUTTON_2 = 0x1, MOUSE_BUTTON_3 = 0x2, MOUSE_BUTTON_4 = 0x3, MOUSE_BUTTON_5 = 0x4, MOUSE_BUTTON_6 = 0x5, MOUSE_BUTTON_7 = 0x6, MOUSE_BUTTON_8 = 0x7, MOUSE_BUTTON_LAST = MOUSE_BUTTON_8, MOUSE_BUTTON_LEFT = MOUSE_BUTTON_1, MOUSE_BUTTON_RIGHT = MOUSE_BUTTON_2, MOUSE_BUTTON_MIDDLE = MOUSE_BUTTON_3;

	private static int[] buttonState = new int[MOUSE_BUTTON_LAST];
	private static int[] buttonDown = new int[MOUSE_BUTTON_LAST];
	
	private static double x = 0, y = 0;
	
	public static void initiateMouse() {
		for (int i = 0; i < buttonState.length; i++) {
			buttonState[i] = -1;
		}
	}

	public static void update() {
		for (int i = 0; i < buttonState.length; i++) {
			buttonState[i] = -1;
		}
	}

	public static boolean isDown(int key) {
		if (key <= MOUSE_BUTTON_LAST) {
			return buttonDown[key] == 1;
		}
		return false;
	}

	public static boolean isClicked(int key) {
		if (key <= MOUSE_BUTTON_LAST) {
			return buttonState[key] == 1;
		}
		return false;
	}

	public static boolean isReleased(int key) {
		if (key <= MOUSE_BUTTON_LAST) {
			return buttonState[key] == 0;
		}
		return false;
	}
	
	public static double getX() {
		return x;
	}

	public static double getY() {
		return y;
	}

	public static void setX(double x) {
		MouseInput.x = x;
	}

	public static void setY(double y) {
		MouseInput.y = y;
	}

	public static void glfw_mouse_button_callback(long window, int button, int action, int mods) {
		if (button <= MOUSE_BUTTON_LAST) {
			buttonState[button] = action;
			buttonDown[button] = action != GLFW_RELEASE ? 1 : 0;
		}
	}
	
	public static void glfw_cursor_pos_callback(long window, double xpos, double ypos) {
		MouseInput.x = xpos;
		MouseInput.y = ypos;
		
	}
	public static double[] position = {getX(), getY()};
}
