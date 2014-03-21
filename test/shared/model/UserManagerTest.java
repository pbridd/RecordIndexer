package shared.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.Database;
import server.database.UserDAO;

public class UserManagerTest {
	Database db;
	UserDAO userDAO;
	User u1;
	User u2;

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
		userDAO = db.getUserDAO();

		//setup tests
		u1 = new User(-1, "hello", "kitty", "james", "potter", "g@potter.com", 
				1000);
		u2 = new User(-1, "over", "9000", "dragonball", "z", "over@9000.com", 
				9001);
		
		
		UserManager.initialize();
	}

	@After
	public void tearDown() throws Exception {
		//clear out database
		db = new Database();
		CommandProcessor.clearDatabase();
		db = null;
		userDAO = null;
	}

	@Test
	public void testValidateUser() throws Exception{
		UserManager.addUser(u1);
		UserManager.addUser(u2);
		
		db.startTransaction();
		List<User> all = userDAO.getAll();
		db.endTransaction(true);
		assertEquals(all.size(), 2);
		
		//test some cases that work
		User testUsr1 = UserManager.validateUser(u1.getUsername(), u1.getPassword());
		User testUsr2 = UserManager.validateUser(u2.getUsername(), u2.getPassword());
		assertEquals(areEqual(u1, testUsr1, true), true);
		assertEquals(areEqual(u2, testUsr2, true), true);
		
		 
		//test a case that doesn't
		User testUsr3 = UserManager.validateUser("FAKE", "USER");
		if(testUsr3 != null){
			fail("A user was returned when null should have been returned");
		}
	}

	@Test
	public void testAddUser() throws Exception{
		//Create the users
				
		UserManager.addUser(u1);
		UserManager.addUser(u2);
				
		//check to see that the users were added
		db.startTransaction();
		List<User> all = userDAO.getAll();
		db.endTransaction(true);
		assertEquals(all.size(), 2);
		
		boolean foundU1 = false;
		boolean foundU2 = false;
				
		//double check that it worked right
		for(User p : all){
			if(areEqual(p, u1, false)){
				foundU1 = true;
						
			}
			if(areEqual(p, u2, false)){
				foundU2 = true;
			}
		}
		assertEquals(foundU1, true);
		assertEquals(foundU2, true);
	}

	@Test
	public void testUpdateUser() throws Exception{
		//add them to the database
		UserManager.addUser(u1);
		UserManager.addUser(u2);
				
		db.startTransaction();
		List<User> all = userDAO.getAll();
		db.endTransaction(true);
		assertEquals(all.size(), 2);
				
		//change the users
		u1.setUsername("Johnny");
		u1.setPassword("Bravo");
		u1.setFirstName("Jhonny");
		u1.setLastName("Brahvo");
		u1.setEmail("jb@cartoonnetwork.com");
		u1.setIndexedRecords(1);
		
		u2.setUsername("Veloci");
		u2.setPassword("Raptor");
		u2.setFirstName("Jurassic");
		u2.setLastName("Park");
		u2.setEmail("prepare_to@die.com");
		u2.setIndexedRecords(12312);
				
		//update them on the database
		UserManager.updateUser(u1);
		UserManager.updateUser(u2);
			
		//make sure the users were actually updated
		db.startTransaction();
		all = userDAO.getAll();
		db.endTransaction(true);
		assertEquals(all.size(), 2);
				
		boolean foundU1 = false;
		boolean foundU2 = false;
				
		for(User p : all){
			if(areEqual(p, u1, false)){
				foundU1 = true;
						
			}
			if(areEqual(p, u2, false)){
				foundU2 = true;
			}
		}
		assertEquals(foundU1, true);
		assertEquals(foundU2, true);
	}

	@Test
	public void testDeleteUser() throws Exception{				
		//add them to the database
		UserManager.addUser(u1);
		UserManager.addUser(u2);
				
		//make sure that both have actually been added to the database
		db.startTransaction();
		List<User> all = userDAO.getAll();
		db.endTransaction(true);
		assertEquals(all.size(), 2);
				
		//delete one from the database and make sure it's actually deleted
		UserManager.deleteUser(u1);
		db.startTransaction();
		all = userDAO.getAll();
		db.endTransaction(true);
		assertEquals(all.size(), 1);
				
		UserManager.deleteUser(u2);
		db.startTransaction();
		all = userDAO.getAll();
		db.endTransaction(true);
		assertEquals(all.size(), 0);
	}

	@Test
	public void testClearUsers() throws Exception{
		//add them to the database
		UserManager.addUser(u1);
		UserManager.addUser(u2);
		
		UserManager.clearUsers();
		
		db.startTransaction();
		List<User> all = userDAO.getAll();
		db.endTransaction(true);
		assertEquals(all.size(), 0);
	}
	
	private boolean areEqual(User a, User b, boolean compareIDs){
		if(compareIDs){
			if(a.getUserID() != b.getUserID())
				return false;
		}
		return(safeEquals(a.getUsername(), b.getUsername()) &&
				safeEquals(a.getPassword(), b.getPassword()) &&
				safeEquals(a.getFirstName(), b.getFirstName()) &&
				safeEquals(a.getLastName(), b.getLastName()) &&
				safeEquals(a.getEmail(), b.getEmail()) &&
				safeEquals(a.getIndexedRecords(), b.getIndexedRecords()));
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
