package model;

import utils.Vector3;

public abstract class Renderable extends Object3D{

	// ********************************************************************
	// This is an abstract class: instances of classes that want to be
	// rendered must extend this class.
	// ********************************************************************

	// Instance Attribute
	protected String name;
	protected Material material;
	protected String type;
	protected int id;  // This is used like an ID (must be unique)

	// Constructor
	public Renderable(Vector3 position, Vector3 direction, String name, Material material) {
		super(position, direction);
		this.name = name;
		this.material = material;
		//this.id = ControllerForModel.getInstance().getNewID();
		this.id=-1;
	}

	public Renderable(Vector3 position, String name, Material material) {
		super(position);
		this.name = name;
		this.material = material;
		//this.id = ControllerForModel.getInstance().getNewID();
		this.id=-1;
	}

	public Renderable() {
		super(Vector3.NULL_VECTOR3);
		this.name = "";
		this.material = null;
		this.id = -1;
	}

	// Abstract Methods
	public abstract HitRecord intersection(Ray ray);

	// GetterSetter
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Material getMaterial() {
		return this.material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
