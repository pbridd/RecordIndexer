package utilities;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.Database;
import server.database.DatabaseException;
import shared.model.Batch;
import shared.model.BatchManager;
import shared.model.CommandProcessor;
import shared.model.Field;
import shared.model.FieldManager;
import shared.model.IndexedData;
import shared.model.ModelException;
import shared.model.ModelManager;
import shared.model.Project;
import shared.model.ProjectManager;
import shared.model.Record;
import shared.model.User;
import shared.model.UserManager;

import java.util.*;

public class DataImporterTest {

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//Clear all data from the database
		ModelManager.initialize();
		CommandProcessor.clearDatabase();
		String[] args = {"data/testData/Records.xml"};
		DataImporter.main(args);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		CommandProcessor.clearDatabase();
	}

	@Before
	public void setUp() throws Exception {
		try{
			//initialize the database
			ModelManager.initialize();
		}
		catch(ModelException e){
			throw new UtilitiesException(e.getMessage(), e);
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProcessUsers() throws Exception{
		UserManager.initialize();
		//make sure that the users were actually added
		User usr1 = UserManager.validateUser("test1", "test1");
		assertEquals(usr1.getEmail(), "test1@gmail.com");
		assertEquals(usr1.getFirstName(), "Test");
		assertEquals(usr1.getLastName(), "One");
		assertEquals(usr1.getIndexedRecords(), 0);
		assertEquals(usr1.getUsername(), "test1");
		assertEquals(usr1.getPassword(), "test1");
		
		//try one more user
		User usr2 = UserManager.validateUser("test2", "test2");
		assertEquals(usr2.getEmail(), "test2@gmail.com");
		
		User usr3 = UserManager.validateUser("sheila", "parker");
		assertEquals(usr3.getFirstName(), "Sheila");
	}

	@Test
	public void testProcessProjects() throws Exception{
		ProjectManager.initialize();
		
		//make sure the first project matches
		Project prj1 = ProjectManager.getAllProjects().get(0);
		assertEquals(prj1.getProjectTitle(), "1890 Census");
		assertEquals(prj1.getRecordsPerImage(), 8);
		assertEquals(prj1.getRecordHeight(), 60);
		assertEquals(prj1.getFirstYCoord(), 199);
		
		Project prj2 = ProjectManager.getAllProjects().get(1);
		assertEquals(prj2.getProjectTitle(), "1900 Census");
		
	}

	@Test
	public void testProcessFields() throws Exception{
		//make sure various fields were imported
		int secondProj = ProjectManager.getAllProjects().get(1).getProjectID();
		List<Field> fields1 = FieldManager.getFields(secondProj);
		assertEquals(fields1.size(), 5);
		Field f1_3 = fields1.get(2);
		assertEquals(f1_3.getFieldName(), "Last Name");
		assertEquals(f1_3.getFieldNumber(), 3);
		assertEquals(f1_3.getWidth(), 325);
		assertEquals(f1_3.getXCoord(), 370);
		
	}

	@Test
	public void testProcessBatches() throws Exception{
		Project proj1 = ProjectManager.getAllProjects().get(0);
		Batch testBatch1 = BatchManager.getSampleImage(proj1.getProjectID());
		assertEquals(testBatch1.getProjectID(), proj1.getProjectID());
		assertEquals(testBatch1.isFullyIndexed(), false);
		
		
		Project proj2 = ProjectManager.getAllProjects().get(1);
		Batch testBatch2 = BatchManager.getSampleImage(proj2.getProjectID());
		assertEquals(testBatch2.getProjectID(), proj2.getProjectID());
		assertEquals(testBatch2.isFullyIndexed(), false);
		
		Project proj3 = ProjectManager.getAllProjects().get(2);
		Batch testBatch3 = BatchManager.getSampleImage(proj3.getProjectID());
		assertEquals(testBatch3.getProjectID(), proj3.getProjectID());
		assertEquals(testBatch3.isFullyIndexed(), true);
		
		
	}
	
	@Test
	public void testProcessRecords() throws Exception{
		Database db = new Database();
		Database.initialize();
		List<Record> allRecords;
		try{
			db.startTransaction();
			allRecords = db.getRecordDAO().getAll();
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw e;
		}
		
		Record testRecord1 = allRecords.get(0);
		assertEquals(testRecord1.getRecordNumber(),1);
		Record testRecord2 = allRecords.get(4);
		assertEquals(testRecord2.getRecordNumber(), 5);
			
	}
	
	@Test
	public void testProcessIndexedData() throws Exception{
		Database db = new Database();
		Database.initialize();
		List<IndexedData> allIndexedData;
		try{
			db.startTransaction();
			allIndexedData = db.getIndexedDataDAO().getAll();
			db.endTransaction(true);
		}
		catch(DatabaseException e){
			db.endTransaction(false);
			throw e;
		}
		
		IndexedData testID1 = allIndexedData.get(0);
		assertEquals(testID1.getDataValue().equalsIgnoreCase("fox"), true);
		IndexedData testID2 = allIndexedData.get(2);
		assertEquals(testID2.getDataValue().equalsIgnoreCase("19"), true);
		
		IndexedData testID3 = allIndexedData.get(99);
		assertEquals(testID3.getDataValue().equalsIgnoreCase("black"), true);
			
	}


}
