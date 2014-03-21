package shared.communication;

import java.util.ArrayList;
import java.util.List;



public class Search_Result {
	//Global Variables
	List<Integer> batchIDs;
	List<String> imageURLs;
	List<Integer> recordNumbers;
	List<Integer> fieldIDs;
	
	
	//Constructors
	/**
	 * Default constructor, initializes all lists
	 */
	public Search_Result(){
		batchIDs = new ArrayList<Integer>();
		imageURLs = new ArrayList<String>();
		recordNumbers = new ArrayList<Integer>();
		fieldIDs = new ArrayList<Integer>();
	}

	/**
	 * Constructor, sets all of the lists
	 * @param batchIDs The results' batch IDs
	 * @param imageURLs The results' Images URLS
	 * @param recordNumbers The results' record numbers
	 * @param FieldIDs The results' field IDs
	 */
	public Search_Result(List<Integer> batchIDs, List<String> imageURLs, List<Integer> recordNumbers,
						List<Integer> fieldIDs){
		this.batchIDs = batchIDs;
		this.imageURLs = imageURLs;
		this.recordNumbers = recordNumbers;
		this.fieldIDs = fieldIDs;
	}
	
	
	//Encapsulated Object Modifier Methods
	
	/**
	* Adds a batchID to the encapsulated List of batchIDs
	* @param bi The batchID to be added
	*/
	public void addBatchID(int bi){
		batchIDs.add(bi);
	}
	
	/**
	* Adds a imageURL to the encapsulated List of imageURLs
	* @param iu The imageURL to be added
	*/
	public void addImageURL(String iu){
		imageURLs.add(iu);
	}
	
	/**
	* Adds a recordNumber to the encapsulated List of recordNumbers
	* @param rn The recordNumber to be added
	*/
	public void addRecordNumber(int rn){
		recordNumbers.add(rn);
	}
	
	/**
	* Adds a fieldID to the encapsulated List of fieldIDs
	* @param fi The fieldID to be added
	*/
	public void addFieldID(int fi){
		fieldIDs.add(fi);
	}
	
	
	//Getter and Setter Methods
	/**
	 * @return the batchIDs
	 */
	public List<Integer> getBatchIDs() {
		return batchIDs;
	}

	/**
	 * @param batchIDs the batchIDs to set
	 */
	public void setBatchIDs(List<Integer> batchIDs) {
		this.batchIDs = batchIDs;
	}

	/**
	 * @return the imageURLs
	 */
	public List<String> getImageURLs() {
		return imageURLs;
	}

	/**
	 * @param imageURLs the imageURLs to set
	 */
	public void setImageURLs(List<String> imageURLs) {
		this.imageURLs = imageURLs;
	}

	/**
	 * @return the recordNumbers
	 */
	public List<Integer> getRecordNumbers() {
		return recordNumbers;
	}

	/**
	 * @param recordNumbers the recordNumbers to set
	 */
	public void setRecordNumbers(List<Integer> recordNumbers) {
		this.recordNumbers = recordNumbers;
	}

	/**
	 * @return the fieldIDs
	 */
	public List<Integer> getFieldIDs() {
		return fieldIDs;
	}

	/**
	 * @param fieldIDs the fieldIDs to set
	 */
	public void setFieldIDs(List<Integer> fieldIDs) {
		this.fieldIDs = fieldIDs;
	}

	//Overridden Methods
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		//TODO implement
		return "Search_Result [batchIDs=" + batchIDs + ", imageURLs="
				+ imageURLs + ", recordNumbers=" + recordNumbers
				+ ", fieldIDs=" + fieldIDs + "]";
	}
	
	
	

	 
	
	
	
}
