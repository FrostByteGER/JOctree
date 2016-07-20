/**
 * 
 */
package de.frostbyteger.core;

import java.util.ArrayList;
import java.util.Vector;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import de.frostbyteger.engine.Vector3f;

/**
 * @author Kevin Kuegler
 * @version 1.00
 */
public class JOC3D {
	
    /** frames per second */
    public float fps;
    /** last fps time */
    public long lastFPS;
    
    private Vector<Vector3f> blocks = new Vector<Vector3f>(0);
    private Vector<Float> blocksScale = new Vector<Float>(0);
    private Camera camera =  new Camera(0.0f, 0.0f, 0.0f);
    
    private float mouseSensitivity = 0.10f;
	
	
	
	public void start() {  
	    try {
	        Display.setDisplayMode(new DisplayMode(640,640));
	        Display.setTitle("FPS: " + fps);
	        // Enables Anti-Aliasing
	        Display.create(new PixelFormat(8,8,0,8));
	    } catch (LWJGLException e) {
	        e.printStackTrace();
	    }
	    float dx = 0.0f;
	    float dy = 0.0f;
	    GL11.glMatrixMode(GL11.GL_PROJECTION);
	    GL11.glLoadIdentity();
	    GL11.glOrtho(-30, 30, -30, 30, -100000, 100000);
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
	    //int x=0;
	    Mouse.setGrabbed(true);
	    lastFPS = getTime(); // call before loop to initialise fps timer
	    while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
	        //Display.sync(0);

	        dx = Mouse.getDX();
	        dy = Mouse.getDY();
	        camera.yaw(dx * mouseSensitivity);
	        camera.pitch(dy * mouseSensitivity);
	        camera.lookThrough();

	        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT|GL11.GL_COLOR_BUFFER_BIT);
	        //Enables backfaceculling for Non-Wireframe mode
	        //GL11.glEnable(GL11.GL_CULL_FACE);
	        GL11.glEnable(GL11.GL_DEPTH_TEST);
	        // Enables Wireframe without backfaceculling
	        GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_LINE );
	        GL11.glColor3f(1, 0, 0);
	        
	        GL11.glTranslatef(0, 0, 0);
		    for(int i  = 0;i < blocks.size();i++){
		    	
		    	System.out.println(blocks.get(i) + "-----" + blocksScale.get(i));
		    	System.out.println(i);
		    	renderCube(blocks.get(i), blocksScale.get(i));
		    }
	        //renderCube((float)((Math.sin(Math.toRadians(x++)))*660.0f), 20.0f);
	        
	        GL11.glLoadIdentity();
	        updateFPS(); // update FPS Counter
	        Display.update();
	    }

	    Display.destroy();
	}
	
	public void renderCube(Vector3f position,float scale){
		GL11.glPushMatrix();
		GL11.glTranslatef(position.getX()/2.0f, position.getY()/2.0f, position.getZ()/2.0f);
		GL11.glScalef(scale, scale, scale);
		GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor3f(0.0f, 1.0f, 0.0f);
        GL11.glVertex3f(0.5f, 0.5f, -0.5f);
        GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
        GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
        GL11.glVertex3f(0.5f, 0.5f, 0.5f);
        GL11.glColor3f(1.0f, 0.5f, 0.0f);
        GL11.glVertex3f(0.5f, -0.5f, 0.5f);
        GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
        GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
        GL11.glVertex3f(0.5f, -0.5f, -0.5f);
        GL11.glColor3f(1.0f, 0.0f, 0.0f); 
        GL11.glVertex3f(0.5f, 0.5f, 0.5f); 
        GL11.glVertex3f(-0.5f, 0.5f, 0.5f); 
        GL11.glVertex3f(-0.5f, -0.5f, 0.5f); 
        GL11.glVertex3f(0.5f, -0.5f, 0.5f);
        GL11.glColor3f(1.0f, 1.0f, 0.0f);
        GL11.glVertex3f(0.5f, -0.5f, -0.5f); 
        GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
        GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
        GL11.glVertex3f(0.5f, 0.5f, -0.5f);
        GL11.glColor3f(0.0f, 0.0f, 1.0f);
        GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
        GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
        GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
        GL11.glVertex3f(-0.5f, -0.5f, 0.5f); 
        GL11.glColor3f(1.0f, 0.0f, 1.0f);
        GL11.glVertex3f(0.5f, 0.5f, -0.5f);
        GL11.glVertex3f(0.5f, 0.5f, 0.5f);
        GL11.glVertex3f(0.5f, -0.5f, 0.5f); 
        GL11.glVertex3f(0.5f, -0.5f, -0.5f); 
        GL11.glEnd();
        GL11.glPopMatrix();
	}

    /**
     * Get the accurate system time
     * 
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
	
    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0.0f;
            lastFPS += 1000;
        }
        fps++;
    }

	/**
	 * @return the blocks
	 */
	public Vector<Vector3f> getBlocks() {
		return blocks;
	}

	/**
	 * @param blocks the blocks to set
	 */
	public void setBlocks(Vector<Vector3f> blocks) {
		this.blocks = blocks;
	}

	/**
	 * @return the blocksScale
	 */
	public Vector<Float> getBlocksScale() {
		return blocksScale;
	}

	/**
	 * @param blocksScale the blocksScale to set
	 */
	public void setBlocksScale(Vector<Float> blocksScale) {
		this.blocksScale = blocksScale;
	}

}
