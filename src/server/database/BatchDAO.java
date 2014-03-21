package server.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import shared.model.Batch;

public class BatchDAO {
	//Global Variables
	private static Logger logger;
	static{
		logger = Logger.getLogger("IndexerServer");
	}
	
	private Database db;
	
	
	//Constructors
	/**
	 * Default constructor, sets database to null
	 */
	public BatchDAO(){
		db = null;
	}
	
	/**
	 * Constructor, sets db
	 * @param db The database to use
	 */
	public BatchDAO(Database db){
		this.db = db;
	}
	
	
	//Methods
	/**
	 * Assigns the first available batch to the specified user by updating
	 * the batch in the database and returns the batch that was assigned
	 * @param userID The userID of the user that needs to be assigned the batch
	 * @param projectID The project to assign the batch from
	 * @return The batch that the user was assigned. Returns null if there is no available
	 * batch in the specified project.
	 * @throws DatabaseException
	 */
	public Batch assignBatch(int userID, int projectID) throws DatabaseException{
		logger.entering("server.database.BatchDAO", "assignBatch");
		//set up variable to store the result
		Batch result = null;
		//query the database
		PreparedStatement stmt  = null;
		ResultSet rs = null;
		try{
			String sql = "select batchid,imagepath,projectid,fullyindexed,userid "+
						 "from batches "+
						 "where projectid ="+projectID+" and userid="+ -1 +" and fullyindexed=" + 0;
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()){
				//make the returned object into a batch
				int nBatchID = rs.getInt(1);
				String nImagePath = rs.getString(2);
				int nProjectID = rs.getInt(3);
				boolean nFullyIndexed = false;
				if(rs.getInt(4) == 1)
					nFullyIndexed = true;
				int nUserID = rs.getInt(5);
				result = new Batch(nBatchID, nImagePath, nProjectID, nFullyIndexed,
							nUserID);
				
				//update the userID of the newly assigned batch
				result.setUserID(userID);
				update(result);
			}		
		}
		catch(SQLException e){
			throw new DatabaseException(e.getMessage(), e);
		}
		finally{
			try{
				if(stmt != null){
					stmt.close();
				}
			}
			catch(SQLException e){
				System.out.println("Something went terribly wrong when trying to close a statement in "+
									"server.database.BatchDAO assignBatch\n" + e.getMessage());
			}
		}

		logger.exiting("server.database.BatchDAO", "assignBatch");
		return result;
	}
	/**
	 * Gets a sample image based on the projectID
	 * @param projectID The project to get the sample image from 
	 * @return A Batch which contains the sample image URL
	 */
	public Batch getSampleImage(int projectID) throws DatabaseException{
		logger.entering("server.database.BatchDAO", "getSampleImage");
		//set up variable to store the result
		Batch result = null;
		//query the database
		PreparedStatement stmt  = null;
		ResultSet rs = null;
		try{
			String sql = "select batchid,imagepath,projectid,fullyindexed,userid "+
						 "from batches "+
						 "where projectid = " + projectID;
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()){
				int nBatchID = rs.getInt(1);
				String nImagePath = rs.getString(2);
				int nProjectID = rs.getInt(3);
				boolean nFullyIndexed = false;
				if(rs.getInt(4) == 1)
					nFullyIndexed = true;
				int nUserID = rs.getInt(5);
				result = new Batch(nBatchID, nImagePath, nProjectID, nFullyIndexed,
							nUserID);
			}		
		}
		catch(SQLException e){
			throw new DatabaseException(e.getMessage(), e);
		}
		finally{
			try{
				if(stmt != null){
					stmt.close();
				}
			}
			catch(SQLException e){
				System.out.println("Something went terribly wrong when trying to close a statement in "+
									"server.database.BatchDAO getSampleImage\n" + e.getMessage());
			}
		}

		logger.exiting("server.database.BatchDAO", "getSampleImage");
		return result;
	}
	
	/**
	 * Gets the specified batch
	 * @param batchID The batch to get from the database
	 * @return The batch that corresponds to the batchID passed in
	 */
	public Batch getBatch(int batchID) throws DatabaseException{
		logger.entering("server.database.BatchDAO", "getBatch");
		//set up variable to store the result
		Batch result = null;
		//query the database
		PreparedStatement stmt  = null;
		ResultSet rs = null;
		try{
			String sql = "select batchid,imagepath,projectid,fullyindexed,userid "+
						 "from batches "+
						 "where batchid=" + batchID;
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()){
				int nBatchID = rs.getInt(1);
				String nImagePath = rs.getString(2);
				int nProjectID = rs.getInt(3);
				boolean nFullyIndexed = false;
				if(rs.getInt(4) == 1)
					nFullyIndexed = true;
				int nUserID = rs.getInt(5);
				result = new Batch(nBatchID, nImagePath, nProjectID, nFullyIndexed,
							nUserID);
			}		
		}
		catch(SQLException e){
			throw new DatabaseException(e.getMessage(), e);
		}
		finally{
			if(stmt != null){
				try{
					stmt.close();
				}
				catch(SQLException e){
					System.out.println("Something went terribly wrong when trying to close a statement in "+
										"server.database.BatchDAO getBatch\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.BatchDAO", "getBatch");
		return result;
	}
	
	/**
	 * Gets all the Batch from the database and returns it in a list
	 * @return A list of all the Batch
	 * @throws DatabaseException If any errors occur
	 */
	public List<Batch> getAll() throws DatabaseException{
		logger.entering("server.database.BatchDAO", "getAll");
		//set up variable to store the result
		List<Batch> result = new ArrayList<Batch>();
		//query the database
		PreparedStatement stmt  = null;
		ResultSet rs = null;
		try{
			String sql = "select BatchID,ImagePath,ProjectID,FullyIndexed,UserID "+
						 "from Batches";
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				int nBatchID = rs.getInt(1);
				String nImagePath = rs.getString(2);
				int nProjectID = rs.getInt(3);
				boolean nFullyIndexed = false;
				if(rs.getInt(4) == 1)
					nFullyIndexed = true;
				int nUserID = rs.getInt(5);
				result.add(new Batch(nBatchID, nImagePath, nProjectID, nFullyIndexed,
							nUserID));
			}		
		}
		catch(SQLException e){
			throw new DatabaseException(e.getMessage(), e);
		}
		finally{
			if(stmt != null){
				try{
					stmt.close();
				}
				catch(SQLException e){
					System.out.println("Something went terribly wrong when trying to close a statement in "+
										"server.database.BatchDAO getAll\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.BatchDAO", "getAll");
		return result;
	}
	
	
	/**
	 * Adds the specified Batch object to the database
	 * @param batch The Batch to add to the database
	 * @throws DatabaseException If an error occurs
	 */
	public void add(Batch batch) throws DatabaseException{
		logger.entering("server.database.BatchDAO", "add");
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		try{
			String sql = "insert into batches (imagepath,projectid,fullyindexed,userid) "+
							"values (?,?,?,?)";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString(1, batch.getImagePath());
			stmt.setInt(2, batch.getProjectID());
			int nFullyIndexed = 0;
			if(batch.isFullyIndexed())
				nFullyIndexed = 1;
			stmt.setInt(3, nFullyIndexed);
			stmt.setInt(4, batch.getUserID());
			if(stmt.executeUpdate() == 1){
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1); //new ID of the bath
				batch.setBatchID(id);
			}
		}
		catch(SQLException e){
			throw new DatabaseException(e.getMessage(), e);
		}
		finally{
			try{
				if(stmt != null){
					stmt.close();
				}
				if(keyRS != null){
					keyRS.close();
				}
				if(keyStmt != null){
					keyStmt.close();
				}
			}
			catch(SQLException e){
				System.out.println("Something went terribly wrong when trying to close a statement in "+
									"server.database.BatchDAO getSampleImage\n" + e.getMessage());
			}
		}
		logger.exiting("server.database.BatchDAO", "add");
	}
	
	
	/**
	 * Updates the specified Batch object in the database
	 * @param batch The Batch to update in the database
	 * @throws DatabaseException If an error occurs
	 */
	public void update(Batch batch) throws DatabaseException{
		logger.entering("server.database.BatchDAO", "update");
		
		//execute the update
		PreparedStatement stmt = null;
		try{
			String sql = "update batches set imagepath=?,projectid=?,fullyindexed=?,userID=? "+
							"where batchid=?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString(1, batch.getImagePath());
			stmt.setInt(2, batch.getProjectID());
			int nFullyIndexed = 0;
			if(batch.isFullyIndexed())
				nFullyIndexed = 1;
			stmt.setInt(3, nFullyIndexed);
			stmt.setInt(4, batch.getUserID());
			stmt.setInt(5, batch.getBatchID());
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("An error occurred when trying to update a batch in the database.");
			}	
		}
		catch(SQLException e){
			throw new DatabaseException(e.getMessage(), e);
		}
		finally{
			if(stmt != null){
				try{
					stmt.close();
				}
				catch(SQLException e){
					System.out.println("Something went terribly wrong when trying to close a statement in "+
										"server.database.BatchDAO update\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.BatchDAO", "update");
	}
	
	/**
	 * Deletes the specified Batch object from the database
	 * @param batch The Batch to delete from the database
	 * @throws DatabaseException If an error occurs
	 */
	public void delete(Batch batch) throws DatabaseException{
		logger.entering("server.database.BatchDAO", "delete");
		
		//execute the update
		PreparedStatement stmt = null;
		try{
			String sql = "delete from batches where (batchid=?)";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, batch.getBatchID());
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("An error occurred when trying to delete a batch from the database.");
			}	
		}
		catch(SQLException e){
			throw new DatabaseException(e.getMessage(), e);
		}
		finally{
			if(stmt != null){
				try{
					stmt.close();
				}
				catch(SQLException e){
					System.out.println("Something went terribly wrong when trying to close a statement in "+
										"server.database.BatchDAO delete\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.BatchDAO", "delete");
	}
}

