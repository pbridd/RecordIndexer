package client.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import shared.model.User;

public class ImagePanel extends JPanel {
	
	//Global variables
	JButton zoomInButton;
	JButton zoomOutButton;
	JButton invertImageButton;
	JButton toggleHighlightsButton;
	JButton saveButton;
	JButton submitButton;
	
	public ImagePanel(String server_host, int server_port, User user){
		createComponents();
	}
	
	private void createComponents(){
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		
		//create the buttons panel and its buttons, and add them
		JPanel buttonsPanel = new JPanel();

		
		zoomInButton = new JButton("Zoom In");
		zoomOutButton = new JButton("Zoom Out");
		invertImageButton = new JButton("Invert Image");
		toggleHighlightsButton = new JButton("Toggle Highlights");
		saveButton = new JButton("Save");
		submitButton = new JButton("Submit");
		
	
		buttonsPanel.add(zoomInButton);
		buttonsPanel.add(zoomOutButton);
		buttonsPanel.add(invertImageButton);
		buttonsPanel.add(toggleHighlightsButton);
		buttonsPanel.add(saveButton);
		buttonsPanel.add(submitButton);
		disableButtons();
		
		//create the image panel and its image
		JPanel imagePanel = new JPanel();
		
		JPanel bufPanel = new JPanel();
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(bufPanel, BorderLayout.CENTER);
		topPanel.add(buttonsPanel, BorderLayout.WEST);
		
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(imagePanel, BorderLayout.CENTER);
		
		this.add(mainPanel);
		
		
	}
	
	private void disableButtons(){
		zoomInButton.setEnabled(false);
		zoomOutButton.setEnabled(false);
		invertImageButton.setEnabled(false);
		toggleHighlightsButton.setEnabled(false);
		saveButton.setEnabled(false);
		submitButton.setEnabled(false);
	}
	
	private void enableButtons(){
		zoomInButton.setEnabled(true);
		zoomOutButton.setEnabled(true);
		invertImageButton.setEnabled(true);
		toggleHighlightsButton.setEnabled(true);
		saveButton.setEnabled(true);
		submitButton.setEnabled(true);
	}
}
