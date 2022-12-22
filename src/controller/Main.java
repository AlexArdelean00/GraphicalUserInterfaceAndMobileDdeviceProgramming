package controller;

import java.io.IOException;

import model.Model;
import utils.SceneFile;
import view.View;

public class Main {
    public static void main(String[] args) throws IOException{
		Model.getInstance().loadScene(SceneFile.getDefaultSceneFileFullPath());
		View.getInstance().startWindow();		
	}
}
