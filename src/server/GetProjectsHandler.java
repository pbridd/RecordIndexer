package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import shared.communication.GetProjects_Params;
import shared.communication.GetProjects_Result;
import shared.model.CommandProcessor;
import shared.model.ModelException;
import shared.model.Project;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetProjectsHandler implements HttpHandler {
	private XStream xStream;
	private Logger logger;
	
	/**
	 * Default constructor; initializes the XStream
	 */
	public GetProjectsHandler(){
		xStream = new XStream(new DomDriver());
		logger = Logger.getLogger("server");
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		GetProjects_Params params = (GetProjects_Params) xStream.fromXML(exchange.getRequestBody());
		List<Project> projects;

		try{
			projects = CommandProcessor.getProjects(params.getUsername(), params.getPassword());
		}
		catch(ModelException e){
			logger.log(Level.SEVERE, e.getMessage(), e);
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		//Make the result
		GetProjects_Result result = new GetProjects_Result();
		if(projects == null)
			result = null;
		else{
			result.setProjects(projects);
		}
		//send the result back
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}

}
