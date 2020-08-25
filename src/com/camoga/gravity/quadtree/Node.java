package com.camoga.gravity.quadtree;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.camoga.gravity.BarnesHut;
import com.camoga.gravity.Body;
import com.camoga.gravity.Vec3;
import com.camoga.gravity.gfx.Screen;

public class Node {

	public static double theta = 0.99;
	private int order;
	
	public static int calculations = 0;

	private Node[] childs;

	private ArrayList<Body> bodies;

	private Vec3 centerOfMass;
	private double mass;
	private double width, depth, height, x, y, z;

	public Node(double width, double depth, double height, double x, double y, double z, ArrayList<Body> bodies, int order) {
		this.width = width;
		this.depth = depth;
		this.height = height;
		this.x = x;
		this.y = y;
		this.z = z;
		this.order = order;
		centerOfMass = new Vec3();
		if (bodies.size() > 1) {
			this.bodies = bodies;
			childs = new Node[8];

			ArrayList<Body> childBodies = new ArrayList<>();
			ArrayList<Body> childBodies2 = new ArrayList<>();
			ArrayList<Body> childBodies3 = new ArrayList<>();
			ArrayList<Body> childBodies4 = new ArrayList<>();
			ArrayList<Body> childBodies5 = new ArrayList<>();
			ArrayList<Body> childBodies6 = new ArrayList<>();
			ArrayList<Body> childBodies7 = new ArrayList<>();
			ArrayList<Body> childBodies8 = new ArrayList<>();

			for (Body b : bodies) {
					   if (b.getPos().x < x && b.getPos().y < y && b.getPos().z >= z) {
					childBodies.add(b);
				} else if (b.getPos().x >= x && b.getPos().y < y && b.getPos().z >= z) {
					childBodies2.add(b);
				} else if (b.getPos().x >= x && b.getPos().y >= y && b.getPos().z >= z) {
					childBodies3.add(b);
				} else if (b.getPos().x < x && b.getPos().y >= y && b.getPos().z >= z) {
					childBodies4.add(b);
				} else if (b.getPos().x < x && b.getPos().y >= y && b.getPos().z < z) {
					childBodies5.add(b);
				} else if (b.getPos().x >= x && b.getPos().y >= y && b.getPos().z < z) {
					childBodies6.add(b);
				} else if (b.getPos().x >= x && b.getPos().y < y && b.getPos().z < z) {
					childBodies7.add(b);
				} else if (b.getPos().x < x && b.getPos().y < y && b.getPos().z < z) {
					childBodies8.add(b);
				} else {
					 System.err.println("Body outside quadtree");
				}
			}
			double newW = width/2;
			double newD = depth/2;
			double newH = height/2;
			double centerW = width/4;
			double centerD = depth/4;
			double centerH = height/4;
			
			childs[0] = new Node(newW, newD, newH, x - centerW, y - centerD, z + centerH, childBodies, order + 1);
			childs[1] = new Node(newW, newD, newH, x + centerW, y - centerD, z + centerH, childBodies2, order + 1);
			childs[2] = new Node(newW, newD, newH, x + centerW, y + centerD, z + centerH, childBodies3, order + 1);
			childs[3] = new Node(newW, newD, newH, x - centerW, y + centerD, z + centerH, childBodies4, order + 1);
			childs[4] = new Node(newW, newD, newH, x - centerW, y + centerD, z - centerH, childBodies5, order + 1);
			childs[5] = new Node(newW, newD, newH, x + centerW, y + centerD, z - centerH, childBodies6, order + 1);
			childs[6] = new Node(newW, newD, newH, x + centerW, y - centerD, z - centerH, childBodies7, order + 1);
			childs[7] = new Node(newW, newD, newH, x - centerW, y - centerD, z - centerH, childBodies8, order + 1);

			for (Node child : childs) {
//				if (isEmpty())
//					continue;
				mass += child.mass;
				centerOfMass.add(Vec3.mul(child.centerOfMass, child.mass));
				// System.out.println(order + ": " + centerOfMass);
			}
			centerOfMass.div(mass);
		} else if (bodies.size() == 1) {
			this.bodies = bodies;
			centerOfMass = bodies.get(0).getPos();
			mass = bodies.get(0).getMass();
		}
	}

	public void render(Graphics g, Vec3 offset) {
//		if(bodies==null||bodies.size()==0) return;
		g.setColor(Color.red);
		double width = 0;
		double height = 0;
		switch (Screen.projection) {
			case 0:
				width = this.depth / BarnesHut.SCALE;
				height = this.height / BarnesHut.SCALE;
				g.drawRect((int) ((y-offset.y) / BarnesHut.SCALE + BarnesHut.width / 2 - width / 2),
						(int) ((z-offset.z) / BarnesHut.SCALE + BarnesHut.height / 2 - height / 2), (int)width, (int)height);
				break;

			case 1:
				width = this.width / BarnesHut.SCALE;
				height = this.height / BarnesHut.SCALE;
				g.drawRect((int) ((x-offset.x) / BarnesHut.SCALE + BarnesHut.width / 2 - width / 2),
						(int) ((z-offset.z) / BarnesHut.SCALE + BarnesHut.height / 2 - height / 2), (int)width, (int)height);
				break;
			case 2:
				width = this.width / BarnesHut.SCALE;
				height = this.depth / BarnesHut.SCALE;
				g.drawRect((int) ((x-offset.x) / BarnesHut.SCALE + BarnesHut.width / 2 - width / 2),
						(int) ((y-offset.y) / BarnesHut.SCALE + BarnesHut.height / 2 - height / 2), (int)width, (int)height);
				break;
		}
		
		if (childs != null) {
			for (Node child : childs) {
//				if (isEmpty())
//					continue;
//				if(order == 1) continue;
				child.render(g, offset);
			}
//			g.fillOval((int) (centerOfMass.x / BarnesHut.SCALE + BarnesHut.width / 2 - 3),
//					(int) (centerOfMass.y / BarnesHut.SCALE + BarnesHut.height / 2 - 3), 6, 6);

		} else if (bodies!=null){
			g.drawString(bodies.size()+"", 
					(int) ((x-offset.x) / BarnesHut.SCALE + BarnesHut.width / 2), 
					(int) ((y-offset.y) / BarnesHut.SCALE + BarnesHut.height / 2));
		}
	}

	public void attract(Body b, double dt) {
		if (isEmpty())
			return;
		if (childs == null && bodies.size() == 1 && !bodies.get(0).equals(b)) {
			b.atract(bodies.get(0), dt);
			if(b.name.equals("Jupiter"))
			calculations++;
			// System.out.println("childs null");
			return;
		}

		if (!bodies.contains(b)) {
			double distance = Vec3.getDistance(centerOfMass, b.getPos());
			if (distance / (width) <= theta) {
				b.attract(mass, centerOfMass, dt, distance);
				if(b.name.equals("Jupiter"))
				calculations++;
				return;
			}

		}

		if (childs != null)
			for (Node child : childs) {
				child.attract(b, dt);
//				calculations++;
			}
	}

	public Node[] getChilds() {
		return childs;
	}

	public double getMass() {
		return mass;
	}

	public Vec3 getCenterOfMass() {
		return centerOfMass;
	}

	private boolean isEmpty() {
		return childs == null && (bodies == null || bodies.size() == 0);
	}

	public void remove(Body b) {
		bodies.remove(b);
		if (childs != null)
			for (Node child : childs) {
				if (child.bodies != null)
					if (child.bodies.contains(b))
						child.remove(b);
			}
	}

	public void add(Body b) {
		bodies.add(b);
	}
}