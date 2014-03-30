package client.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

import client.gui.synchronization.WindowManager;
import client.gui.synchronization.WindowManagerListener;
import shared.model.User;

public class MainFrame extends JFrame implements ActionListener, WindowManagerListener {

	/**
	 * Default generated serial version UID--generated by Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	//Non static methods and variables
	//Global Variables
	String server_host;
	int server_port;
	User user;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem downloadBatchMenuOption;
	JMenuItem logoutMenuOption;
	JMenuItem exitMenuOption;
	WindowManager wManager;
	
	
	public MainFrame(String server_host, int server_port, User user, WindowManager wManager) {
		this.user = user;
		this.server_host = server_host;
		this.server_port = server_port;
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.createComponents();
		this.wManager = wManager;
		this.wManager.addListener(this);
	}
	
	private void createComponents(){
		//set the layout manager for the main frame
		this.setLayout(new BorderLayout());
		//Create the menubar and the menu
		menuBar = new JMenuBar();
		
		menu = new JMenu("File");
		
		downloadBatchMenuOption = new JMenuItem("Download Batch");
		logoutMenuOption = new JMenuItem("Logout");
		exitMenuOption = new JMenuItem("Exit");
		menu.add(downloadBatchMenuOption);
		menu.add(logoutMenuOption);
		menu.add(exitMenuOption);
		menuBar.add(menu);
		
		downloadBatchMenuOption.addActionListener(this);
		logoutMenuOption.addActionListener(this);
		exitMenuOption.addActionListener(this);
		
		this.add(menuBar, BorderLayout.NORTH);
		
		ImagePanel imagePanel = new ImagePanel(server_host, server_port, user);
		InfoPanel infoPanel = new InfoPanel();
		TablePanel tablePanel = new TablePanel();
		
		JSplitPane verticalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,tablePanel,
				infoPanel);
		JSplitPane horizontalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, imagePanel, 
				verticalSplitPane);
		this.add(horizontalSplitPane, BorderLayout.CENTER);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == logoutMenuOption){
			wManager.toggleVisibility(null);
		}
		
		else if(e.getSource() == downloadBatchMenuOption){
			
		}
		
		else if(e.getSource() == exitMenuOption){
			System.exit(0);
		}
		
	}

	@Override
	public void visibilityToggled(boolean loginWindowVisible,
			boolean mainWindowVisible, User currUser) {
		if(!mainWindowVisible){
			this.setVisible(false);
		}
		else{
			this.user = currUser;
			this.setVisible(true);
		}
		
	}


	
	
	

}
