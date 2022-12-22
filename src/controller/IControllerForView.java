package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import utils.ObjectProperties;

public interface IControllerForView {

    // -------------------- Scene File Handler ---------------------
	public void importScene(File file);

	public void saveScene(File file);

	// -------------------- Camera Controller Handler ---------------------
	public void setLastMouseClick(int x, int y);

	public void mouseDraggedAt(int x, int y);

	public void wheelScrollAmount(int unitsToScroll);

	public void setLookAtPoint(double xLookAtValue, double yLookAtValue, double zLookAtValue);

	public void lookAtSelectedObject();
	
	public void setAmbientLight(double value);
	
	// -------------------- Rendering logic ---------------------
	public void renderScene();

	public int getRenderingProgress();

	public BufferedImage getRenderingPartialImage();

	public void saveRenderedImage(File file);

	public void stopRenderer();

	public void updateRenderOccupationInPreview();

	public void updatePreview();

	// -------------------- Object logic ---------------------
	public ArrayList<ObjectProperties> getObjectPropertiesListFromModel();

	public void setObjectProperties(ObjectProperties op);

	public String getSelectedObjectName();

	public boolean changeObjectName(String text);

	public boolean createObject(ObjectProperties op);

	public void removeSelected();

	public void deleteAll();
	// -------------------- View Handler ---------------------
	public void setSelectedObjectTab();

	public void repaintList();

	// -------------------- Application Handler -------------------
	public void quitApplication();

	public void quitAndSaveApplication(File selectedFile);

	public void selectObject(int x, int y);

    public int getSceneComplexity();

    public int getObjectNumber();

	// ------------------- Scene Information ------------------
    public void updateInformationLabel();
}
