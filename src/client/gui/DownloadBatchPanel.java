package client.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.EventListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shared.model.User;
import shared.model.Project;
import client.gui.synchronization.BatchState;

public class DownloadBatchPanel extends JPanel implements EventListener {
	
	private String server_host;
	private int server_port;
	private BatchState bchS;
	private JComboBox<String> projectSelectorBox;
	private JButton viewSampleButton;
	private JButton cancelButton;
	private JButton downloadButton;
	private User user;
	private List<Project> projList;
	private DefaultComboBoxModel<String> cbm;
		
	
	public DownloadBatchPanel(String server_host, int server_port, User usr, BatchState bchS){
		this.server_host = server_host;
		this.server_port = server_port;
		this.user = usr;
		this.bchS = bchS;
		this.createComponents();
		
	}
	
	private void createComponents(){
		JLabel projectLabel = new JLabel("Project:");
		cbm = new DefaultComboBoxModel<String>();
		projList = UIIntegration.getProjects(user.getUsername(), user.getPassword(), server_host, server_port);
		
		//add the projects to the defaultComboBoxModel
		for(Project p : projList){
			cbm.addElement(p.getProjectTitle());
		}
		
		projectSelectorBox = new JComboBox<String>(cbm);
		viewSampleButton = new JButton("View Sample");
		cancelButton = new JButton("Cancel");
		downloadButton = new JButton("Download");

		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.add(projectLabel);
		topPanel.add(projectSelectorBox);
		topPanel.add(viewSampleButton);
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bottomPanel.add(cancelButton);
		bottomPanel.add(downloadButton);
		
		this.add(topPanel);
		this.add(bottomPanel);
		
	}
	
	private void actionPerformed(ActionEvent e){
		if(e.getSource() == viewSampleButton){
			
		}
	}
}
