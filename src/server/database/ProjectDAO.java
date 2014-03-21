package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import shared.model.Project;

public class ProjectDAO {
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
	public ProjectDAO(){
		db = null;
	}
	
	/**
	 * Constructor, sets db
	 * @param db The database to use
	 */
	public ProjectDAO(Database db){
		this.db = db;
	}
	
	
	//Methods
	
	/**
	 * Gets all the Projects from the database and returns them in a list
	 * @return A list of all the Projects
	 * @throws DatabaseException If any errors occur
	 */
	public List<Project> getAll() throws DatabaseException{
		logger.entering("server.database.ProjectDAO", "getAll");
		List<Project> result = new ArrayList<Project>();
		//query the database
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			String sql = "select projectid,projecttitle,recordsperimage,firstycoord,recordheight "
					+"from projects";
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				int nProjectID = rs.getInt(1);
				String nProjectTitle = rs.getString(2);
				int nRecordsPerImage = rs.getInt(3);
				int nFirstYCoord = rs.getInt(4);
				int nRecordHeight = rs.getInt(5);
				result.add(new Project(nProjectID, nProjectTitle, nRecordsPerImage, nFirstYCoord, nRecordHeight));
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
										"server.database.ProjectDAO getAll\n" + e.getMessage());
				}
			}
		}
	logger.exiting("server.database.ProjectDAO", "getAll");
	return result;
	}
	
	/**
	 * Gets the specified project from the database
	 * @param projectID The ID of the project to get from the database
	 * @return The specified project if it exists, null if it doesn't
	 */
	public Project getProject(int projectID) throws DatabaseException{
		logger.entering("server.database.ProjectDAO", "getProject");
		//set up variable to store the result
		Project result = null;
		//query the database
		PreparedStatement stmt  = null;
		ResultSet rs = null;
		try{
			String sql = "select projectid,projecttitle,recordsperimage,firstycoord,recordheight "
						+"from projects "+
						 "where projectid=" + projectID;
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()){
				int nProjectID = rs.getInt(1);
				String nProjectTitle = rs.getString(2);
				int nRecordsPerImage = rs.getInt(3);
				int nFirstYCoord = rs.getInt(4);
				int nRecordHeight = rs.getInt(5);
				result = new Project(nProjectID, nProjectTitle, nRecordsPerImage, nFirstYCoord, nRecordHeight);
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
										"server.database.ProjectDAO getProject\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.ProjectDAO", "getProject");
		return result;
	}
	
	
	/**
	 * Adds the specified Project object to the database
	 * @param project The Project to add to the database
	 * @throws DatabaseException If an error occurs
	 */
	public void add(Project project) throws DatabaseException{
		logger.entering("server.database.ProjectDAO", "add");
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		try{
			String sql = "insert into projects (projecttitle,recordsperimage,firstycoord,recordheight) "+
							"values (?,?,?,?)";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString(1, project.getProjectTitle());
			stmt.setInt(2, project.getRecordsPerImage());
			stmt.setInt(3, project.getFirstYCoord());
			stmt.setInt(4, project.getRecordHeight());
			if(stmt.executeUpdate() == 1){
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1); //new ID of the bath
				project.setProjectID(id);
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
									"server.database.ProjectDAO add\n" + e.getMessage());
			}
		}
		logger.exiting("server.database.ProjectDAO", "add");
	}
	
	
	/**
	 * Updates the specified Project object in the database
	 * @param project The Project to update in the database
	 * @throws DatabaseException If an error occurs
	 */
	public void update(Project project) throws DatabaseException{
logger.entering("server.database.ProjectDAO", "update");
		
		//execute the update
		PreparedStatement stmt = null;
		try{
			String sql = "update projects set projecttitle=?,recordsperimage=?,firstycoord=?,recordheight=? "+
							"where projectid=?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString(1, project.getProjectTitle());
			stmt.setInt(2, project.getRecordsPerImage());
			stmt.setInt(3, project.getFirstYCoord());
			stmt.setInt(4, project.getRecordHeight());
			stmt.setInt(5, project.getProjectID());
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("An error occurred when trying to update a project in the database.");
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
										"server.database.ProjectDAO update\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.ProjectDAO", "update");
	}
	
	/**
	 * Deletes the specified Project object from the database
	 * @param project The Project to delete from the database
	 * @throws DatabaseException If an error occurs
	 */
	public void delete(Project project) throws DatabaseException{
		logger.entering("server.database.ProjectDAO", "delete");
		
		//execute the update
		PreparedStatement stmt = null;
		try{
			String sql = "delete from projects where (projectid=?)";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, project.getProjectID());
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("An error occurred when trying to delete a project from the database.");
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
										"server.database.ProjectDAO delete\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.ProjectDAO", "delete");
	}
}
