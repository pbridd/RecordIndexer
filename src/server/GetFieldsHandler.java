package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import shared.communication.GetFields_Params;
import shared.communication.GetFields_Result; 
import shared.model.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetFieldsHandler implements HttpHandler {
	private XStream xStream;
	private Logger logger;
	
	/**
	 * Default constructor; initializes the XStream
	 */
	public GetFieldsHandler(){
		xStream = new XStream(new DomDriver());
		logger = Logger.getLogger("server");
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		GetFields_Params params = (GetFields_Params) xStream.fromXML(exchange.getRequestBody());
		List<Field> fields;

		try{
			fields = CommandProcessor.getFields(params.getUsername(), params.getPassword(), params.getProjectID());
		}
		catch(ModelException e){
			logger.log(Level.SEVERE, e.getMessage(), e);
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		//Make the result
		GetFields_Result result = new GetFields_Result();
		if(fields == null || fields.size() == 0){
			result = null;
		}
		else{
			result.setFields(fields);
		}

		//send the result back
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}
