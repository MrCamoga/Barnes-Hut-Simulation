package com.camoga.gravity.quadtree;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.camoga.gravity.BarnesHut;
import com.camoga.gravity.Body;
import com.camoga.gravity.Vec2;

@Deprecated
public class Node2D {

	private static final double theta = 0.8;
	private int order;

	private Node2D[] childs;

	private ArrayList<Body> bodies;

	private Vec2 centerOfMass;
	private double mass;
	private double width, height, x, y;

	public Node2D(double width, double height, double x, double y, ArrayList<Body> bodies, int order) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.order = order;
		centerOfMass = new Vec2();
		if (bodies.size() > 1) {
			this.bodies = bodies;
			childs = new Node2D[4];

			//TODO change this to octree
			ArrayList<Body> childBodies = new ArrayList<>();
			ArrayList<Body> childBodies2 = new ArrayList<>();
			ArrayList<Body> childBodies3 = new ArrayList<>();
			ArrayList<Body> childBodies4 = new ArrayList<>();

			for (Body b : bodies) {
				if (b.getPos().x < x && b.getPos().y < y) {
					childBodies.add(b);
				} else if (b.getPos().x >= x && b.getPos().y < y) {
					childBodies2.add(b);
				} else if (b.getPos().x >= x && b.getPos().y >= y) {
					childBodies3.add(b);
				} else if (b.getPos().x < x && b.getPos().y >= y) {
					childBodies4.add(b);
				} else {
					// System.err.println("Body outside quadtree");
				}
			}
			double newW = width/2;
			double newH = height/2;
			double centerW = width/4;
			double centerH = height/4;
			
			childs[0] = new Node2D(newW, newH, x - centerW, y - centerH, childBodies, order + 1);
			childs[1] = new Node2D(newW, newH, x + centerW, y - centerH, childBodies2, order + 1);
			childs[2] = new Node2D(newW, newH, x + centerW, y + centerH, childBodies3, order + 1);
			childs[3] = new Node2D(newW, newH, x - centerW, y + centerH, childBodies4, order + 1);

			for (Node2D child : childs) {
				if (isEmpty())
					continue;
				mass += child.mass;
				centerOfMass.add(Vec2.mul(child.centerOfMass, child.mass));
				// System.out.println(order + ": " + centerOfMass);
			}
			centerOfMass.div(mass);
		} else if (bodies.size() == 1) {
			this.bodies = bodies;
			centerOfMass = bodies.get(0).getPos();
			mass = bodies.get(0).getMass();
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.green);
		double width = this.width / BarnesHut.SCALE;
		double height = this.height / BarnesHut.SCALE;
		g.drawRect((int) (x / BarnesHut.SCALE + BarnesHut.width / 2 - width / 2),
				(int) (y / BarnesHut.SCALE + BarnesHut.height / 2 - height / 2), (int)width, (int)height);
		if (childs != null) {
			for (Node2D child : childs) {
				if (isEmpty())
					continue;
				child.render(g);
			}
//			g.fillOval((int) (centerOfMass.x / BarnesHut.SCALE + BarnesHut.width / 2 - 3),
//					(int) (centerOfMass.y / BarnesHut.SCALE + BarnesHut.height / 2 - 3), 6, 6);

		}
	}


	public void attract(Body b, double dt) {
		if (isEmpty())
			return;
		if (childs == null && bodies.size() == 1 && !bodies.get(0).equals(b)) {
			b.atract(bodies.get(0), dt);
			// System.out.println("childs null");
			return;
		}

		if (!bodies.contains(b)) {
			double distance = Vec2.getDistance(centerOfMass, b.getPos());
			if (distance / width <= theta) {
				b.attract(mass, centerOfMass, dt);
				return;
			}

		}

		if (childs != null)
			for (Node child : childs) {
				child.attract(b, dt);
			}
	}

	public Node2D[] getChilds() {
		return childs;
	}

	public double getMass() {
		return mass;
	}

	public Vec2 getCenterOfMass() {
		return centerOfMass;
	}

	private boolean isEmpty() {
		return childs == null && (bodies == null || bodies.size() == 0);
	}

	public void remove(Body b) {
		bodies.remove(b);
		if (childs != null)
			for (Node2D child : childs) {
				if (child.bodies != null)
					if (child.bodies.contains(b))
						child.remove(b);
			}
	}

	public void add(Body b) {
		bodies.add(b);
	}
}