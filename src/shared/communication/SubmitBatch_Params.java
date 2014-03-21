package shared.communication;

import shared.model.*;

import java.util.ArrayList;
import java.util.List;

import shared.model.Record;

public class SubmitBatch_Params extends ValidateUser_Params{
	 //Global Variables
	private int batchID;
	private List<Record> records;
	private List<IndexedData> indexedData;
	private boolean tempIDs;
	
	/**
	 * Default constructor, calls superclass constructor, sets batchID to -1, initializes records
	 * and data lists
	 */
	public SubmitBatch_Params(){
		batchID = -1;
		records = new ArrayList<Record>();
		indexedData = new ArrayList<IndexedData>();
		tempIDs=false;
		
	}
	/**
	 * Constructor, calls superclass constructor, sets batchID, records and indexedData. Sets 
	 * tempIDs to false.
	 * @param username The username to authenticate
	 * @param password The password to authenticate
	 * @param batchID The ID of the batch that is submitted
	 * @param records The records that have been indexed in this batch
	 * @param indexedData The data that have been indexed
	 */
	public SubmitBatch_Params(String username, String password,
								int batchID, List<Record> records, List<IndexedData> indexedData){
		super(username, password);
		this.batchID = batchID;
		this.records = records;
		this.indexedData = indexedData;
		this.tempIDs = false;
	}
	
	
	/**
	 * Constructor, calls superclass constructor, sets batchID, records and indexedData
	 * @param username The username to authenticate
	 * @param password The password to authenticate
	 * @param batchID The ID of the batch that is submitted
	 * @param records The records that have been indexed in this batch
	 * @param indexedData The data that have been indexed
	 * @param tempIDs Whether the IndexedData has temporary IDs that need to be reassigned
	 */
	public SubmitBatch_Params(String username, String password,
								int batchID, List<Record> records, List<IndexedData> indexedData, boolean tempIDs){
		super(username, password);
		this.batchID = batchID;
		this.records = records;
		this.indexedData = indexedData;
		this.tempIDs = tempIDs;
	}
	
	//Encapsulated Object Modifier Methods
	/**
	* Adds a record to the encapsulated List of records
	* @param rec The record to be added
	*/
	public void addRecord(Record rec){
		records.add(rec);
	}
	
	/**
	* Adds a indexedData to the encapsulated List of data
	* @param id The data to be added
	*/
	public void addIndexedData(IndexedData id){
		indexedData.add(id);
	}

	//Getter and Setter Methods
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

	/**
	 * @return the records
	 */
	public List<Record> getRecords() {
		return records;
	}

	/**
	 * @param records the records to set
	 */
	public void setRecords(List<Record> records) {
		this.records = records;
	}

	/**
	 * @return the indexedData
	 */
	public List<IndexedData> getIndexeddata() {
		return indexedData;
	}

	/**
	 * @param indexedData the indexedData to set
	 */
	public void setIndexedData(List<IndexedData> indexedData) {
		this.indexedData = indexedData;
	}
	/**
	 * @return the tempIDs
	 */
	public boolean hasTempIDs() {
		return tempIDs;
	}
	/**
	 * @param tempIDs the tempIDs to set
	 */
	public void setTempIDs(boolean tempIDs) {
		this.tempIDs = tempIDs;
	}
	
	
	
	
	
	
	
}
