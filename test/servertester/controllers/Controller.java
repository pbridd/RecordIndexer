package servertester.controllers;

import java.util.*;

import client.ClientException;
import client.communication.ClientCommunicator;
import servertester.views.*;
import shared.communication.DownloadBatch_Params;
import shared.communication.DownloadBatch_Result;
import shared.communication.GetFields_Params;
import shared.communication.GetFields_Result;
import shared.communication.GetProjects_Params;
import shared.communication.GetProjects_Result;
import shared.communication.GetSampleImage_Params;
import shared.communication.GetSampleImage_Result;
import shared.communication.Search_Params;
import shared.communication.Search_Result;
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

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() {
		ClientCommunicator cc = new ClientCommunicator(getView().getHost(),
				Integer.parseInt(getView().getPort()));
		String[] strParams = getView().getParameterValues();
		ValidateUser_Params params = new ValidateUser_Params(strParams[0], strParams[1]);
		
		ValidateUser_Result result;
		try{
			result = cc.validateUser(params);
		}
		catch(ClientException e){
			getView().setResponse("Failed\n");
			return;
		}
		if(result == null || result.isAuthenticated() == false){
			getView().setResponse("False\n");
			return;
		}
		
		User user = result.getUsr();
		
		StringBuilder build = new StringBuilder();
		build.append("True\n");
		build.append(user.getFirstName() + "\n");
		build.append(user.getLastName() + "\n");
		build.append(user.getIndexedRecords() + "\n");
		getView().setResponse(build.toString());
		
	}
	
	private void getProjects() {
		ClientCommunicator cc = new ClientCommunicator(getView().getHost(),
				Integer.parseInt(getView().getPort()));
		String[] strParams = getView().getParameterValues();
		GetProjects_Params params = new GetProjects_Params(strParams[0], strParams[1]);
		
		GetProjects_Result result;
		try{
			result = cc.getProjects(params);
		}
		catch(ClientException e){
			getView().setResponse("Failed\n");
			return;
		}
		if(result == null){
			getView().setResponse("Failed\n");
			return;
		}
		
		List<Project> projects = result.getProjects();
		
		StringBuilder build = new StringBuilder();
		for(Project p : projects){
			build.append(p.getProjectID() + "\n");
			build.append(p.getProjectTitle() + "\n");
		}
		
		getView().setResponse(build.toString());
	}
	
	private void getSampleImage() {
		ClientCommunicator cc;
		GetSampleImage_Params params;
		try{
			cc = new ClientCommunicator(getView().getHost(),
					Integer.parseInt(getView().getPort()));
			String[] strParams = getView().getParameterValues();
			params =new GetSampleImage_Params(strParams[0], strParams[1], 
					Integer.parseInt(strParams[2]));
		}
		catch(NumberFormatException e){
			getView().setResponse("Failed\n");
			return;
		}
		
		GetSampleImage_Result result;
		try{
			result=cc.getSampleImage(params);
		}
		catch(ClientException e){
			getView().setResponse("Failed\n");
			return;
		}
		if(result == null){
			getView().setResponse("Failed\n");
			return;
		}
		Batch batch = result.getBatch();
		
		String op = "http://" + getView().getHost() + ":" + getView().getPort() + "/"  
				+batch.getImagePath()+"\n";
		getView().setResponse(op);
	}
	
	private void downloadBatch() {
		ClientCommunicator cc;
		DownloadBatch_Params params;
		try{
			cc = new ClientCommunicator(getView().getHost(),
				Integer.parseInt(getView().getPort()));
			String[] strParams = getView().getParameterValues();
			 params = new DownloadBatch_Params(strParams[0], strParams[1],
					Integer.parseInt(strParams[2]));
		}
		catch(NumberFormatException e){
			getView().setResponse("Failed\n");
			return;
		}
		
		DownloadBatch_Result result;
		try{
			result = cc.downloadBatch(params);
		}
		catch(ClientException e){
			getView().setResponse("Failed\n");
			return;
		}
		if(result == null){
			getView().setResponse("Failed\n");
			return;
		}
		
		Batch batch = result.getBatch();
		Project project = result.getProject();
		List<Field> fields = result.getFields();
		
		StringBuilder build = new StringBuilder();
		build.append(batch.getBatchID() + "\n");
		build.append(batch.getProjectID() + "\n");
		build.append("http://" + getView().getHost() + ":"+ getView().getPort() + "/" 
				+ batch.getImagePath() + "\n");
		build.append(project.getFirstYCoord() + "\n");
		build.append(project.getRecordHeight() + "\n");
		build.append(project.getRecordsPerImage() + "\n");
		build.append(fields.size() + "\n");
		for(int i = 0; i < fields.size(); i++){
			Field field = fields.get(i);
			build.append(field.getFieldID() + "\n");
			build.append(field.getFieldNumber()+"\n");
			build.append(field.getFieldName() + "\n");
			build.append("http://" + getView().getHost() + ":" + getView().getPort() + "/"  
					+field.getHelpHTML()+"\n");
			build.append(field.getXCoord() + "\n");
			build.append(field.getWidth() + "\n");
			if(field.getKnownData() != null && field.getKnownData().length() > 0){
				build.append("http://" + getView().getHost() + ":" + getView().getPort() +"/"
						+field.getKnownData()+"\n");
			}
		}
		getView().setResponse(build.toString());
		
		
	}
	
	private void getFields() {
		ClientCommunicator cc;
		//make sure that they are passing in valid params
		try{
			 cc = new ClientCommunicator(getView().getHost(),
					Integer.parseInt(getView().getPort()));
		}
		catch(NumberFormatException e){
			getView().setResponse("Failed\n");
			return;
		}
		String[] strParams = getView().getParameterValues();
		GetFields_Params params;
		
		//make sure that they really passed in a valid int, and a valid parameter
		try{
			if(strParams[2].length() == 0){
				params = new GetFields_Params();
				params.setUsername(strParams[0]);
				params.setPassword(strParams[1]);
				params.setProjectID(-1);
			}
		
			else if(Integer.parseInt(strParams[2]) < 0){
				getView().setResponse("Failed\n");
				return;
			}
			else{
				params = new GetFields_Params(strParams[0], strParams[1],
						Integer.parseInt(strParams[2]));
			}
		}
		catch(NumberFormatException e){
			getView().setResponse("Failed\n");
			return;
		}
		
		
		
		GetFields_Result result;
		try{
			result = cc.getFields(params);
		}
		catch(ClientException e){
			getView().setResponse("Failed\n");
			return;
		}
		if(result == null){
			getView().setResponse("Failed\n");
			return;
		}
		
		List<Field> fields = result.getFields();
		
		StringBuilder build = new StringBuilder();
		
		for(int i = 0; i < fields.size(); i++){
			Field field = fields.get(i);
			build.append(field.getProjectID() + "\n");
			build.append(field.getFieldID()+"\n");
			build.append(field.getFieldName() + "\n");
		}
		getView().setResponse(build.toString());

	}
	
	private void submitBatch() {
		ClientCommunicator cc;
		SubmitBatch_Params params;
		int batchID;
		String[] strParams;
		//make sure that the user passed in a valid int
		try{
			cc = new ClientCommunicator(getView().getHost(),
					Integer.parseInt(getView().getPort()));
			strParams = getView().getParameterValues();
			params = new SubmitBatch_Params();
			params.setUsername(strParams[0]);
			params.setPassword(strParams[1]);
			batchID = Integer.parseInt(strParams[2]);
			params.setBatchID(batchID);
		}
		catch(NumberFormatException e){
			getView().setResponse("Failed\n");
			return;
		}

		
		List<String> records = Arrays.asList(strParams[3].split(";"));
		for(int i = 0; i < records.size(); i++){
			Record tempRec = new Record(-1, i+1, batchID);
			params.addRecord(tempRec);
			List<String> indexedData = Arrays.asList(records.get(i).split(",", -1));
			for(int j = 0; j < indexedData.size(); j++){
				params.addIndexedData(new IndexedData(-1, indexedData.get(j), -1, -1));
			}
		}
		params.setTempIDs(true);
		
		SubmitBatch_Result result;
		try{
			result = cc.submitBatch(params);
		}
		catch(ClientException e){
			getView().setResponse("Failed\n");
			return;
		}
		if(result == null){
			getView().setResponse("Failed\n");
			return;
		}
		
		getView().setResponse("True\n");
		
		
		
	}
	
	private void search() {
		//make sure that all of the int parameters were valid
		ClientCommunicator cc;
		Search_Params params;
		try{
			cc = new ClientCommunicator(getView().getHost(),
					Integer.parseInt(getView().getPort()));
			String[] strParams = getView().getParameterValues();
	
			
			if(strParams[2].length() == 0){
				getView().setResponse("Failed\n");
				return;
			}
			if(strParams[3].length() == 0){
				getView().setResponse("Failed\n");
				return;
			}
			
			List<String> fieldsB4Processing = Arrays.asList(strParams[2].split(","));
			List<Integer> fields = new ArrayList<Integer>();
			for(String str : fieldsB4Processing){
				fields.add(Integer.parseInt(str));
			}
			
			List<String> searchValues = Arrays.asList(strParams[3].split(","));
			
			params = new Search_Params(strParams[0], strParams[1], fields, searchValues);
		}
		catch(NumberFormatException e){
			getView().setResponse("Failed\n");
			return;
		}
		
		//send it to the server and get the result
		Search_Result result;
		try{
			result = cc.search(params);
		}
		catch(ClientException e){
			getView().setResponse("Failed\n");
			return;
		}
		if(result == null){
			getView().setResponse("Failed\n");
			return;
		}
		
		List<Integer> batchIDs = result.getBatchIDs();
		List<String> imageURLs = result.getImageURLs();
		List<Integer> recordNumbers = result.getRecordNumbers();
		List<Integer> fieldIDs = result.getFieldIDs();
		
		StringBuilder build = new StringBuilder();
		
		for(int i = 0; i < batchIDs.size(); i++){
			build.append(batchIDs.get(i) + "\n");
			build.append("http://" + getView().getHost() + ":" + getView().getPort() + "/" 
					+ imageURLs.get(i)+ "\n");
			build.append(recordNumbers.get(i) + "\n");
			build.append(fieldIDs.get(i) + "\n");
		}
		
		getView().setResponse(build.toString());
	}

}

