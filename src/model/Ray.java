package model;

import utils.Color;
import utils.Vector3;

public class Ray extends Object3D{

	// ********************************************************************
	// This class represents a light ray.
	// ********************************************************************

	// Instance Attributes
	private Color color;
	private int bounces;

	// Constants
	private final float VACUUM_REFRACTIVE_INDEX = 1f;
	private final int MAX_NUMBER_OF_BOUNCES = 10;

	// Static Variables
	public static double MIN_RAY_DISTANCE = 0.001;
	public static double MAX_RAY_DISTANCE = 100000;

	// Constructor
	public Ray(Vector3 position, Vector3 direction, Color color) {
		super(position, direction.normalize());
		this.color = color;
		this.bounces = 0;
	}

	public Ray(Vector3 position, Vector3 direction) {
		super(position, direction.normalize());
		this.color = Color.WHITE;
		this.bounces = 0;
	}

	public Ray() {
		super(Vector3.NULL_VECTOR3, Vector3.NULL_VECTOR3);
		this.color = Color.WHITE;
		this.bounces = 0;
	}

	// Getter-Setter
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	// Instance Methods
	public Vector3 getPointAt(double distance) {
		return this.position.add(this.direction.scale(distance));
	}

	public boolean canBounce() {
		return this.bounces<MAX_NUMBER_OF_BOUNCES;
	}

	public void incrementBounces() {
		this.bounces++;
	}

	public void resetBounces() {
		this.bounces = 0;
	}

	// To String
	@Override
	public String toString() {
		return "Ray{" +
				"position=" + position +
				", direction=" + direction +
				", color=" + color +
				", bounces=" + bounces +
				", VACUUM_REFRACTIVE_INDEX=" + VACUUM_REFRACTIVE_INDEX +
				", MAX_NUMBER_OF_BOUNCES=" + MAX_NUMBER_OF_BOUNCES +
				'}';
	}
}
