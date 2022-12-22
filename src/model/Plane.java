package model;

import utils.ObjectProperties;
import utils.Vector3;

public class Plane extends Renderable{

	// ********************************************************************
	// This is a concrete implementation of a renderable object, it
	// represents a plane
	// ********************************************************************

	// Constructor
	public Plane(Vector3 position, Vector3 direction, String name, Material material) {
		super(position, direction, name, material);
		this.type = ObjectProperties.PLANE_OBJ_TYPE;
	}

	public Plane() {
		super(Vector3.NULL_VECTOR3, Vector3.NULL_VECTOR3, "", null);
		this.type = ObjectProperties.PLANE_OBJ_TYPE;
	}

	// Implemetation of intersection abstract method
	// This method perform a line plane intersection
	@Override
	public HitRecord intersection(Ray ray) {
		double den = ray.getDirection().dot(this.direction);
		HitRecord hit = new HitRecord();
		if(den != 0){
			double distance = (this.position.subtract(ray.getPosition()).dot(this.direction))/den;
			if(distance<Ray.MAX_RAY_DISTANCE && distance>Ray.MIN_RAY_DISTANCE){
				hit.setDistance(distance);
				hit.setHitted(true);
				if(den<0)
					hit.setNormal(this.direction);
				else
					hit.setNormal(this.direction.scale(-1));
				hit.setPoint(ray.getPointAt(distance));
				hit.setMaterial(this.material);
			}
		}
		return hit;
	}
}
