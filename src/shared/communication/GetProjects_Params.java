package shared.communication;

public class GetProjects_Params extends ValidateUser_Params{
	//Constructors
	/**
	 * Default constructor, calls superclass constructor
	 */
	public GetProjects_Params(){
		super();
	}
	
	/**
	 * Constructor which sets all of the global variables, calls superclass constructor
	 * @param username The username to authenticate
	 * @param password The password to authenticate
	 */
	public GetProjects_Params(String username, String password){
		super(username, password);
	}
	

}
