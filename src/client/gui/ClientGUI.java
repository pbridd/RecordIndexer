package client.gui;

public class ClientGUI {
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args){
		String server_host;
		int server_port;
		
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
		
		LoginFrame login = new LoginFrame(server_host, server_port);
		login.pack();
		login.setVisible(true);
		//run the main frame if the user is authenticated
		if(login.userIsAuthenticated()){
			
		}
	}
}
