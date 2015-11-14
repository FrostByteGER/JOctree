/**
 * 
 */
package de.frostbyteger.engine;

import java.util.Arrays;

/**
 * @author Kevin Kuegler
 * @version 1.00
 */
public class OctreeNode extends Thread{
	
	private OctreeController root;
	private OctreeNode parent;
	private OctreeNode[] childs;
	private Object[] objects;
	
	private int level;
	private float length;
	private Vector3f position;
	
	/**
	 * @param parent
	 * @param childs
	 * @param objects
	 * @param length
	 * @param position
	 */
	public OctreeNode(OctreeController root, OctreeNode parent,int level, float length, Vector3f position) {
		this.root = root;
		this.level = level;
		this.parent = parent;
		this.childs = new OctreeNode[root.OCTREE_LENGTH];
		objects = new Object[]{};
		this.length = length;
		this.position = position;
		//System.out.println("Position: " + position.toString() + " Dimension: " + length);
		root.getJoc().getBlocks().add(position);
		root.getJoc().getBlocksScale().add(length);
		root.increaseNode();
		
		
		setDaemon(true);
		start();
	}
	
	public void subdivide(){
		float length = this.length/2.0f;
		/*
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		childs[0] = new OctreeNode(root, this, level + 1, length, new Vector3f(position.getX() + length, position.getY() + length, position.getZ() + length)); //+++
		childs[1] = new OctreeNode(root, this, level + 1, length, new Vector3f(position.getX() - length, position.getY() + length, position.getZ() + length)); //-++
		childs[2] = new OctreeNode(root, this, level + 1, length, new Vector3f(position.getX() - length, position.getY() - length, position.getZ() + length)); //--+
		childs[3] = new OctreeNode(root, this, level + 1, length, new Vector3f(position.getX() + length, position.getY() - length, position.getZ() + length)); //+-+
		childs[4] = new OctreeNode(root, this, level + 1, length, new Vector3f(position.getX() + length, position.getY() + length, position.getZ() - length)); //++-
		childs[5] = new OctreeNode(root, this, level + 1, length, new Vector3f(position.getX() - length, position.getY() + length, position.getZ() - length)); //-+-
		childs[6] = new OctreeNode(root, this, level + 1, length, new Vector3f(position.getX() - length, position.getY() - length, position.getZ() - length)); //---
		childs[7] = new OctreeNode(root, this, level + 1, length, new Vector3f(position.getX() + length, position.getY() - length, position.getZ() - length)); //+--
	}
	
	public void clear(){
		Arrays.fill(objects,null);
		for(int i = 0;i < childs.length;i++){
			if(childs[i] != null){
				childs[i].clear();
				childs[i].root.getJoc().getBlocks().remove(childs[i].position);
				childs[i].root.getJoc().getBlocksScale().remove(childs[i].length);
				childs[i] = null;
				root.decreaseNode();
			}
		}
	}
	
	/*
	 * Determine which node the object belongs to. -1 means
	 * object cannot completely fit within a child node and is part
	 * of the parent node
	 * TODO: Needs to be fixed
	 */
	 private int getIndex(Object obj) {
	   int index = -1;
	   double verticalMidpoint = bounds.getX() + length;
	   double horizontalMidpoint = bounds.getY() + length;
	 
	   // Object can completely fit within the top quadrants
	   boolean topQuadrant = (pRect.getY() < horizontalMidpoint && pRect.getY() + pRect.getHeight() < horizontalMidpoint);
	   // Object can completely fit within the bottom quadrants
	   boolean bottomQuadrant = (pRect.getY() > horizontalMidpoint);
	 
	   // Object can completely fit within the left quadrants
	   if (pRect.getX() < verticalMidpoint && pRect.getX() + pRect.getWidth() < verticalMidpoint) {
	      if (topQuadrant) {
	        index = 1;
	      }
	      else if (bottomQuadrant) {
	        index = 2;
	      }
	    }
	    // Object can completely fit within the right quadrants
	    else if (pRect.getX() > verticalMidpoint) {
	     if (topQuadrant) {
	       index = 0;
	     }
	     else if (bottomQuadrant) {
	       index = 3;
	     }
	   }
	 
	   return index;
	 }
	 
	 /*
	  * Insert the object into the quadtree. If the node exceeds the capacity, it
	  * will split and add all objects to their corresponding nodes.
	  */
	 public void insert(Object obj) {
		 if (childs[0] != null) {
			 int index = getIndex(obj);

			 if (index != -1) {
				 childs[index].insert(obj);

				 return;
			 }
		 }

		 objects[0] = obj;

		 if (objects.length > root.MAX_OBJECTS && level < root.DEPTH) {
			 if (childs[0] == null) {
				 subdivide();
			 }

			 int i = 0;
			 while (i < objects.length) {
				 int index = getIndex(objects[i]);
				 if (index != -1) {
					 childs[index].insert(objects[i] = null);
				 } else {
					 i++;
				 }
			 }
		 }
	 }


	@Override
	public void run() {
		if(level < root.DEPTH){
			subdivide();
		}
	}

	/**
	 * @return the root
	 */
	public OctreeController getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(OctreeController root) {
		this.root = root;
	}

	/**
	 * @return the parent
	 */
	public OctreeNode getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(OctreeNode parent) {
		this.parent = parent;
	}

	/**
	 * @return the childs
	 */
	public OctreeNode[] getChilds() {
		return childs;
	}

	/**
	 * @param childs the childs to set
	 */
	public void setChilds(OctreeNode[] childs) {
		this.childs = childs;
	}

	/**
	 * @return the objects
	 */
	public Object[] getObjects() {
		return objects;
	}

	/**
	 * @param objects the objects to set
	 */
	public void setObjects(Object[] objects) {
		this.objects = objects;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the length
	 */
	public float getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(float length) {
		this.length = length;
	}

	/**
	 * @return the position
	 */
	public Vector3f getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector3f position) {
		this.position = position;
	}

}
