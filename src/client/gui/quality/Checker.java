package client.gui.quality;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Checker implements SpellCorrector {
	Dictionary currDict;
	
	public Checker(){
		
	}

	@Override
	public void useDictionary(String dictionaryFileName) throws IOException {
		currDict = new Dictionary();
		File srcFile = new File(dictionaryFileName);
		Scanner scan = new Scanner(srcFile);
		//loop until there aren't any more words in the file
		while(scan.hasNext()){
			currDict.add(scan.next());
		}
		
		scan.close();
	}
	

	public void useDictionary(URL dictionaryFileURL) throws IOException {
		currDict = new Dictionary();
		
		//get the reader
		Scanner scan = new Scanner(
				new InputStreamReader(dictionaryFileURL.openStream()));

		//read the file in question

        while (scan.hasNextLine()){
        	String nextLn = scan.nextLine();
        	List<String> knowndatas = Arrays.asList(nextLn.split(","));
        	for(String a: knowndatas){
        		String inputLine = a.replaceAll("[^a-zA-Z0-9\\s]", "");
        		if(inputLine.length() != 0)
        			//make sure everything is in lower case and trimmed
        			currDict.add(inputLine.toLowerCase().trim());
        	}
        }
        scan.close();
    }
		
	
	/**
	 * Returns true if the word exists, false if it's not found
	 * @param word The word to check
	 * @return True if the word exists, false if it's not found
	 */
	public boolean existsInDict(String word){
		String lowerCaseWord = word.toLowerCase().trim();
	
		if(lowerCaseWord.length() == 0){
			return true;
		}
		if(currDict.find(lowerCaseWord) == null){
				return false;
		}
		
		return true;
	}

	@Override
	public String suggestSimilarWord(String inputWord)
			throws NoSimilarWordFoundException {
		
		
		//go through the base cases--if there's an empty string, throw nosimilarwordfound
		if(inputWord.length() == 0)
			throw new NoSimilarWordFoundException();
		
		String lowerCaseWord = inputWord.toLowerCase();
		if(currDict.find(lowerCaseWord) != null){
			return lowerCaseWord;
		}
		
		int greatestVal = 0;
		
		//go through the list the first time for edit distance 1
		LinkedList<DictionaryNode> sugs = new LinkedList<DictionaryNode>();
		LinkedList<String> ufList1 = new LinkedList<String>();
		greatestVal = currDict.getInsSugs(lowerCaseWord, sugs, ufList1, greatestVal);
		greatestVal = currDict.getDelSugs(lowerCaseWord, sugs, ufList1, greatestVal);
		greatestVal = currDict.getAltSugs(lowerCaseWord, sugs, ufList1, greatestVal);
		greatestVal = currDict.getTransSugs(lowerCaseWord,  sugs, ufList1, greatestVal);
		//if something has been found, return it!
		if(sugs.size() != 0){
			//if we've found something, go through the list and return the found value
			DictionaryNode highestNode = sugs.get(0);
			for(DictionaryNode a : sugs){
				if(a.getNodeStr().compareTo(highestNode.getNodeStr()) < 0){
					highestNode = a;
				}
			}
			
			return highestNode.getNodeStr();
		}
		else{
			//if not, go through the unfiltered suggestion list for edit distance 2
			for(String str : ufList1){
				LinkedList<String> ufList2 = new LinkedList<String>();
				greatestVal = currDict.getInsSugs(str, sugs, ufList2, greatestVal);
				greatestVal = currDict.getDelSugs(str, sugs, ufList2, greatestVal);
				greatestVal = currDict.getAltSugs(str, sugs, ufList2, greatestVal);
				greatestVal = currDict.getTransSugs(str,  sugs, ufList2, greatestVal);
			}
			//if we still haven't found anything, cry then throw a NoSimilarWordFoundException
			if(sugs.size() == 0){
				throw new NoSimilarWordFoundException();
			}
			//return the node with the highest frequency that we've been able to find
			DictionaryNode highestNode = sugs.get(0);
			for(DictionaryNode a : sugs){
				if(a.getNodeStr().compareTo(highestNode.getNodeStr()) < 0){
					highestNode = a;
				}
			}
			
			return highestNode.getNodeStr();
		}
		
		
	}
	
	public List<String> suggestSimilarWords(String inputWord)
			throws NoSimilarWordFoundException {
		//go through the base cases--if there's an empty string, throw nosimilarwordfound
		if(inputWord.length() == 0)
			throw new NoSimilarWordFoundException();
		
		String lowerCaseWord = inputWord.toLowerCase();
		if(currDict.find(lowerCaseWord) != null){
			return null;
		}
		
		int greatestVal = 0;
		
		//go through the list the first time for edit distance 1
		LinkedList<DictionaryNode> sugs = new LinkedList<DictionaryNode>();
		LinkedList<String> ufList1 = new LinkedList<String>();
		greatestVal = currDict.getInsSugs(lowerCaseWord, sugs, ufList1, greatestVal);
		greatestVal = currDict.getDelSugs(lowerCaseWord, sugs, ufList1, greatestVal);
		greatestVal = currDict.getAltSugs(lowerCaseWord, sugs, ufList1, greatestVal);
		greatestVal = currDict.getTransSugs(lowerCaseWord,  sugs, ufList1, greatestVal);
		//if something has been found, return it!
		
			
		//go through again for the second edit distance
		for(String str : ufList1){
			LinkedList<String> ufList2 = new LinkedList<String>();
			greatestVal = currDict.getInsSugs(str, sugs, ufList2, greatestVal);
			greatestVal = currDict.getDelSugs(str, sugs, ufList2, greatestVal);
			greatestVal = currDict.getAltSugs(str, sugs, ufList2, greatestVal);
			greatestVal = currDict.getTransSugs(str,  sugs, ufList2, greatestVal);
		}
		//if we still haven't found anything, cry then throw a NoSimilarWordFoundException
		if(sugs.size() == 0){
			throw new NoSimilarWordFoundException();
		}
		//return the node with the highest frequency that we've been able to find
		
		Set<String> retSet = new TreeSet<String>();
		
		for(DictionaryNode dn : sugs){
			retSet.add(dn.getNodeStr());
		}
		
		List<String> retList = new ArrayList<String>(retSet);
		
		return retList;
		
		
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currDict == null) ? 0 : currDict.hashCode());
		return result;
	}
	
	@Override
	public String toString(){
		return currDict.toString();
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Checker))
			return false;
		Checker other = (Checker) obj;
		if (currDict == null) {
			if (other.currDict != null)
				return false;
		} else if (!currDict.equals(other.currDict))
			return false;
		return true;
	}
}
