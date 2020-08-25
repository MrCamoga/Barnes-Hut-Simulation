package com.camoga.gravity.gfx;

import static com.camoga.gravity.BarnesHut.height;
import static com.camoga.gravity.BarnesHut.width;
import static com.camoga.gravity.Simulation.dt;
import static com.camoga.gravity.Simulation.kineticEnergy;
import static com.camoga.gravity.Simulation.potentialEnergy;
import static com.camoga.gravity.Simulation.simulationTime;
import static com.camoga.gravity.Space.bodies;

import java.awt.Color;
import java.awt.Graphics;

import com.camoga.gravity.Body;
import com.camoga.gravity.Simulation;
import com.camoga.gravity.Vec3;
import com.camoga.gravity.quadtree.Node;

public class Screen {

	public static int projection = 0;
	public static boolean renderTree = false;
	public static boolean targetCoM = true;
	public static int target = 0;
	
	public void render(Graphics g) {

		Vec3 offset = null;
		if(targetCoM) offset = Simulation.node.getCenterOfMass();
		else offset = bodies.get(target).getPos();
		for(Body b : bodies) {
			b.render(g, offset);
		}
		
		//Render octree
		if(renderTree) Simulation.node.render(g, offset);
		g.setColor(Color.white);
		
		//Render text
		int xd = 250;
		g.drawString("Target's pos: " + bodies.get(target).getPos(), 20, 60);
		g.drawString("Target's mass: " + bodies.get(target).getMass(), 20, 80);
//		double targetEnergy = Simulation.computeBodyEnergy(bodies.get(target));
//		if(targetEnergy > 0) g.setColor(Color.red);
//		else g.setColor(Color.GREEN);
//		g.drawString("Target's energy: " + targetEnergy, 20, 100);
		
		g.setColor(Color.WHITE);
		
		g.drawString("Total energy: " + (potentialEnergy+kineticEnergy), width - xd, height-180);
		g.drawString("Simulation Time (years): " + simulationTime/31556926, width - xd, height-160);
		g.drawString("Barnes-Hut Algorithm theta: " + Node.theta, width - xd, height-140);
		g.drawString("Projection: " + (projection==0 ? "X-axis":(projection == 1 ? "Y-axis":"Z-axis")), width - xd, height-120);
		g.drawString("Delta-t: " + dt, width - xd, height-100);
		g.drawString("Num of bodies: " + bodies.size(), width - xd, height-80);
		
		//Axes
		g.setColor(Color.RED);
		if(projection > 0) g.drawString("X-axis", width-60, height/2);
		else g.drawString("Y-axis", width-60, height/2);
		if(projection < 2) g.drawString("Z-axis", width/2, 20);
		else g.drawString("Y-axis", width/2, 20);
	}
}