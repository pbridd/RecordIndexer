package shared.model;

import java.util.List;

import server.database.Database;
import server.database.DatabaseException;

public class ProjectManager extends ModelManager{
	/**
	 * Default constructor
	 */
	public ProjectManager(){
	}
	
	/**
	 * Gets all of the projects in this database using the ProjectDAO class
	 * @return A list of all of the projects in the database
	 * @throws ModelException if an error occurs with the database
	 */
	public static List<Project> getAllProjects() throws ModelException{
		Database db = new Database();
		List<Project> result = null;
		try{
			db.startTransaction();
			result = db.getProjectDAO().getAll();
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
		return result;
	}
	
	
	/**
	 * Gets a certain project from this database using the ProjectDAO class
	 * @param projectID The projectID of the project to get from the database
	 * @return A project object if the projectID was found, null if it was not
	 * @throws ModelException if an error occurs with the database
	 */
	public static Project getProject(int projectID) throws ModelException{
		Database db = new Database();
		Project result = null;
		try{
			db.startTransaction();
			result = db.getProjectDAO().getProject(projectID);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
		return result;
	}
	
	
	/**
	 * Adds a project to the database
	 * @param prj The project to add into the database
	 * @throws ModelException if an error happens with the database
	 */
	public static void addProject(Project prj) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			db.getProjectDAO().add(prj);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * Updates the database to reflect the changes made to the project object passed into 
	 * the function
	 * @param prj The project to update, with the new data already in the project object
	 * @throws ModelException if the project specified does not exist, or if an error occurs
	 * with the database
	 */
	public static void updateProject(Project prj) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			db.getProjectDAO().update(prj);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * Deletes the specified project from the database
	 * @param prj The project to delete from the database
	 * @throws ModelException if the project specified does not exist, or if an error occurs
	 * with the database. 
	 */
	public static void deleteProject(Project prj) throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			db.getProjectDAO().delete(prj);
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
	
	/**
	 * Deletes all Project objects from the database using the ProjectDAO methods
	 * @throws ModelException If an error occurs with the database
	 */
	public static void clearProjects() throws ModelException{
		Database db = new Database();
		try{
			db.startTransaction();
			List<Project> projectList = db.getProjectDAO().getAll();
			for(Project project : projectList){
				db.getProjectDAO().delete(project);
			}
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw new ModelException(e.getMessage(), e);
		}
	}
}
