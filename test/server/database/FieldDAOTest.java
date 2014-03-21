package server.database;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.model.Field;

public class FieldDAOTest {
	private Database db;
	private FieldDAO fieldDAO;

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
		
		List<Field> fields = db.getFieldDAO().getAll();
		for(Field b : fields)
			db.getFieldDAO().delete(b);
		db.endTransaction(true);
		
		//Prepare database for test case
		db = new Database();
		db.startTransaction();
		fieldDAO = db.getFieldDAO();
	}

	@After
	public void tearDown() throws Exception {
		//Roll back transaction so changes to the database are undone
		db.endTransaction(false);
		db = null;
		fieldDAO = null;
	}

	@Test
	public void testGetAll() throws Exception{
		Field f1 = new Field(-1, 3, "glasses", 20, 30, "/apple/guns.html", "/orange/fields.xml", 6);
		Field f2 = new Field(-1, 6, "teeth", 10, 35, "/chuck/norris.html", "", 2);
		
		fieldDAO.add(f1);
		fieldDAO.add(f2);
		
		List<Field> all = fieldDAO.getAll();
		assertEquals(2, all.size());
		boolean foundF1 = false;
		boolean foundF2 = false;
		
		for(Field f : all){
			assertFalse(f.getFieldID() == -1);
			
			if(!foundF1){
				foundF1 = areEqual(f, f1, false);
			}
			if(!foundF2){
				foundF2 = areEqual(f, f2, false);
			}
		}
		assertTrue(foundF1 && foundF2);
		
	}
	
	@Test
	public void testGetFields() throws Exception{
		Field f1 = new Field(-1, 3, "glasses", 20, 30, "/apple/guns.html", "/orange/fields.xml", 6);
		Field f2 = new Field(-1, 6, "teeth", 10, 35, "/chuck/norris.html", "", 2);
		
		fieldDAO.add(f1);
		fieldDAO.add(f2);

		//test the function to see if it returns everything with a negative parameter
		//passed in
		List<Field> all = fieldDAO.getFields(-1);
		assertEquals(2, all.size());
		boolean foundF1 = false;
		boolean foundF2 = false;
		
		for(Field f : all){
			assertFalse(f.getFieldID() == -1);
			
			if(!foundF1){
				foundF1 = areEqual(f, f1, false);
			}
			if(!foundF2){
				foundF2 = areEqual(f, f2, false);
			}
		}
		assertTrue(foundF1 && foundF2);
		
		//double check that passing in project 6 will return the first field
		List<Field> proj6 =fieldDAO.getFields(6);
		assertEquals(1, proj6.size());
		assertEquals(true, areEqual(f1, proj6.get(0), false));
		
		//double check that passing in project 2 will return the second field
		List<Field> proj2 =fieldDAO.getFields(2);
		assertEquals(1, proj2.size());
		assertEquals(true, areEqual(f2, proj2.get(0), false));
		
		
	}
	
	@Test
	public void testAdd() throws Exception{
		Field f1 = new Field(-1, 3, "glasses", 20, 30, "/apple/guns.html", "/orange/fields.xml", 6);
		Field f2 = new Field(-1, 6, "teeth", 10, 35, "/chuck/norris.html", "", 2);
		
		fieldDAO.add(f1);
		fieldDAO.add(f2);
		
		List<Field> all = fieldDAO.getAll();
		assertEquals(2, all.size());
		boolean foundF1 = false;
		boolean foundF2 = false;
		
		for(Field f : all){
			assertFalse(f.getFieldID() == -1);
			
			if(!foundF1){
				foundF1 = areEqual(f, f1, false);
			}
			if(!foundF2){
				foundF2 = areEqual(f, f2, false);
			}
		}
		assertTrue(foundF1 && foundF2);
	}
	
	@Test
	public void testUpdate() throws Exception{
		Field f1 = new Field(-1, 3, "glasses", 20, 30, "/apple/guns.html", "/orange/fields.xml", 6);
		Field f2 = new Field(-1, 6, "teeth", 10, 35, "/chuck/norris.html", "", 2);
		
		fieldDAO.add(f1);
		fieldDAO.add(f2);
		
		List<Field> all = fieldDAO.getAll();
		assertEquals(2, all.size());
		
		f1.setFieldNumber(2);
		f1.setFieldName("death");
		f1.setXCoor(556);
		f1.setWidth(435);
		f1.setHelpHTML("asd");
		f1.setKnownData("");
		f1.setProjectID(1);
		
		f2.setFieldNumber(3);
		f2.setFieldName("awefhu");
		f2.setXCoor(234);
		f2.setWidth(123);
		f2.setHelpHTML("nkacjls");
		f2.setKnownData("dasfjll");
		f2.setProjectID(9);
		
		fieldDAO.update(f1);
		fieldDAO.update(f2);
		
		all = fieldDAO.getAll();
		assertEquals(2, all.size());
		boolean foundF1 = false;
		boolean foundF2 = false;
		
		for(Field f : all){
			assertFalse(f.getFieldID() == -1);
			
			if(!foundF1){
				foundF1 = areEqual(f, f1, false);
			}
			if(!foundF2){
				foundF2 = areEqual(f, f2, false);
			}
		}
		assertTrue(foundF1 && foundF2);
	}
	
	@Test
	public void testDelete() throws Exception{
		Field f1 = new Field(-1, 3, "glasses", 20, 30, "/apple/guns.html", "/orange/fields.xml", 6);
		Field f2 = new Field(-1, 6, "teeth", 10, 35, "/chuck/norris.html", "", 2);
		
		fieldDAO.add(f1);
		fieldDAO.add(f2);
		
		List<Field> all = fieldDAO.getAll();
		assertEquals(2, all.size());
		
		fieldDAO.delete(f1);
		all = fieldDAO.getAll();
		assertEquals(1, all.size());
		fieldDAO.delete(f2);
		all = fieldDAO.getAll();
		assertEquals(0, all.size());
	}
	
	private boolean areEqual(Field a, Field b, boolean compareIDs){
		if(compareIDs){
			if(a.getFieldID() != b.getFieldID())
				return false;
		}
		return(safeEquals(a.getFieldNumber(), b.getFieldNumber()) &&
				safeEquals(a.getFieldName(), b.getFieldName()) &&
				safeEquals(a.getHelpHTML(),b.getHelpHTML()) &&
				safeEquals(a.getKnownData(),b.getKnownData()) &&
				safeEquals(a.getProjectID(), b.getProjectID()) &&
				safeEquals(a.getWidth(), b.getWidth()) &&
				safeEquals(a.getXCoord(), b.getXCoord()));
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
