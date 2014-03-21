package shared.communication;
import java.util.List;

import shared.model.*;

public class GetFields_Result {
	//Global Variables
	private List<Field> fields;
	
	//Constructors
	/**
	 * Default constructor, calls superclass constructor, initializes fields list
	 */
	public GetFields_Result(){
		//TODO implement
	}
	
	/**
	 * Constructor, calls superclass constructor, sets fields to passed-in value
	 * @param username The username to authenticate
	 * @param password The password to authenticate
	 * @param fields The List of fields to encapsulate
	 */
	public GetFields_Result(String username, String password, List<Field> fields){
		//TODO implement
	}
	
	//Encapsulated Object Modifier Methods
	/**
	* Adds a field to the encapsulated List of fields
	* @param fld The field to be added
	*/
	public void addField(Field fld){
		//TODO implement
	}

	
	//Getter and Setter Methods
	/**
	 * @return the fields
	 */
	public List<Field> getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	
	//Overridden Methods
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		//TODO implement
		return "GetFields_Result [fields=" + fields + "]";
	}
	
	
	
}
