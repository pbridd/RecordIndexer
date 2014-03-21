/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.searchgui;

import client.ClientException;
import client.communication.ClientCommunicator;
import java.util.List;
import shared.communication.GetFields_Params;
import shared.communication.GetFields_Result;
import shared.communication.GetProjects_Params;
import shared.communication.GetProjects_Result;
import shared.communication.Search_Params;
import shared.communication.Search_Result;
import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;
import shared.model.Field;
import shared.model.Project;

/**
 *
 * @author pbridd
 */
public class SearchUIIntegration {
    public static boolean validateUser(String username, String password, String host, int port){
        ClientCommunicator cc = new ClientCommunicator(host, port);
        ValidateUser_Params params = new ValidateUser_Params();
        params.setPassword(password);
        params.setUsername(username);
        
        ValidateUser_Result result;
        try{
                result = cc.validateUser(params);
        }
        catch(ClientException e){
                return false;
        }
        return result != null && result.isAuthenticated() != false;
        
        
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
     * Searches the database for the given fields and strings
     * @param username
     * @param password
     * @param fieldIDs
     * @param strings
     * @param host
     * @param port
     * @return 
     */
    public static Search_Result search(String username, String password, 
            List<Integer> fieldIDs, List<String> strings, String host, int port){
        ClientCommunicator cc = new ClientCommunicator(host,port);
        Search_Params params = new Search_Params(username, password, fieldIDs, strings);
        
        Search_Result result;
        try{
                result = cc.search(params);
        }
        catch(ClientException e){
                return null;
        }
        return result;


    }
}
