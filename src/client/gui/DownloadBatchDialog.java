package client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URL;
import java.util.EventListener;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import shared.communication.DownloadBatch_Result;
import shared.model.User;
import shared.model.Project;
import client.ClientException;
import client.gui.synchronization.BatchState;

public class DownloadBatchDialog extends JDialog implements ActionListener {
	
	private String server_host;
	private int server_port;
	private BatchState bchS;
	private JComboBox<String> projectSelectorBox;
	private JButton viewSampleButton;
	private JButton cancelButton;
	private JButton downloadButton;
	private JButton closeSampleImageButton;
	private User user;
	private List<Project> projList;
	private DefaultComboBoxModel<String> cbm;
	JDialog viewSampleImageDialog;
		
	
	public DownloadBatchDialog(String server_host, int server_port, User usr, BatchState bchS){
		this.server_host = server_host;
		this.server_port = server_port;
		this.user = usr;
		this.bchS = bchS;
		this.setModal(true);
		this.setResizable(false);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int scWidth = gd.getDisplayMode().getWidth();
		int scHeight = gd.getDisplayMode().getHeight();
		this.setLocation(scWidth/2 - this.getWidth(), scHeight/2 - this.getHeight());
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
			displayViewSampleImageDialog();
		}
		
		else if(e.getSource() == cancelButton){
			this.dispose();
		}
		
		else if(e.getSource() == closeSampleImageButton){
			viewSampleImageDialog.dispose();
		}
		else if(e.getSource() == downloadButton){
			DownloadBatch_Result res;
			int projID = getProjectID((String)projectSelectorBox.getSelectedItem());
			try{
				 res = UIIntegration.downloadBatch(user.getUsername(), user.getPassword(), projID, server_host, server_port);
				 bchS.processDownloadedBatch(res, server_host, server_port);
			}
			catch(ClientException ce){
				JOptionPane.showMessageDialog(this, "There was an error while trying to download the batch from the server:\n" 
						+ ce.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.dispose();
			
			
		}
		
	}
	
	private void displayViewSampleImageDialog(){
		int projID = getProjectID((String)projectSelectorBox.getSelectedItem());
		
		//make sure a project was found
		if(projID == -1){
			JOptionPane.showMessageDialog(this, "Could not find a project with the specified name",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		
		String imgURL;
		try{
			imgURL = UIIntegration.getSampleImage(user.getUsername(), user.getPassword(), projID, server_host, 
					server_port);
		}
		catch(ClientException ce){
			JOptionPane.showMessageDialog(this, "There was an error when contacting the server to get a URL for the sample image:\n" 
					+ ce.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//get the image from the server
		Image image;
		try{
            URL url = new URL(imgURL);
            image = ImageIO.read(url);
        }
        catch(IOException ioex){
        	JOptionPane.showMessageDialog(this, "There was an error when trying to retrieve the image from the server:\n" 
					+ ioex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
		
		
		//make the dialog for viewing the sample image
		viewSampleImageDialog = new JDialog();
		
		closeSampleImageButton = new JButton("Close");
		
		closeSampleImageButton.addActionListener(this);
		
		viewSampleImageDialog.setModal(true);
		viewSampleImageDialog.setResizable(false);
		viewSampleImageDialog.setTitle("Sample Image from " + (String)projectSelectorBox.getSelectedItem());
		
		
		JPanel mJPanel = new JPanel();
		mJPanel.setLayout(new BoxLayout(mJPanel, BoxLayout.Y_AXIS));
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createGlue());
		buttonPanel.add(closeSampleImageButton);
		buttonPanel.add(Box.createGlue());
		
		JPanel imgPanel = new JPanel();
		Image imgResized = image.getScaledInstance(500, 400, Image.SCALE_SMOOTH);
        JLabel lbImage = new JLabel(new ImageIcon(imgResized));
        imgPanel.add(lbImage);
        mJPanel.add(imgPanel);
        mJPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mJPanel.add(buttonPanel);
        mJPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        viewSampleImageDialog.setContentPane(mJPanel);
        viewSampleImageDialog.pack();
        viewSampleImageDialog.setVisible(true);
	}
	
	/**
	 * Searches the list of projects projList for a certain name and returns its projectID
	 * @param projectTitle The name of the project to search for
	 * @return an int that is the project ID of the project that was asked for, or -1 if there
	 * was no project with that name found
	 */
	private int getProjectID(String projectTitle){
		for(Project p : projList){
			if(projectTitle.equals(p.getProjectTitle())){
				return p.getProjectID();
			}
		}
		return -1;
	}
}


