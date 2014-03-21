package shared.model;

import java.util.List;

/**
 * The CommandProcessor class serves as a processor for all needed
 * commands to simplify communications coming in from the client.
 * Essentially, it is a facade.
 * @author Parker Ridd
 *
 */
public class CommandProcessor extends ModelManager{
	
	

	
	//Static Methods
	/**
	 * Authenticates the user against the existing database using the method defined in the
	 * UserMangaer class
	 * @param username The username to authenticate
	 * @param password The password to authenticate
	 * @return The corresponding user if the authentication was 
	 * successful
	 * @return null if the user doesn't exist, or the password was incorrect
	 * @throws ModelException if an error occurs with the database
	 */
	public static User validateUser(String username, String password) throws ModelException {
		User usr = UserManager.validateUser(username, password);
		return usr;
	}
	
	/**
	 * Gets all of the projects in this database using the method defined in ProjectManager class,
	 * also makes sure the user's credentials are correct
	 * @return A list of all of the projects in the database
	 * @param username The username of the user that requested this operation
	 * @param password The password of the user who requested this operation
	 * @throws ModelException if an error occurs with the database, or if the user could not be authenticated
	 */
	public static  List<Project> getProjects(String username, String password) throws ModelException{
		User usr = validateUser(username, password);
		if(usr == null)
			return null;
		List<Project> projs = ProjectManager.getAllProjects();
		return projs;
	}
	
	/**
	 * Gets all of the projects in this database using the method defined in ProjectManager class,
	 * also makes sure the user's credentials are correct
	 * @return A list of all of the projects in the database
	 * @param username The username of the user that requested this operation
	 * @param password The password of the user who requested this operation
	 * @throws ModelException if an error occurs with the database, or if the user could not be authenticated
	 */
	public static Project getProject(String username, String password, int projectID) throws ModelException{
		User usr = validateUser(username, password);
		if(usr == null)
			return null;
		return ProjectManager.getProject(projectID);
		
	}
	
	/**
	 * Gets a sample image of the specified project using the the method defined in  the ProjectManager
	 * class
	 * @param username The username of the user that requested this operation
	 * @param password The password of the user who requested this operation
	 * @param projectID The ID of the project to get the sample image from
	 * @return A batch which contains the sample image the user wants to see
	  * @throws ModelException if an error occurs with the database, or if the
	 * user cannot be authenticated
	 */
	public static Batch getSampleImage(String username, String password, int projectID) throws ModelException{
		User usr = validateUser(username, password);
		if(usr == null)
			return null;
		Batch bch = BatchManager.getSampleImage(projectID);
		return bch;
	}
	
	/**
	 * Returns the batch that is assigned to the user
	 * @param username The username of the user that requested this operation
	 * @param password The password of the user who requested this operation
	 * @param projectID The project to download the batch from
	 * @return The batch that is assigned to the user
	 * @throws ModelException if an error occurs with the database, if the user could not be authenticated
	 */
	public static Batch downloadBatchBatch(String username, String password, int projectID) throws ModelException{
		User usr = validateUser(username, password);
		if(usr == null)
			return null;
		Batch asBatch = BatchManager.assignBatch(projectID, usr);
		return asBatch;
	}
	
	/**
	 * Gets a List of all fields from a certain project in the database by using the corresponding method in the 
	 * FieldManager class
	 * @param projectID the ID of the project to get fields from. If the prjID is negative, returns
	 * a list of all fields in the database
	 * @return A List of all the fields in the specified project
	 * @throws ModelException if an error occurs with the database
	 */
	public static List<Field> getFields(String username, String password, int projectID) throws ModelException{
		User usr = validateUser(username, password);
		if(usr == null)
			return null;
		return FieldManager.getFields(projectID);
	}
	
	/**
	 * Does everything necessary to submit the batch using the appropriate method
	 * in the BatchManager class (sets the batch's FullyIndexed value to 
	 * true, updates the batch, inserts the records and the indexedData into the database etc)
	 * @param username The username of the user that requested this operation
	 * @param password The password of the user who requested this operation
	 * @param batchID The batch ID to submit
	 * @param records The records to add to the database
	 * @param indexedData The indexedData to add to the database
	 * @throws ModelException If an error occurs with the database
	 */
	public static void submitBatch(String username, String password,
					int batchID, List<Record> records, List<IndexedData> indexedData, boolean tempIDs)
									throws ModelException{
		User user = validateUser(username, password);
		if(user == null)
			throw new ModelException("The user could not be validated!");
		BatchManager.submitBatch(user, batchID, records, indexedData, tempIDs);
		
	}
	
	/**
	 * Authenticates the user and then searches the specified fields for the specified string
	 * using the appropriate IndexedDataManager method and returns a list of IndexedData as a result
	 * @param username The username of the user that requested this operation
	 * @param password The password of the user who requested this operation
	 * @param fieldID The fieldIDs to search
	 * @param str The string to search for
	 * @return A list of all of the found IndexedData
	 * @throws ModelException if an error occurs in the database
	 */
	public static List<IndexedData> search(String username, String password, int fieldID, String str) throws ModelException{
		User usr = validateUser(username, password);
		if(usr == null)
			return null;
		return IndexedDataManager.search(fieldID, str);
		
	}
	
	/**
	 * Authenticates the user, then Gets the specified batch from the appropriate method on in the BatchManager class
	 * @param username The username of the user that requested this operation
	 * @param password The password of the user who requested this operation
	 * @param batchID The batch to fetch
	 * @return The specified Batch
	 * @throws ModelException if an error occurs in the database
	 */
	public static Batch getBatch(String username, String password, int batchID) throws ModelException{
		User usr = validateUser(username, password);
		if(usr == null)
			return null;
		Batch bch = BatchManager.getBatch(batchID);
		return bch;
	}
	
	/**
	 * Authenticates the user, then Gets the specified record from the appropriate method in the RecordManager class
	 * @param username The username of the user that requested this operation
	 * @param password The password of the user who requested this operation
	 * @param recordID The record to fetch
	 * @return The specified record
	 * @throws ModelException If an error occurs in the database
	 */
	public static Record getRecord(String username, String password, int recordID) throws ModelException{
		User usr = validateUser(username, password);
		if(usr == null)
			return null;
		Record rcd = RecordManager.getRecord(recordID);
		return rcd;
	}
	
	/**
	 * Clears the database
	 * @throws ModelException if something goes wrong
	 */
	public static void clearDatabase() throws ModelException{
		BatchManager.clearBatches();
		UserManager.clearUsers();
		FieldManager.clearFields();
		ProjectManager.clearProjects();
		RecordManager.clearRecords();
		IndexedDataManager.clearIndexedData();
	}
	
	
	
	
	
}
