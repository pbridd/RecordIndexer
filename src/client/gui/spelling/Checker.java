package client.gui.spelling;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

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

	public void testDriver() throws NoSimilarWordFoundException{
		System.out.print(currDict.toString());
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
