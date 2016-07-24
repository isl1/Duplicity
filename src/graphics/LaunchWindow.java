package graphics;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector3f;
import org.lwjglx.Sys;

import entities.Camera;
import entities.Entity;
import entities.Player;
import gui.GuiRenderer;
import gui.GuiTexture;
import input.Cursor;
import input.KeyParser;
import input.KeyboardInput;
import input.MouseInput;
import input.MouseParser;
import input.MousePicker;
import world.Save;
import world.Terrain;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("unused")
public class LaunchWindow {
 
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWCursorPosCallback cursorPosCallback;
    private GLFWFramebufferSizeCallback framebufferSizeCallback;
    private GLFWScrollCallback scrollCallback;
 
    public int width = 1024;
    public int height = 600;
    public String title = "Duplicity";
    public long fullscreen = glfwGetPrimaryMonitor();
    public long window;
    private Cursor cursor;
    private boolean vsync;
    private long variableYieldTime, lastTime;
    public int FPS_CAP = 60;
    private static long lastFrameTime;
    private static float delta;
    private double scrollAmount = 0;
    
    public void glfwScrollCallback(long window, double xoffset, double yoffset){
		scrollAmount = yoffset;
	}
 
    public void run() {
 
        try {
            init();
            loop();
            glfwDestroyWindow(window);
            keyCallback.release();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            glfwTerminate();
            errorCallback.release();
        }
    }
 
    private void init() {

        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        
        KeyboardInput.initiate();
        MouseInput.initiateMouse();
 
        if ( glfwInit() != GLFW_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        	glfwDefaultWindowHints();
        	glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        	glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
 
        window = glfwCreateWindow(width, height, title, fullscreen, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");
 
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GLFW_TRUE);
            }
        });
        
		
 
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        
        keyCallback = GLFWKeyCallback.create(KeyboardInput::glfw_key_callback);
		keyCallback.set(window);

		mouseButtonCallback = GLFWMouseButtonCallback.create(MouseInput::glfw_mouse_button_callback);
		mouseButtonCallback.set(window);

		cursorPosCallback = GLFWCursorPosCallback.create(MouseInput::glfw_cursor_pos_callback);
		cursorPosCallback.set(window);
		
		scrollCallback = GLFWScrollCallback.create(this::glfwScrollCallback);
		scrollCallback.set(window);
		
		cursor = new Cursor(Cursor.Standard.ARROW);
		glfwSetCursor(window, cursor.getCursor());

		GL.createCapabilities();
		lastFrameTime = getCurrentTime();
    }
    
    public void updateInput() {
		KeyboardInput.update();
		MouseInput.update();
		glfwPollEvents();
	}
    
    private void sync(int fps) {
        if (fps <= 0) return;
          
        long sleepTime = 1000000000 / fps;
        long yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000*1000));
        long overSleep = 0;
          
        try {
            while (true) {
                long t = System.nanoTime() - lastTime;
                  
                if (t < sleepTime - yieldTime) {
                    Thread.sleep(1);
                }else if (t < sleepTime) {
                    Thread.yield();
                }else {
                    overSleep = t - sleepTime;
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);
             
            if (overSleep > variableYieldTime) {
                variableYieldTime = Math.min(variableYieldTime + 200*1000, sleepTime);
            } else if (overSleep < variableYieldTime - 200*1000) {
                variableYieldTime = Math.max(variableYieldTime - 2*1000, 0);
            }
        }
    }
    
    public void updateDisplay() {
    	glfwSwapInterval(0);
		glfwSwapBuffers(window);
    	sync(FPS_CAP);
    	glfwPollEvents();
    	
    	long currentFrameTime = getCurrentTime();
    	delta = currentFrameTime - lastFrameTime;
    	lastFrameTime = currentFrameTime;
	}
    
    public static float getFrameTimeMilliseconds(){
    	return delta;
    }
    
    public void loop() throws IOException {
    	
    	if(KeyboardInput.isDown(KeyboardInput.KEY_O)){
			glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
		} else {
			glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
		}
    	
    	KeyParser keyParser = new KeyParser();
    	Loader loader = new Loader();
    	Handler handler = new Handler();
    	Player player = handler.PlayerHandler();
    	Camera camera = new Camera(player);
    	MasterRenderer renderer = new MasterRenderer(loader);
    	MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix());
       	GuiRenderer guiRenderer = new GuiRenderer(loader);
		Save save = new Save();
       	
       	Terrain terrain = handler.TerrainHandler();
       	List<Entity> entities = handler.EntityHandler();
       	List<GuiTexture> guis = handler.GuiHandler();

        
        while ( glfwWindowShouldClose(window) == GLFW_FALSE ) {
        	
        	camera.moveCamera();
        	picker.update();
        	//System.out.println(picker.getCurrentRay());
        	player.move(terrain);
        	camera.moveOrientation();
        	renderer.processEntity(player);
        	renderer.processEntity(entities.get(0));
        	renderer.processEntity(entities.get(1));
        	renderer.processTerrain(terrain);
        	renderer.render(handler.LightHandler(), camera);
        	

        	//guiRenderer.render(guis);
        	updateDisplay();
        	
            try {
				new KeyParser().checkKeyState();
				new MouseParser().checkMouseState();
				save.save(player.getPosition(), new Vector3f(player.getRotX(), player.getRotY(), player.getRotZ()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        guiRenderer.cleanUp();
        renderer.cleanUp();
        handler.cleanUp();
        loader.cleanUp();

    }
    
    public void setCursor(Cursor cursor) {
		this.cursor = cursor;
		glfwSetCursor(window, cursor.getCursor());
	}
	
	public Cursor getCursor() {
		return cursor;
	}
	
	public static long getCurrentTime(){
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}
	
	public double getScrollAmount(){
		return scrollAmount;
	}
 
    public static void main(String[] args) {
        new LaunchWindow().run();
    }
 
}