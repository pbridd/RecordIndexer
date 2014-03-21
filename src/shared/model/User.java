package shared.model;

public class User {
	private int userID;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private int indexedRecords;
	
	//Constructors
	/**
	 * Default Constructor, userID is set to -1, indexedRecords is set to 0,
	 * and every String value to "New " and whatever the variable name is (ie "New username).
	 */
	public User(){
		this.userID = -1;
		this.username = "New username";
		this.password = "New password";
		this.firstName = "New firstName";
		this.lastName = "New lastName";
		this.email = "New email";
		this.indexedRecords = 0;
	}

	/**
	 * 
	 * @param userID The unique ID assigned to each User in the database
	 * @param username The username used for login of each User
	 * @param password The password used for login of each User
	 * @param firstName The first name of the User
	 * @param lastName The last name of the User
	 * @param email The email of the User
	 * @param indexedRecords The number of records indexed by the User
	 */
	public User(int userID, String username, String password, String firstName, String lastName, 
				String email, int indexedRecords){
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.indexedRecords = indexedRecords;
	}
	
	
	//Getter and Setter methods
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
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the indexedRecords
	 */
	public int getIndexedRecords() {
		return indexedRecords;
	}
	/**
	 * @param indexedRecords the indexedRecords to set
	 */
	public void setIndexedRecords(int indexedRecords) {
		this.indexedRecords = indexedRecords;
	}
}
