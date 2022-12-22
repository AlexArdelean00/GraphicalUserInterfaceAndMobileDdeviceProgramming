package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.ControllerForView;

public class MenuBar extends JMenuBar{

    public static final String FILE_MENU_STRING = "File";
    public static final String SAVE_SCENE_MENU_ITEM_STRING = "Save Scene";
    public static final String IMPORT_SCENE_MENU_ITEM_STRING = "Import Scene";
    public static final String EDIT_MENU_STRING = "Edit";
    public static final String DELETE_ALL_MENU_ITEM_STRING = "Delete All";
    public static final String DELETE_SELECTED_MENU_ITEM_STRING = "Delete Selected";
    public static final String EXIT_MENU_STRING = "Exit";
    public static final String QUIT_APPLICATION_MENU_ITEM_STRING = "Quit Application";
    public static final String SAVE_AND_QUIT_APPLICATION_MENU_ITEM_STRING = "Save And Quit Application";

    private JMenu file;
    private JMenu edit;
    private JMenu exit;

    public MenuBar() {
        this.createFileMenu();

        this.createEditMenu();

        this.createExitMenu();

        this.add(file);
        this.add(edit);
        this.add(exit);
    }

    private void createExitMenu() {
        exit = new JMenu(EXIT_MENU_STRING);
        JMenuItem quit = new JMenuItem(QUIT_APPLICATION_MENU_ITEM_STRING);
        quit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerForView.getInstance().quitApplication();
            }
            
        });
        JMenuItem quitAndSave = new JMenuItem(SAVE_AND_QUIT_APPLICATION_MENU_ITEM_STRING);
        quitAndSave.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("saving scene...");
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Desktop")); 
			    fc.showDialog(null, "Save");
                ControllerForView.getInstance().quitAndSaveApplication(fc.getSelectedFile());
            }
            
        });
        exit.add(quit);
        exit.add(quitAndSave);
    }

    private void createEditMenu() {
        edit = new JMenu(EDIT_MENU_STRING);
        JMenuItem deleteAll = new JMenuItem(DELETE_ALL_MENU_ITEM_STRING);
        deleteAll.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerForView.getInstance().deleteAll();
            }
            
        });
        JMenuItem deleteSelected = new JMenuItem(DELETE_SELECTED_MENU_ITEM_STRING);
        deleteSelected.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerForView.getInstance().removeSelected();
            }
            
        });
        edit.add(deleteAll);
        edit.add(deleteSelected);
    }

    private void createFileMenu() {
        file = new JMenu(FILE_MENU_STRING);
        JMenuItem saveScene = new JMenuItem(SAVE_SCENE_MENU_ITEM_STRING);
        JMenuItem importScene = new JMenuItem(IMPORT_SCENE_MENU_ITEM_STRING);
        file.add(saveScene);
        file.add(importScene);
        importScene.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Importing scene...");
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Desktop"));
			    fc.showDialog(null, "Save");
                ControllerForView.getInstance().importScene(fc.getSelectedFile());
            }
            
        });
        saveScene.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("saving scene...");
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Desktop")); 
			    fc.showDialog(null, "Save");
                ControllerForView.getInstance().saveScene(fc.getSelectedFile());
                
            }
            
        });
    }
    
}
