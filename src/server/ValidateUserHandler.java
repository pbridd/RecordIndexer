package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
import shared.model.*;

public class ValidateUserHandler implements HttpHandler{
	private XStream xStream;
	private Logger logger;
	
	/**
	 * Default constructor; initializes the XStream
	 */
	public ValidateUserHandler(){
		xStream = new XStream(new DomDriver());
		logger = Logger.getLogger("server");
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		ValidateUser_Params params = (ValidateUser_Params) xStream.fromXML(exchange.getRequestBody());
		User user;
		try{
			user = CommandProcessor.validateUser(params.getUsername(), params.getPassword());
		}
		catch(ModelException e){
			logger.log(Level.SEVERE, e.getMessage(), e);
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		//Make the result
		ValidateUser_Result result = new ValidateUser_Result();
		if(user == null){
			result.setAuthenticated(false);
		}
		else{
			result.setAuthenticated(true);
		}	
		result.setUsr(user);
		//send the result back
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}
