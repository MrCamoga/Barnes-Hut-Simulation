package com.camoga.gravity4D.quadtree;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.camoga.gravity4D.Body;
import com.camoga.gravity4D.Main;
import com.camoga.gravity4D.Vec4;

public class Node4D {

	public static double theta = 0.99;
	private int order;

	private Node4D[] childs;

	private ArrayList<Body> bodies;

	private Vec4 centerOfMass;
	private double mass;
	private double length, width, depth, height;
	private double w, x, y, z;

	public Node4D(double length, double width, double depth, double height, double w, double x, double y, double z, ArrayList<Body> bodies, int order) {
		this.length = length;
		this.width = width;
		this.depth = depth;
		this.height = height;
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
		this.order = order;
		centerOfMass = new Vec4();
		if (bodies.size() > 1) {
			this.bodies = bodies;
			childs = new Node4D[16];

			ArrayList<Body> childBodies1 = new ArrayList<>();
			ArrayList<Body> childBodies2 = new ArrayList<>();
			ArrayList<Body> childBodies3 = new ArrayList<>();
			ArrayList<Body> childBodies4 = new ArrayList<>();
			ArrayList<Body> childBodies5 = new ArrayList<>();
			ArrayList<Body> childBodies6 = new ArrayList<>();
			ArrayList<Body> childBodies7 = new ArrayList<>();
			ArrayList<Body> childBodies8 = new ArrayList<>();
			ArrayList<Body> childBodies9 = new ArrayList<>();
			ArrayList<Body> childBodies10 = new ArrayList<>();
			ArrayList<Body> childBodies11 = new ArrayList<>();
			ArrayList<Body> childBodies12 = new ArrayList<>();
			ArrayList<Body> childBodies13 = new ArrayList<>();
			ArrayList<Body> childBodies14 = new ArrayList<>();
			ArrayList<Body> childBodies15 = new ArrayList<>();
			ArrayList<Body> childBodies16 = new ArrayList<>();

			for (Body b : bodies) {
				if (b.getPos().x < x && b.getPos().y < y && b.getPos().z >= z && b.getPos().w >= w) {
					childBodies1.add(b);
				} else if (b.getPos().x >= x && b.getPos().y < y && b.getPos().z >= z && b.getPos().w >= w) {
					childBodies2.add(b);
				} else if (b.getPos().x >= x && b.getPos().y >= y && b.getPos().z >= z && b.getPos().w >= w) {
					childBodies3.add(b);
				} else if (b.getPos().x < x && b.getPos().y >= y && b.getPos().z >= z && b.getPos().w >= w) {
					childBodies4.add(b);
				} else if (b.getPos().x < x && b.getPos().y >= y && b.getPos().z < z && b.getPos().w >= w) {
					childBodies5.add(b);
				} else if (b.getPos().x >= x && b.getPos().y >= y && b.getPos().z < z && b.getPos().w >= w) {
					childBodies6.add(b);
				} else if (b.getPos().x >= x && b.getPos().y < y && b.getPos().z < z && b.getPos().w >= w) {
					childBodies7.add(b);
				} else if (b.getPos().x < x && b.getPos().y < y && b.getPos().z < z && b.getPos().w >= w) {
					childBodies8.add(b);
				} else if (b.getPos().x < x && b.getPos().y < y && b.getPos().z >= z && b.getPos().w < w) {
					childBodies9.add(b);
				} else if (b.getPos().x >= x && b.getPos().y < y && b.getPos().z >= z && b.getPos().w < w) {
					childBodies10.add(b);
				} else if (b.getPos().x >= x && b.getPos().y >= y && b.getPos().z >= z && b.getPos().w < w) {
					childBodies11.add(b);
				} else if (b.getPos().x < x && b.getPos().y >= y && b.getPos().z >= z && b.getPos().w < w) {
					childBodies12.add(b);
				} else if (b.getPos().x < x && b.getPos().y >= y && b.getPos().z < z && b.getPos().w < w) {
					childBodies13.add(b);
				} else if (b.getPos().x >= x && b.getPos().y >= y && b.getPos().z < z && b.getPos().w < w) {
					childBodies14.add(b);
				} else if (b.getPos().x >= x && b.getPos().y < y && b.getPos().z < z && b.getPos().w < w) {
					childBodies15.add(b);
				} else if (b.getPos().x < x && b.getPos().y < y && b.getPos().z < z && b.getPos().w < w) {
					childBodies16.add(b);
				}
				
				
				
				else {
					// System.err.println("Body outside quadtree");
				}
			}
			double newL = length/2;
			double newW = width/2;
			double newD = depth/2;
			double newH = height/2;
			double centerL = length/4;
			double centerW = width/4;
			double centerD = depth/4;
			double centerH = height/4;
//			for(int i = 0; i < 1; i++) {
//				for(int j = 0; j < 1; j++) {
//					for(int k = 0; k < 1; k++) {
//						for(int l = 0; l < 1; l++) {
//							int index = (i<<3) | (j << 2) | (k << 1) | l;
//							childs[index] = new Node4D(newL, newW, newD, newH, w + centerL*(i==0 ? -1:1), x + centerW*(j==0 ? -1:1), y + centerD*(k==0 ? -1:1), z + centerH*(l==0 ? -1:1), childBodies[index], order+1);
//						}
//					}
//				}
//			}
			childs[0] = new Node4D(newL, newW, newD, newH, w + centerL, x - centerW, y - centerD, z + centerH, childBodies1, order + 1);
			childs[1] = new Node4D(newL, newW, newD, newH, w + centerL, x + centerW, y - centerD, z + centerH, childBodies2, order + 1);
			childs[2] = new Node4D(newL, newW, newD, newH, w + centerL, x + centerW, y + centerD, z + centerH, childBodies3, order + 1);
			childs[3] = new Node4D(newL, newW, newD, newH, w + centerL, x - centerW, y + centerD, z + centerH, childBodies4, order + 1);
			childs[4] = new Node4D(newL, newW, newD, newH, w + centerL, x - centerW, y + centerD, z - centerH, childBodies5, order + 1);
			childs[5] = new Node4D(newL, newW, newD, newH, w + centerL, x + centerW, y + centerD, z - centerH, childBodies6, order + 1);
			childs[6] = new Node4D(newL, newW, newD, newH, w + centerL, x + centerW, y - centerD, z - centerH, childBodies7, order + 1);
			childs[7] = new Node4D(newL, newW, newD, newH, w + centerL, x - centerW, y - centerD, z - centerH, childBodies8, order + 1);
			childs[15] = new Node4D(newL, newW, newD, newH, w - centerL, x - centerW, y - centerD, z - centerH, childBodies16, order + 1);
			childs[14] = new Node4D(newL, newW, newD, newH, w - centerL, x + centerW, y - centerD, z - centerH, childBodies15, order + 1);
			childs[13] = new Node4D(newL, newW, newD, newH, w - centerL, x + centerW, y + centerD, z - centerH, childBodies14, order + 1);
			childs[12] = new Node4D(newL, newW, newD, newH, w - centerL, x - centerW, y + centerD, z - centerH, childBodies13, order + 1);
			childs[11] = new Node4D(newL, newW, newD, newH, w - centerL, x - centerW, y + centerD, z + centerH, childBodies12, order + 1);
			childs[10] = new Node4D(newL, newW, newD, newH, w - centerL, x + centerW, y + centerD, z + centerH, childBodies11, order + 1);
			childs[9] = new Node4D(newL, newW, newD, newH, w - centerL, x + centerW, y - centerD, z + centerH, childBodies10, order + 1);
			childs[8] = new Node4D(newL, newW, newD, newH, w - centerL, x - centerW, y - centerD, z + centerH, childBodies9, order + 1);

			for (Node4D child : childs) {
				if (isEmpty())
					continue;
				mass += child.mass;
				centerOfMass.add(Vec4.mul(child.centerOfMass, child.mass));
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
		double width = this.width / Main.SCALE;
		double height = this.height / Main.SCALE;
		
		switch (Main.projection) {
			case 0:
				g.drawRect((int) (y / Main.SCALE + Main.width / 2 - width / 2),
						(int) (z / Main.SCALE + Main.height / 2 - height / 2), (int)width, (int)height);
				break;

			case 1:
				g.drawRect((int) (x / Main.SCALE + Main.width / 2 - width / 2),
						(int) (z / Main.SCALE + Main.height / 2 - height / 2), (int)width, (int)height);
				break;
			case 2:
				g.drawRect((int) (x / Main.SCALE + Main.width / 2 - width / 2),
						(int) (y / Main.SCALE + Main.height / 2 - height / 2), (int)width, (int)height);
				break;
			case 3:
				g.drawRect((int) (w / Main.SCALE + Main.width / 2 - width / 2),
						(int) (y / Main.SCALE + Main.height / 2 - height / 2), (int)width, (int)height);
				break;
			case 4:
				g.drawRect((int) (w / Main.SCALE + Main.width / 2 - width / 2),
						(int) (z / Main.SCALE + Main.height / 2 - height / 2), (int)width, (int)height);
				break;
			case 5:
				g.drawRect((int) (w / Main.SCALE + Main.width / 2 - width / 2),
						(int) (x / Main.SCALE + Main.height / 2 - height / 2), (int)width, (int)height);
				break;
		}
		
		if (childs != null) {
			for (Node4D child : childs) {
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
			double distance = Vec4.getDistance(centerOfMass, b.getPos());
			if (distance / width <= theta) {
				b.attract(mass, centerOfMass, dt);
				return;
			}

		}

		if (childs != null)
			for (Node4D child : childs) {
				child.attract(b, dt);
			}
	}

	public Node4D[] getChilds() {
		return childs;
	}

	public double getMass() {
		return mass;
	}

	public Vec4 getCenterOfMass() {
		return centerOfMass;
	}

	private boolean isEmpty() {
		return childs == null && (bodies == null || bodies.size() == 0);
	}

	public void remove(Body b) {
		bodies.remove(b);
		if (childs != null)
			for (Node4D child : childs) {
				if (child.bodies != null)
					if (child.bodies.contains(b))
						child.remove(b);
			}
	}

	public void add(Body b) {
		bodies.add(b);
	}
}