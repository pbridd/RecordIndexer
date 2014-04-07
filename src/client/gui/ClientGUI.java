package client.gui;

import java.awt.EventQueue;

import shared.model.User;
import client.gui.synchronization.WindowManager;
import client.gui.synchronization.WindowManagerListener;

public class ClientGUI implements WindowManagerListener{
	//Static Global Variables
	private static String server_host;
	private static int server_port;
	private static WindowManager wManager;

	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args){
		
		//process the arguments
		if(args.length == 2){
			server_host = args[0];
			try{
				server_port = Integer.parseInt(args[1]);
			}
			catch(NumberFormatException e){
				System.out.println("The port number passed in was invalid. Using default 8080.");
				server_port = 8080;
			}
		}
		else{
			server_host = "localhost";
			server_port = 8080;
		}
		
		wManager = new WindowManager();
		
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				@SuppressWarnings("unused")
				ClientGUI gui = new ClientGUI();
			}
		});

		
		
	}
	
	//nonstatic global variables
	private MainFrame mainFrame;
	private LoginFrame loginFrame;
	
	public ClientGUI(){
		//Start with the first login frame
		loginFrame = new LoginFrame(server_host, server_port, wManager);
		loginFrame.pack();
		loginFrame.setVisible(true);
		wManager.addListener(this);
	}

	@Override
	public void visibilityToggled(boolean loginWindowVisible,
			boolean mainWindowVisible, User currUser) {
		if(loginWindowVisible == true){
			mainFrame.dispose();
			mainFrame = null;
			loginFrame = new LoginFrame(server_host, server_port, wManager);
			loginFrame.pack();
			mainFrame = null;
			loginFrame.setVisible(true);
		}
		else if(mainWindowVisible == true){
			loginFrame.dispose();
			loginFrame = null;
			mainFrame = new MainFrame(server_host, server_port, currUser, wManager);
			mainFrame.setVisible(true);
		}
		
	}
}
