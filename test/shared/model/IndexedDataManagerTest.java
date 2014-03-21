package shared.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.Database;
import server.database.IndexedDataDAO;

public class IndexedDataManagerTest {
	Database db;
	IndexedDataDAO indexedDataDAO;
	IndexedData id1;
	IndexedData id2;
	IndexedData id3;
	IndexedData id4;

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
		indexedDataDAO = db.getIndexedDataDAO();

		//setup tests
		id1 = new IndexedData(-1, "Happiness", 2, 5);
		id2 = new IndexedData(-1, "Jumpy", 6, 4);
		id3 = new IndexedData(-1, "Happiness", 3, 5);
		id4 = new IndexedData(-1, "Mini", 6, 2);
		
		IndexedDataManager.initialize();
	}

	@After
	public void tearDown() throws Exception {
		//clear out database
		db = new Database();
		CommandProcessor.clearDatabase();
		db = null;
		indexedDataDAO = null;
	}

	@Test
	public void testSearch() throws Exception{
		IndexedDataManager.addIndexedData(id1);
		IndexedDataManager.addIndexedData(id2);
		IndexedDataManager.addIndexedData(id3);
		IndexedDataManager.addIndexedData(id4);
		

		
		List<IndexedData> sr1 = IndexedDataManager.search(5, "happiness");
		assertEquals(sr1.size(), 2);
		
		boolean foundSR1 = false;
		boolean foundSR2 = false;
		for(IndexedData i : sr1){
			if(areEqual(id1, i, true)){
				foundSR1=true;
			}
			if(areEqual(id3, i, true)){
				foundSR2=true;
			}
		}
		assertEquals(foundSR1, true);
		assertEquals(foundSR2, true);
		
		List<IndexedData> sr2 = IndexedDataManager.search(4, "jUmPy");
		assertEquals(sr2.size(), 1);
		assertEquals(areEqual(sr2.get(0), id2, true), true);
		
		List<IndexedData> sr3 = IndexedDataManager.search(134312, "mini");
		assertEquals(sr3.size(), 0);
		
		List<IndexedData> sr4 = IndexedDataManager.search(2, "Happiness");
		assertEquals(sr4.size(), 0);
	}

	@Test
	public void testAddIndexedData() throws Exception{
		IndexedDataManager.addIndexedData(id1);
		IndexedDataManager.addIndexedData(id2);
		IndexedDataManager.addIndexedData(id3);
		IndexedDataManager.addIndexedData(id4);
	
		db.startTransaction();
		List<IndexedData> sr1 = indexedDataDAO.getAll();
		assertEquals(sr1.size(), 4);
		db.endTransaction(false);
	
		boolean foundSR1 = false;
		boolean foundSR2 = false;
		boolean foundSR3 = false;
		boolean foundSR4 = false; 
		for(IndexedData i : sr1){
			if(areEqual(id1, i, true)){
				foundSR1=true;
			}
			if(areEqual(id2, i, true)){
				foundSR2=true;
			}
			if(areEqual(id3, i, true)){
				foundSR3=true;
			}
			if(areEqual(id4, i, true)){
				foundSR4=true;
			}
		}
		assertEquals(foundSR1, true);
		assertEquals(foundSR2, true);
		assertEquals(foundSR3, true);
		assertEquals(foundSR4, true);
	}

	@Test
	public void testUpdateIndexedData() throws Exception{
		IndexedDataManager.addIndexedData(id1);
		IndexedDataManager.addIndexedData(id2);
		IndexedDataManager.addIndexedData(id3);
		IndexedDataManager.addIndexedData(id4);
		
		db.startTransaction();
		List<IndexedData> sr1 = indexedDataDAO.getAll();
		db.endTransaction(true);
		assertEquals(sr1.size(), 4);
		
		boolean foundSR1 = false;
		boolean foundSR2 = false;
		boolean foundSR3 = false;
		boolean foundSR4 = false; 
		for(IndexedData i : sr1){
			if(areEqual(id1, i, true)){
				foundSR1=true;
			}
			if(areEqual(id2, i, true)){
				foundSR2=true;
			}
			if(areEqual(id3, i, true)){
				foundSR3=true;
			}
			if(areEqual(id4, i, true)){
				foundSR4=true;
			}
		}
		assertEquals(foundSR1, true);
		assertEquals(foundSR2, true);
		assertEquals(foundSR3, true);
		assertEquals(foundSR4, true);
		
		id1.setDataValue("holmes");
		id1.setFieldID(4254523);
		id1.setRecordID(123);
		
		id2.setDataValue("ticktock");
		id2.setFieldID(88);
		id2.setRecordID(288);
		
		id3.setDataValue("underneath");
		id3.setFieldID(329);
		id3.setRecordID(847);
		
		id4.setDataValue("greenie");
		id4.setFieldID(45);
		id4.setRecordID(290);
		
		
		IndexedDataManager.updateIndexedData(id1);
		IndexedDataManager.updateIndexedData(id2);
		IndexedDataManager.updateIndexedData(id3);
		IndexedDataManager.updateIndexedData(id4);
		
		db.startTransaction();
		sr1 = indexedDataDAO.getAll();
		db.endTransaction(true);
		assertEquals(sr1.size(), 4);
		
		foundSR1 = false;
		foundSR2 = false;
		foundSR3 = false;
		foundSR4 = false; 
		for(IndexedData i : sr1){
			if(areEqual(id1, i, true)){
				foundSR1=true;
			}
			if(areEqual(id2, i, true)){
				foundSR2=true;
			}
			if(areEqual(id3, i, true)){
				foundSR3=true;
			}
			if(areEqual(id4, i, true)){
				foundSR4=true;
			}
		}
		assertEquals(foundSR1, true);
		assertEquals(foundSR2, true);
		assertEquals(foundSR3, true);
		assertEquals(foundSR4, true);
		
	}

	@Test
	public void testDeleteIndexedData() throws Exception{
		IndexedDataManager.addIndexedData(id1);
		IndexedDataManager.addIndexedData(id2);
		IndexedDataManager.addIndexedData(id3);
		IndexedDataManager.addIndexedData(id4);
		
		db.startTransaction();
		List<IndexedData> sr1 = indexedDataDAO.getAll();
		assertEquals(sr1.size(), 4);
		db.endTransaction(true);
		
		//make extra sure these were added
		boolean foundSR1 = false;
		boolean foundSR2 = false;
		boolean foundSR3 = false;
		boolean foundSR4 = false; 
		for(IndexedData i : sr1){
			if(areEqual(id1, i, true)){
				foundSR1=true;
			}
			if(areEqual(id2, i, true)){
				foundSR2=true;
			}
			if(areEqual(id3, i, true)){
				foundSR3=true;
			}
			if(areEqual(id4, i, true)){
				foundSR4=true;
			}
		}
		assertEquals(foundSR1, true);
		assertEquals(foundSR2, true);
		assertEquals(foundSR3, true);
		assertEquals(foundSR4, true);
		
		//delete them one by one and make sure that they really are being deleted
		
		IndexedDataManager.deleteIndexedData(id1);
		db.startTransaction();
		sr1 = indexedDataDAO.getAll();
		db.endTransaction(true);
		assertEquals(sr1.size(), 3);
		
		IndexedDataManager.deleteIndexedData(id2);
		db.startTransaction();
		List<IndexedData> sr2 = indexedDataDAO.getAll();
		db.endTransaction(true);
		assertEquals(sr2.size(), 2);
		
		IndexedDataManager.deleteIndexedData(id3);
		db.startTransaction();
		List<IndexedData> sr3 = indexedDataDAO.getAll();
		db.endTransaction(true);
		assertEquals(sr3.size(), 1);
		
		IndexedDataManager.deleteIndexedData(id4);
		db.startTransaction();
		List<IndexedData> sr4 = indexedDataDAO.getAll();
		assertEquals(sr4.size(), 0);
		db.endTransaction(true);
	}

	@Test
	public void testClearIndexedData() throws Exception{
		IndexedDataManager.addIndexedData(id1);
		IndexedDataManager.addIndexedData(id2);
		IndexedDataManager.addIndexedData(id3);
		IndexedDataManager.addIndexedData(id4);
		
		db.startTransaction();
		List<IndexedData> sr1 = indexedDataDAO.getAll();
		assertEquals(sr1.size(), 4);
		db.endTransaction(true);
		
		//make extra sure these were added
		boolean foundSR1 = false;
		boolean foundSR2 = false;
		boolean foundSR3 = false;
		boolean foundSR4 = false; 
		for(IndexedData i : sr1){
			if(areEqual(id1, i, true)){
				foundSR1=true;
			}
			if(areEqual(id2, i, true)){
				foundSR2=true;
			}
			if(areEqual(id3, i, true)){
				foundSR3=true;
			}
			if(areEqual(id4, i, true)){
				foundSR4=true;
			}
		}
		assertEquals(foundSR1, true);
		assertEquals(foundSR2, true);
		assertEquals(foundSR3, true);
		assertEquals(foundSR4, true);
		
		//clear the database and make sure it was really cleared
		db.startTransaction();
		IndexedDataManager.clearIndexedData();
		sr1 = indexedDataDAO.getAll();
		assertEquals(sr1.size(), 0);
		db.endTransaction(true);
	}
	
	private boolean areEqual(IndexedData a, IndexedData b, boolean compareIDs){
		if(compareIDs){
			if(a.getFieldID() != b.getFieldID())
				return false;
		}
		return(safeEquals(a.getDataValue(), b.getDataValue()) &&
				safeEquals(a.getRecordID(), b.getRecordID()) &&
				safeEquals(a.getFieldID(),b.getFieldID()));
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
