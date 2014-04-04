package client.gui.synchronization;

public interface BatchStateListener {
	//enumerated type declaration
	enum BatchActions{
		
		PROJECTHASCHANGED, BATCHHASCHANGED,
		FIELDHASCHANGED, SELECTEDXHASCHANGED,
		SELECTEDYHASCHANGED, DATAVALUEHASCHANGED,
		IMAGEHASCHANGED
		
	}
	
	public void batchActionPerformed(BatchActions ba);
}
