package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.ControllerForView;


public class RenderTab extends JPanel{

       // Instance Attribute
       private JSpinner horizontalResolutionSetter;
       private JSpinner verticalResolutionSetter;  
       private JSpinner samplePerPixleSetter;
       private JSpinner lookAtX;
       private JSpinner lookAtY;
       private JSpinner lookAtZ;
       private int currentRow;
       private JButton lookAtSelectedObject;
       private JButton startRendering;
       private double xLookAtValue, yLookAtValue, zLookAtValue;
   
       // Constants
       private final static int DEFAULT_VERTICAL_RESOLUTION = 720;
       private final static int MIN_RESOLUTION = 20;
       private final static int MAX__RESOLUTION = 10000;
       private final static int DEFAULT_HORIZONTAL_RESOLUTION = 1080;
       private final static double RESOLUTION_SETTER_STEP = 1;
       private final static String HORIZONTAL_RESOLUTION_STRING = "Horizontal Resolution";
       private final static String VERTICAL_RESOLUTION_STRING = "Vertical Resolution";
       private final static int DEFAULT_SAMPLE_PER_PIXEL = 100;
       private final static int SAMPLE_SETTER_STEP = 1;
       private final static int MIN_SAMPLE_PER_PIXEL = 1;
       private final static int MAX_SAMPLE_PER_PIXEL = 1000;
       private final static String SAMPLE_PER_PIXEL_STRING = "Sample Per Pixel";
       private final static String LOOK_AT_POINT_STRING = "Look At Point";
       private final static double MAX_LOOK_AT_POINT = 10000;
       private final static double MIN_LOOK_AT_POINT = -MAX_LOOK_AT_POINT;
       private final static double DEFAULT_LOOK_AT_POINT = 0;
       private final static double LOOK_AT_POINT_STEP = 0.1;
       private final static String LOOK_AT_POINT_X_STRING = "X";
       private final static String LOOK_AT_POINT_Y_STRING = "Y";
       private final static String LOOK_AT_POINT_Z_STRING = "Z";
       private final static String START_RENDERING_BUTTON_STRING = "Start Rendering";
       private final static Color BACKGROUND_COLOR = Color.WHITE;
       private final static String LOOK_AT_SELECTED_OBJECT_BUTTON_STRING = "Look At Selected Object";
       private final static String BORDER_TITLE_STRING = "Render Settings";
       private final static int GAP = 10;

    public RenderTab() {
        // -----------------------Component Creation----------------------
        // Create horizontal resolution setter row
        JLabel horizontalResolutionLabel = new JLabel(HORIZONTAL_RESOLUTION_STRING);
        SpinnerModel horizontalResolutionModel = new SpinnerNumberModel(
            DEFAULT_HORIZONTAL_RESOLUTION, MIN_RESOLUTION, MAX__RESOLUTION, RESOLUTION_SETTER_STEP);
        this.horizontalResolutionSetter = new JSpinner(horizontalResolutionModel);
        this.horizontalResolutionSetter.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent e) {
                ControllerForView.getInstance().updateRenderOccupationInPreview();
                ControllerForView.getInstance().updatePreview();
                ControllerForView.getInstance().updateInformationLabel();
            }
            
        });

        // Create vertical resolution setter row
        JLabel verticalResolutionLabel = new JLabel(VERTICAL_RESOLUTION_STRING);
        SpinnerModel verticalResolutionModel = new SpinnerNumberModel(
            DEFAULT_VERTICAL_RESOLUTION, MIN_RESOLUTION, MAX__RESOLUTION, RESOLUTION_SETTER_STEP);
        this.verticalResolutionSetter = new JSpinner(verticalResolutionModel);
        this.verticalResolutionSetter.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent e) {
                ControllerForView.getInstance().updateRenderOccupationInPreview();
                ControllerForView.getInstance().updatePreview();
                ControllerForView.getInstance().updateInformationLabel();
            }
            
        });

        // Create sample per pixel setter
        JLabel samplePerPixelLabel = new JLabel(SAMPLE_PER_PIXEL_STRING);
        SpinnerModel samplePerPixelModel = new SpinnerNumberModel(
            DEFAULT_SAMPLE_PER_PIXEL, MIN_SAMPLE_PER_PIXEL, MAX_SAMPLE_PER_PIXEL, SAMPLE_SETTER_STEP);
        this.samplePerPixleSetter = new JSpinner(samplePerPixelModel);
        this.samplePerPixleSetter.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent e) {
                ControllerForView.getInstance().updateInformationLabel();
            }
            
        });

        // Create set look at point setter
        JLabel lookAtPointLabel = new JLabel(LOOK_AT_POINT_STRING);
        // X component
        JLabel lookAtPointXLabel = new JLabel(LOOK_AT_POINT_X_STRING);
        SpinnerModel lookAtXModel = new SpinnerNumberModel(
            DEFAULT_LOOK_AT_POINT, MIN_LOOK_AT_POINT, MAX_LOOK_AT_POINT, LOOK_AT_POINT_STEP);
        this.lookAtX = new JSpinner(lookAtXModel);
        this.lookAtX.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                JSpinner spinner = (JSpinner) e.getSource();
                xLookAtValue = (double)spinner.getValue();
                ControllerForView.getInstance().setLookAtPoint(xLookAtValue, yLookAtValue, zLookAtValue );
            }
        });
        // Y component
        JLabel lookAtPointYLabel = new JLabel(LOOK_AT_POINT_Y_STRING);
        SpinnerModel lookAtYModel = new SpinnerNumberModel(
            DEFAULT_LOOK_AT_POINT, MIN_LOOK_AT_POINT, MAX_LOOK_AT_POINT, LOOK_AT_POINT_STEP);
        this.lookAtY = new JSpinner(lookAtYModel);
        this.lookAtY.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                JSpinner spinner = (JSpinner) e.getSource();
                yLookAtValue = (double)spinner.getValue();
                ControllerForView.getInstance().setLookAtPoint(xLookAtValue, yLookAtValue, zLookAtValue );
            }
        });
        // Z component
        JLabel lookAtPointZLabel = new JLabel(LOOK_AT_POINT_Z_STRING);
        SpinnerModel lookAtZModel = new SpinnerNumberModel(
            DEFAULT_LOOK_AT_POINT, MIN_LOOK_AT_POINT, MAX_LOOK_AT_POINT, LOOK_AT_POINT_STEP);
        this.lookAtZ = new JSpinner(lookAtZModel);
        this.lookAtZ.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                JSpinner spinner = (JSpinner) e.getSource();
                zLookAtValue = (double)spinner.getValue();
                ControllerForView.getInstance().setLookAtPoint(xLookAtValue, yLookAtValue, zLookAtValue );
            }
        });
        // Panel container for components
        JPanel lookAtComponents = new JPanel();
        lookAtComponents.setOpaque(false);
        GridBagLayout layout = new GridBagLayout();
        lookAtComponents.setLayout(layout);
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.weightx = 1;
        gbc1.weighty = 0;
        this.createRow(lookAtComponents, gbc1, lookAtPointXLabel, this.lookAtX, 0);
        this.createRow(lookAtComponents, gbc1, lookAtPointYLabel, this.lookAtY, 1);
        this.createRow(lookAtComponents, gbc1, lookAtPointZLabel, this.lookAtZ, 2);

        // Create lookat selected object
        this.lookAtSelectedObject = new JButton(LOOK_AT_SELECTED_OBJECT_BUTTON_STRING);
        this.lookAtSelectedObject.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                ControllerForView.getInstance().lookAtSelectedObject();
            }
        });

        // Create start rendering button
        this.startRendering = new JButton(START_RENDERING_BUTTON_STRING);
        this.startRendering.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                ControllerForView.getInstance().renderScene();
                new RenderingProgress();
            }
        });


        // ----------------------Layout Definition----------------------
        this.setLayout(new GridLayout(0, 1, 2, 2)); // any numb of row, 1 col, 2 hgap, 2 vgap
        this.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1)
            , BORDER_TITLE_STRING
            , TitledBorder.LEFT // Title horizontal position
            , TitledBorder.TOP)); // Title vertical position

        JPanel panel = new JPanel();
        //panel.setOpaque(true);
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP)); // set the gap between border and internal component
        GridBagConstraints gbc = new GridBagConstraints();

        this.currentRow = 0;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        
        this.createRow(panel, gbc, horizontalResolutionLabel, this.horizontalResolutionSetter, this.currentRow++);

        this.createRow(panel, gbc, verticalResolutionLabel, this.verticalResolutionSetter, this.currentRow++);

        this.createRow(panel, gbc, samplePerPixelLabel, this.samplePerPixleSetter, this.currentRow++);

        this.createRow(panel, gbc, lookAtPointLabel, lookAtComponents, this.currentRow++);

        gbc.gridwidth = 2;
        gbc.gridx = 0;

        gbc.gridy = this.currentRow++;
        panel.add(this.lookAtSelectedObject, gbc);

        gbc.gridy = this.currentRow++;
        panel.add(this.startRendering, gbc);

        gbc.gridwidth = 1;

        gbc.weighty=1;
        gbc.gridx=0;
        gbc.gridy=this.currentRow++;
        JPanel filler = new JPanel();
        filler.setOpaque(false);
        panel.add(filler, gbc); // Fill the void vertical space

        JScrollPane scroller = new JScrollPane(panel);

        this.add(scroller);
    }

    private void createRow(JPanel panel, GridBagConstraints gbc, Component c1, Component c2, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(c1, gbc);
        gbc.gridx = 1;
        panel.add(c2, gbc);
    }

    public int getHeightResolution() {
		return (int)(double)(((JSpinner)verticalResolutionSetter).getValue());
        
	}

	public int getWidthResolution() {
		return (int)(double)(((JSpinner)horizontalResolutionSetter).getValue());
	}

    public int getSamplePerPixel() {
        return (int)(((JSpinner)samplePerPixleSetter).getValue());
    }
}
