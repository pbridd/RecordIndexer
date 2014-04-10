package client.gui.synchronization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImageState implements Serializable{
	/**
	 * Automatically generated by eclipse
	 */
	private static final long serialVersionUID = 7018414239710439345L;
	private boolean imageIsInverted;
	private int imagePosX;
	private int imagePosY;
	private List<ImageStateListener> listeners;
	
	
	
	public ImageState(){
		this.listeners = new ArrayList<ImageStateListener>();
	}
	
	/**
	 * Add a listener to the list of listeners on this object
	 * @param listener the ImageStateListener to add
	 */
	public void addListener(ImageStateListener listener){
		this.listeners.add(listener);
	}
	
	/**
	 * Set imageIsInverted
	 * @param imageIsInverted The value to change imageIsInverted to
	 */
	public void setImageIsInverted(boolean imageIsInverted){
		this.imageIsInverted = imageIsInverted;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public boolean getImageIsInverted(){
		return this.imageIsInverted;
	}
	
	/**
	 * return imagePosX
	 * @return imagePosX
	 */
	public int getImagePosX(){
		return this.imagePosX;
	}
	
	/**
	 * set imagePosX
	 * @param imagePosX
	 */
	public void setImagePosX(int imagePosX){
		this.imagePosX = imagePosX;
	}
	
	
	/**
	 * return imagePosY
	 * @return imagePosY
	 */
	public int getImagePosY(){
		return this.imagePosY;
	}
	
	/**
	 * Set imagePosY
	 * @param imagePosY
	 */
	public void setImagePosY(int imagePosY){
		this.imagePosY = imagePosY;
	}
	
}
