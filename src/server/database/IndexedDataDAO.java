package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.*;

import shared.model.*;

public class IndexedDataDAO {
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
	public IndexedDataDAO(){
		db = null;
	}
	
	/**
	 * Constructor, sets db
	 * @param db The database to use
	 */
	public IndexedDataDAO(Database db){
		this.db = db;
	}
	
	
	//Methods
	/**
	 * Searches the specified fields for the specified string and returns a list of IndexedData as
	 * a result
	 * @param fieldID The fieldIDs to search
	 * @param str The string to search for
	 * @return A list of all of the found IndexedData
	 * @throws DatabaseException If an error occurs
	 */
	public List<IndexedData> search(int fieldID, String str) throws DatabaseException{
		logger.entering("server.database.IndexedDataDAO", "search");
		//set up variable to store the result
		List<IndexedData> result = new ArrayList<IndexedData>();
		//query the database
		PreparedStatement stmt  = null;
		ResultSet rs = null;
		try{
			String sql = "select indexeddataid,datavalue,recordid,fieldid "+
						 "from indexeddata where fieldid=" + fieldID;
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				int nIndexedDataID = rs.getInt(1);
				String nDataValue = rs.getString(2);
				int nRecordID = rs.getInt(3);
				int nFieldID = rs.getInt(4);
				if(str.toLowerCase().equals(nDataValue.toLowerCase())){
					result.add(new IndexedData(nIndexedDataID, nDataValue, nRecordID, nFieldID));
				}
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
										"server.database.IndexedDataDAO search\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.IndexedDataDAO", "search");
		return result;
	}
	
	/**
	 * Gets all the IndexedData from the database and returns it in a list
	 * @return A list of all the IndexedData
	 * @throws DatabaseException If any errors occur
	 */
	public List<IndexedData> getAll() throws DatabaseException{
		logger.entering("server.database.IndexedDataDAO", "getAll");
		//set up variable to store the result
		List<IndexedData> result = new ArrayList<IndexedData>();
		//query the database
		PreparedStatement stmt  = null;
		ResultSet rs = null;
		try{
			String sql = "select indexeddataid,datavalue,recordid,fieldid "+
						 "from indexeddata";
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				int nIndexedDataID = rs.getInt(1);
				String nDataValue = rs.getString(2);
				int nRecordID = rs.getInt(3);
				int nFieldID = rs.getInt(4);
				result.add(new IndexedData(nIndexedDataID, nDataValue, nRecordID, nFieldID));
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
										"server.database.IndexedDataDAO getAll\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.IndexedDataDAO", "getAll");
		return result;
	}
	
	/**
	 * Adds the specified IndexedData object to the database
	 * @param indexedData The IndexedData to add to the database
	 * @throws DatabaseException If an error occurs
	 */
	public void add(IndexedData indexedData) throws DatabaseException{
		logger.entering("server.database.IndexedDataDAO", "add");
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		try{
			String sql = "insert into indexeddata (datavalue,recordid,fieldid) "+
							"values (?,?,?)";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString(1, indexedData.getDataValue());
			stmt.setInt(2, indexedData.getRecordID());
			stmt.setInt(3, indexedData.getFieldID());
			if(stmt.executeUpdate() == 1){
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1); //new ID of the bath
				indexedData.setIndexedDataID(id);
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
									"server.database.IndexedDataDAO add\n" + e.getMessage());
			}
		}
		logger.exiting("server.database.IndexedDataDAO", "add");
	}
	
	
	/**
	 * Updates the specified IndexedData object in the database
	 * @param indexedData The IndexedData to update in the database
	 * @throws DatabaseException If an error occurs
	 */
	public void update(IndexedData indexedData) throws DatabaseException{
		logger.entering("server.database.IndexedDataDAO", "update");
		PreparedStatement stmt = null;
		try{
			String sql = "update indexeddata set datavalue=?,recordid=?,fieldid=? "+
							"where indexeddataid=?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString(1, indexedData.getDataValue());
			stmt.setInt(2, indexedData.getRecordID());
			stmt.setInt(3, indexedData.getFieldID());
			stmt.setInt(4, indexedData.getIndexedDataID());
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("An error occurred when trying to update an IndexedData in the database.");
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
									"server.database.IndexedDataDAO update\n" + e.getMessage());
			}
		}
		logger.exiting("server.database.IndexedDataDAO", "update");
	}
	
	/**
	 * Deletes the specified IndexedData object from the database
	 * @param indexedData The IndexedData to delete from the database
	 * @throws DatabaseException If an error occurs
	 */
	public void delete(IndexedData indexedData) throws DatabaseException{
		logger.entering("server.database.IndexedDataDAO", "delete");
		PreparedStatement stmt = null;

		try{
			String sql = "delete from indexeddata where (indexeddataid=?)";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, indexedData.getIndexedDataID());
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("An error occurred when trying to delete an IndexedData from the database.");
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
									"server.database.IndexedDataDAO delete\n" + e.getMessage());
			}
		}
		logger.exiting("server.database.IndexedDataDAO", "delete");
	}
	
}
