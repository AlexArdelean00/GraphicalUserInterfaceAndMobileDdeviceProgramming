package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.ControllerForView;

public class PreviewPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener, ComponentListener{

	private BufferedImage image;
	private double verticalOccupation;
	private double horizontalOccupation;
	private int samplePerPixel;
	private int renderResolutionFactor;

	private final double PREVIEW_SHADE_PERCENTAGE = 0.5;
	private final int PREVIEW_BORDER = 10;

    public PreviewPanel() {
        this.setPreferredSize(new Dimension(500, 400));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
		this.addComponentListener(this);
    }

	public void updateImage(BufferedImage img) {
		this.image = img;
		ControllerForView.getInstance().updateRenderOccupationInPreview();
		repaint();
	}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(this.image, 0, 0, this.getWidth(), this.getHeight(), this);
		
		int leftBorder = (int)(this.getWidth()*(1-this.horizontalOccupation)/2);
		int rightBorder = this.getWidth()-leftBorder;
		int topBorder = (int)(this.getHeight()*(1-this.verticalOccupation)/2);
		int bottomBorder = this.getHeight()-topBorder;

		drawTransparentRect(g2d, PREVIEW_SHADE_PERCENTAGE, 0,0,this.getWidth(), topBorder);
		drawTransparentRect(g2d, PREVIEW_SHADE_PERCENTAGE,0,bottomBorder, this.getWidth(), this.getHeight());
		drawTransparentRect(g2d, PREVIEW_SHADE_PERCENTAGE, 0, topBorder, leftBorder, bottomBorder-topBorder);
		drawTransparentRect(g2d, PREVIEW_SHADE_PERCENTAGE, rightBorder, topBorder, this.getWidth(), bottomBorder-topBorder);
	}

	private void drawTransparentRect(Graphics2D g2d, double transparentPercentage,
									 int startX, int startY, int endX, int endY) {
		int alpha = (int)(transparentPercentage*255); // 50% transparent
		Color myColour = new Color(0, 0, 0, alpha);
		g2d.setPaint(myColour);
		g2d.fillRect(startX, startY, endX, endY);
	}

    // MouseMotionListener Interface
	@Override
	public void mouseDragged(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)){
			ControllerForView.getInstance().mouseDraggedAt(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

    // MouseListener Interface
	@Override
	public void mouseClicked(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)){
			//System.out.println("mouse clicked");
			ControllerForView.getInstance().selectObject(e.getX(), e.getY());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)){
			//System.out.println("mouse pressed");
			ControllerForView.getInstance().setLastMouseClick(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

    // MouseWheelListener Interfae
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		ControllerForView.getInstance().wheelScrollAmount(e.getUnitsToScroll());
		ControllerForView.getInstance().updatePreview();
	}

	// ComponentListener Interface
	@Override
	public void componentResized(ComponentEvent e) {
		ControllerForView.getInstance().updatePreview();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	// Instance Methods
	public double getWidthOccupation() {
		return this.horizontalOccupation;
	}

	public int getSamplePerPixel() {
		return this.samplePerPixel;
	}

	public int getResolutionFactor() {
		return this.renderResolutionFactor;
	}

	public void setHorizontalOccupation(double horizontalOccupation) {
		this.horizontalOccupation = horizontalOccupation;
	}

	public void setVerticalOccupation(double verticalOccupation) {
		this.verticalOccupation = verticalOccupation;
	}

	public int getPreviewBorder() {
		return PREVIEW_BORDER;
	}

}
