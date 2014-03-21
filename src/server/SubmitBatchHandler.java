package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;


import java.util.logging.Logger;

import shared.communication.*;
import shared.model.CommandProcessor;
import shared.model.ModelException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SubmitBatchHandler implements HttpHandler {
	private XStream xStream;
	private Logger logger;

	/**
	 * Default constructor; initializes the XStream
	 */
	public SubmitBatchHandler(){
		xStream = new XStream(new DomDriver());
		logger = Logger.getLogger("server");
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		SubmitBatch_Params params = (SubmitBatch_Params) xStream.fromXML(exchange.getRequestBody());
		try{
			CommandProcessor.submitBatch(params.getUsername(), params.getPassword(), params.getBatchID(),
					params.getRecords(), params.getIndexeddata(), params.hasTempIDs());
			}
		catch(ModelException e){
			//if the user just couldn't be validated, send this back.
			if(e.getMessage().equals("The user could not be validated!")){
				//Make the result
				SubmitBatch_Result result = null;
				//send the result back
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				xStream.toXML(result, exchange.getResponseBody());
				exchange.getResponseBody().close();
			}
			else if(e.getMessage().equals("This batch has already been submitted!")){
				//Make the result
				SubmitBatch_Result result = null;
				//send the result back
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				xStream.toXML(result, exchange.getResponseBody());
				exchange.getResponseBody().close();
			}
			else{	
				logger.log(Level.SEVERE, e.getMessage(), e);
			
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
				return;
			}
		}
		//Make the result
		SubmitBatch_Result result = new SubmitBatch_Result();
		result.setSucceeded(true);
		//send the result back
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}

}
