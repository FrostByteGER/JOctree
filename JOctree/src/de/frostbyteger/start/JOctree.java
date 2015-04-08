/**
 * 
 */
package de.frostbyteger.start;

import de.frostbyteger.core.JOC3D;
import de.frostbyteger.engine.OctreeController;

/**
 * @author Kevin Kuegler
 * @version 1.00
 */
public class JOctree {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JOC3D joc = new JOC3D();
		new OctreeController(joc);
		joc.start();
	}

}
