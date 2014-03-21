package shared.communication;

import java.util.ArrayList;
import java.util.List;

import shared.model.Project;

public class GetProjects_Result {
	//Global Variables
	private List<Project> projects;
	
	//Constructors
	/**
	 * Default Constructor, initializes projects
	 */
	public GetProjects_Result(){
		projects = new ArrayList<Project>();
	}
	
	/**
	 * Constructor, sets all global variables
	 * @param projects The project to be encapsulated
	 */
	public GetProjects_Result(List<Project> projects){
		this.projects = projects;
	}
	
	//Encapsulated Object Modifier Methods
	/**
	 * Adds a project to the encapsulated List of projects
	 * @param prj The project to be add
	 */
	public void addProject(Project prj){
		projects.add(prj);
	}

	//Getter and Setter Methods
	/**
	 * @return the projects
	 */
	public List<Project> getProjects() {
		return projects;
	}

	/**
	 * @param projects the projects to set
	 */
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	
	//Overridden Methods

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		//TODO implement
		return "GetProjects_Result [projects=" + projects + "]";
	}
	
	
	
	
	

}
