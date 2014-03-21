package client.communication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
import client.*;

public class ClientCommunicator {
	//Global Variables
	private String SERVER_HOST;
	private int SERVER_PORT;
	private String URL_PREFIX = "http://" + SERVER_HOST +
			":" + SERVER_PORT;
	private final String HTTP_GET = "GET";
	private final String HTTP_POST = "POST";
	
	
	
	
	/**
	 * Default constructor
	 */
	public ClientCommunicator(){
		SERVER_HOST = "localhost";
		SERVER_PORT = 8080;
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	}
	
	/**
	 * Constructor, sets SERVER_HOST and SERVER_PORT
	 * @param server_host The hostname of the server
	 * @param server_port The port with which to connect to the server
	 */
	public ClientCommunicator(String server_host, int server_port){
		SERVER_HOST = server_host;
		SERVER_PORT = server_port;
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	}
	

	/**
	 * Makes sure the username and password the user typed in are correct
	 * @param params The ValidateUser_Params object which encapsulates the needed parameters to 
	 * validate the user
	 * @return The result of the user validation
	 * @throws ClientException if there are any errors
	 */
	public ValidateUser_Result validateUser(ValidateUser_Params params) throws ClientException{
		ValidateUser_Result res =(ValidateUser_Result) doPost("/validateUser", params);
		return res;
	}

	/**
	 * Gets a list of all projects in the database encapsulated in the GetProjects_Result
	 * object returned
	 * @param params The GetProjects_Params object which encapsulates the needed parameters
	 * @return A list of all projects in the database encapsulated in a GetProjects_Result object
	 * @throws ClientException if there are any errors
	 */
	public GetProjects_Result getProjects(GetProjects_Params params) throws ClientException{
		GetProjects_Result res = (GetProjects_Result) doPost("/getProjects", params);
		return res;
	}

	
	/**
	 * Gets a sample image for the user of the project he/she specifies
	 * @param params The GetSampleImage_Params object which encapsulates the needed parameters
	 * @return A GetSampleImage_Result object with the needed results inside of it
	 * @throws ClientException if there are any errors
	 */
	public GetSampleImage_Result getSampleImage(GetSampleImage_Params params) throws ClientException{
		GetSampleImage_Result res = (GetSampleImage_Result) doPost("/getSampleImage", params);
		return res;
	}
	
	/**
	 * Downloads all of the needed information for a particular batch
	 * @param params The DownloadBatch_Params object which encapsulates the needed parameters
	 * @return A DownloadBatch_Result object with the needed results inside of it
	 * @throws ClientException if there are any errors
	 */
	public DownloadBatch_Result downloadBatch(DownloadBatch_Params params) throws ClientException{
		DownloadBatch_Result res = (DownloadBatch_Result) doPost("/downloadBatch", params);
		return res;
	}
	
	/**
	 * Submits a completed batch to the database
	 * @param params The SubmitBatch_Params object which encapsulates the needed parameters
	 * @return A SubmitBatch_Result object with the needed results inside of it
	 * @throws ClientException if there are any errors
	 */
	public SubmitBatch_Result submitBatch(SubmitBatch_Params params) throws ClientException{
		SubmitBatch_Result res = (SubmitBatch_Result) doPost("/submitBatch", params);
		return res;
	}
	
	/**
	 * Gets information from all of the fields of a specified project
	 * @param params The GetFields_Params object which encapsulates the needed parameters
	 * @return A GetFields_Result object with information from all of the fields of the project
	 * @throws ClientException if there are any errors
	 */
	public GetFields_Result getFields(GetFields_Params params) throws ClientException{
		GetFields_Result res = (GetFields_Result) doPost("/getFields", params);
		return res;
	}
	
	/**
	 * Searches all indexed records for the specified strings
	 * @param params The Search_Params object which encapsulates the needed parameters
	 * @return A Search_Result object with information about the search results
	 * @throws ClientException if there are any errors
	 */
	public Search_Result search(Search_Params params) throws ClientException{
		Search_Result res = (Search_Result) doPost("/search", params);
		return res;
	}
	
	/**
	 * Gets an image from the server based on the passed in parameters
	 * 
	 */
	public byte[] ImageDownload(String imgURL) throws ClientException{
		byte[] res = (byte[]) doGet(imgURL);
		return res;
	}
	
	//Utility Methods
	/**
	 * Performs the Get operation that has been requested to get an object from the server
	 * @param urlPath The path of the URL to get from
	 * @return An Object that's requested
	 * @throws ClientException
	 */
	private Object doGet(String urlPath) throws ClientException{
		try{
			XStream xStream = new XStream(new DomDriver());
			URL url = new URL(URL_PREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(HTTP_GET);
			connection.connect();
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
				Object result = xStream.fromXML(connection.getInputStream());
				return result;				
			}
			else{
				throw new ClientException(String.format("doGet failed: %s (http code %d)",
						urlPath, connection.getResponseCode()));
			}
		}
		catch(MalformedURLException e){
			throw new ClientException("In client.communication.ClientCommunicator doGet, there was a malformed "
					+ "URL\n" + e.getMessage());
		}
		catch(IOException e){
			throw new ClientException(String.format("doGet failed : %s", e.getMessage()), e);
		}
	}
	
	/**
	 * Posts the data passed into the method to the server
	 * @param urlPath The path of the URL to post to
	 * @param postData The data to post
	 * @throws ClientException If something goes wrong with the post
	 */
	private Object doPost(String urlPath, Object postData) throws ClientException {
		//form the URL
		try{
			XStream xStream = new XStream(new DomDriver());
			URL url = new URL(URL_PREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoOutput(true);
			connection.connect();
			xStream.toXML(postData, connection.getOutputStream());
			connection.getOutputStream().close();
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
				Object result = xStream.fromXML(connection.getInputStream());
				return result;				
			}
			else{
				throw new ClientException(String.format("doPost failed: %s (http code %d)", 
						urlPath, connection.getResponseCode()));	
			}
			
		}
		catch(MalformedURLException e){
			throw new ClientException("In client.communication.ClientCommunicator doPost, there was a malformed "
					+ "URL\n" + e.getMessage());
		}
		catch(IOException e){
			throw new ClientException(String.format("doPost failed : %s", e.getMessage()), e);
		}
		
	}
	
	//Getter and Setter Methods

	/**
	 * @return the SERVER_HOST
	 */
	public String getSERVER_HOST() {
		return SERVER_HOST;
	}

	/**
	 * @param sERVER_HOST the sERVER_HOST to set
	 */
	public void setSERVER_HOST(String sERVER_HOST) {
		SERVER_HOST = sERVER_HOST;
	}

	/**
	 * @return the sERVER_PORT
	 */
	public int getSERVER_PORT() {
		return SERVER_PORT;
	}

	/**
	 * @param sERVER_PORT the sERVER_PORT to set
	 */
	public void setSERVER_PORT(int sERVER_PORT) {
		SERVER_PORT = sERVER_PORT;
	}
	
}
