package com.elevox.main;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.glu.GLU;

public class MainWindow {
	private DisplayMode displayMode;
	
	public int mouseX = 0;
	public int mouseY = 0;
	public boolean rightMouseDown = false;
	public boolean leftMouseDown = false;
	public boolean mouseOnWindow = false;
	
	private World theWorld;
	
	private Timer timer = new Timer(20.0F);

	private long lastFPS;

	private int fps;
	
	public static Gui gui;

	public int width = 640;
	public int height = 480;
	
	public boolean getQuads = true;
	
	public static FPCameraController camera;
	
	public static ArrayList<Integer> vertexBufferIds = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		new MainWindow();
	}
	
	public MainWindow() {
		try {
			Display.setFullscreen(false);
			Display.sync(60);
			Display.setVSyncEnabled(true);
			Display.setTitle("FPS: -");
	        DisplayMode d[] = Display.getAvailableDisplayModes();
	        for (int i = 0; i < d.length; i++) {
	            if (d[i].getWidth() == width
	                && d[i].getHeight() == height
	                && d[i].getBitsPerPixel() == 32) {
	                displayMode = d[i];
	                break;
	            }
	        }
	        Display.setDisplayMode(displayMode);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
    	camera = new FPCameraController();
		
		initGL();
		
		createVertexBufferIds();
		
		lastFPS = getSystemTime(); //set lastFPS to current Time
		
		World.createWorld("test", new Random().nextLong());
		theWorld = World.load("test");
    	
		gui = new Gui(this);
		gui.setActualGui(new GuiDebug(this, camera, theWorld.chunkMap));
    	 
        float dx        = 0.0f;
        float dy        = 0.0f;
        float dt        = 0.0f; //length of frame
        long lastTime   = 0L; // when the last frame was
        long time       = 0L;
     
        float mouseSensitivity = 0.05f;
        float movementSpeed = 10.0f; //move 10 units per second
        
        //hide the mouse
        Mouse.setGrabbed(true);
        
    	while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
        	mouseX = Mouse.getX();
        	mouseY = Mouse.getY();
        	leftMouseDown = Mouse.isButtonDown(0);
        	rightMouseDown = Mouse.isButtonDown(1);
        	mouseOnWindow = Mouse.isInsideWindow();
    		
    		time = Sys.getTime();
            dt = (time - lastTime)/1000.0F;
            lastTime = time;
            
    		//distance in mouse movement from the last getDX() call.
            dx = Mouse.getDX();
            //distance in mouse movement from the last getDY() call.
            dy = Mouse.getDY();
     
            //control camera yaw from x movement from the mouse
            camera.yaw(dx * mouseSensitivity);
            //control camera pitch from y movement from the mouse
            camera.pitch(dy * mouseSensitivity);
            
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                camera.walkForward(movementSpeed*dt);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                camera.walkBackwards(movementSpeed*dt);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                camera.strafeLeft(movementSpeed*dt);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                camera.strafeRight(movementSpeed*dt);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                camera.down(movementSpeed*dt);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                camera.up(movementSpeed*dt);
            }

            // set the modelview matrix back to the identity
            GL11.glLoadIdentity();
            // look through the camera before you draw anything
            camera.lookThrough();
            
			timer.updateTimer();
			
			gui.run();
			
			for (int var3 = 0; var3 < this.timer.elapsedTicks; ++var3) {
                this.run();
            }
			
			this.render();
			enterOrtho();
			gui.render();
			leaveOrtho();

			this.updateFPS();
			
			Display.sync(60);
			Display.update();
		}
		
    	theWorld.save();
    	
		Display.destroy();
		System.exit(0);
	}
	
	private void createVertexBufferIds() {
		vertexBufferIds.clear();
		
		for(int i = 0; i < Math.pow(Options.viewDistance*2+1, 2); i++) {
			IntBuffer buffer = BufferUtils.createIntBuffer(1);
			GL15.glGenBuffers(buffer);
			
			vertexBufferIds.add(buffer.get(0));
		}
	}

	private void run() {
		
	}

	private void initGL() {
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
	    GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
	    GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
	    GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);

	    GL11.glShadeModel(GL11.GL_SMOOTH);

	    GL11.glEnable(GL11.GL_COLOR_MATERIAL);
	    GL11.glEnable(GL11.GL_DEPTH_TEST);

	    // set up lighting
//	    GL11.glEnable(GL11.GL_LIGHTING);
//	    GL11.glEnable(GL11.GL_LIGHT0);
//
//	    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, floatBuffer(1.0f, 1.0f, 1.0f, 1.0f));
//	    GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 25.0f);
//
//	    GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, floatBuffer(-5.0f, 5.0f, 15.0f, 0.0f));
//
//	    GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, floatBuffer(1.0f, 1.0f, 1.0f, 1.0f));
//	    GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, floatBuffer(1.0f, 1.0f, 1.0f, 1.0f));
//
//	    GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, floatBuffer(0.1f, 0.1f, 0.1f, 1.0f));

	    // set up the camera
	    GL11.glMatrixMode(GL11.GL_PROJECTION);
	    GL11.glLoadIdentity();
	    
	    GLU.gluPerspective(45.0f,((float)displayMode.getWidth()/(float)displayMode.getHeight()),0.1f,1000.0f);

	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
	    GL11.glLoadIdentity();
	        
        GL11.glClearDepth(1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
    }
	
	public FloatBuffer floatBuffer(float a, float b, float c, float d) {
	    float[] data = new float[] {a, b, c, d};
	    FloatBuffer fb = BufferUtils.createFloatBuffer(data.length);
	    fb.put(data);
	    fb.flip();
	    
	    return fb;
	}

    /**
     * Gets the system time in milliseconds.
     */
    public static long getSystemTime()
    {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }
    	 
    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
    	if (getSystemTime() - lastFPS > 1000) {
	    	Display.setTitle("FPS: " + fps);
	    	fps = 0; //reset the FPS counter
	    	lastFPS += 1000; //add one second
    	}
    	fps++;
    }
    
    public static void enterOrtho() {
		// store the current state of the renderer
		GL11.glPushAttrib(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_ENABLE_BIT);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_PROJECTION); 
		GL11.glPushMatrix();
			
		// now enter orthographic projection
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 640, 480, 0, -1, 1);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
	}

	public static void leaveOrtho() {
		// restore the state of the renderer
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		GL11.glPopAttrib();
	}
	
	private void render() {
	    // clear the display
	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		SkySphere.render();
		
		for(Chunk chunk : theWorld.chunkMap.chunkMap.values()) {
			chunk.render();
		}
	}
}
