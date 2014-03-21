package shared.model;

import java.util.List;

import server.database.BatchDAO;
import server.database.Database;
import server.database.DatabaseException;
import server.database.IndexedDataDAO;
import server.database.RecordDAO;
import server.database.UserDAO;

public class BatchManager extends ModelManager{

	
	//Static Functions
	/**
	 * Gets a sample image from the BatchDAO based on the projectID
	 * @param projectID The project to get the sample image from 
	 * @return A Batch which contains the sample image URL
	 * @throws ModelException If an error occurs
	 */
	public static Batch getSampleImage(int projectID) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			Batch retBatch = db.getBatchDAO().getSampleImage(projectID);
			db.endTransaction(true);
			return retBatch;
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
		
	}
	
	
	/**
	 * Does everything necessary to submit the batch using the DAOs (sets the batch's FullyIndexed value to 
	 * true, updates the batch, inserts the records and the indexedData into the database etc)
	 * @param user The user who submitted the batch, to update the record count of this user
	 * @param batchID The batch ID to submit
	 * @param records The records to add to the database
	 * @param indexedData The indexedData to add to the database
	 * @throws ModelException If an error occurs with the database
	 */
	public static void submitBatch(User user, int batchID, List<Record> records, List<IndexedData> indexedData,
			boolean tempIDs)	throws ModelException{
		Database db = new Database();
		try{
			//set the batch to indexed and update it in the database
			//also unassign the user from the batch
			db.startTransaction();
			BatchDAO bd = db.getBatchDAO();
			Batch tempBatch = bd.getBatch(batchID);
			if(tempBatch.isFullyIndexed()){
				throw new ModelException("This batch has already been submitted!");
			}
			tempBatch.setFullyIndexed(true);
			tempBatch.setUserID(-1);
			bd.update(tempBatch);
			
			//add the new records to the database
			
			for(Record r : records){
				RecordDAO rd = db.getRecordDAO();
				rd.add(r);
			}
			
			
			//add the newly indexed data to the database
			//if there are temp IDs assigned, make them not temporary!
			if(!tempIDs){
				
				for(IndexedData d : indexedData){
					IndexedDataDAO idd = db.getIndexedDataDAO();
					idd.add(d);
				}
				
			}
			else{
				//get the applicable fields with their IDs
				List<Field> fieldsForID = FieldManager.getFields(tempBatch.getProjectID());
				int idcnt = 0;
				int rcdcnt = 0;
				while(idcnt < indexedData.size()){
					for(int i = 0; i < fieldsForID.size(); i++){
						if(idcnt >= indexedData.size())
							continue;
						IndexedData temp = indexedData.get(idcnt);
						temp.setFieldID(fieldsForID.get(i).getFieldID());
						temp.setRecordID(records.get(rcdcnt).getRecordID());
						db.getIndexedDataDAO().add(temp);
						idcnt++;
					}
					rcdcnt++;
				}

				
			}
			//update the number of records the user has indexed
			
			UserDAO ud = db.getUserDAO();
			int tempRecCnt = user.getIndexedRecords();
			tempRecCnt += ProjectManager.getProject(tempBatch.getProjectID()).getRecordsPerImage(); 
			user.setIndexedRecords(tempRecCnt);
			ud.update(user);
			db.endTransaction(true);
			
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
		catch(ModelException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * Gets the specified batch from the BatchDAO
	 * @param batchID The batch to fetch
	 * @return The specified Batch
	 * @throws ModelException if an error occurs with the database
	 */
	public static Batch getBatch(int batchID) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			BatchDAO bd = db.getBatchDAO();
			Batch retBatch = bd.getBatch(batchID);
			db.endTransaction(true);
			return retBatch;
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}

	}
	
	/**
	 * This function returns the first available batch in the specified
	 * project and assigns it to the user in the database.
	 * @param projectID The project ID of the project to assign the batch from
	 * @param usr The user to assign the batch to
	 * @return The batch the user is assigned to
	 */
	public static Batch assignBatch(int projectID, User usr) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			Batch retBatch = db.getBatchDAO().assignBatch(usr.getUserID(), projectID);
			db.endTransaction(true);
			return retBatch;
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * Adds a batch to the database
	 * @param bch The batch to add into the database
	 * @throws ModelException if an error happens
	 */
	public static void addBatch(Batch bch) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			db.getBatchDAO().add(bch);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * Updates the database to reflect the changes made to the batch object passed into 
	 * the function
	 * @param bch The batch to update, with the new data already in the batch object
	 * @throws ModelException if the batch specified does not exist, or if an error occurs
	 */
	public static void updateBatch(Batch bch) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			db.getBatchDAO().update(bch);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * Deletes the specified batch from the database
	 * @param bch The batch to delete from the database
	 * @throws ModelException if the batch specified does not exist, or if an error occurs
	 */
	public static void deleteBatch(Batch bch) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			db.getBatchDAO().delete(bch);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * Deletes all Batch objects from the database using the BatchDAO methods
	 * @throws ModelException If an error occurs with the database
	 */
	public static void clearBatches() throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			List<Batch> batchList = db.getBatchDAO().getAll();
			for(Batch batch : batchList){
				db.getBatchDAO().delete(batch);
			}
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}


	
	

}
