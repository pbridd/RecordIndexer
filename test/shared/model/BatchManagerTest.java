package shared.model;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.BatchDAO;
import server.database.Database;

public class BatchManagerTest {
	private Database db;
	private BatchDAO batchDAO;
	private Batch b1;
	private Batch b2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Database.initialize();
		CommandProcessor.clearDatabase();
		
		
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		CommandProcessor.clearDatabase();
		return;	
	}

	@Before
	public void setUp() throws Exception {
		//add sample batches to the database
		db = new Database();
		batchDAO = db.getBatchDAO();
		db.startTransaction();
		//setup test batches
		b1 = new Batch(-1, "/hello/kids.png", 60, false, -1);
		b2 = new Batch(-1, "/hello/adults.png", 61, true, 5);
		BatchManager.addBatch(b1);
		BatchManager.addBatch(b2);
		db.endTransaction(true);
		
		//set up BatchManager
		BatchManager.initialize();
	}

	@After
	public void tearDown() throws Exception {
		//clear out database
		CommandProcessor.clearDatabase();
		db = new Database();
		db = null;
		batchDAO = null;
	}

	@Test
	public void testGetSampleImage() throws Exception{
		
		Batch result1 = BatchManager.getSampleImage(60);
		assertEquals(areEqual(result1, b1, true), true);
		Batch result2 = BatchManager.getSampleImage(61);
		assertEquals(result2.getBatchID(), b2.getBatchID());
		assertEquals(areEqual(result2, b2, true), true);
		
		Batch result3 = BatchManager.getSampleImage(3453);
		if(result3 != null){
			fail("in testGetSampleImage, the function returned a batch from a project that doesn't exist");
		}
		
		
	}

	@Test
	public void testSubmitBatch() throws Exception{
		
		//Set up the database
	
		User testUsr = new User(-1, "test1", "test1", "Test", "One", 
				"test1@gmail.com", 0 );
		UserManager.addUser(testUsr);
		
		Project testPrj = new Project(-1, "Test Project", 7, 100, 20 );
		ProjectManager.addProject(testPrj);
		
		Field testField1 = new Field(-1, 1, "Test1", 20, 30, "/helpme1.html", 
				"known1.txt", 1);
		Field testField2 = new Field(-1, 2, "Test1", 20, 30, "/helpme2.html", 
				"known2.txt", 1);
		Field testField3 = new Field(-1, 3, "Test3", 20, 30, "/helpme3.html", 
				"known3.txt", 1);
		Field testField4 = new Field(-1, 4, "Test4", 20, 30, "/helpme4.html", 
				"known4.txt", 1);
		FieldManager.addField(testField1);
		FieldManager.addField(testField2);
		FieldManager.addField(testField3);
		FieldManager.addField(testField4);
		
		Batch testBatch = new Batch(-1, "/batch.png", testPrj.getProjectID(), false, testUsr.getUserID());
		BatchManager.addBatch(testBatch);
		//start feeding the test data
		List<IndexedData> idList = new ArrayList<IndexedData>();
		
		idList.add(new IndexedData(-1, "darkk", 1, 1));
		idList.add(new IndexedData(-1, "dangit",1, 2));
		idList.add(new IndexedData(-1, "I'm Batman", 1, 3));
		idList.add(new IndexedData(-1, "Robin", 1, 4));
		
		List<Record> recordList = new ArrayList<Record>();
		recordList.add(new Record(-1, 1, 1));
		
		User usr1 = UserManager.validateUser("test1", "test1");
		int batchID = testBatch.getBatchID();
		
		BatchManager.submitBatch(usr1, batchID, recordList, idList, false);
		
		//check that the user's indexed records has been updated
		
		usr1 = UserManager.validateUser("test1", "test1");
		assertEquals(usr1.getIndexedRecords(), 7);
		
		//check to make sure the IndexedData has been added to the database
		IndexedData t1 = IndexedDataManager.search(1, "darkk").get(0);
		assertEquals(t1.getIndexedDataID(), idList.get(0).getIndexedDataID());
		
		Record t2 = RecordManager.getRecord(recordList.get(0).getRecordID());
		assertEquals(t2.getBatchID(), recordList.get(0).getBatchID());
		
		Batch retTestBatch = BatchManager.getBatch(batchID);
		assertEquals(retTestBatch.isFullyIndexed(), true);
		CommandProcessor.clearDatabase();
		
	}
	
	@Test(expected = ModelException.class)
	public void testSubmitBatchTwice() throws Exception{
		//Set up the database
	
		User testUsr = new User(-1, "test1", "test1", "Test", "One", 
				"test1@gmail.com", 0 );
		UserManager.addUser(testUsr);
		
		Project testPrj = new Project(-1, "Test Project", 7, 100, 20 );
		ProjectManager.addProject(testPrj);
		
		Field testField1 = new Field(-1, 1, "Test1", 20, 30, "/helpme1.html", 
				"known1.txt", 1);
		Field testField2 = new Field(-1, 2, "Test1", 20, 30, "/helpme2.html", 
				"known2.txt", 1);
		Field testField3 = new Field(-1, 3, "Test3", 20, 30, "/helpme3.html", 
				"known3.txt", 1);
		Field testField4 = new Field(-1, 4, "Test4", 20, 30, "/helpme4.html", 
				"known4.txt", 1);
		FieldManager.addField(testField1);
		FieldManager.addField(testField2);
		FieldManager.addField(testField3);
		FieldManager.addField(testField4);
		
		Batch testBatch = new Batch(-1, "/batch.png", testPrj.getProjectID(), false, testUsr.getUserID());
		BatchManager.addBatch(testBatch);
		//start feeding the test data
		List<IndexedData> idList = new ArrayList<IndexedData>();
		
		idList.add(new IndexedData(-1, "darkk", 1, 1));
		idList.add(new IndexedData(-1, "dangit",1, 2));
		idList.add(new IndexedData(-1, "I'm Batman", 1, 3));
		idList.add(new IndexedData(-1, "Robin", 1, 4));
		
		List<Record> recordList = new ArrayList<Record>();
		recordList.add(new Record(-1, 1, 1));
		
		User usr1 = UserManager.validateUser("test1", "test1");
		int batchID = testBatch.getBatchID();
		
		BatchManager.submitBatch(usr1, batchID, recordList, idList, false);
		
		//check that the user's indexed records has been updated
		
		usr1 = UserManager.validateUser("test1", "test1");
		assertEquals(usr1.getIndexedRecords(), 7);
		
		//check to make sure the IndexedData has been added to the database
		IndexedData t1 = IndexedDataManager.search(1, "darkk").get(0);
		assertEquals(t1.getIndexedDataID(), idList.get(0).getIndexedDataID());
		
		Record t2 = RecordManager.getRecord(recordList.get(0).getRecordID());
		assertEquals(t2.getBatchID(), recordList.get(0).getBatchID());
		
		Batch retTestBatch = BatchManager.getBatch(batchID);
		assertEquals(retTestBatch.isFullyIndexed(), true);
		
		//MAKE SURE THAT I CAN'T SUBMIT THE SAME BATCH TWICE
		BatchManager.submitBatch(usr1, batchID, recordList, idList, false);
		CommandProcessor.clearDatabase();
		
	}

	@Test
	public void testGetBatch() throws Exception{
		Batch tBatch = new Batch(-1, "/test/monkies", 489, false, 8);
		BatchManager.addBatch(tBatch);
		
		//make sure it added correctly
		Batch retTBatch = BatchManager.getBatch(tBatch.getBatchID());
		assertEquals(areEqual(retTBatch, tBatch, true), true);
		
	}

	@Test
	public void testAssignBatch() throws Exception{
		Batch tBatch = new Batch(-1, "/test/monkies", 489, false, -1);
		BatchManager.addBatch(tBatch);
		
		//set up the user
		User testUsr = new User(-1, "test1", "test1", "Test", "One", 
				"test1@gmail.com", 0 );
		UserManager.addUser(testUsr);
		
		User usr = UserManager.validateUser("test1", "test1");
		
		Batch retTBatch = BatchManager.assignBatch(489, usr);
		
		assertEquals(retTBatch.getUserID(), usr.getUserID());
		
		//make sure it was correctly assigned
		assertEquals(retTBatch.getUserID(), usr.getUserID());
	}

	@Test
	public void testAddBatch() throws Exception{
		Batch tBatch = new Batch(-1, "/test/monkies", 489, false, 8);
		BatchManager.addBatch(tBatch);
		
		//make sure it added correctly
		Batch retTBatch = BatchManager.getBatch(tBatch.getBatchID());
		assertEquals(areEqual(retTBatch, tBatch, true), true);
		
	}

	@Test
	public void testUpdateBatch() throws Exception{
		Batch tBatch = new Batch(-1, "/test/monkies", 500, false, 12);
		BatchManager.addBatch(tBatch);
		
		tBatch.setImagePath("/heath/x");
		tBatch.setFullyIndexed(true);
		tBatch.setProjectID(28314);
		tBatch.setUserID(-1);
		
		BatchManager.updateBatch(tBatch);
		Batch upRes = BatchManager.getBatch(tBatch.getBatchID());
		assertEquals(areEqual(tBatch, upRes, true), true);
	}

	@Test
	public void testDeleteBatch() throws Exception{
		Batch tBatch = new Batch(-1, "/test/monkies", 500, false, 12);
		BatchManager.addBatch(tBatch);
		BatchManager.deleteBatch(tBatch);
		
		Batch upRes = BatchManager.getBatch(tBatch.getBatchID());
		if(upRes != null){
			fail("The database query returned a batch that should have been deleted.");
		}
	}

	@Test
	public void testClearBatches() throws Exception{
		BatchManager.clearBatches();
		db.startTransaction();
		List<Batch> resList= batchDAO.getAll();
		db.endTransaction(false);
		assertEquals(resList.size(), 0);
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
