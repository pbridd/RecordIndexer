package client.gui.synchronization;

public interface BatchStateListener {
	public void projectHasChanged();
	public void batchHasChanged();
	/**
	 * Notifies the listeners that the field has changed. If the index is -1,
	 * 	then all of the fields have changed.
	 * @param index The index of the field that has changed
	 */
	public void fieldHasChanged(int index);
	public void selectedXHasChanged();
	public void selectedYHasChanged();
	public void dataValueHasChanged(int xIdx, int yIdx);
	public void imageHasChanged();
}
