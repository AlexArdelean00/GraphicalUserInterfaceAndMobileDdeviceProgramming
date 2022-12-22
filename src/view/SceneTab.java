package view;

import java.awt.Color;
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


public class SceneTab extends JPanel implements ActionListener{

    // Constants
    private final static String BORDER_TITLE_STRING = "Scene Settings";
    private final static String ADD_OBJECT_BUTTON_STRING = "Add Object";
    private final static String REMOVE_SELECTED_OBJECT_BUTTON_STRING = "Remove Selected Object";
    private final static int GAP = 10;
    private final static Color BACKGROUND_COLOR = Color.WHITE;
    private static final String AMBIENT_LIGHT_INTENSITY_LABEL_STRING = "Ambient Light Intensity";

    // Instance Attributes
    private JButton addObject;
    private JButton removeSelected;

    public SceneTab() {
        // -----------------------Component Creation----------------------
        JLabel ambientLightIntensityLabel = new JLabel(AMBIENT_LIGHT_INTENSITY_LABEL_STRING);
        SpinnerModel ambientLightModel = new SpinnerNumberModel(0.5, 0, 1, 0.1);
        JSpinner ambientLightSpinner = new JSpinner(ambientLightModel);
        ambientLightSpinner.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent e) {
                ControllerForView.getInstance().setAmbientLight((double)((JSpinner)e.getSource()).getValue());
            }
            
        });
        this.addObject = new JButton(ADD_OBJECT_BUTTON_STRING);
        this.addObject.addActionListener(this);
        this.removeSelected = new JButton(REMOVE_SELECTED_OBJECT_BUTTON_STRING);
        this.removeSelected.addActionListener(this);

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
        gbc.weightx = 1;
        gbc.weighty = 0;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(ambientLightIntensityLabel, gbc);
        gbc.gridx = 1;
        panel.add(ambientLightSpinner, gbc);

        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(this.addObject, gbc);

        gbc.gridy = 2;
        panel.add(this.removeSelected, gbc);

        gbc.gridy = 3;
        gbc.weighty = 1;
        JPanel filler = new JPanel();
        filler.setOpaque(false);
        panel.add(filler, gbc);

        JScrollPane scroller = new JScrollPane(panel);

        this.add(scroller);
    }

    @Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.removeSelected){
            ControllerForView.getInstance().removeSelected();
        }
        if(e.getSource() == this.addObject) {
            //System.out.println("add obj");
            AddObjectDialog createObj = new AddObjectDialog();
            createObj.setVisible(true);
        }
	}
}
