package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import shared.model.Record;


public class RecordDAO {
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
	public RecordDAO(){
		db = null;
	}
	
	/**
	 * Constructor, sets db
	 * @param db The database to use
	 */
	public RecordDAO(Database db){
		this.db = db;
	}
	
	
	//Methods
	
	/**
	 * Gets all the Record from the database and returns it in a list
	 * @return A list of all the Records
	 * @throws DatabaseException If any errors occur
	 */
	public List<Record> getAll() throws DatabaseException{
		logger.entering("server.database.RecordDAO", "get");
		//set up variable to store the result
		List<Record> result = new ArrayList<Record>();
		//query the database
		PreparedStatement stmt  = null;
		ResultSet rs = null;
		try{
			String sql = "select recordid,recordnumber,batchid "+
						 "from records";
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				int nRecordID = rs.getInt(1);
				int nRecordNumber = rs.getInt(2);
				int nBatchID = rs.getInt(3);
				
				result.add(new Record(nRecordID, nRecordNumber, nBatchID));
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
										"server.database.RecordDAO get\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.RecordDAO", "get");
		return result;
	}
	
	/**
	 * Gets the specified record from the database
	 * @param recordID The ID of the record to get from the database
	 * @return The specified record if it exists, null if it doesn't
	 */
	public Record getRecord(int recordID) throws DatabaseException{
		logger.entering("server.database.RecordDAO", "getRecord");
		//set up variable to store the result
		Record result = null;
		//query the database
		PreparedStatement stmt  = null;
		ResultSet rs = null;
		try{
			String sql = "select recordid,recordnumber,batchid "
						+"from records "+
						 "where recordid=" + recordID;
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()){
				int nRecordID = rs.getInt(1);
				int nRecordNumber = rs.getInt(2);
				int nBatchID = rs.getInt(3);
				
				result = new Record(nRecordID, nRecordNumber, nBatchID);
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
										"server.database.RecordDAO getRecord\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.RecordDAO", "getRecord");
		return result;
	}
	
	/**
	 * Adds the specified Record object to the database
	 * @param record The Record to add to the database
	 * @throws DatabaseException If an error occurs
	 */
	public void add(Record record) throws DatabaseException{
		logger.entering("server.database.RecordDAO", "add");
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		try{
			String sql = "insert into records (recordnumber,batchid) "+
							"values (?,?)";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, record.getRecordNumber());
			stmt.setInt(2, record.getBatchID());
			
			if(stmt.executeUpdate() == 1){
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1); //new ID of the bath
				record.setRecordID(id);
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
									"server.database.RecordDAO add\n" + e.getMessage());
			}
		}
		logger.exiting("server.database.RecordDAO", "add");
	}
	
	
	/**
	 * Updates the specified Record object in the database
	 * @param record The Record to update in the database
	 * @throws DatabaseException If an error occurs
	 */
	public void update(Record record) throws DatabaseException{
		logger.entering("server.database.RecordDAO", "update");
		
		//execute the update
		PreparedStatement stmt = null;
		try{
			String sql = "update records set recordnumber=?,batchid=? "+
							"where recordid=?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, record.getRecordNumber());
			stmt.setInt(2, record.getBatchID());
			stmt.setInt(3, record.getRecordID());
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("An error occurred when trying to update a record in the database.");
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
										"server.database.RecordDAO update\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.RecordDAO", "update");
	}
	
	/**
	 * Deletes the specified Record object from the database
	 * @param record The Record to delete from the database
	 * @throws DatabaseException If an error occurs
	 */
	public void delete(Record record) throws DatabaseException{
		logger.entering("server.database.RecordDAO", "delete");
		
		//execute the update
		PreparedStatement stmt = null;
		try{
			String sql = "delete from records where (recordid=?)";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, record.getRecordID());
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("An error occurred when trying to delete a record from the database.");
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
										"server.database.RecordDAO delete\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.RecordDAO", "delete");
	}
}

