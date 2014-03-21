package shared.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.Database;
import server.database.RecordDAO;

public class RecordManagerTest {
	Database db;
	RecordDAO recordDAO;
	Record r1;
	Record r2;
	
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
		//add samples to the database
		db = new Database();
		recordDAO = db.getRecordDAO();

		//setup tests
		r1 = new Record(-1, 2, 3);
		r2 = new Record(-1, 5, 9);
		
		
		RecordManager.initialize();
	}

	@After
	public void tearDown() throws Exception {
		//clear out database
		db = new Database();
		CommandProcessor.clearDatabase();
		db = null;
		recordDAO = null;
	}

	@Test
	public void testAddRecord() throws Exception{
		RecordManager.addRecord(r1);
		RecordManager.addRecord(r2);
				
		//check to see that the records were added
		db.startTransaction();
		List<Record> all = recordDAO.getAll();
		db.endTransaction(true);
		assertEquals(all.size(), 2);
				
		boolean foundR1 = false;
		boolean foundR2 = false;
				
		//double check that it worked right
		for(Record p : all){
			if(areEqual(p, r1, false)){
				foundR1 = true;
						
			}
			if(areEqual(p, r2, false)){
				foundR2 = true;
			}
		}
		assertEquals(foundR1, true);
		assertEquals(foundR2, true);
	}

	@Test
	public void testGetRecord() throws Exception{
		//Create the records
		
		RecordManager.addRecord(r1);
		RecordManager.addRecord(r2);
		
		db.startTransaction();
		List<Record> all = recordDAO.getAll();
		db.endTransaction(true);
		assertEquals(all.size(), 2);
		
		Record result = RecordManager.getRecord(r1.getRecordID());
		assertEquals(areEqual(r1, result, true), true);
		
		result = RecordManager.getRecord(r2.getRecordID());
		assertEquals(areEqual(r2, result, true), true);
	}

	@Test
	public void testUpdateRecord() throws Exception{
		//add them to the database
		RecordManager.addRecord(r1);
		RecordManager.addRecord(r2);
				
		db.startTransaction();
		List<Record> all = recordDAO.getAll();
		db.endTransaction(true);
		assertEquals(all.size(), 2);
				
		//change the records
		r1.setRecordNumber(213412);
		r1.setBatchID(12377);
				
		r2.setRecordNumber(2113412);
		r2.setBatchID(122377);
				
		//update them on the database
		RecordManager.updateRecord(r1);
		RecordManager.updateRecord(r2);
			
		//make sure the records were actually updated
		db.startTransaction();
		all = recordDAO.getAll();
		db.endTransaction(true);
		assertEquals(all.size(), 2);
				
		boolean foundR1 = false;
		boolean foundR2 = false;
				
		for(Record p : all){
			if(areEqual(p, r1, false)){
				foundR1 = true;
						
			}
			if(areEqual(p, r2, false)){
				foundR2 = true;
			}
		}
		assertEquals(foundR1, true);
		assertEquals(foundR2, true);

	}

	@Test
	public void testDeleteRecord() throws Exception{
		//add them to the database
		RecordManager.addRecord(r1);
		RecordManager.addRecord(r2);
				
		//make sure that both have actually been added to the database
		db.startTransaction();
		List<Record> all = recordDAO.getAll();
		db.endTransaction(true);
		assertEquals(all.size(), 2);
				
		//delete one from the database and make sure it's actually deleted
		RecordManager.deleteRecord(r1);
		db.startTransaction();
		all = recordDAO.getAll();
		db.endTransaction(true);
		assertEquals(all.size(), 1);
				
		RecordManager.deleteRecord(r2);
		db.startTransaction();
		all = recordDAO.getAll();
		db.endTransaction(true);
		assertEquals(all.size(), 0);
	}

	@Test
	public void testClearRecords() throws Exception{
		RecordManager.addRecord(r1);
		RecordManager.addRecord(r2);
		
		RecordManager.clearRecords();
		
		db.startTransaction();
		List<Record> all = recordDAO.getAll();
		db.endTransaction(true);
		
		assertEquals(all.size(), 0);
	}

	private boolean areEqual(Record a, Record b, boolean compareIDs){
		if(compareIDs){
			if(a.getRecordID() != b.getRecordID())
				return false;
		}
		return(safeEquals(a.getRecordNumber(), b.getRecordNumber()) &&
				safeEquals(a.getBatchID(), b.getBatchID()));
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
