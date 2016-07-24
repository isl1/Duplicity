package input;

import org.lwjgl.glfw.GLFWImage;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

@SuppressWarnings("unused")
public class Cursor {
	
	private long cursor;
	private int xHot, yHot;
	
	
	public Cursor(int xHot, int yHot) {
		this.xHot = xHot;
		this.yHot = yHot;
		
		GLFWImage image = GLFWImage.malloc();
		
		cursor = glfwCreateCursor(image, xHot, yHot);
		
		image.free();
	}
		
	public Cursor(Standard cursor) {
		this.cursor = glfwCreateStandardCursor(cursor.get());
	}
	
	public int getXHot() {
		return xHot;
	}
	
	public int getYHot() {
		return yHot;
	}
	
	
	public long getCursor() {
		return cursor;
	}
	
	public enum Standard {
        ARROW(GLFW_ARROW_CURSOR),
        IBEAM(GLFW_IBEAM_CURSOR),
        CROSSHAIR(GLFW_CROSSHAIR_CURSOR),
        HAND(GLFW_HAND_CURSOR),
        HRESIZE(GLFW_HRESIZE_CURSOR),
        VRESIZE(GLFW_VRESIZE_CURSOR);

        private int standard;

        Standard(int standard) {
            this.standard = standard;
        }
        
        public int get() {
            return standard;
        }
    }
}