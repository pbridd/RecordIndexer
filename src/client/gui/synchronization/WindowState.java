package client.gui.synchronization;

import java.io.Serializable;

public class WindowState implements Serializable{
	/**
	 * Automatically added by Java
	 */
	private static final long serialVersionUID = -6693255986177069610L;
	private double xPosOnDesktop;
	private double yPosOnDesktop;
	private int widthOfWindow;
	private int heightOfWindow;
	private int verticalPanePosition;
	private int horizontalPanePosition;
	
	
	
	/**
	 * Default Constructor
	 */
	public WindowState(){
		
	}
	/**
	 * @return the xPosOnDesktop
	 */
	public double getxPosOnDesktop() {
		return xPosOnDesktop;
	}


	/**
	 * @param xPosOnDesktop the xPosOnDesktop to set
	 */
	public void setxPosOnDesktop(double xPosOnDesktop) {
		this.xPosOnDesktop = xPosOnDesktop;
	}


	/**
	 * @return the yPosOnDesktop
	 */
	public double getyPosOnDesktop() {
		return yPosOnDesktop;
	}


	/**
	 * @param yPosOnDesktop the yPosOnDesktop to set
	 */
	public void setyPosOnDesktop(double yPosOnDesktop) {
		this.yPosOnDesktop = yPosOnDesktop;
	}


	/**
	 * @return the widthOfWindow
	 */
	public int getWidthOfWindow() {
		return widthOfWindow;
	}


	/**
	 * @param widthOfWindow the widthOfWindow to set
	 */
	public void setWidthOfWindow(int widthOfWindow) {
		this.widthOfWindow = widthOfWindow;
	}


	/**
	 * @return the heightOfWindow
	 */
	public int getHeightOfWindow() {
		return heightOfWindow;
	}


	/**
	 * @param heightOfWindow the heightOfWindow to set
	 */
	public void setHeightOfWindow(int heightOfWindow) {
		this.heightOfWindow = heightOfWindow;
	}


	/**
	 * @return the verticalPanePosition
	 */
	public int getVerticalPanePosition() {
		return verticalPanePosition;
	}


	/**
	 * @param verticalPanePosition the verticalPanePosition to set
	 */
	public void setVerticalPanePosition(int verticalPanePosition) {
		this.verticalPanePosition = verticalPanePosition;
	}


	/**
	 * @return the horizontalPanePosition
	 */
	public int getHorizontalPanePosition() {
		return horizontalPanePosition;
	}


	/**
	 * @param horizontalPanePosition the horizontalPanePosition to set
	 */
	public void setHorizontalPanePosition(int horizontalPanePosition) {
		this.horizontalPanePosition = horizontalPanePosition;
	}
}
