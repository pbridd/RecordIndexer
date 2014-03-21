package shared.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.Database;
import server.database.FieldDAO;

public class FieldManagerTest {
	Database db;
	FieldDAO fieldDAO;
	Field f1;
	Field f2;

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
		//add sample batches to the database
		db = new Database();
		fieldDAO = db.getFieldDAO();		
		//setup test batches
		f1 = new Field(-1, 3, "glasses", 20, 30, "/apple/guns.html", "/orange/fields.xml", 6);
		f2 = new Field(-1, 6, "teeth", 10, 35, "/chuck/norris.html", "", 2);
		
		//set up BatchManager
		FieldManager.initialize();
	}

	@After
	public void tearDown() throws Exception {
		//clear out database
		db = new Database();
		CommandProcessor.clearDatabase();
		db = null;
		fieldDAO = null;
	}

	@Test
	public void testGetFields() throws ModelException{
		//add the fields
		FieldManager.addField(f1);
		FieldManager.addField(f2);
		
		
		
		List<Field> fields1 = FieldManager.getFields(6);
		List<Field> fields2 = FieldManager.getFields(2);
		
		assertEquals(fields1.size(), 1);
		assertEquals(fields2.size(), 1);
		
		assertEquals(areEqual(fields1.get(0), f1, true), true);
		assertEquals(areEqual(fields2.get(0), f2, true), true);
	}

	@Test
	public void testAddField() throws Exception{
		//make sure the add field function works
		FieldManager.addField(f1);
		FieldManager.addField(f2);
		
		List<Field> fields1 = FieldManager.getFields(6);
		List<Field> fields2 = FieldManager.getFields(2);
		
		assertEquals(fields1.size(), 1);
		assertEquals(fields2.size(), 1);
		
		assertEquals(areEqual(fields1.get(0), f1, true), true);
		assertEquals(areEqual(fields2.get(0), f2, true), true);
	}

	@Test
	public void testUpdateField() throws Exception{
		FieldManager.addField(f1);
		FieldManager.addField(f2);
		
		
		
		f1.setFieldNumber(2);
		f1.setFieldName("death");
		f1.setXCoor(556);
		f1.setWidth(435);
		f1.setHelpHTML("asd");
		f1.setKnownData("");
		f1.setProjectID(16);
		
		f2.setFieldNumber(3);
		f2.setFieldName("awefhu");
		f2.setXCoor(234);
		f2.setWidth(123);
		f2.setHelpHTML("nkacjls");
		f2.setKnownData("dasfjll");
		f2.setProjectID(9);
		
		FieldManager.updateField(f1);
		FieldManager.updateField(f2);
		
		List<Field> fields1 = FieldManager.getFields(16);
		List<Field> fields2 = FieldManager.getFields(9);
		
		assertEquals(fields1.size(), 1);
		assertEquals(fields2.size(), 1);
		
		assertEquals(areEqual(fields1.get(0), f1, true), true);
		assertEquals(areEqual(fields2.get(0), f2, true), true);
		
	}

	@Test
	public void testDeleteField() throws Exception {
		//make sure the add field function works
		FieldManager.addField(f1);
		FieldManager.addField(f2);
		
		FieldManager.deleteField(f1);
		//get all of the fields
		db.startTransaction();
		List<Field> all = fieldDAO.getAll();
		db.endTransaction(false);
		assertEquals(all.size(), 1);
		FieldManager.deleteField(f2);	
		//get all of the fields
		db.startTransaction();
		all = fieldDAO.getAll();
		db.endTransaction(false);
		assertEquals(all.size(), 0);
		
	}

	@Test
	public void testClearFields() throws Exception{
		//make sure the add field function works
		FieldManager.addField(f1);
		FieldManager.addField(f2);
		
		FieldManager.clearFields();
		
		//get all of the fields
		db.startTransaction();
		List<Field> all = fieldDAO.getAll();
		db.endTransaction(false);
		
		if(all.size() != 0){
			fail("After attempting to delete all fields in the database, it a call to "
					+ "getAll returned something other than null");
		}
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
