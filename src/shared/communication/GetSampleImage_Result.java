package shared.communication;

import shared.model.Batch;

public class GetSampleImage_Result {
	//Global Variables
	private Batch batch;
	
	//Constructors
	/**
	 * Default constructor, sets batch to null
	 */
	public GetSampleImage_Result(){
		batch = null;
	}
	
	/**
	 * Sets this.batch to the batch that is passed in
	 * @param batch The batch to be encapsulated by this object
	 */
	public GetSampleImage_Result(Batch batch){
		this.batch = batch;
	}

	
	//Getter and Setter Methods
	/**
	 * @return the batch
	 */
	public Batch getBatch() {
		return batch;
	}

	/**
	 * @param batch the batch to set
	 */
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	
	/**
	 * Get the URL of the sample image encapsulated in this object
	 * @return The URL of the sample image
	 */
	public String getImageURL(){
		return batch.getImagePath();
	}
	
	
	//Overridden Methods
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		//TODO implement
		return "GetSampleImage_Result [batch=" + batch + "]";
	}
	
	
	
}
