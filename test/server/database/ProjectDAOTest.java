package server.database;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.model.Project;

public class ProjectDAOTest {
	Database db;
	ProjectDAO projectDAO;
	
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
		
		List<Project> projects = db.getProjectDAO().getAll();
		for(Project b : projects)
			db.getProjectDAO().delete(b);
		db.endTransaction(true);
		
		//Prepare database for test case
		db = new Database();
		db.startTransaction();
		projectDAO = db.getProjectDAO();
	}

	@After
	public void tearDown() throws Exception {
		//Roll back transaction so changes to the database are undone
		db.endTransaction(false);
		db = null;
		projectDAO = null;
	}

	@Test
	public void testGetAll() throws Exception{
		Project p1 = new Project(-1, "1990s Computer Games", 40, 400, 10);
		Project p2 = new Project(-1, "2000s Computer Games", 50, 400, 5);
		
		projectDAO.add(p1);
		projectDAO.add(p2);
		
		List<Project> all = projectDAO.getAll();
		assertEquals(all.size(), 2);
		
		boolean foundP1 = false;
		boolean foundP2 = false;
		
		for(Project p : all){
			if(areEqual(p, p1, false)){
				foundP1 = true;
				
			}
			if(areEqual(p, p2, false)){
				foundP2 = true;
			}
		}
		assertEquals(foundP1, true);
		assertEquals(foundP2, true);
	}

	@Test
	public void testGetProject() throws Exception{
		Project p1 = new Project(-1, "1990s Computer Games", 40, 400, 10);
		Project p2 = new Project(-1, "2000s Computer Games", 50, 400, 5);
		
		projectDAO.add(p1);
		projectDAO.add(p2);
		
		List<Project> all = projectDAO.getAll();
		assertEquals(all.size(), 2);
		
		Project result = all.get(0);
		assertEquals(areEqual(p1, result, true), true);
		
		result = all.get(1);
		assertEquals(areEqual(p2, result, true), true);
		
	}

	@Test
	public void testAdd() throws Exception{
		Project p1 = new Project(-1, "1990s Computer Games", 40, 400, 10);
		Project p2 = new Project(-1, "2000s Computer Games", 50, 400, 5);
		
		projectDAO.add(p1);
		projectDAO.add(p2);
		
		List<Project> all = projectDAO.getAll();
		assertEquals(all.size(), 2);
		
		boolean foundP1 = false;
		boolean foundP2 = false;
		
		for(Project p : all){
			if(areEqual(p, p1, false)){
				foundP1 = true;
				
			}
			if(areEqual(p, p2, false)){
				foundP2 = true;
			}
		}
		assertEquals(foundP1, true);
		assertEquals(foundP2, true);
	}

	@Test
	public void testUpdate() throws Exception{
		//Create test projects
		Project p1 = new Project(-1, "1990s Computer Games", 40, 400, 10);
		Project p2 = new Project(-1, "2000s Computer Games", 50, 400, 5);
		
		//add them to the database
		projectDAO.add(p1);
		projectDAO.add(p2);
		
		List<Project> all = projectDAO.getAll();
		assertEquals(all.size(), 2);
		
		//change the projects
		p1.setProjectTitle("hahahahaha");
		p1.setRecordHeight(1000);
		p1.setRecordsPerImage(60);
		p1.setFirstYCoord(9);
		
		p2.setProjectTitle("pokemon");
		p2.setRecordHeight(540);
		p2.setRecordsPerImage(1001);
		p2.setFirstYCoord(12);
		
		//update them on the database
		projectDAO.update(p1);
		projectDAO.update(p2);
		
		//make sure the projects were actually updated
		all = projectDAO.getAll();
		assertEquals(all.size(), 2);
		
		boolean foundP1 = false;
		boolean foundP2 = false;
		
		for(Project p : all){
			if(areEqual(p, p1, false)){
				foundP1 = true;
				
			}
			if(areEqual(p, p2, false)){
				foundP2 = true;
			}
		}
		assertEquals(foundP1, true);
		assertEquals(foundP2, true);
	}

	@Test
	public void testDelete() throws Exception{
		//Create test projects
		Project p1 = new Project(-1, "1990s Computer Games", 40, 400, 10);
		Project p2 = new Project(-1, "2000s Computer Games", 50, 400, 5);
		
		//add them to the database
		projectDAO.add(p1);
		projectDAO.add(p2);
		
		//make sure that both have actually been added to the database
		List<Project> all = projectDAO.getAll();
		assertEquals(all.size(), 2);
		
		//delete one from the database and make sure it's actually deleted
		projectDAO.delete(p1);
		all = projectDAO.getAll();
		assertEquals(all.size(), 1);
		
		projectDAO.delete(p2);
		all = projectDAO.getAll();
		assertEquals(all.size(), 0);
		
		
		
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
	
	private boolean safeEquals(Object a, Object b){
		if(a == null || b == null){
			return (a==null && b==null);
		}
		else{
			return a.equals(b);
		}
	}

}
