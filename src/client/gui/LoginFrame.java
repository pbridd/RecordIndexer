package client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import shared.model.User;

public class LoginFrame extends JFrame implements ActionListener{
	//static global variables
	/**
	 * Auto-generated serialVersionUID from Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	//global variables
	JTextField userTextField;
	JPasswordField passwordField;
	JButton loginButton;
	JButton exitButton;
	boolean userIsAuthenticated;
	User authenticatedUser;
	
	//constructors
	/**
	 * @param server_host the host to communicate with on the server
	 * Constructor
	 * @param server_port The port number to use when communicating with the server
	 */
	public LoginFrame(String server_host, int server_port){
		super("Login to Indexer");
		userIsAuthenticated = false;
		authenticatedUser = null;
		this.setResizable(false);
		this.createComponents();
		
		
	}
	
	private void createComponents(){
		
		JPanel mainPanel = new JPanel();
		JPanel loginPanel = new JPanel();
		JPanel fieldsPanel = new JPanel();
		JPanel labelPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		
		JLabel userLabel = new JLabel("Username:");
		userTextField = new JTextField();
		userTextField.setPreferredSize(new Dimension(300, 20));
		
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(300, 20));
		
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		labelPanel.add(userLabel);
		labelPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		labelPanel.add(passwordLabel);
		
		fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
		fieldsPanel.add(userTextField);
		fieldsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		fieldsPanel.add(passwordField);
		
		loginPanel.setLayout(new BorderLayout());
		loginPanel.add(labelPanel, BorderLayout.WEST);
		loginPanel.add(fieldsPanel, BorderLayout.CENTER);
		
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.add(Box.createHorizontalGlue());
		loginButton = new JButton("Login");
		buttonsPanel.add(loginButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		exitButton = new JButton("Exit");
		buttonsPanel.add(exitButton);
		buttonsPanel.add(Box.createHorizontalGlue());
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		mainPanel.add(loginPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		mainPanel.add(buttonsPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		//add action listeners
		loginButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
	}
	
	/**
	 * ActionListener implemented methods
	 */
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == loginButton){
			
		}
		else if(e.getSource() == exitButton){
			System.exit(0);
		}
		
	}
	
	/**
	 * Returns whether a user has been authenticated after the last call of this function's
	 * 	constructor
	 * @return a boolean indicating whether a user was successfully authenticated
	 */
	public boolean userWasAuthenticated(){
		return userIsAuthenticated;
	}
	
	/**
	 * Returns the currently authenticated user. If no user has been authenticated successfully,
	 * 	returns null.
	 * @return the currently authenticated user
	 */
	public User getAuthenticatedUser(){
		return authenticatedUser;
	}
	
	
	
	
	
}


