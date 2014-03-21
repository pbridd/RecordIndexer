package shared.model;

public class Record {
	private int recordID;
	private int recordNumber;
	private int batchID;
	//Constructors
	/**
	 * Default constructor, sets recordID, recordNumber, and batchID
	 * to -1
	 */
	public Record(){
		this.recordID = -1;
		this.recordNumber = -1;
		this.batchID = -1;
	}
	
	/**
	 * Constructor. Sets the global variables recordID and batchID to the
	 * parameters passed in
	 * @param recordID The unique ID assigned to each Record in the database
	 * @param recordNumber The row this record is in the batch
	 * @param batchID The unique ID to identify the Batch this record belongs to
	 */
	public Record(int recordID, int recordNumber, int batchID){
		this.recordID = recordID;
		this.recordNumber = recordNumber;
		this.batchID = batchID;
	}
	
	//Getter and Setter methods
	/**
	 * @return the recordID
	 */
	public int getRecordID() {
		return recordID;
	}
	/**
	 * @param recordID the recordID to set
	 */
	public void setRecordID(int recordID) {
		this.recordID = recordID;
	}
	/**
	 * @return the recordNumber
	 */
	public int getRecordNumber() {
		return recordNumber;
	}

	/**
	 * @param recordNumber the recordNumber to set
	 */
	public void setRecordNumber(int recordNumber) {
		this.recordNumber = recordNumber;
	}

	/**
	 * @return the batchID
	 */
	public int getBatchID() {
		return batchID;
	}
	/**
	 * @param batchID the batchID to set
	 */
	public void setBatchID(int batchID) {
		this.batchID = batchID;
	}

}
