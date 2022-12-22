package model;

import java.util.ArrayList;

import utils.ObjectProperties;
import utils.SceneFile;

public interface IModel {
	// --------------- Scene file handler --------------
	public void loadScene(String filepath);

	public SceneFile getSceneFile();

	// -------------------- Camera Handler ---------------------
	public int getPixelColor(int r, int c, int samplePerPixel);

	public void setCameraDimensions(int width, int height);

	public void setVirtualScreenDimesion(double widthOccupation);

	public void arcballCamera(int x, int y);

	public void moveCameraForwardBackward(int unitsToScroll);

	public void setAmbientLight(double value);

	public void setLookAtPoint(double xLookAtValue, double yLookAtValue, double zLookAtValue);

	public void setLookAtPointById(int id);

	// ---------------- Object Handler -----------------------
	public boolean setObjectProperties(ObjectProperties op);

	public boolean changeObjectName(int selectedId, String text);

	public boolean createObj(ObjectProperties op);

	public void removeObjectById(int id);

	public ArrayList<ObjectProperties> getObjectPropertiesList();

	public boolean isNameAvalible(String name);

    public void removeAllObjects();

    public int getObjectIdAt(int x, int y);

}
