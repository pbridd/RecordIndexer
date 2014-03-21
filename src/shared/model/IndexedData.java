package shared.model;

public class IndexedData {
	private int indexedDataID;
	private String dataValue;
	private int recordID;
	private int fieldID;
	//Constructors\
	/**
	 * Default constructor, sets indexedDataID, recordID, and fieldID
	 * to -1 and dataValue to null
	 */
	public IndexedData(){
		this.indexedDataID = -1;
		this.dataValue = "New dataValue";
		this.recordID = -1;
		this.fieldID = -1;
	}
	
	/**
	 * @param indexedDataID The unique ID assigned to each IndexedData in the database
	 * @param dataValue The value of the IndexedData, what the user actually input
	 * @param recordID The unique value of the Record that contains this element
	 * @param fieldID The unique value of the Field that contains this element
	 */
	public IndexedData(int indexedDataID, String dataValue, int recordID, int fieldID){
		this.indexedDataID = indexedDataID;
		this.dataValue = dataValue;
		this.recordID = recordID;
		this.fieldID = fieldID;
	}
	
	
	//Getter and Setter methods
	/**
	 * @return the indexedDataID
	 */
	public int getIndexedDataID() {
		return indexedDataID;
	}
	/**
	 * @param indexedDataID the indexedDataID to set
	 */
	public void setIndexedDataID(int indexedDataID) {
		this.indexedDataID = indexedDataID;
	}
	/**
	 * @return the dataValue
	 */
	public String getDataValue() {
		return dataValue;
	}
	/**
	 * @param dataValue the dataValue to set
	 */
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
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
	 * @return the fieldID
	 */
	public int getFieldID() {
		return fieldID;
	}
	/**
	 * @param fieldID the fieldID to set
	 */
	public void setFieldID(int fieldID) {
		this.fieldID = fieldID;
	}
	
	
}
