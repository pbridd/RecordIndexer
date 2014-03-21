package server.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import shared.model.User;

public class UserDAO {
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
	public UserDAO(){
		db = null;
	}
	
	/**
	 * Constructor, sets db
	 * @param db The database to use
	 */
	public UserDAO(Database db){
		this.db = db;
	}
	
	
	//Methods
	
	/**
	 * Authenticates the user. Returns a User object if the user was successfully authenticated,
	 * and null if it wasn't
	 * @param username The username to authenticate
	 * @param password The password to authenticate
	 * @return The User that was authenticated, and null if there was no matching username-password
	 * combination found
	 * @throws DatabaseException If errors occur
	 */
	public User validate(String username, String password) throws DatabaseException{
		logger.entering("server.database.UserDAO", "validate");
		//set up variable to store the result
		User result = null;
		//query the database
		PreparedStatement stmt  = null;
		ResultSet rs = null;
		try{
			String sql = "select userid,username,password,firstname,lastname,email,indexedrecords "+
						 "from users where username='" + username + "' and password='"+password + "'";
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()){
				int nUserID = rs.getInt(1);
				String nUsername = rs.getString(2);
				String nPassword = rs.getString(3);
				String nFirstName = rs.getString(4);
				String nLastName = rs.getString(5);
				String nEmail = rs.getString(6);
				int nIndexedRecords = rs.getInt(7);
				result = new User(nUserID, nUsername, nPassword, nFirstName, nLastName, 
						nEmail, nIndexedRecords);
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
										"server.database.UserDAO validate\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.UserDAO", "validate");
		return result;
	}
	
	/**
	 * Gets all the User from the database and returns it in a list
	 * @return A list of all the Users
	 * @throws DatabaseException If any errors occur
	 */
	public List<User> getAll() throws DatabaseException{
		logger.entering("server.database.UserDAO", "getAll");
		//set up variable to store the result
		List<User> result = new ArrayList<User>();
		//query the database
		PreparedStatement stmt  = null;
		ResultSet rs = null;
		try{
			String sql = "select userid,username,password,firstname,lastname,email,indexedrecords "+
						 "from users";
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				int nUserID = rs.getInt(1);
				String nUsername = rs.getString(2);
				String nPassword = rs.getString(3);
				String nFirstName = rs.getString(4);
				String nLastName = rs.getString(5);
				String nEmail = rs.getString(6);
				int nIndexedRecords = rs.getInt(7);
				result.add(new User(nUserID, nUsername, nPassword, nFirstName, nLastName, 
						nEmail, nIndexedRecords));
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
										"server.database.UserDAO validate\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.UserDAO", "validate");
		return result;
	}
	
	/**
	 * Adds the specified User object to the database
	 * @param user The User to add to the database
	 * @throws DatabaseException If an error occurs
	 */
	public void add(User user) throws DatabaseException{
		logger.entering("server.database.UserDAO", "add");
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		try{
			String sql = "insert into users (username,password,firstname,lastname,email,indexedrecords) "+
							"values (?,?,?,?,?,?)";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstName());
			stmt.setString(4, user.getLastName());
			stmt.setString(5, user.getEmail());
			stmt.setInt(6, user.getIndexedRecords());
			if(stmt.executeUpdate() == 1){
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1); //new ID of the bath
				user.setUserID(id);
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
									"server.database.UserDAO add\n" + e.getMessage());
			}
		}
		logger.exiting("server.database.UserDAO", "add");
	}
	
	
	/**
	 * Updates the specified User object in the database
	 * @param user The User to update in the database
	 * @throws DatabaseException If an error occurs
	 */
	public void update(User user) throws DatabaseException{
		logger.entering("server.database.UserDAO", "update");
		
		//execute the update
		PreparedStatement stmt = null;
		try{
			String sql = "update users set username=?,password=?,firstname=?,lastname=?,email=?,indexedrecords=? "+
							"where userid=?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstName());
			stmt.setString(4, user.getLastName());
			stmt.setString(5, user.getEmail());
			stmt.setInt(6, user.getIndexedRecords());
			stmt.setInt(7, user.getUserID());
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("An error occurred when trying to update a user in the database.");
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
										"server.database.UserDAO update\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.UserDAO", "update");
	}
	
	/**
	 * Deletes the specified User object from the database
	 * @param user The User to delete from the database
	 * @throws DatabaseException If an error occurs
	 */
	public void delete(User user) throws DatabaseException{
		logger.entering("server.database.UserDAO", "delete");
		
		//execute the update
		PreparedStatement stmt = null;
		try{
			String sql = "delete from users where (userid=?)";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, user.getUserID());
			if(stmt.executeUpdate() != 1){
				throw new DatabaseException("An error occurred when trying to delete a user from the database.");
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
										"server.database.UserDAO delete\n" + e.getMessage());
				}
			}
		}
		logger.exiting("server.database.UserDAO", "delete");
	}
}
