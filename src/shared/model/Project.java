package shared.model;

public class Project {
	private int projectID;
	private String projectTitle;
	private int recordsPerImage;
	private int firstYCoord;
	private int recordHeight;
	
	//Constructors
	/**
	 * Default constructor, sets all integer values to -1 and projectTitle to "New Project"
	 */
	public Project(){
		this.projectID = -1;
		this.projectTitle = "New Project";
		this.recordsPerImage = -1;
		this.firstYCoord = -1;
		this.recordHeight = -1;
	}
	
	/**
	 * 
	 * @param projectID The unique ID assigned to each Project in the database
	 * @param projectTitle The title of the Project
	 * @param recordsPerImage The number of records per image in this Project
	 * @param firstYCoord The first Y Coordinate of the top of the first record in this Project
	 * @param recordHeight The height of each record in the Project's images, measured in pixels
	 */
	public Project(int projectID, String projectTitle, int recordsPerImage, 
					int firstYCoord, int recordHeight){
		this.projectID = projectID;
		this.projectTitle = projectTitle;
		this.firstYCoord = firstYCoord;
		this.recordsPerImage = recordsPerImage;
		this.recordHeight = recordHeight;
	}
	
	
	//Getter and Setter methods
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
	 * @return the projectTitle
	 */
	public String getProjectTitle() {
		return projectTitle;
	}
	/**
	 * @param projectTitle the projectTitle to set
	 */
	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}
	/**
	 * @return the recordsPerImage
	 */
	public int getRecordsPerImage() {
		return recordsPerImage;
	}
	/**
	 * @param recordsPerImage the recordsPerImage to set
	 */
	public void setRecordsPerImage(int recordsPerImage) {
		this.recordsPerImage = recordsPerImage;
	}
	/**
	 * @return the firstYCoord
	 */
	public int getFirstYCoord() {
		return firstYCoord;
	}
	/**
	 * @param firstYCoord the firstYCoord to set
	 */
	public void setFirstYCoord(int firstYCoord) {
		this.firstYCoord = firstYCoord;
	}
	/**
	 * @return the recordHeight
	 */
	public int getRecordHeight() {
		return recordHeight;
	}
	/**
	 * @param recordHeight the recordHeight to set
	 */
	public void setRecordHeight(int recordHeight) {
		this.recordHeight = recordHeight;
	}
}
