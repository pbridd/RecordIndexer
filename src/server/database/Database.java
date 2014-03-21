 package server.database;
import java.io.File;
import java.sql.*;
import java.util.logging.*;


public class Database {
	//Global Variables
	private BatchDAO batchDAO;
	private FieldDAO fieldDAO;
	private IndexedDataDAO indexedDataDAO;
	private ProjectDAO projectDAO;
	private RecordDAO recordDAO;
	private UserDAO userDAO;
	private Connection connection;
	
	
	private static Logger logger;
	static {
		logger = Logger.getLogger("IndexerServer");
	}
	
	//Constructors
	/**
	 * Default constructor, initializes the database and makes the appropriate DAOs.
	 */
	public Database(){
		batchDAO = new BatchDAO(this);
		fieldDAO = new FieldDAO(this);
		indexedDataDAO = new IndexedDataDAO(this);
		projectDAO = new ProjectDAO(this);
		recordDAO = new RecordDAO(this);
		userDAO = new UserDAO(this);
		connection = null;
	}
	
	//Static Methods
	/**
	 * Initialize the database by loading the SQLite driver
	 * @throws DatabaseException If an error occurs
	 */
	public static void initialize() throws DatabaseException {
		//TODO implement
		logger.entering("server.database.Database", "initialize");
		try{
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch(ClassNotFoundException e){
			throw new DatabaseException("Could not load database driver");
		}
		logger.exiting("server.database.Databse", "initialize");
	}
	
	
	//Methods
	/**
	 * Starts a database transaction
	 * @throws DatabaseException If an error occurs
	 */
	public void startTransaction() throws DatabaseException{
		logger.entering("server.database.Database", "startTransaction");
		String dbName = "data" + File.separator + "database" +File.separator + "indexerdb.sqlite";
		String connectionURL = "jdbc:sqlite:" + dbName;
		connection = null;
		try{
			//open a database connection
			connection = DriverManager.getConnection(connectionURL);
			connection.setAutoCommit(false);
		}
		catch(SQLException e){
			//an error has occurred
			throw new DatabaseException(e.getMessage(), e);
		}
		
		logger.exiting("server.database.Database",  "startTransaction");
	}
	
	/**
	 * Ends a database transaction, and commits it if commit=true
	 * @param commit Whether or not to commit the transaction
	 */
	public void endTransaction(boolean commit){
		logger.entering("server.database.Database", "endTransaction");
		try{
			if(commit){
				connection.commit();
			}
			else{
				connection.rollback();
			}
		}
		catch(SQLException e){
			//Don't throw an exception, just alert the user to the problem
			System.out.println("Something just went very, very wrong with the endTransaction function "
					+"in server.database.Database\n" + e.getMessage());
		}
		finally{
			try{
				connection.close();
			}
			catch(SQLException e){
				System.out.println("Something just went very, very wrong when trying to close the "
						+"transaction in server.database.Database\n" + e.getMessage());
			}
		}
		logger.exiting("server.database.Database", "endTransaction");
	}
	
	
	//Getter and Setter Methods
	/**
	 * @return the batchDAO
	 */
	public BatchDAO getBatchDAO() {
		return batchDAO;
	}

	/**
	 * @return the fieldDAO
	 */
	public FieldDAO getFieldDAO() {
		return fieldDAO;
	}

	/**
	 * @return the indexedDataDAO
	 */
	public IndexedDataDAO getIndexedDataDAO() {
		return indexedDataDAO;
	}

	/**
	 * @return the projectDAO
	 */
	public ProjectDAO getProjectDAO() {
		return projectDAO;
	}

	/**
	 * @return the recordDAO
	 */
	public RecordDAO getRecordDAO() {
		return recordDAO;
	}

	/**
	 * @return the userDAO
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}

	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}
	
	
	
	

}


