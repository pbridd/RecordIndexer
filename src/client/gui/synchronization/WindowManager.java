package client.gui.synchronization;

import java.util.ArrayList;
import java.util.List;

import shared.model.User;

public class WindowManager {
	//global variables
	private boolean loginWindowVisible;
	private boolean mainWindowVisible;
	private User currUser;
	private List<WindowManagerListener> listeners;
	
	/**
	 * Default Constructor, makes loginVisible and mainWindowVisible
	 */
	public WindowManager(){
		loginWindowVisible = true;
		mainWindowVisible = false;
		currUser = null;
		listeners = new ArrayList<WindowManagerListener>();
	}
	
	/**
	 * Toggles which one of the windows are visible
	 * @param user The User who is to be logged into the program
	 */
	public void toggleVisibility(User user){
		loginWindowVisible = !loginWindowVisible;
		mainWindowVisible = !mainWindowVisible;
		currUser = user;
		
		//notify the listeners that the state has changed
		for(WindowManagerListener w : listeners){
			w.visibilityToggled(loginWindowVisible, mainWindowVisible, currUser);
		}
	}
	
	/**
	 * Set the current user
	 * @param user The user who is currently using the program
	 */
	public void setCurrentUser(User user){
		currUser = user;
	}
	
	/**
	 * Return if the login window is visible
	 * @return a boolean that indicates whether the login window is visible
	 */
	public boolean isLoginWindowVisible(){
		return loginWindowVisible;
	}
	
	/**
	 * Return if the main window is visible
	 * @return a boolean that indicates whether the main window is visible
	 */
	public boolean isMainWindowVisible(){
		return mainWindowVisible;
	}
	
	/**
	 * Add a listener to this WindowManager
	 * @param w
	 */
	public void addListener(WindowManagerListener w){
		listeners.add(w);
	}

}