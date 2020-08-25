package com.camoga.gravity4D;

import java.awt.Color;
import java.awt.Graphics;

public class Body {

	public static final double G = 6.67408*Math.pow(10, -11);
	
	private double mass;
	private double radius;
	private Vec4 velocity, pos;
	
	public String name;
	private Color color;
	
	public Body(double mass, double radius, Vec4 velocity, Vec4 pos, Color color, String name) {
		this.mass = mass;
		this.radius = radius;
		this.velocity = velocity;
		this.pos = pos;
		this.color = color;
		this.name = name;
	}
	
	public void tick(double dt) {
		pos.add(velocity.w*dt, velocity.x*dt, velocity.y*dt, velocity.z*dt);
	}
	
	public void atract(Body b, double dt) {
//		System.out.println(name + ": attract");
		double distance = Vec4.getDistance(b.pos, pos);
		Main.steps++;
		if(distance <= b.radius+radius) Main.main.merge(this, b);
		double acceleration = -G*b.mass/Math.pow(distance, 3);
		double deltaV = acceleration*dt;
		velocity.add((pos.w-b.pos.w)*deltaV, (pos.x-b.pos.x)*deltaV, (pos.y-b.pos.y)*deltaV, (pos.z-b.pos.z)*deltaV);
	}
	
	public void attract(double mass, Vec4 centerOfMass, double dt) {
		Main.steps++;
		double distance = Vec4.getDistance(pos, centerOfMass);
		double acceleration = -G*(mass)/Math.pow(distance, 3);
		double deltaV = acceleration*dt;
		velocity.add((pos.w-centerOfMass.w)*deltaV, (pos.x-centerOfMass.x)*deltaV, (pos.y-centerOfMass.y)*deltaV, (pos.z-centerOfMass.z)*deltaV);
	}
	
	//TODO center at any body
	public void render(Graphics g, Vec4 offset) {
		g.setColor(color);
		int size = (int) (radius/Main.SCALE);
		if(size < 2) size = 2;
		//top-down projection
		switch(Main.projection) {
			case 0:
				g.fillOval((int)((pos.y-offset.y)/(double)Main.SCALE) + Main.width/2 - size/2, (int)((pos.z-offset.z)/(double)Main.SCALE) + Main.height/2 - size/2, size, size);
				g.drawString(name, (int)((pos.y-offset.y)/Main.SCALE)+Main.width/2-15, (int)((pos.z-offset.z)/Main.SCALE)+Main.height/2-size/2);
				break;
			case 1:
				g.fillOval((int)((pos.x-offset.x)/(double)Main.SCALE) + Main.width/2 - size/2, (int)((pos.z-offset.z)/(double)Main.SCALE) + Main.height/2 - size/2, size, size);
				g.drawString(name, (int)((pos.x-offset.x)/Main.SCALE)+Main.width/2-15, (int)((pos.z-offset.z)/Main.SCALE)+Main.height/2-size/2);
				break;
			case 2:
				g.fillOval((int)((pos.x-offset.x)/(double)Main.SCALE) + Main.width/2 - size/2, (int)((pos.y-offset.y)/(double)Main.SCALE) + Main.height/2 - size/2, size, size);
				g.drawString(name, (int)((pos.x-offset.x)/Main.SCALE)+Main.width/2-15, (int)((pos.y-offset.y)/Main.SCALE)+Main.height/2-size/2);
				break;
			case 3:
				g.fillOval((int)((pos.w-offset.w)/(double)Main.SCALE) + Main.width/2 - size/2, (int)((pos.y-offset.y)/(double)Main.SCALE) + Main.height/2 - size/2, size, size);
				g.drawString(name, (int)((pos.w-offset.w)/Main.SCALE)+Main.width/2-15, (int)((pos.y-offset.y)/Main.SCALE)+Main.height/2-size/2);
				break;
			case 4:
				g.fillOval((int)((pos.w-offset.w)/(double)Main.SCALE) + Main.width/2 - size/2, (int)((pos.z-offset.z)/(double)Main.SCALE) + Main.height/2 - size/2, size, size);
				g.drawString(name, (int)((pos.w-offset.w)/Main.SCALE)+Main.width/2-15, (int)((pos.z-offset.z)/Main.SCALE)+Main.height/2-size/2);
				break;
			case 5:
				g.fillOval((int)((pos.w-offset.w)/(double)Main.SCALE) + Main.width/2 - size/2, (int)((pos.x-offset.x)/(double)Main.SCALE) + Main.height/2 - size/2, size, size);
				g.drawString(name, (int)((pos.w-offset.w)/Main.SCALE)+Main.width/2-15, (int)((pos.x-offset.x)/Main.SCALE)+Main.height/2-size/2);
				break;
		}
	}

	public Vec4 getPos() {
		return pos;
	}

	public Vec4 getVel() {
		return velocity;
	}

	public double getMass() {
		return mass;
	}

	public double getRadius() {
		return radius;
	}

}
