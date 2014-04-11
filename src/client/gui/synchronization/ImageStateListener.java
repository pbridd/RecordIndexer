package client.gui.synchronization;

public interface ImageStateListener {
	public void invertedToggled(boolean invertSetting);
	public void imageZoomChanged(double zoom);
	public void highlightsVisibleToggled(boolean highlightSetting);
	public void imageCoordsChanged(int x, int y);

}
