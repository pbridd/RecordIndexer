package server.database;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.model.Record;

public class RecordDAOTest {
	private Database db;
	private RecordDAO recordDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}

	@Before
	public void setUp() throws Exception {
		db = new Database();
		db.startTransaction();
		
		List<Record> records = db.getRecordDAO().getAll();
		for(Record b : records)
			db.getRecordDAO().delete(b);
		db.endTransaction(true);
		
		//Prepare database for test case
		db = new Database();
		db.startTransaction();
		recordDAO = db.getRecordDAO();
	}

	@After
	public void tearDown() throws Exception {
		//Roll back transaction so changes to the database are undone
		db.endTransaction(false);
		db = null;
		recordDAO = null;
	}

	@Test
	public void testGetAll() throws Exception{
		//Create the records
		Record r1 = new Record(-1, 2, 3);
		Record r2 = new Record(-1, 5, 9);
		
		recordDAO.add(r1);
		recordDAO.add(r2);
		
		//check to see that the records were added
		List<Record> all = recordDAO.getAll();
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
		Record r1 = new Record(-1, 2, 3);
		Record r2 = new Record(-1, 5, 9);
		
		recordDAO.add(r1);
		recordDAO.add(r2);
		
		List<Record> all = recordDAO.getAll();
		assertEquals(all.size(), 2);
		
		Record result = recordDAO.getRecord(r1.getRecordID());
		assertEquals(areEqual(r1, result, true), true);
		
		result = recordDAO.getRecord(r2.getRecordID());
		assertEquals(areEqual(r2, result, true), true);
	}

	@Test
	public void testAdd() throws Exception{
		//Create the records
		Record r1 = new Record(-1, 2, 3);
		Record r2 = new Record(-1, 5, 9);
				
		recordDAO.add(r1);
		recordDAO.add(r2);
				
		//check to see that the records were added
		List<Record> all = recordDAO.getAll();
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
	public void testUpdate() throws Exception{
		//Create test records
		Record r1 = new Record(-1, 2, 3);
		Record r2 = new Record(-1, 5, 9);
				
		//add them to the database
		recordDAO.add(r1);
		recordDAO.add(r2);
				
		List<Record> all = recordDAO.getAll();
		assertEquals(all.size(), 2);
				
		//change the records
		r1.setRecordNumber(213412);
		r1.setBatchID(12377);
				
		r2.setRecordNumber(2113412);
		r2.setBatchID(122377);
				
		//update them on the database
		recordDAO.update(r1);
		recordDAO.update(r2);
			
		//make sure the records were actually updated
		all = recordDAO.getAll();
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
	public void testDelete() throws Exception{
		//Create test records
		Record r1 = new Record(-1, 2, 3);
		Record r2 = new Record(-1, 5, 9);
				
		//add them to the database
		recordDAO.add(r1);
		recordDAO.add(r2);
				
		//make sure that both have actually been added to the database
		List<Record> all = recordDAO.getAll();
		assertEquals(all.size(), 2);
				
		//delete one from the database and make sure it's actually deleted
		recordDAO.delete(r1);
		all = recordDAO.getAll();
		assertEquals(all.size(), 1);
				
		recordDAO.delete(r2);
		all = recordDAO.getAll();
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
