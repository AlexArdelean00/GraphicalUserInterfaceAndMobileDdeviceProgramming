package view;

import java.awt.image.BufferedImage;

public interface IView {
	public void startWindow();

	// ----------------- Preview Handler -------------------
	public void updatePreview(BufferedImage img);

	public int getPreviewWidth();

	public int getPreviewHeight();

	public int getHorizontalPreviewResolution();

	public int getVerticalPreviewResolution();

	public double getWidthOccupation();

	public int getPreviewBorder();

	public void setRenderWidthOccupation(double occupation);

	public void setRenderHeightOccupation(double occupation);

	// ------------------ ObjectTab Handler -------------------
	public void setSelectedObjectTab();

	// ------------------- RenderTab Handler ------------------------
	public int getHorizontalRenderingResolution();

	public int getVerticalRenderingResolution();
	
	public int getSamplePerPixel();

	// -------------------- ObjectList Handler ----------------------
	public String getSelectedObjectName();

	public int getSelectedId();

	public void repaintList();

	public void updateListFromModel();

    public void quit();

    public void selectId(int selectedId);

	// --------------------- Information Label ------------------------
	public void updateInformationLabel();


}
