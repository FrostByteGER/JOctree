/**
 * 
 */
package de.frostbyteger.engine;

/**
 * @author Kevin Kuegler
 * @version 1.00
 */
public class Vector3f {
	
	private float x;
	private float y;
	private float z;

	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @return the z
	 */
	public float getZ() {
		return z;
	}

	/**
	 * @param z the z to set
	 */
	public void setZ(float z) {
		this.z = z;
	}
	
	/**
	 * 
	 * @return
	 */
	public float[] returnVector(){
		return new float[]{x,y,z};
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setVector(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public String toString() {
		return "X: " + x + " Y: " + y + " Z: " + z;
	}
	
	

}
