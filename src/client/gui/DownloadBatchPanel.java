package client.gui;

import java.util.EventListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import shared.model.User;
import shared.model.Project;
import client.gui.synchronization.BatchState;

public class DownloadBatchPanel implements EventListener {
	
	private String server_host;
	private int server_port;
	private BatchState bchS;
	private JComboBox projectSelectorBox;
	private JButton viewSampleButton;
	private JButton cancelButton;
	private JButton downloadButton;
	private User user;
	private List<Project> projList;
	private DefaultComboBoxModel cbm;
		
	
	public DownloadBatchPanel(String server_host, int server_port, User usr, BatchState bchS){
		this.server_host = server_host;
		this.server_port = server_port;
		this.user = usr;
		this.bchS = bchS;
		
	}
	
	private void createComponents(){
		JLabel projectLabel = new JLabel("Project:");
		cbm = new DefaultComboBoxModel();
		projList = UIIntegration.getProjects(user.getUsername(), user.getPassword(), server_host, server_port);
		//add the projects to the defaultComboBoxModel
		for(Project p : projList){
			cbm.addElement(p.getProjectTitle());
		}
		
		projectSelectorBox = new JComboBox(cbm);
		viewSampleButton = new JButton("View Sample");
		cancelButton = new JButton("Cancel");
		downloadButton = new JButton("Download");
	}
}
