package client.gui;

import java.util.Arrays;
import java.util.List;

import shared.communication.DownloadBatch_Params;
import shared.communication.DownloadBatch_Result;
import shared.communication.GetFields_Params;
import shared.communication.GetFields_Result;
import shared.communication.GetProjects_Params;
import shared.communication.GetProjects_Result;
import shared.communication.GetSampleImage_Params;
import shared.communication.GetSampleImage_Result;
import shared.communication.SubmitBatch_Params;
import shared.communication.SubmitBatch_Result;
import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;
import shared.model.Batch;
import shared.model.Field;
import shared.model.IndexedData;
import shared.model.Project;
import shared.model.Record;
import shared.model.User;
import client.ClientException;
import client.communication.ClientCommunicator;
import client.gui.synchronization.BatchState;

public class UIIntegration {
	/**
	 * 
	 * @param username the username to authenticate
	 * @param password the password to authenticate
	 * @param host	the host to authenticate against
	 * @param port the port to use for authentication
	 * @return a user if authentication was successful, null if it wasn't
	 * @throws ClientException if an error occurs with the server
	 */
	public static User validateUser(String username, String password, String host, int port) 
			throws ClientException{
        ClientCommunicator cc = new ClientCommunicator(host, port);
        ValidateUser_Params params = new ValidateUser_Params();
        params.setPassword(password);
        params.setUsername(username);
        
        ValidateUser_Result result;
        result = cc.validateUser(params);
        
        if(result.isAuthenticated()){
        	return result.getUsr();
        }
        return null;
        
    }
    
    /**
     * Get a list of all projects needed for the project selection
     * @param username User to authenticate
     * @param password Password to authenticate
     * @param host The host to authenticate against
     * @param port The port to access
     * @return A GetProjects_Result object that has all of the projects in the database
     * in it
     */
    public static List<Project> getProjects(String username, String password,
               String host, int port){
        ClientCommunicator cc = new ClientCommunicator(host,port);
        GetProjects_Params params = new GetProjects_Params(username, password);

        GetProjects_Result result;
        try{
            result = cc.getProjects(params);
        }
        catch(ClientException e){
            return null;
        }
        if(result == null){
            return null;
        }

        return result.getProjects();
		
		
    }
    
    /**
     * Returns a list of all of the fields from the selected project
     * @param username The username to authenticate
     * @param password The password to authenticate
     * @param projectID The project to get the fields from
     * @param host The host to authenticate against
     * @param port The port to authenticate
     * @return A list of fields from the project
     */
    public static List<Field> getFields(String username, String password,
               int projectID, String host, int port){
        ClientCommunicator cc = new ClientCommunicator(host, port);
        
        GetFields_Params params = new GetFields_Params(username, password, projectID);

        GetFields_Result result;
        try{
                result = cc.getFields(params);
        }
        catch(ClientException e){
                return null;
        }
        if(result == null){
                return null;
        }

	return result.getFields();
		
    }
    
    /**
     * Returns the URL of the requested sample image
     * @return a String which is the URL of a sample image from the requested project
     * @throws ClientException if an error occurs in communication
     */
    public static String getSampleImage(String username, String password, int projectID, String host, int port) 
    		throws ClientException {
		ClientCommunicator cc;
		GetSampleImage_Params params;

		cc = new ClientCommunicator(host, port);
		params =new GetSampleImage_Params(username, password, projectID);
		
		GetSampleImage_Result result;
		result=cc.getSampleImage(params);
		if(result == null){
			throw new ClientException("Error 500: The server didn't send back an image.");
		}
		Batch batch = result.getBatch();
		
		String op = "http://" + host + ":" + port + "/"  + batch.getImagePath();
		return op;
	}
    
    
    public static DownloadBatch_Result downloadBatch(String username, String password, int projectID, String host, int port) 
    		throws ClientException{
		ClientCommunicator cc;
		DownloadBatch_Params params;
		cc = new ClientCommunicator(host, port);
		params = new DownloadBatch_Params(username, password, projectID);
		
		DownloadBatch_Result result;
		result = cc.downloadBatch(params);
		
		return result;
	}
    
    /**
     * Processes the BatchState that was passed in and submits it to the server
     * @param bchS The batchState to process
     */
    public static boolean submitBatch(String username, String password, BatchState bchS){
    	ClientCommunicator cc;
		SubmitBatch_Params params;
		int batchID = bchS.getBatch().getBatchID();
		
		cc = new ClientCommunicator(bchS.getServer_host(), bchS.getServer_port());
		
		params = new SubmitBatch_Params();
		params.setUsername(username);
		params.setPassword(password);
		params.setBatchID(bchS.getBatch().getBatchID());
		
		
		//process the array of indexed data
		IndexedData[][] indexedData = bchS.getValues();
		
		
		int numRecords = bchS.getProject().getRecordsPerImage();
		for(int i = 0; i < numRecords; i++){
			Record tempRec = new Record(-1, i+1, batchID);
			params.addRecord(tempRec);

			for(int j = 0; j < indexedData[i].length; j++){
				params.addIndexedData(indexedData[i][j]);
			}
		}
		params.setTempIDs(true);
		
		SubmitBatch_Result result;
		try{
			result = cc.submitBatch(params);
		}
		catch(ClientException e){
			//for debugging purposes
			e.printStackTrace();
			return false;
		}
		
		if(result == null){
			System.out.println("Result was null--there was a problem on the server when submitting"
					+ " the batch!");
			return false;
		}
		return true;
    }
    
    
}
