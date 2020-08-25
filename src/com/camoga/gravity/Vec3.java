package com.camoga.gravity;

public class Vec3 {
	
	public double x, y, z;
	
	public Vec3() {
		this(0, 0, 0);
	}

	public Vec3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec3 add(double dx, double dy, double dz) {
		this.x += dx;
		this.y += dy;
		this.z += dz;
		return this;
	}
	
	public Vec3 add(Vec3 vec) {
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
		return this;
	}
	
	public Vec3 sub(Vec3 vec) {
		this.x -= vec.x;
		this.y -= vec.y;
		this.z -= vec.z;
		return this;
	}
	
	public Vec3 mul(double s) {
		this.x *= s;
		this.y *= s;
		this.z *= s;
		return this;
	}
	
	public static Vec3 add(Vec3 v, Vec3 w) {
		return new Vec3(v.x+w.x, v.y+w.y, v.z + w.z);
	}
	
	public static Vec3 mul(Vec3 v, double s) {
		return new Vec3(v.x*s, v.y*s, v.z*s);
	}
	
	public Vec3 div(double s) {
		this.x /= s;
		this.y /= s;
		this.z /= s;
		return this;
	}
	
	public static double getDistanceSq(Vec3 v, Vec3 w) {
		return Math.pow(v.x-w.x, 2)+Math.pow(v.y-w.y, 2)+Math.pow(v.z-w.z, 2);
	}
	
	public static double getDistance(Vec3 v, Vec3 w) {
		return Math.sqrt(getDistanceSq(v, w));
	}
	
	public double mod() {
		return Math.sqrt(x*x+y*y+z*z);
	}
	
	public static double dot(Vec3 v, Vec3 u) {
		return u.x*v.x+u.y*v.y+u.z*v.z;
	}
	
	public static Vec3 cross(Vec3 v, Vec3 w) {
		return new Vec3(v.y*w.z-v.z*w.y, v.z*w.x-v.x*w.z, v.x*w.y-v.y*w.x);
	}
	
	public Vec3 normalize() {
		return div(mod());
	}
	
	@Override
	public String toString() {
		return "x: " + x +", y: " + y +", z: " + z;
	}
}