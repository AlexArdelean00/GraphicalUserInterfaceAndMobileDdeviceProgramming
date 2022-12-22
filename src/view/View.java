package view;

import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.ControllerForView;

public class View implements IView {
	private static View instance;

	protected MainGUI mainGUI = null;

	private View() {

	}

	public static IView getInstance() {
		if (instance == null)
			instance = new View();
		return instance;
	}

	@Override
	public void startWindow() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (mainGUI == null)
					mainGUI = new MainGUI();
				mainGUI.setVisible(true);
				ControllerForView.getInstance().updatePreview();
				ControllerForView.getInstance().updateInformationLabel();
			}
		});
	}

	// ----------------- Preview Handler -------------------
	@Override
	public void updatePreview(BufferedImage img) {
		// Set and repaint the preview img
		this.mainGUI.getPreviewPanel().updateImage(img);
	}

	@Override
	public int getPreviewWidth() {
		// Return the current preview panel width
		return this.mainGUI.getPreviewPanel().getWidth();
	}

	@Override
	public int getPreviewHeight() {
		// Return the current preview panel height
		return this.mainGUI.getPreviewPanel().getHeight();
	}

	@Override
	public int getHorizontalPreviewResolution() {
		return this.mainGUI.getPreviewPanel().getWidth();
	}

	@Override
	public int getVerticalPreviewResolution() {
		return this.mainGUI.getPreviewPanel().getHeight();
	}

	@Override
	public double getWidthOccupation() {
		// Get the current rendering width occupation of the preview panel
		return this.mainGUI.getPreviewPanel().getWidthOccupation();
	}

	@Override
	public int getPreviewBorder() {
		// Return the preview border
		return this.mainGUI.getPreviewPanel().getPreviewBorder();
	}

	@Override
	public void setRenderWidthOccupation(double occupation) {
		this.mainGUI.getPreviewPanel().setHorizontalOccupation(occupation);
	}

	@Override
	public void setRenderHeightOccupation(double occupation) {
		this.mainGUI.getPreviewPanel().setVerticalOccupation(occupation);
	}

	// ------------------ ObjectTab Handler -------------------
	@Override
	public void setSelectedObjectTab() {
		// Set the ObjectTab to the current selected ObjectTab in ObjectList
		if(this.mainGUI.getObjectList().getSelectedValue() == null)
			this.mainGUI.setObjectTab(new ObjectTab(null, false));
		else
			this.mainGUI.setObjectTab(this.mainGUI.getObjectList().getSelectedValue());
	}

	// ------------------- RenderTab Handler ------------------------
	@Override
	public int getHorizontalRenderingResolution() {
		// Return the rendering horizontal resolution
		return mainGUI.getRenderTab().getWidthResolution();
	}

	@Override
	public int getVerticalRenderingResolution() {
		// Return the rendering vertical resolution
		return mainGUI.getRenderTab().getHeightResolution();
	}

	@Override
	public int getSamplePerPixel() {
		return this.mainGUI.getRenderTab().getSamplePerPixel();
	}

	// -------------------- ObjectList Handler ----------------------
	@Override
	public String getSelectedObjectName() {
		// This method is used to restore the previous name when user input a incorrect new name
		if(this.mainGUI.getObjectList().getSelectedValue() != null)
			return this.mainGUI.getObjectList().getSelectedValue().getOp().getName();
		else
			return "";
	}

	@Override
	public int getSelectedId() {
		// Return the current selected object id from the object list
		if(this.mainGUI.getObjectList().getSelectedValue() != null)
			return this.mainGUI.getObjectList().getSelectedValue().getOp().getId();
		else
			return -1;
	}

	@Override
	public void repaintList() {
		// Rapaint list used to repaint the list when user change an object name
		this.mainGUI.getObjectList().repaint();
	}

	@Override
	public void updateListFromModel() {
		// When user import scene, create/remove object a list update is needed
		this.mainGUI.getObjectList().updateListFromModel();
	}

	// ------------------- Application Handler ----------------------
	@Override
	public void quit() {
		this.mainGUI.dispatchEvent(new WindowEvent(this.mainGUI, WindowEvent.WINDOW_CLOSING));
	}

	@Override
	public void selectId(int selectedId) {
		this.mainGUI.getObjectList().selectId(selectedId);
	}

	// ------------------- Information label ------------------------
	public void updateInformationLabel() {
		this.mainGUI.getInformationLabel().update();
	}
}