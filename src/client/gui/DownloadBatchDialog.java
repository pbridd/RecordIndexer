package client.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.EventListener;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shared.model.User;
import shared.model.Project;
import client.gui.synchronization.BatchState;

public class DownloadBatchDialog extends JDialog implements ActionListener {
	
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
		
	
	public DownloadBatchDialog(String server_host, int server_port, User usr, BatchState bchS){
		this.server_host = server_host;
		this.server_port = server_port;
		this.user = usr;
		this.bchS = bchS;
		this.setModal(true);
		this.setResizable(false);
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
		
		JPanel mainPanel = new JPanel();
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.add(Box.createRigidArea(new Dimension(5,0)));
		topPanel.add(projectLabel);
		topPanel.add(Box.createRigidArea(new Dimension(5,0)));
		topPanel.add(projectSelectorBox);
		topPanel.add(Box.createRigidArea(new Dimension(5,0)));
		topPanel.add(viewSampleButton);
		topPanel.add(Box.createRigidArea(new Dimension(5,0)));
		bottomPanel.add(Box.createRigidArea(new Dimension(5,0)));
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bottomPanel.add(cancelButton);
		bottomPanel.add(Box.createRigidArea(new Dimension(5,0)));
		bottomPanel.add(downloadButton);
		bottomPanel.add(Box.createRigidArea(new Dimension(5,0)));
		
		mainPanel.add(Box.createRigidArea(new Dimension(0,5)));
		mainPanel.add(topPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
		mainPanel.add(bottomPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0,5)));
		
		//add this to the actionlisteners of the buttons
		viewSampleButton.addActionListener(this);
		cancelButton.addActionListener(this);
		downloadButton.addActionListener(this);
		
		this.add(mainPanel);
		
		
	}
	
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == viewSampleButton){
			//TODO implement
			
			String imgURL = null;
			Image image;
			try{
	            URL url = new URL(imgURL);
	            image = ImageIO.read(url);
	        }
	        catch(IOException ioex){
	            System.out.println("Something bad just happened when trying to read the image"
	                    + " URL!");
	        }
			
		}
		if(e.getSource() == cancelButton){
			this.dispose();
		}
	}
	
	/**
	 * Searches the list of projects projList for a certain name and returns its projectID
	 * @param projectName The name of the project to search for
	 * @return an int that is the project ID of the project that was asked for, or -1 if there
	 * was no project with that name found
	 */
	private int getProjectID(String projectName){
		//TODO implement
		return -1;
	}
}
