package shared.communication;

import java.util.ArrayList;
import java.util.List;

import shared.model.*;

public class DownloadBatch_Result {
	//Global Variables
	private Project project;
	private Batch batch;
	private List<Field> fields;
	
	
	//Constructors
	/**
	 * Default constructor, set batch to null and initialize fields
	 */
	public DownloadBatch_Result(){
		project = null;
		batch = null;
		fields = new ArrayList<Field>();
	}
	
	/**
	 * Constructor, sets batch and fields to the parameters that are passed in
	 * @param project The project to encapsulate
	 * @param batch The batch to encapsulate
	 * @param fields The list of fields to encapsulate
	 */
	public DownloadBatch_Result(Project project, Batch batch, List<Field> fields){
		this.project = project;
		this.batch = batch;
		this.fields = fields;
	}
	
	//Encapsulated Object Modifier Methods
	/**
	* Adds a field to the encapsulated List of fields
	* @param f The field to be added
	*/
	public void addField(Field f){
		fields.add(f);
	}

	
	//Getter and Setter Methods
	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

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
	 * @return the fields
	 */
	public List<Field> getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	
		
	
	

}
