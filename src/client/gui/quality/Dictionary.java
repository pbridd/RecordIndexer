package client.gui.quality;

import java.util.LinkedList;

public class Dictionary implements Trie {

	private DictionaryNode baseNode;
	private int nodeCount;
	private int wordCount;
	private String firstWord;
	
	public Dictionary(){
		baseNode = new DictionaryNode();
		nodeCount = 1;
		wordCount = 0;
	}

	
	
	//adds the string it is passed in to the Trie
	@Override
	public void add(String word) {
		char[] str = word.toCharArray();
		addHelper(word, str, 0, baseNode);
	}
	
	private void addHelper(String word, char[] str, int currentIdx, DictionaryNode currNode){
		if(currNode == null){
			return;
		}
		if(!currNode.existsNode(str[currentIdx])){
			nodeCount ++;
		}
		
		//create a new dictionary node based on where we are right now
		String nodeStr = word.substring(0, currentIdx+1);
		DictionaryNode nextNode = currNode.createNode(nodeStr, str[currentIdx], 0);
		
		//check to see if this is the last char of the word
		if(str.length - 1 == currentIdx){
			if(nextNode.getValue() == 0){
				//get the first word of the Trie for hashing reasons
				if(wordCount == 0){
					firstWord = nodeStr;
				}
				wordCount++;
			}
			nextNode.incrementValue();
			return;
		}
		
		//if not, continue making the nodes
		currentIdx++;
		addHelper(word, str, currentIdx, nextNode);
	}

	@Override
	public Node find(String word) {
		//start the recursive find
		if(word.charAt(0) == ' '){
			return findHelper(word, 0, baseNode.getNode(26));
		}
		else if(word.charAt(0) < 97 || word.charAt(0) > 122){
			return null;
		}
		return findHelper(word, 0, baseNode.getNode(word.charAt(0) - 97));
	}
	
	//helper for the find function--finishes when the word is found in the struct
	private  DictionaryNode findHelper(String word, int idx, DictionaryNode currNode){
		//if we're no longer on an instantiated node, return null--the default
		//false value
		if(currNode == null){
			return null;
		}
		
		if(idx == word.length() - 1){
			//check to see if this is actually a found word
			//or just a partial word in the dictionary
			if(currNode.getValue() > 0){
				return currNode;
			}
			return null;
		}
		
		//get char offset so we can get the next node
		char currChar = word.charAt(idx + 1);
		if((currChar < 97 || currChar > 122) && currChar != ' ')
			return null;
		
		int offsetChar = currChar - 97;
		if(currChar == ' '){
			offsetChar = 26;
		}
		
		
		DictionaryNode nextNode = currNode.getNode(offsetChar);
		
		return findHelper(word, idx+1, nextNode);
		
	}
	
	
	//gets suggestions for any word with an extra character in it
	public int getInsSugs(String word, LinkedList<DictionaryNode> fList, LinkedList<String> ufList, int greatestValue){
		for(int i = 0; i < word.length(); i++){
			StringBuilder tempBdr = new StringBuilder(word);
			tempBdr.deleteCharAt(i);
			String tempStr = tempBdr.toString();
			
			ufList.add(tempStr);
			
			//see if this string is found in the current dictionary
			Node found = null;
			if(tempStr.length() > 0){
				found = this.find(tempStr);
			}
			
			if(found != null){	
				//filter the nodes so only the ones with the greatest value
				//are in the list
				if(found.getValue() == greatestValue){
					fList.add((DictionaryNode)found);
				}
				else if(found.getValue() > greatestValue){
					greatestValue = found.getValue();
					fList.clear();
					fList.add((DictionaryNode)found);
				}
			}
		}
		return greatestValue;
	}
	
	public int getDelSugs(String word, LinkedList<DictionaryNode> fList, LinkedList<String> ufList, int greatestValue)
	{
		for(int i = 0; i <= word.length(); i++){
			for(int j = 97; j < 124; j++){
				char ch =(char)j;
				if(j == 123){
					ch = ' ';
				}
				
				String tempStr;
				tempStr = new StringBuffer(word).insert(i, ch).toString();
				ufList.add(tempStr);
				//see if this string is found in the current dictionary
				Node found = this.find(tempStr);
			
				if(found != null){	
					//filter the nodes so only the ones with the greatest value
					//are in the list
					if(found.getValue() == greatestValue){
						fList.add((DictionaryNode)found);
					}
					else if(found.getValue() > greatestValue){
						greatestValue = found.getValue();
						fList.clear();
						fList.add((DictionaryNode)found);
					}
				}
			}
		}
		return greatestValue;
	}

	public int getAltSugs(String word, LinkedList<DictionaryNode> fList, LinkedList<String> ufList, int greatestValue){
		for(int i = 0; i < word.length(); i++){
			for(int j = 97; j < 124; j++){
				char ch =(char)j;
				if(j == 123){
					ch = ' ';
				}
				String tempStr;
				StringBuilder strBT = new StringBuilder(word);
				strBT.setCharAt(i, ch);
				tempStr = strBT.toString();
				ufList.add(tempStr);
				//see if this string is found in the current dictionary
				Node found = this.find(tempStr);
			
				if(found != null){	
					//filter the nodes so only the ones with the greatest value
					//are in the list
					if(found.getValue() == greatestValue){
						fList.add((DictionaryNode)found);
					}
					else if(found.getValue() > greatestValue){
						greatestValue = found.getValue();
						fList.clear();
						fList.add((DictionaryNode)found);
					}
				}
			}
		}
		return greatestValue;
	}
	
	public int getTransSugs(String word, LinkedList<DictionaryNode> fList, LinkedList<String> ufList, int greatestValue){
		for(int i = 0; i < word.length(); i++){
			if(i+1 == word.length()){
				break;
			}
			StringBuilder tempBdr = new StringBuilder(word);
			char firstChar = word.charAt(i);
			char secondChar = word.charAt(i+1);
			tempBdr.setCharAt(i, secondChar);
			tempBdr.setCharAt(i+1, firstChar);
			String tempStr = tempBdr.toString();
			ufList.add(tempStr);
			//see if this string is found in the current dictionary
			Node found = this.find(tempStr);
			
			if(found != null){	
				//filter the nodes so only the ones with the greatest value
				//are in the list
				if(found.getValue() == greatestValue){
					fList.add((DictionaryNode)found);
				}
				else if(found.getValue() > greatestValue){
					greatestValue = found.getValue();
					fList.clear();
					fList.add((DictionaryNode)found);
				}
			}
		}
		return greatestValue;
	}
 	
	//getters and setters
	@Override
	public int getWordCount() {
		return wordCount;
	}

	@Override
	public int getNodeCount() {
		return nodeCount;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstWord == null) ? 0 : firstWord.hashCode());
		result = prime * result + nodeCount;
		result = prime * result + wordCount;
		return result;
	}

	@Override
	public String toString() {
		StringBuilder tostrB = new StringBuilder();
		for(int i=0; i < 25; i++){
			if(baseNode.existsNode((char)(i+97))){
				toStringHelper(tostrB, baseNode.getNode(i));
			}
		}
		return tostrB.toString();
	}
	
	private void toStringHelper(StringBuilder tostrB, DictionaryNode curr){
		if(curr.getValue() > 0){
			tostrB.append(curr.getNodeStr() + " " + curr.getValue() + "\n");
		}
		for(int i=0; i < 25; i++){
			if(curr.existsNode((char)(i+97))){
				
				toStringHelper(tostrB, curr.getNode(i));
			}
		}
		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Dictionary))
			return false;
		Dictionary other = (Dictionary) obj;
		if (firstWord == null) {
			if (other.firstWord != null)
				return false;
		} else if (!firstWord.equals(other.firstWord))
			return false;
		if (nodeCount != other.nodeCount)
			return false;
		if (wordCount != other.wordCount)
			return false;
		if(! this.toString().equals(other.toString()))
			return false;
		return true;
	}
	

	
}
