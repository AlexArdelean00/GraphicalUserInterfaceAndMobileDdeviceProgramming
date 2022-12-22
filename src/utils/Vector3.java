package utils;

public class Vector3 {

	// ********************************************************************
	// A Utility class, used to perform 3d vector operation.
	// ********************************************************************

	// Instance Attribute
	private double x,y,z;

	// Static variable
	public static Vector3 NULL_VECTOR3 = new Vector3(0,0,0);
	public static Vector3 UNIT_VECTOR_X = new Vector3(1,0,0);
	public static Vector3 UNIT_VECTOR_Y = new Vector3(0,1,0);
	public static Vector3 UNIT_VECTOR_Z = new Vector3(0,0,1);
	public static Vector3 ONES_VECTOR3 = new Vector3(1,1,1);

	// Constructor
	public Vector3(double x, double y, double z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}

	public Vector3() {
		this.x=0;
		this.y=0;
		this.z=0;
	}

	// Getter-Setter
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	// Vector Operations
	public Vector3 add(Vector3 v) {
		return new Vector3(this.x+v.x, this.y+v.y, this.z+v.z);
	}

	public Vector3 subtract(Vector3 v) {
		return new Vector3(this.x-v.x, this.y-v.y, this.z-v.z);
	}

	public Vector3 scale(double d) {
		return new Vector3(this.x*d, this.y*d, this.z*d);
	}

	public double dot(Vector3 v) {
		return this.x*v.x + this.y*v.y + this.z*v.z;
	}

	public double magnitude() {
		return Math.sqrt(this.dot(this));
	}

	public Vector3 normalize() {
		double mag = this.magnitude();
		return new Vector3(this.x/mag, this.y/mag, this.z/mag);
	}

	public Vector3 cross(Vector3 v) {
		return new Vector3(this.y*v.z - this.z*v.y, this.z*v.x - this.x*v.z, this.x*v.y - this.y*v.x);
	}

	public static Vector3 randomUnitVector() {
		return (new Vector3(Math.random()-0.5, Math.random()-0.5, Math.random()-0.5)).normalize();
	}

	public void setComponent(int index, double value) {
		if(index==0)setX(value);
		else if(index==1)setY(value);
		else if(index==2)setZ(value);
	}

	public double getComponent(int index) {
		if(index==0)return getX();
		else if(index==1)return getY();
		else if(index==2)return getZ();
		return 0;
	}

	public Vector3 rotate(double alpha, double beta, double gamma) {
		return (Matrix3x3.getRotationMatrix(alpha, beta, gamma).matVecMult(this));
	}

	// To String
	@Override
	public String toString() {
		return "Vector3{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}
}
