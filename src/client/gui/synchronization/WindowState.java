package client.gui.synchronization;

public class WindowState {
	private int zoomLevel;
	private int scrollPosition;
	private int xPosOnDesktop;
	private int yPosOnDesktop;
	private int widthOfWindow;
	private int heightOfWindow;
	private int verticalPanePosition;
	private int horizontalPanePosition;
	private boolean imageIsInverted;
	private boolean highlightsAreVisible;
	
	
	/**
	 * Default Constructor
	 */
	public WindowState(){
		
	}


	/**
	 * @return the zoomLevel
	 */
	public int getZoomLevel() {
		return zoomLevel;
	}


	/**
	 * @param zoomLevel the zoomLevel to set
	 */
	public void setZoomLevel(int zoomLevel) {
		this.zoomLevel = zoomLevel;
	}


	/**
	 * @return the scrollPosition
	 */
	public int getScrollPosition() {
		return scrollPosition;
	}


	/**
	 * @param scrollPosition the scrollPosition to set
	 */
	public void setScrollPosition(int scrollPosition) {
		this.scrollPosition = scrollPosition;
	}


	/**
	 * @return the xPosOnDesktop
	 */
	public int getxPosOnDesktop() {
		return xPosOnDesktop;
	}


	/**
	 * @param xPosOnDesktop the xPosOnDesktop to set
	 */
	public void setxPosOnDesktop(int xPosOnDesktop) {
		this.xPosOnDesktop = xPosOnDesktop;
	}


	/**
	 * @return the yPosOnDesktop
	 */
	public int getyPosOnDesktop() {
		return yPosOnDesktop;
	}


	/**
	 * @param yPosOnDesktop the yPosOnDesktop to set
	 */
	public void setyPosOnDesktop(int yPosOnDesktop) {
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


	/**
	 * @return the imageIsInverted
	 */
	public boolean isImageIsInverted() {
		return imageIsInverted;
	}


	/**
	 * @param imageIsInverted the imageIsInverted to set
	 */
	public void setImageIsInverted(boolean imageIsInverted) {
		this.imageIsInverted = imageIsInverted;
	}


	/**
	 * @return the highlightsAreVisible
	 */
	public boolean isHighlightsAreVisible() {
		return highlightsAreVisible;
	}


	/**
	 * @param highlightsAreVisible the highlightsAreVisible to set
	 */
	public void setHighlightsAreVisible(boolean highlightsAreVisible) {
		this.highlightsAreVisible = highlightsAreVisible;
	}
}
