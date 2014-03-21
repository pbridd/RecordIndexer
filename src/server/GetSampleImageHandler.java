package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;


import shared.communication.GetSampleImage_Params;
import shared.communication.GetSampleImage_Result;
import shared.model.Batch;
import shared.model.CommandProcessor;
import shared.model.ModelException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetSampleImageHandler implements HttpHandler {
	private XStream xStream;
	private Logger logger;
	
	/**
	 * Default constructor; initializes the XStream
	 */
	public GetSampleImageHandler(){
		xStream = new XStream(new DomDriver());
		logger = Logger.getLogger("server");
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		GetSampleImage_Params params = (GetSampleImage_Params) xStream.fromXML(exchange.getRequestBody());
		Batch batch;

		try{
			batch = CommandProcessor.getSampleImage(params.getUsername(), params.getPassword(), params.getProjectID());
		}
		catch(ModelException e){
			logger.log(Level.SEVERE, e.getMessage(), e);
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		//Make the result
		GetSampleImage_Result result = new GetSampleImage_Result();
		if(batch == null){
			result = null;
		}
		else{
			result.setBatch(batch);
		}
		//send the result back
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}
