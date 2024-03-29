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
	private boolean imageIsHighlighted;
	private boolean isNewImgS;
	private int imagePosX;
	private int imagePosY;
	private double zoom;
	private transient List<ImageStateListener> listeners;
	
	
	
	public ImageState(){
		this.imageIsInverted = false;
		this.imageIsHighlighted = true;
		this.imagePosX = 0;
		this.imagePosY = 0;
		this.zoom = 1.0;
		this.listeners = new ArrayList<ImageStateListener>();
		this.isNewImgS = true;
	}
	
	public void initializeListenerList(){
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
		this.fireInvertToggled();
	}
	
	/**
	 * Set imageIsInverted to the opposite and notifies listeners
	 */
	public void toggleImageIsInverted(){
		this.imageIsInverted = !this.imageIsInverted;
		this.fireInvertToggled();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public boolean getImageIsInverted(){
		return this.imageIsInverted;
	}
	
	
	/**
	 * Set whether the image is highlighted or not
	 * @param imageIsHighlighted
	 */
	public void setImageIsHighlighted(boolean imageIsHighlighted){
		this.imageIsHighlighted = imageIsHighlighted;
		this.fireHighlightsToggled();
	}
	
	/**
	 * Sets imageIsHighlighted to the opposite and notifies listeners
	 */
	public void toggleImageIsHighlighted(){
		this.imageIsHighlighted = ! this.imageIsHighlighted;
		this.fireHighlightsToggled();
	}
	
	
	/**
	 * Get whether the image is highlighted or not
	 * @return The highlighted setting
	 */
	public boolean getImageIsHighlighted(){
		return this.imageIsHighlighted;
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
		this.fireImageCoordsChanged();
	}
	
	
	/**
	 * return imagePosY
	 * @return imagePosY
	 */
	public int getImagePosY(){
		return this.imagePosY;
	}
	
	/**
	 * set the zoom level
	 * @param d the new zoom level
	 */
	public void setZoomLevel(double d){
		this.zoom = d;
		this.fireImageZoomChanged();
	}
	
	
	/**
	 * get the zoom level
	 * @return the zoom level
	 */
	public double getZoomLevel(){
		return this.zoom;
	}
	
	/**
	 * Set imagePosY
	 * @param imagePosY
	 */
	public void setImagePosY(int imagePosY){
		this.imagePosY = imagePosY;
		this.fireImageCoordsChanged();
	}
	
	
	/**
	 * 
	 * @return if the imagestate is new or not -- if not, it was loaded from serialization
	 */
	public boolean getIsNewImgS(){
		return isNewImgS;
	}
	
	/**
	 * 
	 * @param nVal whether imagestate is new or not
	 * 
	 */
	public void setIsNewImgS(boolean nVal){
		this.isNewImgS = nVal;
	}
	private void fireInvertToggled(){
		for(ImageStateListener il : listeners){
			il.invertedToggled(this.imageIsInverted);
		}
	}
	
	private void fireImageCoordsChanged(){
		for(ImageStateListener il : listeners){
			il.imageCoordsChanged(this.imagePosX, this.imagePosY);
		}
	}
	
	private void fireHighlightsToggled(){
		for(ImageStateListener il : listeners){
			il.highlightsVisibleToggled(this.imageIsHighlighted);
		}
	}
	
	private void fireImageZoomChanged(){
		for(ImageStateListener il : listeners){
			il.imageZoomChanged(this.zoom);
		}
	}


	
}
