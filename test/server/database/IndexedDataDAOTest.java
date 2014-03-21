package server.database;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.model.IndexedData;

public class IndexedDataDAOTest {
	private Database db;
	private IndexedDataDAO indexedDataDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//load database driver
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
		
		List<IndexedData> indexedDatas = db.getIndexedDataDAO().getAll();
		for(IndexedData b : indexedDatas)
			db.getIndexedDataDAO().delete(b);
		db.endTransaction(true);
		
		//Prepare database for test case
		db = new Database();
		db.startTransaction();
		indexedDataDAO = db.getIndexedDataDAO();
	}

	@After
	public void tearDown() throws Exception {
		//Roll back transaction so changes to the database are undone
		db.endTransaction(false);
		db = null;
		indexedDataDAO = null;
	}

	@Test
	public void testSearch() throws Exception{
		IndexedData id1 = new IndexedData(-1, "Happiness", 2, 5);
		IndexedData id2 = new IndexedData(-1, "Jumpy", 6, 4);
		IndexedData id3 = new IndexedData(-1, "Happiness", 3, 5);
		IndexedData id4 = new IndexedData(-1, "Mini", 6, 2);
		
		indexedDataDAO.add(id1);
		indexedDataDAO.add(id2);
		indexedDataDAO.add(id3);
		indexedDataDAO.add(id4);
		
		
		List<IndexedData> sr1 = indexedDataDAO.search(5, "happiness");
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
		
		List<IndexedData> sr2 = indexedDataDAO.search(4, "jUmPy");
		assertEquals(sr2.size(), 1);
		assertEquals(areEqual(sr2.get(0), id2, true), true);
		
		List<IndexedData> sr3 = indexedDataDAO.search(134312, "mini");
		assertEquals(sr3.size(), 0);
		
		List<IndexedData> sr4 = indexedDataDAO.search(2, "Happiness");
		assertEquals(sr4.size(), 0);
	}

	@Test
	public void testGetAll() throws Exception{
		IndexedData id1 = new IndexedData(-1, "Happiness", 2, 5);
		IndexedData id2 = new IndexedData(-1, "Jumpy", 6, 4);
		IndexedData id3 = new IndexedData(-1, "Happiness", 3, 5);
		IndexedData id4 = new IndexedData(-1, "Mini", 6, 2);
		
		indexedDataDAO.add(id1);
		indexedDataDAO.add(id2);
		indexedDataDAO.add(id3);
		indexedDataDAO.add(id4);
		
		List<IndexedData> sr1 = indexedDataDAO.getAll();
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
		
	}

	@Test
	public void testAdd() throws Exception{
		IndexedData id1 = new IndexedData(-1, "Happiness", 2, 5);
		IndexedData id2 = new IndexedData(-1, "Jumpy", 6, 4);
		IndexedData id3 = new IndexedData(-1, "Happiness", 3, 5);
		IndexedData id4 = new IndexedData(-1, "Mini", 6, 2);
		
		indexedDataDAO.add(id1);
		indexedDataDAO.add(id2);
		indexedDataDAO.add(id3);
		indexedDataDAO.add(id4);
		
		List<IndexedData> sr1 = indexedDataDAO.getAll();
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
	}

	@Test
	public void testUpdate() throws Exception{
		IndexedData id1 = new IndexedData(-1, "Happiness", 2, 5);
		IndexedData id2 = new IndexedData(-1, "Jumpy", 6, 4);
		IndexedData id3 = new IndexedData(-1, "Happiness", 3, 5);
		IndexedData id4 = new IndexedData(-1, "Mini", 6, 2);
		
		indexedDataDAO.add(id1);
		indexedDataDAO.add(id2);
		indexedDataDAO.add(id3);
		indexedDataDAO.add(id4);
		
		List<IndexedData> sr1 = indexedDataDAO.getAll();
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
		
		indexedDataDAO.update(id1);
		indexedDataDAO.update(id2);
		indexedDataDAO.update(id3);
		indexedDataDAO.update(id4);
		
		sr1 = indexedDataDAO.getAll();
		
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
	public void testDelete() throws Exception{
		IndexedData id1 = new IndexedData(-1, "Happiness", 2, 5);
		IndexedData id2 = new IndexedData(-1, "Jumpy", 6, 4);
		IndexedData id3 = new IndexedData(-1, "Happiness", 3, 5);
		IndexedData id4 = new IndexedData(-1, "Mini", 6, 2);
		
		indexedDataDAO.add(id1);
		indexedDataDAO.add(id2);
		indexedDataDAO.add(id3);
		indexedDataDAO.add(id4);
		
		List<IndexedData> sr1 = indexedDataDAO.getAll();
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
		
		indexedDataDAO.delete(id1);
		indexedDataDAO.delete(id2);
		indexedDataDAO.delete(id3);
		indexedDataDAO.delete(id4);
		sr1 = indexedDataDAO.getAll();
		assertEquals(sr1.size(), 0);
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
