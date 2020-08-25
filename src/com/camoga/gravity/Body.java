package com.camoga.gravity;

import static com.camoga.gravity.Simulation.G;

import java.awt.Color;
import java.awt.Graphics;

import com.camoga.gravity.gfx.Screen;

public class Body {
	
	private double mass;
	private double radius;
	private Vec3 velocity, pos;
	
	public String name;
	private Color color;
	
	public Body(double mass, double radius, Vec3 velocity, Vec3 pos, Color color, String name) {
		this.mass = mass;
		this.radius = radius;
		this.velocity = velocity;
		this.pos = pos;
		this.color = color;
		this.name = name;
	}
	
	public void tick(double dt) {
		pos.add(velocity.x*dt, velocity.y*dt, velocity.z*dt);
	}
	
	public void atract(Body b, double dt) {
//		System.out.println(name + ": attract");
		double distance = Vec3.getDistance(b.pos, pos);
//		BarnesHut.steps++;
		if(distance <= b.radius+radius) Space.merge(this, b);
		double acceleration = -G*b.mass/Math.pow(distance, 3);
		double deltaV = acceleration*dt;
		velocity.add((pos.x-b.pos.x)*deltaV, (pos.y-b.pos.y)*deltaV, (pos.z-b.pos.z)*deltaV);
	}
	
	public void attract(double mass, Vec3 centerOfMass, double dt, double distance) {
//		BarnesHut.steps++;
		double acceleration = -G*(mass)/Math.pow(distance, 3);
		double deltaV = acceleration*dt;
		velocity.add((pos.x-centerOfMass.x)*deltaV, (pos.y-centerOfMass.y)*deltaV, (pos.z-centerOfMass.z)*deltaV);
	}
	
	//TODO center at any body
	public void render(Graphics g, Vec3 offset) {
		g.setColor(color);
		int size = (int) (radius/BarnesHut.SCALE);
		if(size < 4) size = 4;
		//top-down projection
		switch(Screen.projection) {
			case 0:
				g.fillOval((int)((pos.y-offset.y)/(double)BarnesHut.SCALE) + BarnesHut.width/2 - size/2, (int)((pos.z-offset.z)/(double)BarnesHut.SCALE) + BarnesHut.height/2 - size/2, size, size);
				g.drawString(name, (int)((pos.y-offset.y)/BarnesHut.SCALE)+BarnesHut.width/2-15, (int)((pos.z-offset.z)/BarnesHut.SCALE)+BarnesHut.height/2-size/2);
				break;
			case 1:
				g.fillOval((int)((pos.x-offset.x)/(double)BarnesHut.SCALE) + BarnesHut.width/2 - size/2, (int)((pos.z-offset.z)/(double)BarnesHut.SCALE) + BarnesHut.height/2 - size/2, size, size);
				g.drawString(name, (int)((pos.x-offset.x)/BarnesHut.SCALE)+BarnesHut.width/2-15, (int)((pos.z-offset.z)/BarnesHut.SCALE)+BarnesHut.height/2-size/2);
				break;
			case 2:
				g.fillOval((int)((pos.x-offset.x)/(double)BarnesHut.SCALE) + BarnesHut.width/2 - size/2, (int)((pos.y-offset.y)/(double)BarnesHut.SCALE) + BarnesHut.height/2 - size/2, size, size);
				g.drawString(name, (int)((pos.x-offset.x)/BarnesHut.SCALE)+BarnesHut.width/2-15, (int)((pos.y-offset.y)/BarnesHut.SCALE)+BarnesHut.height/2-size/2);
				break;
		}
	}

	public Vec3 getPos() {
		return pos;
	}

	public Vec3 getVel() {
		return velocity;
	}

	public double getMass() {
		return mass;
	}

	public double getRadius() {
		return radius;
	}
}
