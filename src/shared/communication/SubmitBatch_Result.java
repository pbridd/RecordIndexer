package shared.communication;

public class SubmitBatch_Result {
	//Global Variables
	private boolean succeeded;
	
	//Constructors
	/**
	 * Default constructor, sets succeeded to false
	 */
	public SubmitBatch_Result(){
		succeeded = false;
	}
	
	/**
	 * Constructor, sets succeeded
	 * @param succeeded Whether the operation has succeeded or not 
	 */
	public SubmitBatch_Result(boolean succeeded){
		this.succeeded = succeeded;
	}

	
	//Getter and Setter Methods
	/**
	 * @return whether the operation has succeeded
	 */
	public boolean hasSucceeded() {
		return succeeded;
	}

	/**
	 * @param succeeded the succeeded to set
	 */
	public void setSucceeded(boolean succeeded) {
		this.succeeded = succeeded;
	}

	
	//Overridden Methods
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		//TODO implement
		return "SubmitBatch_Result [succeeded=" + succeeded + "]";
	}
	
	
	
	

}
