package shared.model;

import java.util.List;

import server.database.Database;
import server.database.DatabaseException;



public class IndexedDataManager extends ModelManager{
	/**
	 * Default constructor
	 */
	public IndexedDataManager(){
	}
	
	//Static Methods
	/**
	 * Searches the specified fields for the specified string using the
	 * DAOs and returns a list of IndexedData as a result
	 * @param fieldID The fieldIDs to search
	 * @param str The string to search for
	 * @return A list of all of the found IndexedData
	 * @throws ModelException If an error occurs
	 */
	public static List<IndexedData> search(int fieldID, String str) throws ModelException{
		Database db = new Database();
		List<IndexedData> results = null;
		try{
			db.startTransaction();
			results = db.getIndexedDataDAO().search(fieldID, str);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
		return results;
	}
	
	/**
	 * Adds a indexed data to the database
	 * @param idata The indexed data to add into the database
	 * @throws ModelException if an error happens with the database
	 */
	public static void addIndexedData(IndexedData idata) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			db.getIndexedDataDAO().add(idata);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * Updates the database to reflect the changes made to the indexed data object passed into 
	 * the function
	 * @param idata The indexed data to update, with the new data already in the indexed data object
	 * @throws ModelException if the indexed data specified does not exist, or if an error occurs
	 * with the database
	 */
	public static void updateIndexedData(IndexedData idata) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			db.getIndexedDataDAO().update(idata);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * Deletes the specified indexed data from the database
	 * @param idata The indexed data to delete from the database
	 * @throws ModelException if the indexed data specified does not exist, or if an error occurs
	 * with the database. 
	 */
	public static void deleteIndexedData(IndexedData idata) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			db.getIndexedDataDAO().delete(idata);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * Deletes all IndexedData objects from the database using the IndexedDataDAO methods
	 * @throws ModelException If an error occurs with the database
	 */
	public static void clearIndexedData() throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			List<IndexedData> indexedDataList = db.getIndexedDataDAO().getAll();
			for(IndexedData indexedData : indexedDataList){
				db.getIndexedDataDAO().delete(indexedData);
			}
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
}
