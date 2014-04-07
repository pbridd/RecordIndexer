package shared.model;

import java.io.Serializable;

public class Batch implements Serializable{
	/**
	 * Automatically added by Eclipse
	 */
	private static final long serialVersionUID = 793423587252809116L;
	private int batchID;
	private String imagePath;
	private int projectID;
	private boolean fullyIndexed;
	private int userID;
	//Constructors
	/**
	 * Default constructor, sets batchID -1 and imagePath to "/",
	 * also sets fullyIndexed to false and userID -1
	 */
	public Batch(){
		this.batchID = -1;
		this.imagePath = "/";
		this.projectID = -1;
		this.fullyIndexed = false;
		this.userID = -1;
	}
	
	/**
	 * Constructor that sets all of the global variables
	 * @param batchID The unique ID assigned to each Batch in the database
	 * @param imagePath The path to the image file for this Batch, relative
	 * to the xml file
	 * @param projectID The unique ID of the linked Project
	 * @param fullyIndexed Whether this batch is fully indexed or not 
	 * @param userID What user this batch is assigned to, if any
	 */
	public Batch(int batchID, String imagePath, int projectID,
				boolean fullyIndexed, int userID){
		this.batchID = batchID;
		this.imagePath = imagePath;
		this.projectID = projectID;
		this.fullyIndexed = fullyIndexed;
		this.userID = userID;
	}
	
	//Getter and Setter methods
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
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}
	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	/**
	 * @return the projectID
	 */
	public int getProjectID() {
		return projectID;
	}
	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	/**
	 * @return the fullyIndexed
	 */
	public boolean isFullyIndexed() {
		return fullyIndexed;
	}

	/**
	 * @param fullyIndexed the fullyIndexed to set
	 */
	public void setFullyIndexed(boolean fullyIndexed) {
		this.fullyIndexed = fullyIndexed;
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
}
