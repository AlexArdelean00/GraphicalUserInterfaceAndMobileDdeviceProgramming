package view;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.ControllerForView;
import utils.ObjectProperties;


public class ObjectList extends JList<ObjectTab> implements ListSelectionListener{

    private static DefaultListModel<ObjectTab> list = new DefaultListModel<ObjectTab>();
    
    public ObjectList() {
        super(list);
        this.addListSelectionListener(this);
    }

    public void updateListFromModel() {
        //System.out.println("Obj list updated, now list is: " + list);
        ArrayList<ObjectProperties> objList = ControllerForView.getInstance().getObjectPropertiesListFromModel();
        // remove deprecated elements
        for(int i=0; i<list.getSize(); i++){
            if(!isIdContainedInArray(list.getElementAt(i).getOp().getId() , objList)){
                list.removeElementAt(i);
                i--;
            }       
        }
        // add new elements
        for(ObjectProperties objProp : objList){
            if(!this.contains(objProp.getId()))
                list.addElement(new ObjectTab(objProp, false));
        }
    }

    private boolean contains(int id) {
        // Return true if the list contain an object with Id = id
        for(int i=0; i<list.getSize(); i++){
            if(list.getElementAt(i).getOp().getId() == id)
                return true;
        }
        return false;
    }

    private boolean isIdContainedInArray(int id, ArrayList<ObjectProperties> objList) {
        for(ObjectProperties objProp : objList){
            if(objProp.getId() == id)
                return true;
        }
        return false;
    }

    // ListSelectionListener Interface
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            // Set correct Object Tab
			ControllerForView.getInstance().setSelectedObjectTab();
		}
    }

    public void selectId(int selectedId) {
        for(int i=0; i<list.getSize(); i++){
            if(list.getElementAt(i).getOp().getId() == selectedId)
                this.setSelectedIndex(i);
        }
    }
}
