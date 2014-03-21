package server;

import java.io.IOException;
import java.net.*;
import java.util.logging.*;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;

import shared.model.*;

public class Server {
	//Global Variables
	private int SERVER_PORT_NUMBER;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	private HttpServer server;
	
	private HttpHandler validateUserHandler;
	private HttpHandler getFieldsHandler;
	private HttpHandler getProjectsHandler;
	private HttpHandler downloadBatchHandler;
	private HttpHandler searchHandler;
	private HttpHandler submitBatchHandler;
	private HttpHandler getSampleImageHandler;
	private HttpHandler fileDownloadHandler;
	
	
	private static Logger logger;
	
	static {
		try {
			initLog();
		}
		catch (IOException e) {
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	//Constructors
	/**
	 * Default constructor. Sets the port to 8080.
	 */
	private Server(){
		SERVER_PORT_NUMBER = 8080;
		validateUserHandler = new ValidateUserHandler();
		getFieldsHandler = new GetFieldsHandler();
		getProjectsHandler = new GetProjectsHandler();
		downloadBatchHandler = new DownloadBatchHandler();
		searchHandler = new SearchHandler();
		submitBatchHandler = new SubmitBatchHandler();
		getSampleImageHandler = new GetSampleImageHandler();
		fileDownloadHandler = new FileDownloadHandler();
		
		
	}
	
	/**
	 * Constructor. Sets the server port to the passed in parameter
	 * @param port The port number to run the server on
	 */
	private Server(int port){
		SERVER_PORT_NUMBER = port;
		validateUserHandler = new ValidateUserHandler();
		getFieldsHandler = new GetFieldsHandler();
		getProjectsHandler = new GetProjectsHandler();
		downloadBatchHandler = new DownloadBatchHandler();
		searchHandler = new SearchHandler();
		submitBatchHandler = new SubmitBatchHandler();
		getSampleImageHandler = new GetSampleImageHandler();
		fileDownloadHandler = new FileDownloadHandler();
	}
	
	/**
	 * Initializes the logger
	 * @throws IOException If a problem occurs with the logger
	 */
	private static void initLog() throws IOException {
		
		Level logLevel = Level.FINE;
		
		logger = Logger.getLogger("server"); 
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}
		
	/**
	 * Runs the server
	 */
	private void run(){
		logger.info("Initializing Model");
		try{
			ModelManager.initialize();
		}
		catch(ModelException e){
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		
		logger.info("Initializing HTTP Server");
		
		try{
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
					MAX_WAITING_CONNECTIONS);
		}
		catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}
		
		server.createContext("/validateUser", validateUserHandler);
		server.createContext("/getProjects", getProjectsHandler);
		server.createContext("/getSampleImage", getSampleImageHandler);
		server.createContext("/downloadBatch", downloadBatchHandler);
		server.createContext("/submitBatch", submitBatchHandler);
		server.createContext("/getFields", getFieldsHandler);
		server.createContext("/search", searchHandler);
		server.createContext("/images", fileDownloadHandler);
		server.createContext("/knowndata", fileDownloadHandler);
		server.createContext("/fieldhelp", fileDownloadHandler);
		logger.info("Starting HTTP Server");
		server.start();
	}
	
	//main function
	/**
	 * The main function. Runs the server.
	 * @param args Can only accept 1 arg : the port to run the server on
	 * If this is not specified, the server runs on port 8080.
	 */
	public static void main(String[] args){
		Server serve;
		//get the passed in port number, if any
		if(args.length > 0){
			serve = new Server(Integer.parseInt(args[0]));
		}
		else
			serve = new Server();
		serve.run();
	}
}
