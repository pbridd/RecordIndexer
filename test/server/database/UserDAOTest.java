package server.database;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.model.User;

public class UserDAOTest {
	Database db;
	UserDAO userDAO;

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
		
		List<User> users = db.getUserDAO().getAll();
		for(User b : users)
			db.getUserDAO().delete(b);
		db.endTransaction(true);
		
		//Prepare database for test case
		db = new Database();
		db.startTransaction();
		userDAO = db.getUserDAO();
	}

	@After
	public void tearDown() throws Exception {
		db.endTransaction(false);
		db=null;
		userDAO=null;
	}

	@Test
	public void testValidate() throws Exception{
		User u1 = new User(-1, "hello", "kitty", "james", "potter", "g@potter.com", 
				1000);
		User u2 = new User(-1, "over", "9000", "dragonball", "z", "over@9000.com", 
				9001);
		
		userDAO.add(u1);
		userDAO.add(u2);
		
		 List<User> all = userDAO.getAll();
		 assertEquals(all.size(), 2);
		 
		 //test some cases that work
		 User testUsr1 = userDAO.validate(u1.getUsername(), u1.getPassword());
		 User testUsr2 = userDAO.validate(u2.getUsername(), u2.getPassword());
		 assertEquals(areEqual(u1, testUsr1, true), true);
		 assertEquals(areEqual(u2, testUsr2, true), true);
		
		 
		 //test a case that doesn't
		 User testUsr3 = userDAO.validate("FAKE", "USER");
		 if(testUsr3 != null){
			 fail("A user was returned when null should have been returned");
		 }
				 
		 
		
	}

	@Test
	public void testGetAll() throws DatabaseException{
		//Create the records
		User u1 = new User(-1, "hello", "kitty", "james", "potter", "g@potter.com", 
				1000);
		User u2 = new User(-1, "over", "9000", "dragonball", "z", "over@9000.com", 
				9001);
		
		userDAO.add(u1);
		userDAO.add(u2);
		
		//check to see that the users were added
		List<User> all = userDAO.getAll();
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
	public void testAdd() throws Exception{
		//Create the users
		User u1 = new User(-1, "hello", "kitty", "james", "potter", "g@potter.com", 
				1000);
		User u2 = new User(-1, "over", "9000", "dragonball", "z", "over@9000.com", 
				9001);
				
		userDAO.add(u1);
		userDAO.add(u2);
				
		//check to see that the users were added
		List<User> all = userDAO.getAll();
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
	public void testUpdate() throws Exception{
		//Create the users
		User u1 = new User(-1, "hello", "kitty", "james", "potter", "g@potter.com", 
				1000);
		User u2 = new User(-1, "over", "9000", "dragonball", "z", "over@9000.com", 
				9001);
								
		//add them to the database
		userDAO.add(u1);
		userDAO.add(u2);
				
		List<User> all = userDAO.getAll();
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
		userDAO.update(u1);
		userDAO.update(u2);
			
		//make sure the users were actually updated
		
		all = userDAO.getAll();
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
	public void testDelete() throws Exception{
		//Create the users
		User u1 = new User(-1, "hello", "kitty", "james", "potter", "g@potter.com", 
				1000);
		User u2 = new User(-1, "over", "9000", "dragonball", "z", "over@9000.com", 
				9001);
				
		//add them to the database
		userDAO.add(u1);
		userDAO.add(u2);
				
		//make sure that both have actually been added to the database
		List<User> all = userDAO.getAll();
		assertEquals(all.size(), 2);
				
		//delete one from the database and make sure it's actually deleted
		userDAO.delete(u1);
		all = userDAO.getAll();
		assertEquals(all.size(), 1);
				
		userDAO.delete(u2);
		all = userDAO.getAll();
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
