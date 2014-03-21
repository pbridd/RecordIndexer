package utilities;

import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import shared.model.*;

public class DataImporter {
	private static Path xmlPath;
	
	public static void main(String[] args) throws UtilitiesException{

		try{
			//initialize the database
			ModelManager.initialize();
			//Clear all data from the database
			BatchManager.clearBatches();
			FieldManager.clearFields();
			IndexedDataManager.clearIndexedData();
			ProjectManager.clearProjects();
			RecordManager.clearRecords();
			UserManager.clearUsers();
		}
		catch(ModelException e){
			throw new UtilitiesException(e.getMessage(), e);
		}
		//set xmlPath var
		xmlPath = Paths.get(args[0]);
		
		//Make the Document Builder
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try{
			builder = builderFactory.newDocumentBuilder();
		}
		catch(ParserConfigurationException e){
			throw new UtilitiesException(e.getMessage(), e);

		}
		
		try{
			Document document = builder.parse(new FileInputStream(args[0]));
			//Process users
			processUsers(document);
			
			
			//Process projects
			processProjects(document);


		}
		catch (IOException e){
			throw new UtilitiesException(e.getMessage(), e);
		}
		catch(SAXException e){
			throw new UtilitiesException(e.getMessage(), e);
		}
		catch (ModelException e){
			throw new UtilitiesException(e.getMessage(), e);
		}
		
		
	}
	
	public static void processUsers(Document document) throws ModelException{
		NodeList userList = document.getElementsByTagName("user");
		for(int i = 0; i < userList.getLength(); i++){
			Element eUser = (Element)userList.item(i);
			
			Element eUsername = (Element) eUser.getElementsByTagName("username").item(0);
			Element ePassword = (Element) eUser.getElementsByTagName("password").item(0);
			Element eFirstName = (Element) eUser.getElementsByTagName("firstname").item(0);
			Element eLastName = (Element) eUser.getElementsByTagName("lastname").item(0);
			Element eEmail = (Element) eUser.getElementsByTagName("email").item(0);
			Element eIndexedRecords = (Element) eUser.getElementsByTagName("indexedrecords").item(0);
			
			String username = eUsername.getTextContent();
			String password = ePassword.getTextContent();
			String firstname = eFirstName.getTextContent();
			String lastname = eLastName.getTextContent();
			String email = eEmail.getTextContent();
			String indexedrecords = eIndexedRecords.getTextContent();
			 
			//From this information, construct a user
			User user = new User(-1, username, password, firstname, lastname, email, Integer.parseInt(indexedrecords));
			UserManager.addUser(user);
			
		}
	}
	
	public static void processProjects(Document document) throws ModelException{
		NodeList projectList = document.getElementsByTagName("project");
		for(int i = 0; i < projectList.getLength(); i++){
			Element eProject = (Element)projectList.item(i);
			
			Element eProjectTitle = (Element)eProject.getElementsByTagName("title").item(0);
			Element eRecordsPerImage = (Element)eProject.getElementsByTagName("recordsperimage").item(0);
			Element eFirstYCoord = (Element)eProject.getElementsByTagName("firstycoord").item(0);
			Element eRecordHeight = (Element)eProject.getElementsByTagName("recordheight").item(0);
			Element eFields = (Element)eProject.getElementsByTagName("fields").item(0);
			Element eBatches = (Element)eProject.getElementsByTagName("images").item(0);
			
			
			//Get lists of fields and images (batches)
			NodeList fieldList = eFields.getElementsByTagName("field");
			NodeList batchList = eBatches.getElementsByTagName("image");
			
			//make and add the project
			String projecttitle = eProjectTitle.getTextContent();
			String recordsperimage = eRecordsPerImage.getTextContent();
			String firstycoord = eFirstYCoord.getTextContent();
			String recordheight = eRecordHeight.getTextContent();
			
			Project project = new Project(-1, projecttitle, Integer.parseInt(recordsperimage),
					Integer.parseInt(firstycoord), Integer.parseInt(recordheight));
			ProjectManager.addProject(project);
			//process the fields
			processFields(fieldList, project);
			//process the batches
			processBatches(batchList, project);
			 
			
			
		}
	}
	
	public static void processFields(NodeList fieldList, Project project) throws ModelException{
		for(int j = 0; j < fieldList.getLength(); j++){
			Element eField = (Element)fieldList.item(j);
			
			Element eFieldName = (Element)eField.getElementsByTagName("title").item(0);
			Element eXCoord = (Element)eField.getElementsByTagName("xcoord").item(0);
			Element eWidth = (Element)eField.getElementsByTagName("width").item(0);
			Element eHelpHTML = (Element)eField.getElementsByTagName("helphtml").item(0);
			NodeList eKnownData = eField.getElementsByTagName("knowndata");
			
			String fieldname = eFieldName.getTextContent();
			String xcoord = eXCoord.getTextContent();
			String width = eWidth.getTextContent();
			String helphtmlBefore = eHelpHTML.getTextContent();
			String helphtmlAfter;
			//copy helphtml to server directory
			try{
				String newRelPath = "data/fieldhelp/"  + project.getProjectID() + "_" 
						 + (j+1) + ".html";
				helphtmlAfter = "fieldhelp/" + project.getProjectID() + "_" + (j+1) + ".html";
				copyFile(helphtmlBefore, newRelPath);
			}
			catch(IOException e){
				throw new ModelException(e.getMessage(), e);
			}
			
			//Process optional knownData so knowndata is an empty string if it doesn't exist
			String knowndataBefore = "";
			String knowndataAfter = knowndataBefore;
			if(eKnownData.getLength() != 0){
				
				try{
					knowndataBefore = ((Element)eKnownData.item(0)).getTextContent();
					//formulate the new path
					String newRelPath = "data/knowndata/" + project.getProjectID() + "_" 
							 + (j+1) + ".txt";
					knowndataAfter = "knowndata/" + project.getProjectID() + "_" + (j+1) + ".txt";
					copyFile(knowndataBefore, newRelPath);
				}
				catch(IOException e){
					throw new ModelException(e.getMessage(), e);
				}
			}
			
			
			//create the field and put it into the database
			Field field = new Field(-1, j+1, fieldname, Integer.parseInt(xcoord), Integer.parseInt(width),
									helphtmlAfter, knowndataAfter, project.getProjectID());
			FieldManager.addField(field);
			
			
		}
	}
	
	public static void processBatches(NodeList batchList, Project project) throws ModelException{
		//process the batches
		for(int j = 0; j < batchList.getLength(); j++){
			Element eBatch = (Element)batchList.item(j);
			
			Element eImagePath = (Element)eBatch.getElementsByTagName("file").item(0);
			NodeList eRecords = eBatch.getElementsByTagName("records");
			
			
			
			String imagepathBefore = eImagePath.getTextContent();
			String imagepathAfter;
			//make the new imagepath and copy the file to the server's directory
			try{
				String newRelPath = "data/images/" + project.getProjectID() + 
						"_" + (j+1) + ".png";
				imagepathAfter = "images/"+ project.getProjectID() + "_" + (j+1) + ".png";
				copyFile(imagepathBefore, newRelPath);
			}
			catch(IOException e){
				throw new ModelException(e.getMessage(), e);
				
			}
			
			//Create the batch and then put it in the database
			
			if(eRecords.getLength() > 0){
				Batch batch = new Batch();
				batch.setProjectID(project.getProjectID());
				batch.setImagePath(imagepathAfter);
				batch.setFullyIndexed(true);
				BatchManager.addBatch(batch);
				
				NodeList recordsList = ((Element)eRecords.item(0)).getElementsByTagName("record");
				
				//process records
				processRecords(recordsList, batch);
				
			}
			else{
				Batch batch = new Batch();
				batch.setProjectID(project.getProjectID());
				batch.setImagePath(imagepathAfter);
				BatchManager.addBatch(batch);
			}
			
		}
	}
	
	public static void processRecords(NodeList recordsList, Batch batch) throws ModelException{
		for(int k = 0; k < recordsList.getLength(); k++){
			Element eRecord = (Element) recordsList.item(k);
		
			//create record and put it in the database
			Record record = new Record(-1, k+1, batch.getBatchID());
			RecordManager.addRecord(record);
			
			//process indexedData
			NodeList eIndexedDatas = eRecord.getElementsByTagName("values");
			if(eIndexedDatas.getLength() >  0){
				NodeList indexedDataList = ((Element)eIndexedDatas.item(0)).getElementsByTagName("value");
				processIndexedData(indexedDataList, record);
				
			}
			
		
		}
	}
	
	public static void processIndexedData(NodeList indexedDataList, Record record) throws ModelException{
		List<Field> fields = FieldManager.getFields(BatchManager.getBatch(record.getBatchID()).getProjectID());
		for(int l = 0; l < indexedDataList.getLength(); l++){
			Element eIndexedData = (Element) indexedDataList.item(l);
			IndexedData indexedData = new IndexedData(-1, eIndexedData.getTextContent(), record.getRecordID(), 
										fields.get(l).getFieldID());
			//add new indexedData to the database
			IndexedDataManager.addIndexedData(indexedData);
			
		}
	}
	
	public static void copyFile(String path1, String path2) throws IOException{
		Path FROM = Paths.get(xmlPath.getParent()+ "/" + path1);
	    Path TO = Paths.get(path2);
	    
	    //overwrite existing file, if exists
	    CopyOption[] options = new CopyOption[]{
	      StandardCopyOption.REPLACE_EXISTING,
	      StandardCopyOption.COPY_ATTRIBUTES
	    }; 
	    Files.copy(FROM, TO, options);
	}
	
	
	
}
