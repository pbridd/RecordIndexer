package shared.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.Database;
import server.database.ProjectDAO;

public class ProjectManagerTest {
	Database db;
	ProjectDAO projectDAO;
	Project p1;
	Project p2;

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
		projectDAO = db.getProjectDAO();

		//setup tests
		p1 = new Project(-1, "1990s Computer Games", 40, 400, 10);
		p2 = new Project(-1, "2000s Computer Games", 50, 400, 5);
		
		
		ProjectManager.initialize();
	}

	@After
	public void tearDown() throws Exception {
		//clear out database
		db = new Database();
		CommandProcessor.clearDatabase();
		db = null;
		projectDAO = null;
	}

	@Test
	public void testGetAllProjects() throws Exception{
		//add the test projects to the database
		ProjectManager.addProject(p1);
		ProjectManager.addProject(p2);
		
		List<Project> all = ProjectManager.getAllProjects();
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
		//add the test projects to the database
		ProjectManager.addProject(p1);
		ProjectManager.addProject(p2);
		
		List<Project> all = ProjectManager.getAllProjects();
		assertEquals(all.size(), 2);
		
		Project result = all.get(0);
		assertEquals(areEqual(p1, result, true), true);
		
		result = all.get(1);
		assertEquals(areEqual(p2, result, true), true);
	}

	@Test
	public void testAddProject() throws Exception{
		//add the test projects to the database
		ProjectManager.addProject(p1);
		ProjectManager.addProject(p2);
		
		List<Project> all = ProjectManager.getAllProjects();
		assertEquals(all.size(), 2);
		
		Project result = all.get(0);
		assertEquals(areEqual(p1, result, true), true);
		
		result = all.get(1);
		assertEquals(areEqual(p2, result, true), true);
	}

	@Test
	public void testUpdateProject() throws Exception{
		//add them to the database
		ProjectManager.addProject(p1);
		ProjectManager.addProject(p2);
		
		List<Project> all = ProjectManager.getAllProjects();
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
		ProjectManager.updateProject(p1);
		ProjectManager.updateProject(p2);
		
		//make sure the projects were actually updated
		all = ProjectManager.getAllProjects();
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
	public void testDeleteProject() throws Exception{
		//add them to the database
		ProjectManager.addProject(p1);
		ProjectManager.addProject(p2);
		
		//make sure that both have actually been added to the database
		List<Project> all = ProjectManager.getAllProjects();
		assertEquals(all.size(), 2);
		
		//delete one from the database and make sure it's actually deleted
		ProjectManager.deleteProject(p1);
		all = ProjectManager.getAllProjects();
		assertEquals(all.size(), 1);
		
		ProjectManager.deleteProject(p2);
		all = ProjectManager.getAllProjects();
		assertEquals(all.size(), 0);
	}

	@Test
	public void testClearProjects() throws Exception{
		//add them to the database
		ProjectManager.addProject(p1);
		ProjectManager.addProject(p2);
		
		ProjectManager.clearProjects();
		List<Project> all = ProjectManager.getAllProjects();
		
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
