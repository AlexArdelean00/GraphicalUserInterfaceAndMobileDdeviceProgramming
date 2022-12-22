package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import utils.ObjectProperties;

import controller.ControllerForView;

public class AddObjectDialog extends JDialog{

    private static final String CREATE_BUTTON_STRING = "Create Object";
    
    private JPanel cards;
    private JButton createButton;
    private JComboBox<String> selectType;
    private ObjectProperties op;

    public AddObjectDialog() {
        this.cards = new JPanel();
        this.cards.setLayout(new CardLayout());

        // Object type selector
        this.selectType = new JComboBox<String>(ObjectProperties.getObjectsTypes());
        this.add(selectType, BorderLayout.NORTH);

        // Creates a new ObjectTab for each object type
        for(String s : ObjectProperties.getObjectsTypes()) {
            this.cards.add(new ObjectTab(new ObjectProperties(s), true), s);
        } 
        op = ((ObjectTab)getVisibleCard()).getOp(); // Set op to current visible card

        // New listener to the object type selector
        // it switch the current visible card and set the right op
        this.selectType.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e){
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    CardLayout cl = (CardLayout)(cards.getLayout());
                    cl.show(cards, (String)e.getItem());
                    op = ((ObjectTab)getVisibleCard()).getOp();
                    //System.out.println("op is " + op);
                }
            }
        });

        this.add(this.cards, BorderLayout.CENTER);
        this.createButton = new JButton(CREATE_BUTTON_STRING);

        // Create new object if the name is not already used
        this.createButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //op.setType(selectType.getSelectedItem().toString());
                if(!ControllerForView.getInstance().createObject(op)) // This method should try if name is not alredy used
                    JOptionPane.showMessageDialog(null, "Invalid name");
                else
                    dispose();
            }
        });

        this.add(this.createButton, BorderLayout.SOUTH);
        this.setModal(true);
        this.pack();
    }

    // Return the current visible card
    private Component getVisibleCard()
    {
        for(Component c: cards.getComponents())
        {
            if (c.isVisible())
                return c;
        }

        return null;
    }
    
}
