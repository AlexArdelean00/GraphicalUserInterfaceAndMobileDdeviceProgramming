package controller;

public class ControllerForModel implements IControllerForModel{

	private static ControllerForModel instance = null;
	private int currentId;

	// Constructor
	private ControllerForModel() {
		currentId=0;
	}

	public static IControllerForModel getInstance() {
		if (instance == null)
			instance = new ControllerForModel();
		return instance;
	}

	@Override
	public int getNewID() {
		return currentId++;
	}
}
