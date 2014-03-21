package shared.communication;

public class GetSampleImage_Params extends ValidateUser_Params{
	//Global Variables
	private int projectID;
	
	//Constructors
	/**
	 * Default Constructor, sets projectID to -1, calls superclass
	 * default constructor
	 */
	public GetSampleImage_Params(){
		super();
		projectID = -1;
	}
	
	/**
	 * Constructor, calls superclass constructor and sets all global variables
	 * @param username
	 * @param password
	 * @param projectID
	 */
	public GetSampleImage_Params(String username, String password, int projectID){
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
