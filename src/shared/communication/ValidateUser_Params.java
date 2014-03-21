package shared.communication;

public class ValidateUser_Params {
	//Global Variables
	private String username;
	private String password;
	
	
	//Constructors
	/**
	 * Default constructor, sets all fields to the value null
	 */
	public ValidateUser_Params() {
		username = null;
		password = null;
	}

	/**
	 * Constructor which sets all of the global variables
	 * @param username The username to authenticate
	 * @param password The password to authenticate
	 */
	public ValidateUser_Params(String username, String password){
		this.username = username;
		this.password = password;
	}

	
	//Getter and Setter Methods
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	

	
}
