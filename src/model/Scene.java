package model;

import java.util.ArrayList;

import controller.ControllerForModel;
import utils.ObjectProperties;
import utils.SceneFile;

public class Scene{

	// ********************************************************************
	// This class is a collection of Renderable objects and a camera.
	// ********************************************************************

	// Instance Variables
	private Camera camera;
	private ArrayList<Renderable> objects;

	// Constructor
	public Scene() {
		this.camera = new Camera();
		this.objects = new ArrayList<Renderable>();
	}

	// Instance Methods
	public void addObject(Renderable object) {
		object.setId(ControllerForModel.getInstance().getNewID());
		this.objects.add(object);
		//System.out.println("new " + object.getType() + " added with id " + object.getId());
	}

	// Getter-Setter
	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public ArrayList<Renderable> getObjects() {
		return objects;
	}

	// ------------------- Load Scene ---------------------
	public void loadScene(SceneFile scn) {
		ArrayList<ObjectProperties> opList = scn.getObjList();
		this.objects.clear();
		for (ObjectProperties op : opList) {
			this.addObject(getRenderableFromObjectProperties(op));
		}
	}

	// ------------------ Object/Renderable Conversion -------------------
	public static Renderable getRenderableFromObjectProperties(ObjectProperties op) {
		Renderable obj;

		if(op.getType().equals(ObjectProperties.SPHERE_OBJ_TYPE)){
			obj = new Sphere();
			((Sphere)obj).setRadius(op.getRadius());
		}
		else{
			obj = new Plane();
		}

		obj.setPosition(op.getPosition());
		//obj.setDirection(op.getRotation());
		obj.setRotation(op.getRotation());
		obj.setMaterial(new Material(op.getColor(), op.getTransparency(), op.getRoughness(), op.getEmission(), op.getRefractiveIndex()));
		// obj.setId(ControllerForModel.getInstance().getNewID()); dont need it - new id is provided by renderable constructor
		obj.setName(op.getName());
		obj.setType(op.getType());

		return obj;
	}

	public static ObjectProperties getObjectPropertiesFromRenderable(Renderable obj) {
		ObjectProperties op = new ObjectProperties();

		op.setName(obj.getName());
		op.setColor(obj.getMaterial().getColor());
		op.setEmission(obj.getMaterial().getEmission());
		op.setRefractiveIndex(obj.getMaterial().getRefractiveIndex());
		op.setRoughness(obj.getMaterial().getRoughness());
		op.setTransparency(obj.getMaterial().getTransparency());
		op.setId(obj.getId());
		op.setPosition(obj.getPosition());
		op.setRotation(obj.getRotation());
		if(obj instanceof Sphere)
			op.setRadius(((Sphere)obj).getRadius());
		op.setType(obj.getType());

		return op;
	}
}
