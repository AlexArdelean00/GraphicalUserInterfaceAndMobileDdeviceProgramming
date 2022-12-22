package model;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Iterator;

import utils.ObjectProperties;
import utils.SceneFile;
import utils.Vector3;

public class Model implements IModel{
	
	// Instance Methods
	private Scene scene;

	// Static Field
	private static Model instance = null;

	public Model(){
		//System.out.println("Model initialized");
		this.scene = new Scene();
	}

	public static IModel getInstance(){
		if(instance == null)
			instance = new Model();
		return instance;
	}

	// --------------- Scene file handler --------------
	@Override
	public void loadScene(String filepath) {
		SceneFile scn = null;
		try {
			scn = SceneFile.getSceneFileFromFile(filepath);
		} catch (IOException e) {
			//System.out.println("Error loading scene");
		}
		catch(java.lang.NullPointerException e){
			//System.out.println("Error loading scene");
		}
		finally{
			if(scn != null){	
				this.scene.loadScene(scn);
			}
		}
	}

	@Override
	public SceneFile getSceneFile() {
		// Return a SceneFile version of this scene
		ArrayList<ObjectProperties> list = this.getObjectPropertiesList();
		SceneFile sf = new SceneFile();
		for(ObjectProperties op : list){
			sf.add(op);
		}
		return sf;
	}

	// -------------------- Camera Handler ---------------------
	@Override
	public int getPixelColor(int r, int c, int samplePerPixel) {
		return this.scene.getCamera().getPixelColor(c, r, samplePerPixel, this.scene.getObjects());
	}

	@Override
	public void setCameraDimensions(int width, int height) {
		this.scene.getCamera().setDimension(width, height);
	}

	@Override
	public void setVirtualScreenDimesion(double widthOccupation) {
		this.scene.getCamera().setVirtualDim(widthOccupation);
	}

	@Override
	public void arcballCamera(int x, int y) {
		this.scene.getCamera().arcball(x, y);	
	}

	@Override
	public void moveCameraForwardBackward(int unitsToScroll) {
		this.scene.getCamera().lookDirectionShift(unitsToScroll);	
	}

	@Override
	public void setAmbientLight(double value) {
		this.scene.getCamera().setAmbientLight(value);
	}

	@Override
	public void setLookAtPoint(double xLookAtValue, double yLookAtValue, double zLookAtValue) {
		this.scene.getCamera().setLookAtPoint(new Vector3(xLookAtValue, yLookAtValue, zLookAtValue));
	}

	@Override
	public void setLookAtPointById(int id) {
		Renderable obj = findObj(id);
		if(obj != null)
			this.scene.getCamera().setLookAtPoint(obj.getPosition());
	}

	// ---------------- Object Handler -----------------------
	@Override
	public boolean setObjectProperties(ObjectProperties op) {
		Renderable obj = findObj(op.getId());
		if(obj != null){
			//System.out.println("set obj success " + op.getPosition());
			obj.setPosition(op.getPosition());
			//obj.setDirection(op.getRotation());
			obj.setRotation(op.getRotation());
			Material m = obj.getMaterial();
			m.setEmission(op.getEmission());
			m.setTransparency(op.getTransparency());
			m.setRefractiveIndex(op.getRefractiveIndex());
			m.setRoughness(op.getRoughness());
			m.setColor(op.getColor());
			if(obj.getType().equals(ObjectProperties.SPHERE_OBJ_TYPE)){
				((Sphere)obj).setRadius(op.getRadius());
			}
			return true;
		}
		return false;
	}

	public Renderable findObj(int id) {
		Iterator<Renderable> iter = this.scene.getObjects().iterator();
		while(iter.hasNext()){
			Renderable obj = iter.next();

			if(obj.getId() == id)
				return obj;
		}
		return null;
	}

	@Override
	public boolean changeObjectName(int selectedId, String text) {
		for(Renderable r : this.scene.getObjects()){
			if(r.getName().equals(text)) // Name already taken
				return false;
		}

		// No same name found
		Renderable obj = findObj(selectedId);
		if(obj != null){
			obj.setName(text);
			return true;
		}
		return false;
	}

	@Override
	public boolean createObj(ObjectProperties op) {
		this.scene.addObject(Scene.getRenderableFromObjectProperties(op));
		return true;
	}

	@Override
	public void removeObjectById(int id) {
		Renderable obj = findObj(id);

		this.scene.getObjects().remove(obj);
	}

	@Override
	public ArrayList<ObjectProperties> getObjectPropertiesList() {
		// Return a ObjectProperties list of scene objects
		ArrayList<ObjectProperties> objProp = new ArrayList<ObjectProperties>();

		for(Renderable obj : this.scene.getObjects()){
			objProp.add(Scene.getObjectPropertiesFromRenderable(obj));
		}

		return objProp;
	}

	@Override
	public boolean isNameAvalible(String name) {
		for(Renderable r : this.scene.getObjects()){
			if(r.getName().equals(name)) // Name already taken
				return false;
		}
		return true;
	}

	@Override
	public void removeAllObjects() {
		this.scene.getObjects().clear();
	}

	@Override
	public int getObjectIdAt(int x, int y) {
		return this.scene.getCamera().getObjectIdAt(x, y, this.scene.getObjects());
	}

}
