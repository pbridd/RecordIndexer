package shared.model;

import server.database.Database;
import server.database.DatabaseException;

public class ModelManager {
	
	//Constructors
	/**
	 * Default constructor
	 */
	public ModelManager(){
		
	}
	
	//static methods
	/**
	 * Initializes the database
	 * @throws ModelException If there are any errors
	 */
	public static void initialize() throws ModelException{
		try{
			Database.initialize();
		}
		catch (DatabaseException e){
			throw new ModelException(e.getMessage(), e);
		}
	}
	
}
