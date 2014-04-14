package client.gui.quality;


import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CheckerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		for(int i = 1; i < 6; i++){
			String testPath = "data/testData/CheckerTD/test" + i + ".txt";
			String testNewPath = "data/knowndata/test" + i + ".txt";
			copyFile(testPath, testNewPath);
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		for(int i = 1; i < 6; i++){
			try{
				Files.deleteIfExists(Paths.get("data/knowndata/test" + i + ".txt"));
			}
			catch(IOException ex){
				System.out.println("Could not delete the old path file!");
				ex.printStackTrace();
			}
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test1() throws Exception{
		Checker check = new Checker();
		
		String kDataURL = "http://localhost:8080/knowndata/test1.txt";

			
		try{
			URL kDataFormedURL = new URL(kDataURL);
			check.useDictionary(kDataFormedURL);
		}
		catch(IOException e){
			System.out.println("Could not open the knownData URL\n"+e.getMessage());
			e.printStackTrace();
		}
		
		boolean passed = check.existsInDict("Goodman");
		assertTrue(passed);
		passed = check.existsInDict("Gooodman");
		assertTrue(!passed);
		
		List<String> sugList = check.suggestSimilarWords("Gooodman");
		List<String> expected = new ArrayList<String>();
		expected.add("goodman");
		assertTrue(expected.size() == sugList.size());
		for(int i = 0; i < sugList.size(); i++){
			assertTrue(expected.get(i).equals(sugList.get(i)));
		}
		
		sugList = check.suggestSimilarWords("gooodnan");
		assertTrue(expected.size() == sugList.size());
		for(int i = 0; i < sugList.size(); i++){
			assertTrue(expected.get(i).equals(sugList.get(i)));
		}
		
		
	}

	@Test
	public void test2() throws Exception{
		Checker check = new Checker();
		
		String kDataURL = "http://localhost:8080/knowndata/test2.txt";

			
		try{
			URL kDataFormedURL = new URL(kDataURL);
			check.useDictionary(kDataFormedURL);
		}
		catch(IOException e){
			System.out.println("Could not open the knownData URL\n"+e.getMessage());
			e.printStackTrace();
		}
		
		boolean passed = check.existsInDict("Hello");
		assertTrue(passed);
		passed = check.existsInDict("h ello@234");
		assertTrue(!passed);
		
		List<String> sugList = check.suggestSimilarWords("heello");
		List<String> expected = new ArrayList<String>();
		expected.add("hello");
		assertTrue(expected.size() == sugList.size());
		for(int i = 0; i < sugList.size(); i++){
			assertTrue(expected.get(i).equals(sugList.get(i)));
		}
		
		sugList = check.suggestSimilarWords("heelo");
		assertTrue(expected.size() == sugList.size());
		for(int i = 0; i < sugList.size(); i++){
			assertTrue(expected.get(i).equals(sugList.get(i)));
		}
	}
	
	@Test
	public void test3() throws Exception{
		Checker check = new Checker();
		
		String kDataURL = "http://localhost:8080/knowndata/test3.txt";

			
		try{
			URL kDataFormedURL = new URL(kDataURL);
			check.useDictionary(kDataFormedURL);
		}
		catch(IOException e){
			System.out.println("Could not open the knownData URL\n"+e.getMessage());
			e.printStackTrace();
		}
		
		boolean passed = check.existsInDict("Received");
		assertTrue(passed);
		passed = check.existsInDict("Testimony");
		assertTrue(passed);
		passed = check.existsInDict("Reception");
		assertTrue(passed);
		passed = check.existsInDict("Redmond");
		assertTrue(passed);
		passed = check.existsInDict("Redmoond");
		assertTrue(!passed);
		
		List<String> sugList = check.suggestSimilarWords("Reedmond");
		List<String> expected = new ArrayList<String>();
		expected.add("redmond");
		assertTrue(expected.size() == sugList.size());
		for(int i = 0; i < sugList.size(); i++){
			assertTrue(expected.get(i).equals(sugList.get(i)));
		}
		
		sugList = check.suggestSimilarWords("Reziived");
		expected.clear();
		expected.add("received");
		assertTrue(expected.size() == sugList.size());
		for(int i = 0; i < sugList.size(); i++){
			assertTrue(expected.get(i).equals(sugList.get(i)));
		}
	}
	
	@Test
	public void test4() throws Exception{
		Checker check = new Checker();
		
		String kDataURL = "http://localhost:8080/knowndata/test4.txt";

			
		try{
			URL kDataFormedURL = new URL(kDataURL);
			check.useDictionary(kDataFormedURL);
		}
		catch(IOException e){
			System.out.println("Could not open the knownData URL\n"+e.getMessage());
			e.printStackTrace();
		}
		
		boolean passed = check.existsInDict("Jack");
		assertTrue(passed);
		passed = check.existsInDict("Bandit");
		assertTrue(passed);
		passed = check.existsInDict("tex");
		assertTrue(passed);
		passed = check.existsInDict("tex cat");
		assertTrue(passed);
		passed = check.existsInDict("asdljkfhasero");
		assertTrue(!passed);
		
		List<String> sugList = check.suggestSimilarWords("baandit");
		List<String> expected = new ArrayList<String>();
		expected.add("bandit");
		assertTrue(expected.size() == sugList.size());
		for(int i = 0; i < sugList.size(); i++){
			assertTrue(expected.get(i).equals(sugList.get(i)));
		}
		
		sugList = check.suggestSimilarWords("texlcat");
		expected.clear();
		expected.add("tex cat");
		assertTrue(expected.size() == sugList.size());
		for(int i = 0; i < sugList.size(); i++){
			assertTrue(expected.get(i).equals(sugList.get(i)));
		}
	}
	
	@Test
	public void test5() throws Exception{
		Checker check = new Checker();
		
		String kDataURL = "http://localhost:8080/knowndata/test5.txt";

			
		try{
			URL kDataFormedURL = new URL(kDataURL);
			check.useDictionary(kDataFormedURL);
		}
		catch(IOException e){
			System.out.println("Could not open the knownData URL\n"+e.getMessage());
			e.printStackTrace();
		}
		
		boolean passed = check.existsInDict("hello");
		assertTrue(passed);
		passed = check.existsInDict("yellow");
		assertTrue(passed);
		passed = check.existsInDict("jello");
		assertTrue(passed);
		passed = check.existsInDict("fellow");
		assertTrue(!passed);
		
		List<String> sugList = check.suggestSimilarWords("fello");
		List<String> expected = new ArrayList<String>();
		expected.add("hello");
		expected.add("jello");
		expected.add("yellow");
		assertTrue(expected.size() == sugList.size());
		for(int i = 0; i < sugList.size(); i++){
			assertTrue(expected.get(i).equals(sugList.get(i)));
		}
	}
	
	
	public static void copyFile(String path1, String path2) throws IOException{
		Path FROM = Paths.get(path1);
	    Path TO = Paths.get(path2);
	    
	    //overwrite existing file, if exists
	    CopyOption[] options = new CopyOption[]{
	      StandardCopyOption.REPLACE_EXISTING,
	      StandardCopyOption.COPY_ATTRIBUTES
	    }; 
	    Files.copy(FROM, TO, options);
	}
}
