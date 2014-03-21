package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import shared.communication.DownloadBatch_Params;
import shared.communication.DownloadBatch_Result;
import shared.model.Batch;
import shared.model.CommandProcessor;
import shared.model.Field;
import shared.model.ModelException;
import shared.model.Project;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class DownloadBatchHandler implements HttpHandler {
	private XStream xStream;
	private Logger logger;
	
	/**
	 * Default constructor; initializes the XStream
	 */
	public DownloadBatchHandler(){
		xStream = new XStream(new DomDriver());
		logger = Logger.getLogger("server");
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		DownloadBatch_Params params = (DownloadBatch_Params) xStream.fromXML(exchange.getRequestBody());
		Project project;
		Batch batch;
		List<Field> fields;

		try{
			project = CommandProcessor.getProject(params.getUsername(), params.getPassword(), params.getProjectID());
			batch = CommandProcessor.downloadBatchBatch(params.getUsername(), params.getPassword(), params.getProjectID());
			fields = CommandProcessor.getFields(params.getUsername(), params.getPassword(), params.getProjectID());
		}
		catch(ModelException e){
			logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		//if something fails, return a null result
		DownloadBatch_Result result = new DownloadBatch_Result();
		if(project == null || batch == null || fields == null){
			result = null;
		}
		else{
			//Make the result
			result.setProject(project);
			result.setBatch(batch);
			result.setFields(fields);
		}
		
		

		//send the result back
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}
