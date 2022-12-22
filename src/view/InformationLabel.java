package view;

import javax.swing.JLabel;

import controller.ControllerForView;

public class InformationLabel extends JLabel{

    private static final String OBJ_NUMBER_STRING_1 = "Scene contains";
    private static final String OBJ_NUMBER_STRING_2 = "renderable object";

    private static final String SCENE_COMPLEXITY_STRING_1 = "Scene complexity is estimated to";
    private static final String SCENE_COMPLEXITY_STRING_2 = "/10";

    public InformationLabel() {
        super("Information about the scene");
    }

    public void update() {
        int objNumb = ControllerForView.getInstance().getObjectNumber();
        int sceneComplexity = ControllerForView.getInstance().getSceneComplexity();
        String text = "";
        text += OBJ_NUMBER_STRING_1 + " " + objNumb + " " + OBJ_NUMBER_STRING_2;
        text += "   |   " + SCENE_COMPLEXITY_STRING_1 + " " + sceneComplexity + SCENE_COMPLEXITY_STRING_2;
        this.setText(text);
        
    }

}
