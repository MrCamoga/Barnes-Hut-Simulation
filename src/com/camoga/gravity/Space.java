package com.camoga.gravity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Space {
	
	public Space() {
		init();
	}
	
	public static ArrayList<Body> bodies = new ArrayList<>();
	//FIXME non-square trees render improperly

	public void init() {
//		bodies.addAll(Scene.createGalaxy(800));
//		bodies.addAll(Scene.createCloud(1000, 9e12, 1e30, 400));
		bodies.addAll(Scene.createSolarSystem(1000));
	}
	
	public static void merge(Body b, Body b2) {
		if(b.equals(b2) || (!bodies.contains(b) || !bodies.contains(b2))) return;
		bodies.remove(b);
		bodies.remove(b2);
		double mass = b.getMass()+b2.getMass();
		Vec3 velocity = Vec3.mul(b.getVel(), b.getMass()).add(Vec3.mul(b2.getVel(), b2.getMass())).div(mass);
		//TODO
		bodies.add(new Body(mass, Math.cbrt(Math.pow(b.getRadius(),3) + Math.pow(b2.getRadius(),3)), velocity, Vec3.mul(b.getPos(), b.getMass()).add(Vec3.mul(b2.getPos(), b2.getMass())).div(mass) ,new Color(new Random().nextInt(0x1000000)), bodies.size()+""));
	}
}