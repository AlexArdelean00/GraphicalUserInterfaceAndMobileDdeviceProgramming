package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

import model.Model;
import utils.ObjectProperties;
import view.View;

public class ControllerForView implements IControllerForView{

	private static final int PREVIEW_RENDER_SCALE = 5;
	private static final int PREVIEW_SAMPLE_PER_PIXEL = 4;
	
	private static final double SAMPLE_RENDERING_COMPLEXITY = 1.0/10;
	private static final double PIXEL_RENDERING_COMPLEXITY = 1.0/(100000);

	private int lastMouseX, lastMouseY;
	private BufferedImage previewImage;
	private BufferedImage renderImage; 
	private int renderingProgress;
	private SwingWorker<Void, Void> renderer;
	private int previewRenderScale;
	private int previewSamplePerPixel;

	// Static Field
	private static ControllerForView instance = null;

	// Constructor
	private ControllerForView() {
		previewRenderScale = PREVIEW_RENDER_SCALE;
		previewSamplePerPixel = PREVIEW_SAMPLE_PER_PIXEL;
		
	}

	public static IControllerForView getInstance() {
		if (instance == null)
			instance = new ControllerForView();
		return instance;
	}

	// -------------------- Scene File Handler ---------------------
	@Override
	public void importScene(File file) {
		// Load the filepath scene to model and update preview and list
		if(file != null){
			String filepath = file.getAbsolutePath();
			Model.getInstance().loadScene(filepath);
			updatePreview();
			View.getInstance().updateListFromModel();
			View.getInstance().updateInformationLabel();
			//System.out.println("Scene imported from " + filepath);
		}
		else{
			//System.out.println("No file selected");
		}
		
	}

	@Override
	public void saveScene(File file) {
		// Save the scene to "file"
		if(file != null){
			String filepath = file.getAbsolutePath();
			Model.getInstance().getSceneFile().saveSceneToFile(filepath);;
			//System.out.println("Scene saved to " + filepath);
		}
		else{
			//System.out.println("No file selected");
		}
	}

	// -------------------- Camera Controller Handler ---------------------
	@Override
	public void setLastMouseClick(int x, int y) {
		this.lastMouseX = x;
		this.lastMouseY = y;
	}

	@Override
	public void mouseDraggedAt(int x, int y) {
		int deltaX = x-this.lastMouseX;
		int deltaY = y-this.lastMouseY;
		
		Model.getInstance().arcballCamera(deltaX, deltaY);
		this.setLastMouseClick(x, y);
		updatePreview();
	}

	@Override
	public void wheelScrollAmount(int unitsToScroll) {
		Model.getInstance().moveCameraForwardBackward(unitsToScroll);
	}

	@Override
	public void setLookAtPoint(double xLookAtValue, double yLookAtValue, double zLookAtValue) {
		Model.getInstance().setLookAtPoint(xLookAtValue, yLookAtValue, zLookAtValue);
		updatePreview();
	}

	@Override
	public void lookAtSelectedObject() {
		int id = View.getInstance().getSelectedId();
		Model.getInstance().setLookAtPointById(id);
		updatePreview();
	}
	
	@Override
	public void setAmbientLight(double value) {
		Model.getInstance().setAmbientLight(value);
		updatePreview();
	}
	
	// -------------------- Rendering logic ---------------------
	@Override
	public void renderScene() {
		int widthResolution = View.getInstance().getHorizontalRenderingResolution();
		int heightResolution = View.getInstance().getVerticalRenderingResolution();
		int samplePerPixel = View.getInstance().getSamplePerPixel();
		renderImage = new BufferedImage(widthResolution, heightResolution, BufferedImage.TYPE_INT_RGB);
		Model.getInstance().setCameraDimensions(widthResolution, heightResolution);
		Model.getInstance().setVirtualScreenDimesion(View.getInstance().getWidthOccupation());
		//System.out.println(View.getInstance().getWidthOccupation());
		renderingProgress=0;

		renderer = new SwingWorker<Void, Void>() {
			@Override
			public Void doInBackground() {
				//System.out.println("Rendering Started....");
				for(int x=0; x<widthResolution; x++){
					for(int y=0; y<heightResolution; y++){
						renderImage.setRGB(x,y, Model.getInstance().getPixelColor(y,x,samplePerPixel));
						renderingProgress= (int)((double)(y+x*heightResolution)/(widthResolution*heightResolution)*100)+1;
					}
					if(isCancelled()){
						//System.out.println("Rendering Interrupted");
						break;
					}
					//renderingProgress= (int)((double)x/widthResolution*100)+1;
				}
		
				return null;
			}

			public void done() {
				//System.out.println("Rendering Completed!!!");
			}
		};;

		renderer.execute();
	}

	@Override
	public int getRenderingProgress() {
		return renderingProgress;
	}

	@Override
	public BufferedImage getRenderingPartialImage() {
		return this.renderImage;
	}

	@Override
	public void saveRenderedImage(File file) {
		if(file != null){
			file = new File(file.getAbsolutePath()+ ".jpg");
			try {
				ImageIO.write(renderImage, "jpg", file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void stopRenderer() {
		renderer.cancel(true);
	}

	@Override
	public void updateRenderOccupationInPreview() {
		int previewWidth = View.getInstance().getHorizontalPreviewResolution();
		int previewHeight = View.getInstance().getVerticalPreviewResolution();
		int renderWidth = View.getInstance().getHorizontalRenderingResolution();
		int renderHeight = View.getInstance().getVerticalRenderingResolution();
		int previewBorder = View.getInstance().getPreviewBorder();

		double previewAspectRatio = (double)previewWidth/previewHeight;
		double renderAspectRatio = (double)renderWidth/renderHeight;

		if(renderAspectRatio >= previewAspectRatio){
			// Width full occupation
			double w = (double)(previewWidth-(double)previewWidth/previewBorder)/previewWidth;
			View.getInstance().setRenderWidthOccupation(w);
			View.getInstance().setRenderHeightOccupation(w*previewAspectRatio/renderAspectRatio);
		}
		else {
			// Height full occupation
			double h = (double)(previewHeight-(double)previewHeight/previewBorder)/previewHeight;
			View.getInstance().setRenderHeightOccupation(h);
			View.getInstance().setRenderWidthOccupation(h/previewAspectRatio*renderAspectRatio);
		}
	}

	@Override
	public void updatePreview() {
		int previewWidht = View.getInstance().getPreviewWidth()/previewRenderScale;
		int previewHeight = View.getInstance().getPreviewHeight()/previewRenderScale;
		Model.getInstance().setCameraDimensions(previewWidht, previewHeight);
		Model.getInstance().setVirtualScreenDimesion(1);
		boolean isImageResolutionCorrect = true;
		if(this.previewImage != null)
			isImageResolutionCorrect = this.previewImage.getWidth() == previewWidht && this.previewImage.getHeight() == previewHeight;
		if(this.previewImage == null || !isImageResolutionCorrect){
			this.previewImage = new BufferedImage(previewWidht, previewHeight, BufferedImage.TYPE_INT_RGB);
		}

		for (int i = 0; i < previewWidht; i++) {
			for (int j = 0; j < previewHeight; j++) {
				previewImage.setRGB(i, j, Model.getInstance().getPixelColor(j, i, previewSamplePerPixel));
			}
		}

		View.getInstance().updatePreview(previewImage);
	}

	// -------------------- Object logic ---------------------
	@Override
	public ArrayList<ObjectProperties> getObjectPropertiesListFromModel() {
		return Model.getInstance().getObjectPropertiesList();
	}

	@Override
	public void setObjectProperties(ObjectProperties op) {
		if(Model.getInstance().setObjectProperties(op))
			updatePreview();
	}

	@Override
	public String getSelectedObjectName() {
		return View.getInstance().getSelectedObjectName();
	}

	@Override
	public boolean changeObjectName(String text) {
		return Model.getInstance().changeObjectName(View.getInstance().getSelectedId(), text);
	}

	@Override
	public boolean createObject(ObjectProperties op) {
		if(!Model.getInstance().isNameAvalible(op.getName()))
			return false;
		else{
			Model.getInstance().createObj(op);
			View.getInstance().updateListFromModel();
			this.updatePreview();
			View.getInstance().updateInformationLabel();
			return true;
		}
	}

	@Override
	public void removeSelected() {
		int id = View.getInstance().getSelectedId();
		Model.getInstance().removeObjectById(id);
		updatePreview();
		View.getInstance().updateListFromModel();	
		View.getInstance().updateInformationLabel();
	}

	@Override
	public void deleteAll() {
		Model.getInstance().removeAllObjects();
		updatePreview();
		View.getInstance().updateListFromModel();
	}
	// -------------------- View Handler ---------------------
	@Override
	public void setSelectedObjectTab() {
		View.getInstance().setSelectedObjectTab();
	}

	@Override
	public void repaintList() {
		View.getInstance().repaintList();
	}

	// -------------------- Application Handler -------------------
	@Override
	public void quitApplication() {
		View.getInstance().quit();
	}

	@Override
	public void quitAndSaveApplication(File selectedFile) {
		this.saveScene(selectedFile);
		View.getInstance().quit();
	}

	@Override
	public void selectObject(int x, int y) {	
		int previewWidht = View.getInstance().getHorizontalPreviewResolution();
		int previewHeight = View.getInstance().getVerticalPreviewResolution();
		Model.getInstance().setCameraDimensions(previewWidht, previewHeight);
		Model.getInstance().setVirtualScreenDimesion(1);
		int selectedId = Model.getInstance().getObjectIdAt(x, y);
		View.getInstance().selectId(selectedId);
	}

	// ------------------------ Scene information --------------------------
	@Override
	public int getSceneComplexity() {
		double complexity = 0;
		ArrayList<ObjectProperties> list = Model.getInstance().getObjectPropertiesList();
		int hRes = View.getInstance().getHorizontalRenderingResolution();
		int vRes = View.getInstance().getVerticalRenderingResolution();
		int sample = View.getInstance().getSamplePerPixel();
		
		int pixelNumb = hRes*vRes;
		complexity += pixelNumb*PIXEL_RENDERING_COMPLEXITY;
		complexity += sample*SAMPLE_RENDERING_COMPLEXITY;
		for (ObjectProperties op : list){
			String t = op.getType();
			if(t.equals(ObjectProperties.SPHERE_OBJ_TYPE)){
				complexity += ObjectProperties.SPHERE_COMPLEXITY;
			}
			else if(t.equals(ObjectProperties.SPHERE_OBJ_TYPE)){
				complexity += ObjectProperties.PLANE_COMPLEXITY;
			}
		}
		if(complexity >= ObjectProperties.MAX_COMPLEXITY)
			complexity = ObjectProperties.MAX_COMPLEXITY;
		complexity = complexity/ObjectProperties.MAX_COMPLEXITY*10;
		//System.out.println(complexity);
		return (int)complexity;
	}

	@Override
	public int getObjectNumber() {
		return Model.getInstance().getObjectPropertiesList().size();
	}

	@Override
	public void updateInformationLabel() {
		View.getInstance().updateInformationLabel();
	}

	
}
