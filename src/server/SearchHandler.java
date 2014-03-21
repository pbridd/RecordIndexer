package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import shared.model.CommandProcessor;
import shared.model.ModelException;
import shared.communication.*;
import shared.model.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SearchHandler implements HttpHandler {
	private XStream xStream;
	private Logger logger;

	/**
	 * Default constructor; initializes the XStream
	 */
	public SearchHandler(){
		xStream = new XStream(new DomDriver());
		logger = Logger.getLogger("server");
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Search_Params params = (Search_Params) xStream.fromXML(exchange.getRequestBody());
		List<IndexedData> indexedDatas = new ArrayList<IndexedData>();
		Search_Result result = new Search_Result();
		try{
			List<Integer> fieldIDs = params.getFieldIDs();
			List<String> strings = params.getStrings();
			
			//make sure that they are all valid field IDs
			List<Field> allFields = FieldManager.getFields(-1);
			for(int i = 0; i < fieldIDs.size(); i++){
				boolean foundField = false;
				for(Field f :  allFields){
					if(f.getFieldID() == fieldIDs.get(i))
						foundField = true;
				}
				if(!foundField){
					//send the result back if an invalid field exists
					result = null;
					exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
					xStream.toXML(result, exchange.getResponseBody());
					exchange.getResponseBody().close();
				}
			}
			
			//Access the data in the database and get the results. Compile it into a list.
			for(int i = 0; i < fieldIDs.size(); i++){
				for(int j = 0; j < strings.size(); j++){
					List<IndexedData> tempRes = CommandProcessor.search(params.getUsername(), params.getPassword(), 
							fieldIDs.get(i), strings.get(j));
					for(int k = 0; k < tempRes.size(); k++){
						indexedDatas.add(tempRes.get(k));
					}
				}
			}
			
			//process all of the indexed data results and put them into the search Result
			for(int i = 0; i < indexedDatas.size(); i++){
				IndexedData tempData = indexedDatas.get(i);
				Record tempRecord = RecordManager.getRecord(tempData.getRecordID());
				Batch tempBatch = BatchManager.getBatch(tempRecord.getBatchID());
				result.addBatchID(tempBatch.getBatchID());
				result.addRecordNumber(tempRecord.getRecordNumber());
				result.addFieldID(tempData.getFieldID());
				result.addImageURL(tempBatch.getImagePath());
			}
		}
		catch(ModelException e){
			logger.log(Level.SEVERE, e.getMessage(), e);
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		//send the result back
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}

}
