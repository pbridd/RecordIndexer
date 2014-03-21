package shared.communication;

public class GetFields_Params extends ValidateUser_Params {
	//Global Variables
	private int projectID;
	
	//Constructors
	/**
	 * Default constructor, calls superclass constructor, and sets projectID to -1 (which is interpreted
	 * as empty string)
	 */
	public GetFields_Params(){
		super();
		projectID = -1;
	}
	
	/**
	 * Constructor, calls superclass constructor, and sets projectID to the parameter that's passed in
	 * @param username
	 * @param password
	 * @param projectID
	 */
	public GetFields_Params(String username, String password, int projectID){
		super(username, password);
		this.projectID = projectID;
	}

	//Getter and Setter Methods
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
	
	
}
