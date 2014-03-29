package client.gui;

public class WindowManager {
	//global variables
	boolean loginWindowVisible;
	boolean mainWindowVisible;
	
	/**
	 * Default Constructor, makes loginVisible and mainWindowVisible
	 */
	public WindowManager(){
		loginWindowVisible = true;
		mainWindowVisible = false;
	}
	
	/**
	 * Toggles which one of the windows are visible
	 */
	public void toggleVisibility(){
		loginWindowVisible = !loginWindowVisible;
		mainWindowVisible = !mainWindowVisible;
	}
	
	public boolean loginWindowVisible(){
		return loginWindowVisible;
	}
	
	public boolean mainWindowVisible(){
		return mainWindowVisible;
	}

}
