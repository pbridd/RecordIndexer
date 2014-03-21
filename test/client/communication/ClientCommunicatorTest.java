package client.communication;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.communication.*;
import shared.model.Batch;
import shared.model.BatchManager;
import shared.model.CommandProcessor;
import shared.model.Field;
import shared.model.FieldManager;
import shared.model.IndexedData;
import shared.model.IndexedDataManager;
import shared.model.ModelManager;
import shared.model.Project;
import shared.model.ProjectManager;
import shared.model.Record;
import shared.model.User;
import shared.model.UserManager;
import utilities.DataImporter;
import server.database.*;


public class ClientCommunicatorTest {
	ClientCommunicator cc;
	Database db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String[] args = {"data/testData/Records.xml"};
		DataImporter.main(args);
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		CommandProcessor.clearDatabase();
	}

	@Before
	public void setUp() throws Exception {
		//make a ClientCommunicator with default parameters
		cc = new ClientCommunicator();
		db = new Database();
	
		
	}

	@After
	public void tearDown() throws Exception {
		//set the ClientCommunicator to null
		cc = null;
		db = null;
	}


	@Test
	public void testValidateUser() throws Exception{
		ValidateUser_Params params = new ValidateUser_Params("test1", "test1");
		ValidateUser_Result results = cc.validateUser(params);
		assertEquals(results.isAuthenticated(), true);
		User usr = results.getUsr();
		assertEquals(usr.getEmail(), "test1@gmail.com");
		assertEquals(usr.getFirstName(), "Test");
		assertEquals(usr.getLastName(), "One");
		assertEquals(usr.getUsername(), "test1");
		assertEquals(usr.getPassword(), "test1");
		
		params = new ValidateUser_Params("test1", "asdf");
		results = cc.validateUser(params);
		if((results.getUsr() != null) && (results.isAuthenticated() != false)){
			fail("testValidateUser returned a non null value for an invalid user-password set");
		}
		
	}

	@Test
	public void testGetProjects() throws Exception{
		GetProjects_Params params = new GetProjects_Params("test1", "test1");
		GetProjects_Result results = cc.getProjects(params);
		List<Project> resultList = results.getProjects();
		
		ModelManager.initialize();
		List<Project> realProjs;
		realProjs = ProjectManager.getAllProjects();
		assertEquals(realProjs.size(), resultList.size());
		
		for(int i = 0; i < realProjs.size(); i++){
			assertEquals(areEqual(realProjs.get(i), resultList.get(i), true), true);
		}
		
	}

	@Test
	public void testGetSampleImage() throws Exception{
		List<Project> prjList = ProjectManager.getAllProjects();
		assertEquals(prjList.size() > 0, true);
		
		for(int i = 0 ; i < prjList.size(); i++){
			GetSampleImage_Params params = new GetSampleImage_Params("test1", "test1", prjList.get(i).getProjectID());
			Batch resBatch = cc.getSampleImage(params).getBatch();
		
			assertEquals(resBatch.getProjectID(), prjList.get(i).getProjectID());
		}
		
		GetSampleImage_Params params = new GetSampleImage_Params("test1", "test1", 0);
		GetSampleImage_Result resBatch = cc.getSampleImage(params);
		assertEquals(resBatch == null, true);
	}

	@Test
	public void testDownloadBatch() throws Exception{
		List<Project> projects = ProjectManager.getAllProjects();
		for(int i = 0; i < projects.size(); i++){
			DownloadBatch_Params params = new DownloadBatch_Params("test1", "test1", projects.get(i).getProjectID());
			DownloadBatch_Result result = cc.downloadBatch(params);
			//double check to make sure it won't download batches from already indexed projects
			if(i == 2){
				assertEquals(result == null, true);
			}
			else{
				Batch resBatch = result.getBatch();
				List<Field> resFields = result.getFields();
				Project resProject = result.getProject();
				
				Project project = CommandProcessor.getProject(params.getUsername(), params.getPassword(), params.getProjectID());
				List<Field> fields = CommandProcessor.getFields(params.getUsername(), params.getPassword(), params.getProjectID());
				
				for(int j = 0; j < resFields.size(); j++){
					assertEquals(areEqual(fields.get(j), resFields.get(j), true), true);
				}
				
				assertEquals(areEqual(project, resProject, true), true);
			}
		}
	}

	@Test
	public void testSubmitBatch() throws Exception{
		//Set up the database
		List<IndexedData> idList = new ArrayList<IndexedData>();
		
		idList.add(new IndexedData(-1, "darkk", 1, 1));
		idList.add(new IndexedData(-1, "dangit",1, 2));
		idList.add(new IndexedData(-1, "I'm Batman", 1, 3));
		idList.add(new IndexedData(-1, "Robin", 1, 4));
		
		List<Record> recordList = new ArrayList<Record>();
		recordList.add(new Record(-1, 1, 1));
		
		User usr1 = UserManager.validateUser("test1", "test1");
		int batchID = BatchManager.getSampleImage(ProjectManager.getAllProjects().get(0).getProjectID()).getBatchID();
		
		SubmitBatch_Params params = new SubmitBatch_Params("test1", "test1", batchID, recordList, idList);
		
		SubmitBatch_Result result = cc.submitBatch(params);
		
		assertEquals(result.hasSucceeded(), true);
		
		
		//check that the user's indexed records has been updated
		usr1 = UserManager.validateUser("test1", "test1");
		assertEquals(usr1.getIndexedRecords(), 8);
		
		//check to make sure the IndexedData has been added to the database
		IndexedData t1 = IndexedDataManager.search(idList.get(0).getFieldID(), "darkk").get(0);
		assertEquals(t1.getDataValue(), idList.get(0).getDataValue());
		
	}

	@Test
	public void testGetFields() throws Exception{
		List<Project> projects = ProjectManager.getAllProjects();
		for(int i = 0; i <  projects.size(); i++){
			GetFields_Params params= new GetFields_Params("test1", "test1", projects.get(i).getProjectID());
			GetFields_Result result = cc.getFields(params);
			
			List<Field> testResult = result.getFields();
			 
			List<Field> realResult = FieldManager.getFields(i);
			for(int j = 0; j < realResult.size(); j++){
				assertEquals(areEqual(realResult.get(j), testResult.get(j), true), true);
			}
			
			cc.getFields(params);
		}
	}

	@Test
	public void testSearch() throws Exception{
		List<Project> projects = ProjectManager.getAllProjects();
		assertEquals(projects.size() > 0, true);
		List<Field> fields = FieldManager.getFields(projects.get(2).getProjectID());
		List<Integer> fieldIDs = new ArrayList<Integer>();
		for(Field f : fields){
			fieldIDs.add(f.getFieldID());
		}
		List<String> searchVals = new ArrayList<String>();
		searchVals.add("19");
			
		Search_Params params = new Search_Params("test1", "test1", fieldIDs, searchVals);
		Search_Result result = cc.search(params);
		assertEquals(result.getRecordNumbers().size(), 15);
		assertEquals(result.getBatchIDs().size(), 15);
		assertEquals(result.getFieldIDs().size(), 15);
		
	}
	
	private boolean areEqual(Project a, Project b, boolean compareIDs){
		if(compareIDs){
			if(a.getProjectID() != b.getProjectID())
				return false;
		}
		return(safeEquals(a.getProjectTitle(), b.getProjectTitle()) &&
				safeEquals(a.getRecordsPerImage(), b.getRecordsPerImage()) &&
				safeEquals(a.getFirstYCoord(),b.getFirstYCoord()) &&
				safeEquals(a.getRecordHeight(),b.getRecordHeight()));
	}
	
	private boolean areEqual(Field a, Field b, boolean compareIDs){
		if(compareIDs){
			if(a.getFieldID() != b.getFieldID())
				return false;
		}
		return(safeEquals(a.getFieldNumber(), b.getFieldNumber()) &&
				safeEquals(a.getFieldName(), b.getFieldName()) &&
				safeEquals(a.getHelpHTML(),b.getHelpHTML()) &&
				safeEquals(a.getKnownData(),b.getKnownData()) &&
				safeEquals(a.getProjectID(), b.getProjectID()) &&
				safeEquals(a.getWidth(), b.getWidth()) &&
				safeEquals(a.getXCoord(), b.getXCoord()));
	}
	
	private boolean areEqual(Batch a, Batch b, boolean compareIDs){
		if(compareIDs){
			if(a.getBatchID() != b.getBatchID())
				return false;
		}
		return(safeEquals(a.getImagePath(), b.getImagePath()) &&
				safeEquals(a.getProjectID(),b.getProjectID()) &&
				safeEquals(a.getUserID(),b.getUserID()) &&
				safeEquals(a.isFullyIndexed(), b.isFullyIndexed()));
	}
	
	
	private boolean safeEquals(Object a, Object b){
		if(a == null || b == null){
			return (a==null && b==null);
		}
		else{
			return a.equals(b);
		}
	}

}
