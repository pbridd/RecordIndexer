package client.gui.synchronization;

public interface BatchStateListener {
	//enumerated type declaration
	enum BatchActions{
		
		PROJECTCHANGED, BATCHCHANGED,
		FIELDCHANGED, SELECTEDROWCHANGED,
		SELECTEDCOLCHANGED, DATAVALUECHANGED,
		IMAGECHANGED, BATCHDOWNLOADED
		
	}
	
	public void batchActionPerformed(BatchActions ba);
	public void batchActionPerformed(BatchActions ba, int row, int col);
}
