package com.camoga.gravity4D;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import com.camoga.gravity4D.quadtree.Benchmark;
import com.camoga.gravity4D.quadtree.Node4D;

public class Main extends Canvas {
	
	public static final int width = 800, height = 600;
	public static double SCALE = 5e8;
	public BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	
	public ArrayList<Body> bodies = new ArrayList<>();
	public static Node4D node;
	
	public static Main main;
	
	/**
	 * 0 = WX-plane
	 * 1 = WY-plane
	 * 2 = WZ-plane
	 * 3 = XZ-plane
	 * 4 = XY-plane
	 * 5 = YZ-plane
	 */
	public static int projection = 5;
	public static boolean renderTree = false;
	public static boolean targetCoM = true;
	public static int target = 0;
	public static boolean pause = false;
	
	public Main() {
		JFrame frame = new JFrame("");
		frame.setSize(width,height);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.add(this);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_Q) projection = 0;
				if(e.getKeyCode()==KeyEvent.VK_W) projection = 1;
				if(e.getKeyCode()==KeyEvent.VK_E) projection = 2;
				if(e.getKeyCode()==KeyEvent.VK_A) projection = 3;
				if(e.getKeyCode()==KeyEvent.VK_S) projection = 4;
				if(e.getKeyCode()==KeyEvent.VK_D) projection = 5;
				
				if(e.getKeyCode()==KeyEvent.VK_UP) SCALE*=0.8;
				if(e.getKeyCode()==KeyEvent.VK_DOWN) SCALE/=0.8;
				if(e.getKeyCode()==KeyEvent.VK_T) renderTree = !renderTree;
				if(e.getKeyCode()==KeyEvent.VK_SPACE) {
					target++;
					target %= bodies.size();
				}
				if(e.getKeyCode()==KeyEvent.VK_C) targetCoM = !targetCoM;

				if(e.getKeyCode()==KeyEvent.VK_LEFT) dt*=0.8;
				if(e.getKeyCode()==KeyEvent.VK_RIGHT) dt/=0.8;
				if(e.getKeyCode()==KeyEvent.VK_Z) Node4D.theta*=0.9;
				if(e.getKeyCode()==KeyEvent.VK_X) Node4D.theta+=(1-Node4D.theta)/2;
				
				if(e.getKeyCode()==KeyEvent.VK_P) pause = !pause;

			}
		});
		
		Thread thread = new Thread(() -> run(), "Thread");
		thread.start();
	}
	
	public void run() {
//		createCloud(100, 6e11, 7e28, 16000);
		addBodies();
		
		long last = System.nanoTime();

		double ns = 1e9/25D;
		double delta = 0;
		
		int ticks = 0;
		int frames = 0;
		long time = System.currentTimeMillis();
		
		while(true) {
			long now = System.nanoTime();
			delta += (now-last)/ns;
			last = now;
			
			while(delta >= 1) {
				delta--;
				
				render();
				frames++;
			}
			if(!pause) {
				tick();
				ticks++;				
			}
			
			if(System.currentTimeMillis()-time >= 1000) {
				time += 1000;
				System.out.println("UPS: " + ticks+  ", FPS: " + frames);
				ticks = 0;
				frames = 0;
				computeEnergy();
			}
		}
	}
	
	public void addBodies() {
//		bodies.add(new Body(1.988e30, 6317000, new Vec3(), new Vec3(), Color.yellow, "Sun"));
//		
//		bodies.add(new Body(5.97e24, 6.96e8, 
//				new Vec3(0, 30000, 0), 
//				new Vec3(1.5e11, 0, 0), 
//				Color.WHITE, ""));
//		
//		bodies.add(new Body(5.97e24, 6.96e8, 
//				new Vec3(0, 30000*Math.sqrt(2)/2.18, 0), 
//				new Vec3(1.5e11, 0, 1.5e11), 
//				Color.GREEN, "Jool"));
//		
//		bodies.add(new Body(5.97e24, 6.96e8,
//				new Vec3(90000/2, 0, 30000*Math.sqrt(3)/2), 
//				new Vec3(0, -1.5e11, 0), Color.red, "Mars"));
		
		bodies.add(new Body(1.988e30, 6317000, new Vec4(), new Vec4(), Color.yellow, "Sun"));
		
		bodies.add(new Body(5.97e24, 6.96e8, 
				new Vec4(0, 30000, 0, 0), 
				new Vec4(1e10, 0, -1.5e11, 0), 
				Color.cyan, "Wearth"));
//		
//		bodies.add(new Body(5.97e24, 6.96e8, 
//				new Vec4(0, 30000*Math.sqrt(2)/2.18, 0), 
//				new Vec4(1.5e11, 0, 1.5e11), 
//				Color.GREEN, "Jool"));
		
		bodies.add(new Body(5.97e24, 6.96e8,
				new Vec4(0, 30000/2, 0, 30000*Math.sqrt(3)/2), 
				new Vec4(0, 0, -1.503329638e11, 0), Color.red, "Mars"));
		
//		int num = 1000;
//		bodies.add(new Body(5.97e37, 6317000, new Vec3(), new Vec3(), Color.yellow, "Sun"));
//		Random r = new Random();
//		for(int i = 0; i < num; i++) {
//			double radius = 4.6e12*r.nextDouble();
//			double velocity = Math.sqrt(Body.G*9e36/radius);
//			bodies.add(new Body(4e30, 6.96e8, 
//					new Vec3(-velocity*Math.sin(i*2*Math.PI/num), velocity*Math.cos(i*2*Math.PI/num), (Math.random()-0.5)*velocity/20), 
//					new Vec3(radius*Math.cos(i*2*Math.PI/num), radius*Math.sin(i*2*Math.PI/num), (Math.random()-0.5)*radius/2), 
//					Color.WHITE, ""));
//		}
		

		node = new Node4D(Utils.getAbs(bodies)*2, Utils.getAbs(bodies)*2, Utils.getAbs(bodies)*2, Utils.getAbs(bodies)*2, 0, 0, 0, 0, bodies, 0);
	}
	
//	public void createCloud(int numOfParticles, double radius, double particlesMass, double velocities) {
//		for(int i = 0; i < numOfParticles; i++) {
//			double x=2, y=2, z=2;
//			while(x*x+y*y+z*z > 1) {
//				x = Math.random();
//				y = Math.random();
//				z = Math.random();
//			}
//			x *= Math.random() < 0.5 ? -1:1;
//			y *= Math.random() < 0.5 ? -1:1;
//			z *= Math.random() < 0.5 ? -1:1;
//			Vec3 pos = new Vec3(x,y,z).mul(Math.random()*radius);
//			
//			double vx = 2*Math.random()-1;
//			double vy = 2*Math.random()-1;
//			double vz = 2*Math.sqrt(1-x*x-y*y);
//			z *= Math.random() < 0.5 ? -1:1;
//			Vec3 velocity = new Vec3(vx,vy,vz).mul(Math.random()*velocities);
//			
//			bodies.add(new Body(particlesMass, Math.cbrt(particlesMass), 
//					velocity, 
//					pos, Color.WHITE, ""));
//		}
//		
//		node = new Node(Utils.getAbs(bodies)*2, Utils.getAbs(bodies)*2, Utils.getAbs(bodies)*2, 0, 0, 0, bodies, 0);
//	}
//	
	private double dt = 1;
	
	private static double simulationTime = 0;
	
	public void tick() {
//		System.out.println(steps);
		simulationTime += dt;
		steps = 0;
//		System.out.println("Create quadtree map time: " + benchmark(() -> updateNodes())/1e6 +  "ms");
//		System.out.println("Barnes-Hut algorithm time: " + benchmark(() -> barnesHut())/1e6 + "ms");
		updateNodes();
		barnesHut();
//		bruteforce();
		for(int i = 0; i < bodies.size(); i++) {
			bodies.get(i).tick(dt);
		}
	}
	
	public long benchmark(Benchmark b) {
		long start = System.nanoTime();
		b.benchmark();
		long finish = System.nanoTime();
		return finish-start;
	}
	
	public void updateNodes() {
		node = new Node4D(Utils.getAbs(bodies)*2, Utils.getAbs(bodies)*2, Utils.getAbs(bodies)*2, Utils.getAbs(bodies)*2, 0, 0, 0, 0, bodies, 0);
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

	
	public void merge(Body b, Body b2) {
		if(b.equals(b2) || (!bodies.contains(b) || !bodies.contains(b2))) return;
		bodies.remove(b);
		bodies.remove(b2);
		double mass = b.getMass()+b2.getMass();
		Vec4 velocity = Vec4.mul(b.getVel(), b.getMass()).add(Vec4.mul(b2.getVel(), b2.getMass())).div(mass);
		//TODO
		bodies.add(new Body(mass, Math.cbrt(Math.pow(b.getRadius(),3) + Math.pow(b2.getRadius(),3)), velocity, Vec4.mul(b.getPos(), b.getMass()).add(Vec4.mul(b2.getPos(), b2.getMass())).div(mass) ,new Color(new Random().nextInt(0x1000000)), bodies.size()+""));
	
	}

	public static double potentialEnergy = 0;
	public static double kineticEnergy = 0;
	
	public void computeEnergy() {
		for(int i = 0; i < bodies.size(); i++) {
			for(int j = i; j < bodies.size(); j++) {
				double distance = Vec4.getDistance(bodies.get(i).getPos(), bodies.get(j).getPos());
				if(distance == 0) continue;
				potentialEnergy += -Body.G*bodies.get(i).getMass()*bodies.get(j).getMass()/distance;
			}
			kineticEnergy += 0.5*bodies.get(i).getMass()*bodies.get(i).getVel().mod();
		}
	}
	
	public void render() {
		BufferStrategy buffer = getBufferStrategy();
		if(buffer == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = buffer.getDrawGraphics();
		
		g.drawImage(image, 0, 0, width, height, null);
		
		//Render bodies
		Vec4 offset = null;
		if(targetCoM) offset = node.getCenterOfMass();
		else offset = bodies.get(target).getPos();
		for(Body b : bodies) {
			b.render(g, offset);
		}
		
		//Render tree
		if(renderTree) node.render(g);
		g.setColor(Color.white);
		
		//Render text
		int xd = 250;
		g.drawString("Wearth pos: " + bodies.get(target).getPos(), xd, 60);
		
//		g.drawString("Total energy: " + (potentialEnergy+kineticEnergy), width - xd, height-180);
		g.drawString("Simulation Time (years): " + simulationTime/31556926, width - xd, height-160);
		g.drawString("Barnes-Hut Algorithm theta: " + Node4D.theta, width - xd, height-140);
		String plane = "";
		switch(projection) {
			case 0:
				plane = "WX-plane";
				break;
			case 1:
				plane = "WY-plane";				
				break;
			case 2:
				plane = "WZ-plane";				
				break;
			case 3:
				plane = "XZ-plane";				
				break;
			case 4:
				plane = "XY-plane";				
				break;
			case 5:
				plane = "YZ-plane";				
				break;
		}
		g.drawString("Projection plane: " + plane, width-xd, height-120);
		g.drawString("Delta-t: " + dt, width - xd, height-100);
		g.drawString("Num of bodies: " + bodies.size(), width - xd, height-80);
		g.dispose();
		buffer.show();
	}
	
//	private Body getBiggestBody() {
//		Body b = bodies.get(0);
//		for(Body body : bodies) {
//			if(body.getMass() > b.getMass()) b = body;
//		}
//		return b;
//	}
	
	public static void main(String[] args) {
		main = new Main();
	}
}