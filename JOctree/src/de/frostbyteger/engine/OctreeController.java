/**
 * 
 */
package de.frostbyteger.engine;

import de.frostbyteger.core.JOC3D;

/**
 * @author Kevin Kuegler
 * @version 1.00
 */
public class OctreeController extends Thread{

	/** Length of the Octree-Array */
	public final int OCTREE_LENGTH = 8;
	/** Depth of the whole Octree */
	public final int DEPTH = 1;
	/***/
	public final int MAX_OBJECTS = 3;
	/** The first Octree-Node. Acts as a fake-Root */
	private OctreeNode first;
	/** The total count of Octree-Nodes */
	private int nodes;
	/** Reference to the LWJGL Object */
	private JOC3D joc;
	/**
	 * Default Constructor
	 * @param joc The LWJGL-Object
	 */
	public OctreeController(JOC3D joc) {
		this.joc = joc;
		nodes = 0;
		//start();
	}
	
	@Override
	public void run() {
		
		first = new OctreeNode(this, null, 0, 16, new Vector3f(0.0f, 0.0f, 0.0f));
	}
	
	/**
	 * Clears the whole Octree
	 */
	public void clear(){
		first.clear();
		getJoc().getBlocks().remove(first.getPosition());
		getJoc().getBlocksScale().remove(first.getLength());
		first = null;
		decreaseNode();
	}
	
	public int calculateEstimatedCount(){
		int count = 0;
		for(int i = 0;i <= DEPTH;i++){
			System.out.println(i);
			count += Math.pow(OCTREE_LENGTH, i);
		}
		return count;
	}
	
	/**
	 * Increases the Count of Octree-Nodes
	 */
	public synchronized void increaseNode(){
		nodes++;
	}
	
	/**
	 * Decreases the count of Octree-Nodes
	 */
	public synchronized void decreaseNode(){
		nodes--;
	}
	
	public int getNodeCount(){
		return nodes;
	}
	
	/**
	 * Returns the LWJGL-Object
	 * @return The LWJGL-Object
	 */
	public JOC3D getJoc(){
		return joc;
	}

}
