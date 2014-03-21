package server.database;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.model.Batch;

public class BatchDAOTest {
	private Database db;
	private BatchDAO batchDAO;
	
	
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
		
		List<Batch> batches = db.getBatchDAO().getAll();
		for(Batch b : batches)
			db.getBatchDAO().delete(b);
		db.endTransaction(true);
		
		//Prepare database for test case
		db = new Database();
		db.startTransaction();
		batchDAO = db.getBatchDAO();
	}

	@After
	public void tearDown() throws Exception {
		//Roll back transaction so changes to the database are undone
		db.endTransaction(false);
		db = null;
		batchDAO = null;
	}
	
	@Test
	public void assignBatch() throws Exception{
		Batch b1 = new Batch(-1, "/hello/kids.png", 2, false, -1);
		Batch b2 = new Batch(-1, "/hello/adults.png", 3, true, 5);
		batchDAO.add(b1);
		batchDAO.add(b2);
		Batch assignedBatch = batchDAO.assignBatch(3, 2);
		if(!(assignedBatch.getBatchID() == b1.getBatchID())){
			fail("The batch returned by assignBatch was not equal to b1, when trying to assign"
					+ " a batch to user 3 from project 2.");
		}
		assignedBatch = batchDAO.assignBatch(3, 5);
		if(assignedBatch != null){
			fail("The batch returned by assignBatch was not null even though all batches from project 3 "
					+ " had already been assigned.");
		}
		
	}

	@Test
	public void testGetSampleImage() throws Exception{
		Batch b1 = new Batch(-1, "/hello/kids.png", 2, false, -1);
		Batch b2 = new Batch(-1, "/hello/adults.png", 3, true, 5);
		batchDAO.add(b1);
		batchDAO.add(b2);
		Batch r1 = batchDAO.getSampleImage(2);
		if(!areEqual(r1, b1, false)){
			fail("The batch returned by getBatch was not equal to b1, when trying to get "
					+ "a sample image for project 2");
		}
		Batch r2 = batchDAO.getSampleImage(3);
		if(!areEqual(r2, b2, false)){
			fail("The batch returned by getBatch was not equal to b2, when trying to get "
					+ "a sample image for project 3.");
		}
	}

	@Test
	public void testGetBatch() throws Exception{
		Batch b1 = new Batch(-1, "/hello/kids.png", 2, false, -1);
		Batch b2 = new Batch(-1, "/hello/adults.png", 3, true, 5);
		batchDAO.add(b1);
		batchDAO.add(b2);
		List<Batch> all = batchDAO.getAll();
		assertEquals(2, all.size());
		for(Batch b : all){
			Batch bgot = batchDAO.getBatch(b.getBatchID());
			if(!areEqual(bgot, b, true)){
				fail("The batch returned by getBatch was not equal to the batch that should"
						+ " have been returned");
			}
		}
	}

	@Test
	public void testGetAll() throws Exception{
		Batch b1 = new Batch(-1, "/hello/kids.png", 2, false, -1);
		Batch b2 = new Batch(-1, "/hello/adults.png", 3, true, 5);
		batchDAO.add(b1);
		batchDAO.add(b2);
		List<Batch> all = batchDAO.getAll();
		assertEquals(2, all.size());
		boolean foundB1 = false;
		boolean foundB2 = false;
		
		for(Batch b : all){
			assertFalse(b.getBatchID() == -1);
			
			if(!foundB1){
				foundB1 = areEqual(b, b1, false);
			}
			if(!foundB2){
				foundB2 = areEqual(b, b2, false);
			}
		}
		assertTrue(foundB1 && foundB2);
	}

	@Test
	public void testAdd() throws Exception{
		Batch b1 = new Batch(-1, "/hello/kids.png", 2, false, -1);
		Batch b2 = new Batch(-1, "/hello/adults.png", 3, true, 5);
		
		batchDAO.add(b1);
		batchDAO.add(b2);
		
		List<Batch> all = batchDAO.getAll();
		assertEquals(2, all.size());
		
		boolean foundB1 = false;
		boolean foundB2 = false;
		
		for(Batch b : all){
			assertFalse(b.getBatchID() == -1);
			
			if(!foundB1){
				foundB1 = areEqual(b, b1, false);
			}
			if(!foundB2){
				foundB2 = areEqual(b, b2, false);
			}
		}
		assertTrue(foundB1 && foundB2);
		
		
	}

	@Test
	public void testUpdate() throws Exception{
		Batch b1 = new Batch(-1, "/hello/kids.png", 2, false, -1);
		Batch b2 = new Batch(-1, "/hello/adults.png", 3, true, 5);
		
		batchDAO.add(b1);
		batchDAO.add(b2);
		
		b1.setFullyIndexed(true);
		b1.setUserID(20);
		b1.setImagePath("/goodbye/kids.png");
		b1.setProjectID(1);
		
		b2.setFullyIndexed(false);
		b2.setUserID(-1);
		b2.setImagePath("/");
		b2.setProjectID(21);
		
		batchDAO.update(b1);
		batchDAO.update(b2);
		
		List<Batch> all = batchDAO.getAll();
		assertEquals(2, all.size());
		
		boolean foundB1 = false;
		boolean foundB2 = false;
		
		for(Batch b : all){
			assertFalse(b.getBatchID() == -1);
			
			if(!foundB1){
				foundB1 = areEqual(b, b1, false);
			}
			if(!foundB2){
				foundB2 = areEqual(b, b2, false);
			}
		}
		assertTrue(foundB1 && foundB2);
	}

	@Test
	public void testDelete() throws Exception{
		Batch b1 = new Batch(-1, "/hello/kids.png", 2, false, -1);
		Batch b2 = new Batch(-1, "/hello/adults.png", 3, true, 5);
		
		batchDAO.add(b1);
		batchDAO.add(b2);
		
		List<Batch> all = batchDAO.getAll();
		assertEquals(2, all.size());
		
		batchDAO.delete(b1);
		batchDAO.delete(b2);
		
		all = batchDAO.getAll();
		assertEquals(0, all.size());
		
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
