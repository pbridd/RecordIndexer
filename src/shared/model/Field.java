package shared.model;

public class Field {
	private int fieldID;
	private String fieldName;
	private int fieldNumber;
	private int xCoord;
	private int width;
	private String helpHTML;
	private String knownData;
	private int projectID;
	
	//Constructors
	/**
	 * Default constructor, fieldID, fieldNumber, xCoord, width, and projectID
	 * to -1 and all String values to "New" and their names, except for helpHTML, which is set to "/" 
	 * and knownData, which is set to null.
	 */
	public Field(){
		//TODO implement
		this.fieldID = -1;
		this.fieldNumber = -1;
		this.fieldName = "New fieldName";
		this.xCoord = -1;
		this.width = -1;
		this.helpHTML = "/";
		knownData = null;
		
	}
	
	/**
	 * 
	 * @param fieldID The unique ID assigned to each Field in the database
	 * @param fieldNumber The order in which this Field appears in Project
	 * @param fieldName The name of the field
	 * @param xCoord The xCoordinate of this Field in each Record
	 * @param width The width in pixels of this Field on the image
	 * @param helpHTML The path of the helpfile for this project relative from the XML file
	 * @param knownData This variable is optional, and indicates the location of a textfile with 
	 * information already known about this Field
	 * @param projectID The unique ID of the linked project
	 *
	 */
	public Field(int fieldID, int fieldNumber, String fieldName, int xCoord, int width, String helpHTML,
				String knownData, int projectID){
		this.fieldID = fieldID;
		this.fieldNumber = fieldNumber;
		this.fieldName = fieldName;
		this.xCoord = xCoord;
		this.width = width;
		this.helpHTML = helpHTML;
		this.knownData = knownData;
		this.projectID = projectID;
	}
	
	
	
	//Getter and Setter methods
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
	/**
	 * @return the fieldNumber
	 */
	public int getFieldNumber() {
		return fieldNumber;
	}
	/**
	 * @param fieldNumber the fieldNumber to set
	 */
	public void setFieldNumber(int fieldNumber) {
		this.fieldNumber = fieldNumber;
	}
	/**
	 * @return the xCoord
	 */
	public int getXCoord() {
		return xCoord;
	}
	/**
	 * @param xCoord the xCoord to set
	 */
	public void setXCoor(int xCoord) {
		this.xCoord = xCoord;
	}
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return the helpHTML
	 */
	public String getHelpHTML() {
		return helpHTML;
	}
	/**
	 * @param helpHTML the helpHTML to set
	 */
	public void setHelpHTML(String helpHTML) {
		this.helpHTML = helpHTML;
	}
	/**
	 * @return the knownData
	 */
	public String getKnownData() {
		return knownData;
	}
	/**
	 * @param knownData the knownData to set
	 */
	public void setKnownData(String knownData) {
		this.knownData = knownData;
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
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
}
