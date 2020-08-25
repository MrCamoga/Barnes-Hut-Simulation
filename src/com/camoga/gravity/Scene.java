package com.camoga.gravity;

import static com.camoga.gravity.Simulation.G;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Scene {
	public static ArrayList<Body> createGalaxy(int num) {
		ArrayList<Body> bodies = new ArrayList<>();
		bodies.add(new Body(5.97e37, 6.317e6, new Vec3(), new Vec3(), Color.yellow, "Sun"));
		Random r = new Random();
		for(int i = 0; i < num; i++) {
			double radius = 4.6e12*r.nextDouble();
			double velocity = Math.sqrt(G*9e36/radius);
			bodies.add(new Body(4e30, 6.96e8, 
					new Vec3(-velocity*Math.sin(i*2*Math.PI/num), velocity*Math.cos(i*2*Math.PI/num), (Math.random()-0.5)*velocity/20), 
					new Vec3(radius*Math.cos(i*2*Math.PI/num), radius*Math.sin(i*2*Math.PI/num), (Math.random()-0.5)*radius/2.5), 
					Color.WHITE, ""));
		}
		return bodies;
	}
//	createCloud(100, 6e11, 7e28, 16000);
	
	public static ArrayList<Body> createCloud(int numOfParticles, double radius, double particlesMass, double velocities) {
		ArrayList<Body> bodies = new ArrayList<>();
		for(int i = 0; i < numOfParticles; i++) {
			double theta = Math.random()*2*3.1415926;
			double phi = Math.random()*3.1415926;
			double x = Math.cos(theta);
			double y = Math.sin(theta)*Math.cos(phi);
			double z = Math.sin(theta)*Math.sin(phi);
			
			Vec3 pos = new Vec3(x,y,z).mul(Math.random()*radius);
			
			double v = Math.random()*velocities;
			Vec3 velocity = new Vec3(-v*Math.sin(theta),v*Math.cos(theta)*Math.sin(phi),v*Math.cos(theta)*Math.cos(phi));
			
			bodies.add(new Body(particlesMass, Math.cbrt(particlesMass), 
					velocity, 
					pos, Color.WHITE, ""));
		}
		
		return bodies;
	}
	
	public static ArrayList<Body> createSolarSystem(int num) {
		ArrayList<Body> bodies = new ArrayList<>();
		bodies.add(new Body(1.988e30, 6e6, new Vec3(), new Vec3(), Color.YELLOW, "Sun"));
		bodies.add(new Body(1.89819e27, 69.9e6, new Vec3(0, 12440, 0), new Vec3(816.62e9,0,0), Color.ORANGE, "Jupiter"));
		
		for(int i = 0; i < num; i++) {
			double angle = Math.PI/3+(2*Math.random()-1)/10;
			angle *= Math.random() < 0.5 ? -1:1;
			double radius = 8.1662e11 + (2*Math.random()-1)*3e10;
			bodies.add(new Body(3.3e21/num, Math.cbrt(3.3e21/num*3/28), new Vec3(-12440*Math.sin(angle),12440*Math.cos(angle),0), new Vec3(radius*Math.cos(angle), radius*Math.sin(angle),(2*Math.random()-1)*2e10), new Color((int) (Math.random()*0xffffff)), ""));
		}
//		for(int j = 0; j < 300; j++) {
//			double angle = Math.PI;
//			angle *= Math.random() < 0.5 ? -1:1;
//			double radius = 7.8662e11 + (2*Math.random()-1)*0.3e10;
//			bodies.add(new Body(3.3e21/num, Math.cbrt(3.3e21/num*3/28), new Vec3(-12440*Math.sin(angle),12440*Math.cos(angle),0), new Vec3(radius*Math.cos(angle), radius*Math.sin(angle),(2*Math.random()-1)*1e10), Color.LIGHT_GRAY, ""));
//		
//		}
		return bodies;
	}

}
