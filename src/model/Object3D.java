package model;

import utils.Vector3;

public class Object3D {

	// ********************************************************************
	// This is a general class for objects with a 3D behaviour.
	// ********************************************************************

	// Instance Attribute
	protected Vector3 position, direction; // Some objects does not need orientation (in that case it will be Vector3.NULL_VECTOR3)

	// Constructor
	public Object3D(Vector3 position, Vector3 direction) {
		this.position = position;
		this.direction = Vector3.UNIT_VECTOR_Z;
	}

	public Object3D(Vector3 position) {
		this.position = position;
		this.direction = Vector3.NULL_VECTOR3;
	}

	public Object3D() {
		this.position = Vector3.NULL_VECTOR3;
		this.direction = Vector3.NULL_VECTOR3;
	}

	// Setter-Getter
	public Vector3 getPosition() {
		return position;
	}

	public Vector3 getDirection() {
		return direction;
	}

	public Vector3 getRotation() {
		return new Vector3(this.direction.normalize().dot(Vector3.UNIT_VECTOR_X), 
			this.direction.normalize().dot(Vector3.UNIT_VECTOR_Y),
			this.direction.normalize().dot(Vector3.UNIT_VECTOR_X));
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public void setRotation(Vector3 rotation) {
		this.direction = Vector3.UNIT_VECTOR_Z.rotate(rotation.getX(), rotation.getY(), rotation.getZ());
	}

	public void setDirection(Vector3 direction){
		this.direction = direction;
	}

	// To String
	@Override
	public String toString() {
		return "Object3D{" +
				"position=" + position +
				", orientation=" + direction +
				'}';
	}
}
