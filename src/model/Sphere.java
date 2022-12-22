package model;

import utils.ObjectProperties;
import utils.Vector3;

public class Sphere extends Renderable{

	// ********************************************************************
	// This is a concrete implementation of a renderable object, it
	// represents a sphere
	// ********************************************************************

	// Instance Attributes
	private double radius;

	// Constructor
	public Sphere(Vector3 position, double radius, Material material, String name) {
		super(position, name, material);
		this.radius = radius;
		this.type = ObjectProperties.SPHERE_OBJ_TYPE;
	}

	public Sphere() {
		super(Vector3.NULL_VECTOR3, "", null);
		this.radius = 0;
		this.type = ObjectProperties.SPHERE_OBJ_TYPE;
	}

	// Implemetation of intersection abstract method
	// This method perform a line sphere intersection
	@Override
	public HitRecord intersection(Ray ray) {
		Vector3 oc = ray.getPosition().subtract(this.position);
		double a = 1;
		double b = 2*(ray.getDirection().dot(oc));
		double c = oc.dot(oc) - this.radius*this.radius;
		double D = b*b-4*a*c;

		if(D<0)
			return new HitRecord();

		double distance = (-b-Math.sqrt(b*b-4*a*c))/(2*a);
		if(distance <Ray.MIN_RAY_DISTANCE || distance >Ray.MAX_RAY_DISTANCE){
			distance = (-b+Math.sqrt(b*b-4*a*c))/(2*a);
			if(distance <Ray.MIN_RAY_DISTANCE || distance >Ray.MAX_RAY_DISTANCE)
				return new HitRecord();
		}

		HitRecord hit = new HitRecord();
		hit.setHitted(true);
		hit.setDistance(distance);
		hit.setPoint(ray.getPointAt(distance));
		hit.setMaterial(this.material);
		Vector3 normal = hit.getPoint().subtract(this.position);
		hit.setNormal(normal.normalize());
		return hit;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getRadius() {
		return this.radius;
	}
}
