package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import shared.model.Field;

public class FieldDAO {
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
	public FieldDAO(){
		db = null;
	}
	
	/**
	 * Constructor, sets db
	 * @param db The database to use
	 */
	public FieldDAO(Database db){
		this.db = db;
	}
	
	
	//Methods
	
	/**
	 * Gets all the Field from the database and returns it in a list
	 * @return A list of all the Field
	 * @throws DatabaseException If any errors occur
	 */
	public List<Field> getAll() throws DatabaseException{
		logger.entering("server.database.FieldDAO", "getAll");
		//set up variable to store the result
		List<Field> result = new ArrayList<Field>();
		//query the database
		PreparedStatement stmt  = null;
		ResultSet rs = null;
		try{
			String sql = "select fieldid,fieldnumber,fieldname,xcoord,width,helphtml,knowndata,projectid "+
						 "from fields";
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				int nFieldID = rs.getInt(1);
				int nFieldNumber = rs.getInt(2);
				String nFieldName = rs.getString(3);
				int nXCoord = rs.getInt(4);
				int nWidth = rs.getInt(5);
				String nHelpHTML = rs.getString(6);
				String nKnownData = rs.getString(7);
				int nProjectID = rs.getInt(8);
				result.add(new Field(nFieldID, nFieldNumber, nFieldName, nXCoord, nWidth,
							nHelpHTML, nKnownData, nProjectID));
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
										"server.database.FieldDAO getAll\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.FieldDAO", "getAll");
		return result;
	}
	
	/**
	 * Returns a list of all of the fields in the specified project. If a negative number
	 * is passed in, returns a list of all fields in the database.
	 * @param projectID The project to get the fields from
	 * @return A list of all of the fields in the specified project
	 * @throws DatabaseException if an error occurs with the database
	 */
	public List<Field> getFields(int projectID) throws DatabaseException{
		if(projectID < 0) return getAll();
		logger.entering("server.database.FieldDAO", "getFields");
		//set up variable to store the result
		List<Field> result = new ArrayList<Field>();
		//query the database
		PreparedStatement stmt  = null;
		ResultSet rs = null;
		try{
			String sql = "select fieldid,fieldnumber,fieldname,xcoord,width,helphtml,knowndata,projectid "+
						 "from fields where projectid=" + projectID;
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				int nFieldID = rs.getInt(1);
				int nFieldNumber = rs.getInt(2);
				String nFieldName = rs.getString(3);
				int nXCoord = rs.getInt(4);
				int nWidth = rs.getInt(5);
				String nHelpHTML = rs.getString(6);
				String nKnownData = rs.getString(7);
				int nProjectID = rs.getInt(8);
				result.add(new Field(nFieldID, nFieldNumber, nFieldName, nXCoord, nWidth,
							nHelpHTML, nKnownData, nProjectID));
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
										"server.database.FieldDAO getFields\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.FieldDAO", "getFields");
		return result;
	}
	
	/**
	 * Adds the specified Field object to the database
	 * @param field The Field to add to the database
	 * @throws DatabaseException If an error occurs
	 */
	public void add(Field field) throws DatabaseException{
		logger.entering("server.database.FieldDAO", "add");
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		try{
			String sql = "insert into fields (fieldnumber,fieldname,xcoord,width,helphtml,knowndata,projectid) "+
							"values (?,?,?,?,?,?,?)";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, field.getFieldNumber());
			stmt.setString(2, field.getFieldName());
			stmt.setInt(3, field.getXCoord());
			stmt.setInt(4, field.getWidth());
			stmt.setString(5, field.getHelpHTML());
			stmt.setString(6, field.getKnownData());
			stmt.setInt(7, field.getProjectID());
			if(stmt.executeUpdate() == 1){
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1); //new ID of the bath
				field.setFieldID(id);
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
									"server.database.FieldDAO add\n" + e.getMessage());
			}
		}
		logger.exiting("server.database.FieldDAO", "add");
	}
	
	
	/**
	 * Updates the specified Field object in the database
	 * @param field The Field to update in the database
	 * @throws DatabaseException If an error occurs
	 */
	public void update(Field field) throws DatabaseException{
		logger.entering("server.database.FieldDAO", "update");
		
		//execute the update
		PreparedStatement stmt = null;
		try{
			String sql = "update fields set fieldnumber=?,fieldname=?,xcoord=?,width=?,helphtml=?,knowndata=?,projectid=? "+
							"where fieldid=?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, field.getFieldNumber());
			stmt.setString(2, field.getFieldName());
			stmt.setInt(3, field.getXCoord());
			stmt.setInt(4, field.getWidth());
			stmt.setString(5, field.getHelpHTML());
			stmt.setString(6, field.getKnownData());
			stmt.setInt(7, field.getProjectID());
			stmt.setInt(8, field.getFieldID());
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("An error occurred when trying to update a field in the database.");
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
										"server.database.FieldDAO update\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.FieldDAO", "update");
	}
	
	/**
	 * Deletes the specified Field object from the database
	 * @param field The Field to delete from the database
	 * @throws DatabaseException If an error occurs
	 */
	public void delete(Field field) throws DatabaseException{
		logger.entering("server.database.FieldDAO", "delete");
		
		//execute the update
		PreparedStatement stmt = null;
		try{
			String sql = "delete from fields where (fieldid=?)";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, field.getFieldID());
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("An error occurred when trying to delete a field from the database.");
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
										"server.database.FieldDAO delete\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.FieldDAO", "delete");
	}
}
