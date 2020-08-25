package com.camoga.gravity;

public class Vec2 {
	
	public double x, y;
	
	public Vec2() {
		this(0, 0);
	}

	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2 add(double dx, double dy) {
		this.x += dx;
		this.y += dy;
		return this;
	}
	
	public Vec2 add(Vec2 vec) {
		this.x += vec.x;
		this.y += vec.y;
		return this;
	}
	
	public Vec2 sub(Vec2 vec) {
		this.x -= vec.x;
		this.y -= vec.y;
		return this;
	}
	
	public Vec2 mul(double s) {
		this.x *= s;
		this.y *= s;
		return this;
	}
	
	public static Vec2 add(Vec2 v, Vec2 w) {
		return new Vec2(v.x+w.x, v.y+w.y);
	}
	
	public static Vec2 mul(Vec2 v, double s) {
		return new Vec2(v.x*s, v.y*s);
	}
	
	public Vec2 div(double s) {
		this.x /= s;
		this.y /= s;
		return this;
	}
	
	public static double getDistanceSq(Vec2 v, Vec2 w) {
		return Math.pow(v.x-w.x, 2)+Math.pow(v.y-w.y, 2);
	}
	
	public static double getDistance(Vec2 v, Vec2 w) {
		return Math.sqrt(getDistanceSq(v, w));
	}
	
	public double mod() {
		return Math.sqrt(x*x+y*y);
	}
	
	public static double dot(Vec2 v, Vec2 u) {
		return u.x*v.x+u.y*v.y;
	}
	
	public Vec2 normalize() {
		return div(mod());
	}
	
	@Override
	public String toString() {
		return "x: " + x +", y: " + y;
	}
}