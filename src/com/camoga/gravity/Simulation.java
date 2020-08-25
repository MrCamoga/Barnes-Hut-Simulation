package com.camoga.gravity;

import static com.camoga.gravity.Space.bodies;

import com.camoga.gravity.quadtree.Node;

public class Simulation {
	
	public static Space space;

	public static Node node;
	
	public static double dt = 0.1;
	public static double simulationTime = 0;

	public static final double G = 6.67408*Math.pow(10, -11);
	
	public Simulation() {
		space = new Space();
	}
	
	/**
	 * @param alg  0 = bruteforce; 1 = barnes-hut
	 */
	public void tick(int alg) {
		simulationTime += dt;
		steps = 0;
		
		updateNodes();
		
		if(alg==0) bruteforce();
		else barnesHut();
		System.out.println("Calculations: " + Node.calculations);
		Node.calculations = 0;
		for(int i = 0; i < bodies.size(); i++) {
			bodies.get(i).tick(dt);
		}
		
		computeEnergy();
	}
	
	public void barnesHut() {
		for(int i = 0; i < bodies.size(); i++) {
//			if(i%500==0) System.out.println(i);
			node.attract(bodies.get(i), dt);
		}
	}
	
	static double steps;
	public void bruteforce() {
		if(bodies.size() == 1) return;
		for(int i = 0; i < bodies.size(); i++) {
			for(int j = 0; j < bodies.size(); j++) {
				if(i==j) continue;				
				if(bodies.size() > i && bodies.size() > j)
				bodies.get(i).atract(bodies.get(j), dt);
			}
		}
	}
	
	public static double potentialEnergy = 0;
	public static double kineticEnergy = 0;
	
	public void computeEnergy() {
		potentialEnergy = 0;
		kineticEnergy = 0;
		for(int i = 0; i < bodies.size(); i++) {
			for(int j = i; j < bodies.size(); j++) {
				double distance = Vec3.getDistance(bodies.get(i).getPos(), bodies.get(j).getPos());
				if(distance == 0) continue;
				potentialEnergy += -0.5*G*bodies.get(i).getMass()*bodies.get(j).getMass()/distance;
			}
			kineticEnergy += 0.5*bodies.get(i).getMass()*bodies.get(i).getVel().mod();
		}
	}
	
	public static double computeBodyEnergy(Body body) {
		double PE = 0, KE = 0;
		for(Body b : bodies) {
			if(b.equals(body)) continue;
			double distance = Vec3.getDistance(body.getPos(), b.getPos());
			if(distance == 0) continue;
			PE += -0.5*G*body.getMass()*b.getMass()/distance;
		}
		KE = 0.5*body.getMass()*body.getVel().mod();
		return KE + PE;
	}
	
	public void updateNodes() {
		node = new Node(Utils.getAbsX(bodies)*2, Utils.getAbsY(bodies)*2, Utils.getAbsZ(bodies)*2, 0, 0, 0, bodies, 0);
	}
}