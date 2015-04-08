/**
 * 
 */
package de.frostbyteger.engine;

import de.frostbyteger.core.JOC3D;

/**
 * @author Kevin Kuegler
 * @version 1.00
 */
public class OctreeController {

	/** Length of the Octree-Array */
	private final int octreeLength = 8;
	/** Depth of the whole Octree */
	private final int depth = 1;
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
		long startTime = System.nanoTime();
		first = new OctreeNode(this, null, 0, 16, new Vector3f(0.0f, 0.0f, 0.0f));
		long time = System.nanoTime() - startTime;
		System.out.println(">>>>>>>>>>>>>>>>>>>>>FINISHED<<<<<<<<<<<<<<<<<<<<<");
		System.out.println("Time elapsed: " + time / 1000000000.0 + "s");
		System.out.println("Total Nodes: " + nodes);
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
	
	/**
	 * Returns the Depth of this Octree
	 * @return the Depth
	 */
	public int getDepth(){
		return depth;
	}
	
	/**
	 * Returns the Length of the Octree
	 * @return The OctreeLength
	 */
	public int getOctreeLength(){
		return octreeLength;
	}
	
	/**
	 * Increases the Count of Octree-Nodes
	 */
	public void increaseNode(){
		nodes++;
	}
	
	/**
	 * Decreases the count of Octree-Nodes
	 */
	public void decreaseNode(){
		nodes--;
	}
	
	/**
	 * Returns the LWJGL-Object
	 * @return The LWJGL-Object
	 */
	public JOC3D getJoc(){
		return joc;
	}

}
