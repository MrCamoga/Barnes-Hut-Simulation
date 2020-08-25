package com.camoga.gravity;

import java.util.ArrayList;

public class Utils {
	
	public static double getMaxX(ArrayList<Body> bodies) {
		double x = 0;
		for(Body b : bodies) {
			x = b.getPos().x > x ? b.getPos().x:x; 
		}
		return x;
	}
	
	public static double getMinX(ArrayList<Body> bodies) {
		double x = 0;
		for(Body b : bodies) {
			x = b.getPos().x < x ? b.getPos().x:x; 
		}
		return x;
	}
	
	public static double getMaxY(ArrayList<Body> bodies) {
		double y = 0;
		for(Body b : bodies) {
			y = b.getPos().y > y ? b.getPos().y:y; 
		}
		return y;
	}
	
	public static double getMinY(ArrayList<Body> bodies) {
		double y = 0;
		for(Body b : bodies) {
			y = b.getPos().y < y ? b.getPos().y:y; 
		}
		return y;
	}
	
	public static double getAbsY(ArrayList<Body> bodies) {
		double y = 0;
		for(Body b : bodies) {
			y = Math.abs(b.getPos().y) > y ? Math.abs(b.getPos().y):y; 
		}
		return y;
	}
	
	public static double getAbsX(ArrayList<Body> bodies) {
		double x = 0;
//		Body furthest = null;
		for(Body b : bodies) {
//			double temp = x;
			x = Math.abs(b.getPos().x) > x ? Math.abs(b.getPos().x):x; 
//			if(x!=temp) furthest = b;
		}
//		double kineticEnergy = 0;
//		double potentialEnergy = 0;
//		
//		if(Simulation.node!=null) {
//			double distance = Vec3.getDistance(furthest.getPos(), Simulation.node.getCenterOfMass());
//			potentialEnergy = -0.5*Simulation.G*Simulation.node.getMass()*furthest.getMass()/distance;			
//		}
////		for(Body b2 : bodies) {
////			if(b2.equals(furthest)) continue;
////			if(distance != 0)
////			potentialEnergy += -Body.G*b2.getMass()*furthest.getMass()/distance;
////		}
//		kineticEnergy = 0.5*furthest.getMass()*furthest.getVel().mod();
//		
//		System.out.println("PE: " + (potentialEnergy) + ", KE: " + kineticEnergy);
		return x;
	}
	
	public static double getAbsZ(ArrayList<Body> bodies) {
		double z = 0;
		for(Body b : bodies) {
			z = Math.abs(b.getPos().z) > z ? Math.abs(b.getPos().z):z; 
		}
		return z;
	}
	
	public static double getAbs(ArrayList<Body> bodies) {
		double d = 0;
		for(Body b : bodies) {
			d = Math.abs(b.getPos().x) > d ? Math.abs(b.getPos().x):d;
			d = Math.abs(b.getPos().y) > d ? Math.abs(b.getPos().y):d;
		}
		
		
		return d;
	}
}