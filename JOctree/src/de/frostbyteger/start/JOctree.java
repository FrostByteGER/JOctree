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
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		JOC3D joc = new JOC3D();
		OctreeController oc = new OctreeController(joc);
		int count = oc.calculateEstimatedCount();
		long startTime = System.nanoTime();
		oc.start();
		while(true){
			Thread.sleep(1L);
			if(count == oc.getNodeCount()){
				
				long time = System.nanoTime() - startTime;
				
				System.out.println(">>>>>>>>>>>>>>>>>>>>>FINISHED<<<<<<<<<<<<<<<<<<<<<");
				System.out.println("Time elapsed: " + time / 1000000000.0 + "s");
				System.out.println(count + " | " + oc.getNodeCount());
				break;
			}
		}
		joc.start();
	}

}
