package com.camoga.gravity4D;

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
		for(Body b : bodies) {
			x = Math.abs(b.getPos().x) > x ? Math.abs(b.getPos().x):x; 
		}
		return x;
	}
	
	public static double getAbs(ArrayList<Body> bodies) {
		double d = 0;
//		Body furthest = null;
		for(Body b : bodies) {
			double temp = d;
			d = Math.abs(b.getPos().x) > d ? Math.abs(b.getPos().x):d;
			d = Math.abs(b.getPos().y) > d ? Math.abs(b.getPos().y):d;
//			if(d!=temp) furthest = b;
		}
		
//		double kineticEnergy = 0;
//		double potentialEnergy = 0;
//		
//		if(BarnesHut.node!=null) {
//			double distance = Vec3.getDistance(furthest.getPos(), BarnesHut.node.getCenterOfMass());
//			potentialEnergy = -Body.G*BarnesHut.node.getMass()*furthest.getMass()/distance;			
//		}
////		for(Body b2 : bodies) {
////			if(b2.equals(furthest)) continue;
////			if(distance != 0)
////			potentialEnergy += -Body.G*b2.getMass()*furthest.getMass()/distance;
////		}
//		kineticEnergy = 0.5*furthest.getMass()*furthest.getVel().mod();
//		
//		System.out.println("PE: " + (potentialEnergy) + ", KE: " + kineticEnergy);
		
		return d;
	}
}