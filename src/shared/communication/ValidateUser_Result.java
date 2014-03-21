package shared.communication;

import shared.model.User;

public class ValidateUser_Result {
	//Global Variables
	private User usr;
	private boolean authenticated;
	
	//Constructors
	/**
	 * Default constructor, sets usr to null and authenticated to false
	 */
	public ValidateUser_Result(){
		usr = null;
		authenticated = false;
	}
	
	/**
	 * Constructor, sets all global variables
	 * @param usr The user who was authenticated
	 * @param authenticated Whether the user was authenticated or not
	 */
	public ValidateUser_Result(User usr, boolean authenticated){
		this.usr = usr;
		this.authenticated = authenticated;
	}

	//Getter and Setter Methods
	/**
	 * @return the usr
	 */
	public User getUsr() {
		return usr;
	}

	/**
	 * @param usr the usr to set
	 */
	public void setUsr(User usr) {
		this.usr = usr;
	}

	/**
	 * @return the authenticated
	 */
	public boolean isAuthenticated() {
		return authenticated;
	}

	/**
	 * @param authenticated the authenticated to set
	 */
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	
	//Overridden Methods
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		//TODO implement
		return "ValidateUser_Result [usr=" + usr + ", authenticated="
				+ authenticated + "]";
	}
	
	
	

}
