package shared.model;

import java.util.List;

import server.database.Database;
import server.database.DatabaseException;

public class RecordManager extends ModelManager{
	/**
	 * Default constructor
	 */
	public RecordManager(){
	}
	
	/**
	 * Adds a record to the database
	 * @param rcd The record to add into the database
	 * @throws ModelException if an error happens with the database
	 */
	public static void addRecord(Record rcd) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			db.getRecordDAO().add(rcd);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * Get the record with the specified recordID from the database
	 * @param recordID The ID of the record to retrieve
	 * @throws ModelException if an error happens with the database
	 */
	public static Record getRecord(int recordID) throws ModelException{
		Database db = new Database();
		Record result;
		try{
			db.startTransaction();
			result = db.getRecordDAO().getRecord(recordID);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * Updates the database to reflect the changes made to the record object passed into 
	 * the function
	 * @param rcd The record to update, with the new data already in the record object
	 * @throws ModelException if the record specified does not exist, or if an error occurs
	 * with the database
	 */
	public static void updateRecord(Record rcd) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			db.getRecordDAO().update(rcd);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * Deletes the specified record from the database
	 * @param rcd The record to delete from the database
	 * @throws ModelException if the record specified does not exist, or if an error occurs
	 * with the database. 
	 */
	public static void deleteRecord(Record rcd) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			db.getRecordDAO().delete(rcd);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * Deletes all Record objects from the database using the RecordDAO methods
	 * @throws ModelException If an error occurs with the database
	 */
	public static void clearRecords() throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			List<Record> recordList = db.getRecordDAO().getAll();
			for(Record record : recordList){
				db.getRecordDAO().delete(record);
			}
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
}
