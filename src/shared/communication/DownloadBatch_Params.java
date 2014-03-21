package shared.communication;

public class DownloadBatch_Params extends ValidateUser_Params {
	//Global Variables
	private int projectID;
	//Constructors
	/**
	 * Default constructor, calls superclass constructor and sets ProjectID to -1
	 */
	public DownloadBatch_Params(){
		super();
		projectID = -1;
	}
	
	/**
	 * Constructor, caller superclass constructor and sets projectID
	 * @param username The username to authenticate
	 * @param password The password to authenticate
	 * @param projectID The project ID of the project to download
	 */
	public DownloadBatch_Params(String username, String password, int projectID){
		super(username, password);
		this.projectID = projectID;
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

	//Overridden Methods
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		//TODO implement
		return "DownloadBatch_Params [projectID=" + projectID + "]";
	}
	
	

	
}
