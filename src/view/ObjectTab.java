package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JColorChooser;

import utils.ObjectProperties;

import controller.ControllerForView;

public class ObjectTab extends JPanel{

    // Constants
    private static final String BORDER_TITLE_STRING = "Object Properties";
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final int GAP = 5;
    private static final String NAME_LABEL_STRING = "Name";
    private static final String CHANGE_NAME_BUTTON_STRING = "Apply";
    protected static final String INVALID_NAME_DIALOG_STRING = "Invalid name, name already used";
    protected static final String TOO_LONG_NAME_DIALOG_STRING = "Too long name";
    private static final String POSITION_LABEL_STRING = "Position";
    private static final String POSITION_X_STRING = "X";
    private static final String POSITION_Y_STRING = "Y";
    private static final String POSITION_Z_STRING = "Z";
    private static final String ROTATION_LABEL_STRING = "Rotation";
    private static final String ROTATION_X_STRING = "X angle";
    private static final String ROTATION_Y_STRING = "Y angle";
    private static final String ROTATION_Z_STRING = "Z angle";
    private static final String COLOR_LABEL_STRING = "Color";
    private static final String TRANSPARENCY_LABEL_STRING = "Transparency";
    private static final String ROUGHNESS_LABEL_STRING = "Roughness";
    private static final String EMISSION_LABEL_STRING = "Emission";
    private static final String REFRACTIVE_INDEX_LABEL_STRING = "Refractive Index";
    private static final String MATERIAL_LABEL_STRING = "Material";
    private static final String CHOOSE_COLOR_BUTTON_STRING = "Choose Color";
    private static final String RADIUS_LABEL_STRING = "Radius";

    // Instance Attribute
    private ObjectProperties op;
    private int currentRow;
    private GridBagConstraints gbc;
    private JPanel panel;
    private boolean isCreation;

    public ObjectTab(ObjectProperties op, boolean isCreation) {

        this.op = op;
        this.currentRow = 0;
        this.isCreation = isCreation;

        // ----------------------Layout Definition----------------------
        this.setLayout(new GridLayout(0, 1, 2, 2)); // any numb of row, 1 col, 2 hgap, 2 vgap
        this.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1)
            , BORDER_TITLE_STRING
            , TitledBorder.LEFT // Title horizontal position
            , TitledBorder.TOP)); // Title vertical position

        this.panel = new JPanel();
        //panel.setOpaque(true);
        this.panel.setBackground(BACKGROUND_COLOR);
        this.panel.setLayout(new GridBagLayout());
        this.panel.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP)); // set the gap between border and internal component
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        if(this.op != null)
            this.createObjectPropertiesInterface();

        // Filler
        gbc.weighty = 1;
        gbc.gridy = this.currentRow++;
        JPanel filler = new JPanel();
        filler.setOpaque(false);
        panel.add(filler, gbc); // Fill the void vertical space   

        JScrollPane scroller = new JScrollPane(panel);

        this.add(scroller);
    }

    private void createObjectPropertiesInterface() {
        /*private String name;
        private Vector3 position;
        private Vector3 rotation;
        Material
        private double radius;*/

        this.createNameSetter();

        this.createPositionSetter();

        if(!Arrays.asList(ObjectProperties.EXCLUDED_FROM_ROTATION_SETTER).contains(op.getType()))
            this.createRotationSetter();

        this.createMaterialSetter();

        if(op.getType().equals(ObjectProperties.SPHERE_OBJ_TYPE))
            this.createRadiusSetter();

    }

    private void createRadiusSetter() {
        JLabel radiusLabel = new JLabel(RADIUS_LABEL_STRING);
        SpinnerModel radiusModel = new SpinnerNumberModel(
            op.getRadius(), ObjectProperties.MIN_RADIUS, ObjectProperties.MAX_RADIUS, ObjectProperties.RADIUS_STEP);
        JSpinner radiusSpinner = new JSpinner(radiusModel);
        gbc.gridy = this.currentRow++;
        gbc.gridx = 0;
        panel.add(radiusLabel, gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        panel.add(radiusSpinner, gbc);
        gbc.gridwidth = 1;
        radiusSpinner.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                JSpinner spinner = (JSpinner) e.getSource();
                double value = (double)spinner.getValue();
                //System.out.println("radius changed in: " + value);
                op.setRadius(value);
                ControllerForView.getInstance().setObjectProperties(op);
            }
        });
    }

    private void createMaterialSetter() {
        gbc.gridx = 0;    
        gbc.gridy = this.currentRow++;
        JLabel materialLabel = new JLabel(MATERIAL_LABEL_STRING);
        panel.add(materialLabel, gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 1;

        // Color chooser
        JLabel colorLabel = new JLabel(COLOR_LABEL_STRING);
        JButton chooseColor = new JButton(CHOOSE_COLOR_BUTTON_STRING);
        Color defCol = op.getColor().getAWTColor();
        chooseColor.setBackground(defCol);
        chooseColor.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                java.awt.Color col = JColorChooser.showDialog(
                    null,
                    "Choose Background Color",
                    chooseColor.getBackground()
                );
                chooseColor.setBackground(col);
                op.setColor(new utils.Color(col.getRed()/255f, col.getGreen()/255f, col.getBlue()/255f));
                //System.out.println(op.getColor());
                ControllerForView.getInstance().setObjectProperties(op);
            }
        });
        // Transaprency
        double def = op.getTransparency();
        JLabel transparencyLabel = new JLabel(TRANSPARENCY_LABEL_STRING);
        SpinnerModel transparencyModel = new SpinnerNumberModel(def, 
            ObjectProperties.MIN_TRANSPARENCY, ObjectProperties.MAX_TRANSPARENCY, 
            ObjectProperties.MATERIAL_PROP_STEP);
        JSpinner transparencySpinner = new JSpinner(transparencyModel);
        transparencySpinner.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                JSpinner spinner = (JSpinner) e.getSource();
                double value = (double)spinner.getValue();
                //System.out.println("transparency changed in: " + value);
                op.setTransparency((float)value);
                ControllerForView.getInstance().setObjectProperties(op);
            }
        });
        // Roughness
        def = op.getRoughness();
        JLabel roughnessLabel = new JLabel(ROUGHNESS_LABEL_STRING);
        SpinnerModel roughnessModel = new SpinnerNumberModel(def, 
            ObjectProperties.MIN_ROUGHNESS, ObjectProperties.MAX_ROUGHNESS,
            ObjectProperties.MATERIAL_PROP_STEP);
        JSpinner roughnessSpinner = new JSpinner(roughnessModel);
        roughnessSpinner.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                JSpinner spinner = (JSpinner) e.getSource();
                double value = (double)spinner.getValue();
                //System.out.println("roughness changed in: " + value);
                op.setRoughness((float)value);
                ControllerForView.getInstance().setObjectProperties(op);
            }
        });
        // Emission
        def = op.getEmission();
        JLabel emissionLabel = new JLabel(EMISSION_LABEL_STRING);
        SpinnerModel emissionModel = new SpinnerNumberModel(def, 
            ObjectProperties.MIN_EMISSION, ObjectProperties.MAX_EMISSION,
            ObjectProperties.MATERIAL_PROP_STEP);
        JSpinner emissionSpinner = new JSpinner(emissionModel);
        emissionSpinner.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                JSpinner spinner = (JSpinner) e.getSource();
                double value = (double)spinner.getValue();
                //System.out.println("emission changed in: " + value);
                op.setEmission((float)value);
                ControllerForView.getInstance().setObjectProperties(op);
            }
        });
        // Refractive Index
        def = op.getRefractiveIndex();
        JLabel refractiveIndexLabel = new JLabel(REFRACTIVE_INDEX_LABEL_STRING);
        SpinnerModel refractiveIndexModel = new SpinnerNumberModel(def, 
            ObjectProperties.MIN_REFRACTIVE_INDEX, ObjectProperties.MAX_REFRACTIVE_INDEX,
            ObjectProperties.MATERIAL_PROP_STEP);
        JSpinner refractiveIndexSpinner = new JSpinner(refractiveIndexModel);
        refractiveIndexSpinner.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                JSpinner spinner = (JSpinner) e.getSource();
                double value = (double)spinner.getValue();
                //System.out.println("refractiveIndex changed in: " + value);
                op.setRefractiveIndex((float)value);
                ControllerForView.getInstance().setObjectProperties(op);
            }
        });

        // Panel container for components
        JPanel components = new JPanel();
        components.setOpaque(false);
        GridBagLayout layout = new GridBagLayout();
        components.setLayout(layout);
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.weightx = 1;
        gbc1.weighty = 0;

        this.createRow(components, gbc1, colorLabel, chooseColor, 0);
        this.createRow(components, gbc1, transparencyLabel, transparencySpinner, 1);
        this.createRow(components, gbc1, roughnessLabel, roughnessSpinner, 2);
        this.createRow(components, gbc1, emissionLabel, emissionSpinner, 3);
        this.createRow(components, gbc1, refractiveIndexLabel, refractiveIndexSpinner, 4);

        panel.add(components, gbc);
    }

    private void createRotationSetter() {
        gbc.gridx = 0;    
        gbc.gridy = this.currentRow++;
        JLabel rotationLabel = new JLabel(ROTATION_LABEL_STRING);
        panel.add(rotationLabel, gbc);

        // X component
        JLabel xLabel = new JLabel(ROTATION_X_STRING);
        double def = this.op.getRotation().getX(); // Default spinner value
        //System.out.println(def);
        SpinnerModel xModel = new SpinnerNumberModel(def, ObjectProperties.MIN_ROTATION_COMPONENT, 
            ObjectProperties.MAX_ROTATION_COMPONENT, ObjectProperties.ROTATION_COMPONENT_STEP);
        JSpinner xSpinner = new JSpinner(xModel);
        xSpinner.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                JSpinner spinner = (JSpinner) e.getSource();
                double value = (double)spinner.getValue();
                //System.out.println("x rotation changed in: " + value);
                op.getRotation().setX(value);
                ControllerForView.getInstance().setObjectProperties(op); // Apply prop to model
            }
        });
        
        // Y component
        def = this.op.getRotation().getY(); // Default spinner value
        //System.out.println(def);
        JLabel yLabel = new JLabel(ROTATION_Y_STRING);
        SpinnerModel yModel = new SpinnerNumberModel(def, ObjectProperties.MIN_ROTATION_COMPONENT, 
            ObjectProperties.MAX_ROTATION_COMPONENT, ObjectProperties.ROTATION_COMPONENT_STEP);
        JSpinner ySpinner = new JSpinner(yModel);
        ySpinner.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                JSpinner spinner = (JSpinner) e.getSource();
                double value = (double)spinner.getValue();
                //System.out.println("y rotation changed in: " + value);
                op.getRotation().setY(value);
                ControllerForView.getInstance().setObjectProperties(op); // Apply prop to model
            }
        });
        
        // Z component
        def = this.op.getRotation().getZ(); // Default spinner value
        //System.out.println(def);
        JLabel zLabel = new JLabel(ROTATION_Z_STRING);
        SpinnerModel zModel = new SpinnerNumberModel(def, ObjectProperties.MIN_ROTATION_COMPONENT, 
            ObjectProperties.MAX_ROTATION_COMPONENT, ObjectProperties.ROTATION_COMPONENT_STEP);
        JSpinner zSpinner = new JSpinner(zModel);
        zSpinner.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                JSpinner spinner = (JSpinner) e.getSource();
                double value = (double)spinner.getValue();
                //System.out.println("z rotation changed in: " + value);
                op.getRotation().setZ(value);
                ControllerForView.getInstance().setObjectProperties(op); // Apply prop to model
            }
        });

        
        // Panel container for components
        JPanel components = new JPanel();
        components.setOpaque(false);
        GridBagLayout layout = new GridBagLayout();
        components.setLayout(layout);
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.weightx = 1;
        gbc1.weighty = 0;
        gbc1.anchor = GridBagConstraints.NORTHWEST;
        this.createRow(components, gbc1, xLabel, xSpinner, 0);
        this.createRow(components, gbc1, yLabel, ySpinner, 1);
        this.createRow(components, gbc1, zLabel, zSpinner, 2);

        gbc.gridwidth = 2;
        gbc.gridx = 1;
        panel.add(components, gbc);
    }

    private void createPositionSetter() {
        gbc.gridx = 0;    
        gbc.gridy = this.currentRow++;
        JLabel positionLabel = new JLabel(POSITION_LABEL_STRING);
        panel.add(positionLabel, gbc);

        // X component
        JLabel xLabel = new JLabel(POSITION_X_STRING);
        double def = this.op.getPosition().getX(); // Default spinner value
        SpinnerModel xModel = new SpinnerNumberModel(def, ObjectProperties.MIN_POSITION_COMPONENT, 
            ObjectProperties.MAX_POSITION_COMPONENT, ObjectProperties.POSITION_COMPONENT_STEP);
        JSpinner xSpinner = new JSpinner(xModel);
        xSpinner.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                JSpinner spinner = (JSpinner) e.getSource();
                double value = (double)spinner.getValue();
                //System.out.println("x changed in: " + value);
                op.getPosition().setX(value);
                ControllerForView.getInstance().setObjectProperties(op); // Apply prop to model
            }
        });
        
        // Y component
        def = this.op.getPosition().getY(); // Default spinner value
        JLabel yLabel = new JLabel(POSITION_Y_STRING);
        SpinnerModel yModel = new SpinnerNumberModel(def, ObjectProperties.MIN_POSITION_COMPONENT, 
            ObjectProperties.MAX_POSITION_COMPONENT, ObjectProperties.POSITION_COMPONENT_STEP);
        JSpinner ySpinner = new JSpinner(yModel);
        ySpinner.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                JSpinner spinner = (JSpinner) e.getSource();
                double value = (double)spinner.getValue();
                //System.out.println("y changed in: " + value);
                op.getPosition().setY(value);
                ControllerForView.getInstance().setObjectProperties(op); // Apply prop to model
            }
        });
        
        // Z component
        def = this.op.getPosition().getZ(); // Default spinner value
        JLabel zLabel = new JLabel(POSITION_Z_STRING);
        SpinnerModel zModel = new SpinnerNumberModel(def, ObjectProperties.MIN_POSITION_COMPONENT, 
            ObjectProperties.MAX_POSITION_COMPONENT, ObjectProperties.POSITION_COMPONENT_STEP);
        JSpinner zSpinner = new JSpinner(zModel);
        zSpinner.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                JSpinner spinner = (JSpinner) e.getSource();
                double value = (double)spinner.getValue();
                //System.out.println("z changed in: " + value);
                op.getPosition().setZ(value);
                ControllerForView.getInstance().setObjectProperties(op); // Apply prop to model
            }
        });

        // Panel container for components
        JPanel components = new JPanel();
        components.setOpaque(false);
        GridBagLayout layout = new GridBagLayout();
        components.setLayout(layout);
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.weightx = 1;
        gbc1.weighty = 0;
        gbc1.anchor = GridBagConstraints.NORTHWEST;
        this.createRow(components, gbc1, xLabel, xSpinner, 0);
        this.createRow(components, gbc1, yLabel, ySpinner, 1);
        this.createRow(components, gbc1, zLabel, zSpinner, 2);

        gbc.gridwidth = 2;
        gbc.gridx = 1;
        this.panel.add(components, gbc);
        
    }

    private void createNameSetter() {
        gbc.gridx = 0;
        gbc.gridy = this.currentRow++;
        JLabel nameLabel = new JLabel(NAME_LABEL_STRING);
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        JTextArea nameTextArea = new JTextArea(); // Problema quando inserisci troppi caratteri => limitare il numero di caratteri
        nameTextArea.setText(op.getName());
        panel.add(nameTextArea, gbc);
        if(isCreation){
            nameTextArea.getDocument().addDocumentListener(new DocumentListener(){

                @Override
                public void insertUpdate(DocumentEvent e) {
                    op.setName(nameTextArea.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    op.setName(nameTextArea.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    op.setName(nameTextArea.getText());
                }
                
            });
        }
        if(!isCreation){
            gbc.gridx = 2;
            JButton changeNameButton = new JButton(CHANGE_NAME_BUTTON_STRING);
            panel.add(changeNameButton, gbc);
            changeNameButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    //System.out.println("cambio nome in" + nameTextArea.getText());
                    if(nameTextArea.getText().length()>ObjectProperties.MAX_NAME_CHAR){
                        JOptionPane.showMessageDialog(null, TOO_LONG_NAME_DIALOG_STRING);
                        nameTextArea.setText(ControllerForView.getInstance().getSelectedObjectName());
                    }
                    else if(!ControllerForView.getInstance().changeObjectName(nameTextArea.getText())){
                        JOptionPane.showMessageDialog(null, INVALID_NAME_DIALOG_STRING);
                        nameTextArea.setText(ControllerForView.getInstance().getSelectedObjectName());
                    }
                    else{
                        // Update changed name in object list
                        //System.out.println("list name updated");
                        op.setName(nameTextArea.getText());
                        ControllerForView.getInstance().repaintList();
                        op.setName(nameTextArea.getText());
                        //ControllerForView.getInstance().setObjectProperties(op);
                    }
                }
            });
        }
    }

    private void createRow(JPanel pan, GridBagConstraints gbc, Component c1, Component c2, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        pan.add(c1, gbc);
        gbc.gridx = 1;
        pan.add(c2, gbc);
    }

    public ObjectProperties getOp() {
        return this.op;
    }

    @Override
    public String toString() {
        return this.op.getName();
    }

    public void resetOp() {
        this.op = null;
    }
}
