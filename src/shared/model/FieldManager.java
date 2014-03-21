package shared.model;


import java.util.List;

import server.database.Database;
import server.database.DatabaseException;

public class FieldManager extends ModelManager {
	/**
	 * Default constructor
	 */
	public FieldManager(){
		
	}
	
	//Static Methods
	/**
	 * Gets a List of all fields from a certain project in the database
	 * @param prjID the ID of the project to get fields from. If the prjID is negative, returns
	 * a list of all fields in the database
	 * @return A List of all the fields in the specified project
	 * @throws ModelException if an error occurs with the database
	 */
	public static List<Field> getFields(int prjID) throws ModelException{
		Database db = new Database();
		List<Field> result = null;
		try{
			db.startTransaction();
			result = db.getFieldDAO().getFields(prjID);			
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * Adds a field to the database
	 * @param fld The field to add into the database
	 * @throws ModelException if an error happens
	 */
	public static void addField(Field fld) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			db.getFieldDAO().add(fld);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}

	}
	
	/**
	 * Updates the database to reflect the changes made to the field object passed into 
	 * the function
	 * @param fld The field to update, with the new data already in the field object
	 * @throws ModelException if the field specified does not exist, or if an error occurs
	 */
	public static void updateField(Field fld) throws ModelException{
		Database db = new Database();

		try{
			db.startTransaction();
			db.getFieldDAO().update(fld);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}

	}
	
	/**
	 * Deletes the specified field from the database
	 * @param fld The field to delete from the database
	 * @throws ModelException if the field specified does not exist, or if an error occurs
	 */
	public static void deleteField(Field fld) throws ModelException{
		Database db = new Database();

		try{
			db.startTransaction();
			db.getFieldDAO().delete(fld);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * Deletes all Field objects from the database using the FieldDAO methods
	 * @throws ModelException If an error occurs with the database
	 */
	public static void clearFields() throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			List<Field> fieldList = db.getFieldDAO().getAll();
			for(Field field : fieldList){
				db.getFieldDAO().delete(field);
			}
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}

}
