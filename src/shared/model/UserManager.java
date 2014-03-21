package shared.model;

import java.util.List;

import server.database.Database;
import server.database.DatabaseException;


public class UserManager extends ModelManager{
	/**
	 * Default constructor
	 */
	public UserManager(){
	}
	
	/**
	 * Authenticates the user against the existing database using the UserDAO class
	 * @param username The username to authenticate
	 * @param password The password to authenticate
	 * @return The corresponding user if the authentication was 
	 * successful
	 * @return null if the user doesn't exist, or the password was incorrect
	 * @throws ModelException if an error occurs with the database
	 */
	public static User validateUser(String username, String password) throws ModelException{
		Database db = new Database();
		User result = null;
		try{
			db.startTransaction();
			result = db.getUserDAO().validate(username, password);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * Adds a user to the database
	 * @param usr The user to add into the database
	 * @throws ModelException if an error happens with the database
	 */
	public static void addUser(User usr) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			db.getUserDAO().add(usr);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * Updates the database to reflect the changes made to the user object passed into 
	 * the function
	 * @param usr The user to update, with the new data already in the user object
	 * @throws ModelException if the user specified does not exist, or if an error occurs
	 * with the database
	 */
	public static void updateUser(User usr) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			db.getUserDAO().update(usr);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * Deletes the specified user from the database
	 * @param usr The user to delete from the database
	 * @throws ModelException if the user specified does not exist, or if an error occurs
	 * with the database
	 */
	public static void deleteUser(User usr) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			db.getUserDAO().delete(usr);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * Deletes all User objects from the database using the UserDAO methods
	 * @throws ModelException If an error occurs with the database
	 */
	public static void clearUsers() throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			List<User> userList = db.getUserDAO().getAll();
			for(User user : userList){
				db.getUserDAO().delete(user);
			}
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
}
