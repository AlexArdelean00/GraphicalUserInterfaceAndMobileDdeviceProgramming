package model;

import utils.Vector3;

public class HitRecord {

	// ********************************************************************
	// The intersection function of Renderable objects return a HitRecord
	// that contains the basic information about the hit. Used to perform
	// the rendering.
	// ********************************************************************

	// Instance Attribute
	private Vector3 point, normal;
	private double distance;
	private Material material;
	private boolean hitted;

	// Constructor
	public HitRecord(Vector3 point, Vector3 normal, double distance, Material material, boolean hitted) {
		this.point = point;
		this.normal = normal;
		this.distance = distance;
		this.material = material;
		this.hitted = hitted;
	}

	public HitRecord(Vector3 point, Vector3 normal, double distance, Material material) {
		this.point = point;
		this.normal = normal;
		this.distance = distance;
		this.material = material;
		this.hitted = false;
	}

	public HitRecord() {
		this.point = Vector3.NULL_VECTOR3;
		this.normal = Vector3.NULL_VECTOR3;
		this.distance = Double.POSITIVE_INFINITY;
		this.material = null;
		this.hitted = false;
	}

	// Setter-Getter
	public Vector3 getPoint() {
		return point;
	}

	public Vector3 getNormal() {
		return normal;
	}

	public double getDistance() {
		return distance;
	}

	public Material getMaterial() {
		return material;
	}

	public boolean isHitted() {
		return hitted;
	}

	public void setPoint(Vector3 point) {
		this.point = point;
	}

	public void setNormal(Vector3 normal) {
		this.normal = normal;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public void setHitted(boolean hitted) {
		this.hitted = hitted;
	}

	// To String
	@Override
	public String toString() {
		return "HitRecord{" +
				"point=" + point +
				", normal=" + normal +
				", distance=" + distance +
				", material=" + material +
				", hitted=" + hitted +
				'}';
	}
}