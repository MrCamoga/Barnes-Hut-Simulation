package com.camoga.gravity;



import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.camoga.gravity.gfx.Screen;
import com.camoga.gravity.quadtree.Benchmark;
import com.camoga.gravity.quadtree.Node;

public class BarnesHut extends Canvas {
	
	public static final int width = 800, height = 600;
	public static double SCALE = 5e8;
	public BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	
	public static BarnesHut main;
	
	public static boolean pause = false;
	
	private Screen screen;
	private Simulation simulation;
	
	public BarnesHut() {
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
				if(e.getKeyCode()==KeyEvent.VK_X) Screen.projection = 0;
				if(e.getKeyCode()==KeyEvent.VK_Y) Screen.projection = 1;
				if(e.getKeyCode()==KeyEvent.VK_Z) Screen.projection = 2;
				
				if(e.getKeyCode()==KeyEvent.VK_LEFT) Simulation.dt*=0.8;
				if(e.getKeyCode()==KeyEvent.VK_RIGHT) Simulation.dt/=0.8;
				if(e.getKeyCode()==KeyEvent.VK_Q) Node.theta*=0.9;
				if(e.getKeyCode()==KeyEvent.VK_A) Node.theta+=(1-Node.theta)/2;
				
//				if(e.getKeyCode()==KeyEvent.VK_B) ;
				
				if(e.getKeyCode()==KeyEvent.VK_P) pause = !pause;
				if(e.getKeyCode()==KeyEvent.VK_R) Simulation.dt *= -1;
				
				
				if(e.getKeyCode()==KeyEvent.VK_UP) SCALE*=0.8;
				if(e.getKeyCode()==KeyEvent.VK_DOWN) SCALE/=0.8;
				if(e.getKeyCode()==KeyEvent.VK_T) Screen.renderTree = !Screen.renderTree;
				if(e.getKeyCode()==KeyEvent.VK_SPACE) {
					Screen.target++;
					Screen.target %= Space.bodies.size();
				}
				if(e.getKeyCode()==KeyEvent.VK_C) Screen.targetCoM = !Screen.targetCoM;

			}
		});
		
		screen = new Screen();
		simulation = new Simulation();
		
		Thread thread = new Thread(() -> run(), "Thread");
		thread.start();
	}
	
	public void run() {
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
			}
		}
	}
	
	public void tick() {
		simulation.tick(1);
	}
	
	public long benchmark(Benchmark b) {
		long start = System.nanoTime();
		b.benchmark();
		long finish = System.nanoTime();
		return finish-start;
	}
	
	public void render() {
		BufferStrategy buffer = getBufferStrategy();
		if(buffer == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = buffer.getDrawGraphics();
		
		g.drawImage(image, 0, 0, width, height, null);
		
		screen.render(g);
		
		g.dispose();
		buffer.show();
	}
	
	public static void main(String[] args) {
		main = new BarnesHut();
	}
}