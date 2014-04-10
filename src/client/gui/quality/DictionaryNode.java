package client.gui.quality;

import java.util.Arrays;

import client.gui.quality.Trie.Node;

public class DictionaryNode implements Node {
	private String nodeStr;
	private char nodeChar;
	private int value;
	private DictionaryNode[] subNodes;
	
	
	public DictionaryNode(){
		this("0", '0', 0);
	}

	public DictionaryNode(String str, char ctd, int val) {
		subNodes = new DictionaryNode[27];
		value = val;
		nodeChar = ctd;
		nodeStr = str;
	}
	
	//increment frequency value
	public void incrementValue(){
		value++;
	}
	
	
	
	
	//getter and setter methods
	//returns the node at index i, but doesn't check if it is null.
	public DictionaryNode getNode(int i){
		return subNodes[i];
	}
	
	public boolean existsNode(char ch){
		if(ch == ' '){
			if(subNodes[26] == null)
				return false;
			return true;
		}
		if((ch - 97) < 0)
			return true;
		if(subNodes[ch - 97] == null){
			return false;
		}
		return true;
	}
	//creates a node in the array of this node, and then returns the new one
	//CHECKS if the node is already created, and if it is, simply returns the node
	//without creating a new one.
	public DictionaryNode createNode(String str, char a, int val){
	//make sure the character passed is valid
		if((a < 97 || a > 122) && a != ' ')
			return null;
		if(a == ' '){
			if(subNodes[26] == null){
				subNodes[26] = new DictionaryNode(str, a, val);
			}
			return subNodes[26];
		}
		else{
			int offsetChar = a - 97;
			//add node to array
			if(subNodes[offsetChar] == null){
				subNodes[offsetChar] = new DictionaryNode(str, a, val);
			}
			return subNodes[offsetChar];
		}
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nodeChar;
		result = prime * result + ((nodeStr == null) ? 0 : nodeStr.hashCode());
		result = prime * result + Arrays.hashCode(subNodes);
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DictionaryNode))
			return false;
		DictionaryNode other = (DictionaryNode) obj;
		if (nodeChar != other.nodeChar)
			return false;
		if (nodeStr == null) {
			if (other.nodeStr != null)
				return false;
		} else if (!nodeStr.equals(other.nodeStr))
			return false;
		if (!Arrays.equals(subNodes, other.subNodes))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	public String getNodeStr() {
		return nodeStr;
	}

	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}

	public char getNodeChar() {
		return nodeChar;
	}

	public void setNodeChar(char nodeChar) {
		this.nodeChar = nodeChar;
	}

	@Override
	public int getValue() {
		return value;
	}
	
	@Override
	public String toString(){
		return nodeStr + " " + value + "\n";
	}
}
