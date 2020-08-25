package com.camoga.gravity4D;

public class Vec4 {
	
	public double w, x, y, z;
	
	public Vec4() {
		this(0, 0, 0, 0);
	}

	public Vec4(double w, double x, double y, double z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec4 add(double dw, double dx, double dy, double dz) {
		this.w += dw;
		this.x += dx;
		this.y += dy;
		this.z += dz;
		return this;
	}
	
	public Vec4 add(Vec4 vec) {
		this.w += vec.w;
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
		return this;
	}
	
	public Vec4 sub(Vec4 vec) {
		this.w -= vec.w;
		this.x -= vec.x;
		this.y -= vec.y;
		this.z -= vec.z;
		return this;
	}
	
	public Vec4 mul(double s) {
		this.w *= s;
		this.x *= s;
		this.y *= s;
		this.z *= s;
		return this;
	}
	
	public static Vec4 add(Vec4 v, Vec4 w) {
		return new Vec4(v.w+w.w, v.x+w.x, v.y+w.y, v.z + w.z);
	}
	
	public static Vec4 mul(Vec4 v, double s) {
		return new Vec4(v.w*s, v.x*s, v.y*s, v.z*s);
	}
	
	public Vec4 div(double s) {
		this.w /= s;
		this.x /= s;
		this.y /= s;
		this.z /= s;
		return this;
	}
	
	public static double getDistanceSq(Vec4 v, Vec4 w) {
		return Math.pow(v.w-w.w, 2)+Math.pow(v.x-w.x, 2)+Math.pow(v.y-w.y, 2)+Math.pow(v.z-w.z, 2);
	}
	
	public static double getDistance(Vec4 v, Vec4 w) {
		return Math.sqrt(getDistanceSq(v, w));
	}
	
	public double mod() {
		return Math.sqrt(w*w+x*x+y*y+z*z);
	}
	
	public static double dot(Vec4 v, Vec4 u) {
		return u.w*v.w+u.x*v.x+u.y*v.y+u.z*v.z;
	}
	
//	public static Vec4 cross(Vec4 v, Vec4 w) {
//		return new Vec4(v.y*w.z-v.z*w.y, v.z*w.x-v.x*w.z, v.x*w.y-v.y*w.x);
//	}
	
	public Vec4 normalize() {
		return div(mod());
	}
	
	@Override
	public String toString() {
		return "w: " + w + ", x: " + x +", y: " + y +", z: " + z;
	}
}