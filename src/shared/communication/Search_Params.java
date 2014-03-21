package shared.communication;

import java.util.ArrayList;
import java.util.List;


public class Search_Params extends ValidateUser_Params{
	//Global Variables
	private List<Integer> fieldIDs;
	private List<String> strings;
	
	//Constructors
	/**
	 * Default constructor, calls superclass constructor, initializes fields and strings
	 */
	public Search_Params(){
		fieldIDs = new ArrayList<Integer>();
		strings = new ArrayList<String>();
	}
	
	/**
	 * Constructor, calls superclass constructor, sets fields and strings to the passed in parameter
	 * @param username The username to authenticate
	 * @param password The password to authenticate
	 * @param fields The list of fields to search for
	 * @param strings The list of strings to search for
	 */
	public Search_Params(String username, String password, List<Integer> fieldIDs, List<String> strings){
		super(username, password);
		this.fieldIDs = fieldIDs;
		this.strings = strings;
	}
	
	
	//Encapsulated Object Modifier Methods
	/**
	* Adds a fieldID to the encapsulated List of fieldIDs
	* @param f The field to be added
	*/
	public void addField(int f){
		fieldIDs.add(f);
	}
	
	/**
	 * Adds a string to the encapsulated List of strings
	 * @param str The string to be added
	 */
	public void addString(String str){
		strings.add(str);
	}

	
	
	//Getter and Setter Methods
	/**
	 * @return the fieldIDs
	 */
	public List<Integer> getFieldIDs() {
		return fieldIDs;
	}

	/**
	 * @param fieldIDs the fieldIDs to set
	 */
	public void setFieldIDs(List<Integer> fieldIDs) {
		this.fieldIDs = fieldIDs;
	}

	/**
	 * @return the strings
	 */
	public List<String> getStrings() {
		return strings;
	}

	/**
	 * @param strings the strings to set
	 */
	public void setStrings(List<String> strings) {
		this.strings = strings;
	}
	
	

}
