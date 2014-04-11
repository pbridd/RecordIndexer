package client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

import client.gui.synchronization.BatchState;
import client.gui.synchronization.BatchStateListener;
import client.gui.synchronization.ImageState;
import client.gui.synchronization.MainFrameListener;
import client.gui.synchronization.WindowManager;
import client.gui.synchronization.WindowState;
import shared.model.User;

public class MainFrame extends JFrame implements ActionListener, BatchStateListener, Serializable {

	
	
	/**
	 * Automatically Generated by Eclipse
	 */
	private static final long serialVersionUID = 1611729556318338736L;
	//Non static methods and variables
	//Global Variables
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem downloadBatchMenuOption;
	private JMenuItem logoutMenuOption;
	private JMenuItem exitMenuOption;
	private WindowManager wManager;
	private BatchState bchS;
	private ImageState imgS;
	JSplitPane horizontalSplitPane;
	JSplitPane verticalSplitPane;
	JButton zoomInButton;
	JButton zoomOutButton;
	JButton invertImageButton;
	JButton toggleHighlightsButton;
	JButton saveButton;
	JButton submitButton;
	EntryPanel entryPanel;
	InfoPanel infoPanel;
	private List<MainFrameListener> listeners;
	
	
	public MainFrame(String server_host, int server_port, User user, WindowManager wManager, MainFrameListener listen) {
		listeners = new ArrayList<MainFrameListener>();
		listeners.add(listen);
		
		//process the serialized batchState for this user
		Object bsO = (BatchState) getSerializedObject(user.getUsername() + user.getUserID() + "_BatchState");
		
		if(bsO == null){
			bchS = new BatchState();
			bchS.setServer_host(server_host);
			bchS.setServer_port(server_port);
		}
		else{
			bchS = (BatchState) bsO;
			bchS.initializeListenerList();
			bchS.setServer_host(server_host);
			bchS.setServer_port(server_port);
		}
		//set the user
		bchS.setUser(user);
		
		WindowState ws = getWindowStateFromSerialized(user);
		imgS = getImageStateFromSerialized(user);
		
		this.wManager = wManager;
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		bchS.addListener(this);
		this.createComponents(ws);
		
	}
	
	
	
	public MainFrame(BatchState bchS, WindowManager wManager, MainFrameListener listen){
		listeners = new ArrayList<MainFrameListener>();
		listeners.add(listen);
		
		WindowState ws = getWindowStateFromSerialized(bchS.getUser());
		
		this.wManager = wManager;
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		bchS.addListener(this);
		this.createComponents(ws);
		
		
	}
	
	private void createComponents(WindowState ws){
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
		this.setJMenuBar(menuBar);
		downloadBatchMenuOption.addActionListener(this);
		logoutMenuOption.addActionListener(this);
		exitMenuOption.addActionListener(this);
		
		
		
		//setup the buttons panel--contains the buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		zoomInButton = new JButton("Zoom In");
		zoomOutButton = new JButton("Zoom Out");
		invertImageButton = new JButton("Invert Image");
		toggleHighlightsButton = new JButton("Toggle Highlights");
		saveButton = new JButton("Save");
		submitButton = new JButton("Submit");
		buttonsPanel.add(Box.createRigidArea(new Dimension(5,0)));
		buttonsPanel.add(zoomInButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(5,0)));
		buttonsPanel.add(zoomOutButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(5,0)));
		buttonsPanel.add(invertImageButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(5,0)));
		buttonsPanel.add(toggleHighlightsButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(5,0)));
		buttonsPanel.add(saveButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(5,0)));
		buttonsPanel.add(submitButton);
		buttonsPanel.add(Box.createGlue());
		disableButtons();
		saveButton.addActionListener(this);
		submitButton.addActionListener(this);
		

		
		this.add(buttonsPanel, BorderLayout.NORTH);
		//create the image panel and its image
		ImageComponent imageComp = new ImageComponent(bchS, imgS);		
		infoPanel = new InfoPanel(bchS);
		entryPanel = new EntryPanel(bchS);
		
		
		verticalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,entryPanel,
				infoPanel);
		horizontalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, imageComp, 
				verticalSplitPane);
		
		//make sure the panes are in the same places they were on the last close
		if(ws == null){
			verticalSplitPane.setDividerLocation(this.getWidth()/2);
			horizontalSplitPane.setDividerLocation(this.getHeight()/2);
		}
		else{
			verticalSplitPane.setDividerLocation(ws.getVerticalPanePosition());
			horizontalSplitPane.setDividerLocation(ws.getHorizontalPanePosition());
		}
		this.add(horizontalSplitPane, BorderLayout.CENTER);
		
		if(bchS.getBatch() != null){
			bchS.fireBatchDownloaded();
			bchS.fireSelectedCellRowChanged();
			bchS.fireSelectedCellColChanged();
		}
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == logoutMenuOption){
			saveState();
			wManager.toggleVisibility(null);
		}
		else if(e.getSource() == downloadBatchMenuOption){
			JDialog downloadBatchDialog = new DownloadBatchDialog(bchS);
			downloadBatchDialog.pack();
			downloadBatchDialog.setVisible(true);
			
			
		}
		else if(e.getSource() == exitMenuOption){
			saveState();
			System.exit(0);
		}
		
		else if(e.getSource() == submitButton){
			//submit and get the result
			boolean successful = UIIntegration.submitBatch(bchS.getUser().getUsername(), 
					bchS.getUser().getPassword(), bchS);
			//make sure the batch submit was successful.
			if(!successful){
				JOptionPane.showMessageDialog(this, "There was a problem when submitting the batch! Batch not submitted.\n", 
							 "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			else{
				this.disableButtons();
				downloadBatchMenuOption.setEnabled(true);
				bchS.clearBatch();
				try{
					Files.deleteIfExists(Paths.get("SavedData/" + bchS.getUser().getUsername() + 
							bchS.getUser().getUserID() +"_BatchState"  + ".ser"));
					Files.deleteIfExists(Paths.get("SavedData/" + bchS.getUser().getUsername() +
							bchS.getUser().getUserID() + "_ImageState" + ".ser"));
				}
				catch(IOException ex){
					System.out.println("Could not delete the old path file!");
					ex.printStackTrace();
				}
				saveWindowState();
				
				for(MainFrameListener l : listeners){
					l.MainFrameSubmittedBatch();
				}
				
			}
		}
		else if(e.getSource() == saveButton){
			saveState();
		}
		else if(e.getSource() == invertImageButton){
			imgS.toggleImageIsInverted();
		}
		else if(e.getSource() == toggleHighlightsButton){
			imgS.toggleImageIsHighlighted();
		}
	}

	
	/** 
	 * Saves the state of the window and batch into a JSON file.
	 */
	public void saveState(){
		this.saveWindowState();
		this.saveBatchState();
		this.saveImageState();
		
	}
	
	private void saveWindowState(){
		//save all of the GUI states
		WindowState ws = new WindowState();
		ws.setHeightOfWindow(this.getHeight());
		ws.setWidthOfWindow(this.getWidth());
		ws.setHorizontalPanePosition(horizontalSplitPane.getDividerLocation());
		ws.setVerticalPanePosition(verticalSplitPane.getDividerLocation());
		double xOnScreen = this.getLocationOnScreen().getX();
		double yOnScreen = this.getLocationOnScreen().getY();
		ws.setxPosOnDesktop(xOnScreen);
		ws.setyPosOnDesktop(yOnScreen);
		ws.setWidthOfWindow(this.getWidth());
		ws.setHeightOfWindow(this.getHeight());
		serializeObject(ws, bchS.getUser().getUsername() + bchS.getUser().getUserID() + "_WindowState");
	}
	
	private void saveBatchState(){
		serializeObject(bchS, bchS.getUser().getUsername() + bchS.getUser().getUserID() + "_BatchState");
	}
	
	private void saveImageState(){
		serializeObject(imgS, bchS.getUser().getUsername() + bchS.getUser().getUserID() + "_ImageState");
	}
	
	
	/**
	 * Return the batchState
	 * @return the current batchState
	 */
	public BatchState getBatchState(){
		return bchS;
	}
	
	private WindowState getWindowStateFromSerialized(User user){
		//process the serialized windowState for this user
		Object wsO = (WindowState) getSerializedObject(user.getUsername() + user.getUserID() + "_WindowState");
		WindowState ws = null;
		if(wsO == null){
			//set default size
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			int scWidth = gd.getDisplayMode().getWidth();
			int scHeight = gd.getDisplayMode ().getHeight();
			this.setSize(scWidth, scHeight);
			this.setLocation(scWidth/2 - this.getWidth(), scHeight/2 - this.getHeight());
		}
		else{
			ws = (WindowState) wsO;
			this.setSize((int)ws.getWidthOfWindow(), (int)ws.getHeightOfWindow());
			this.setLocation((int)ws.getxPosOnDesktop(), (int)ws.getyPosOnDesktop());
		}
		
		return ws;
	}
	
	private ImageState getImageStateFromSerialized(User user){
		Object isO = (ImageState) getSerializedObject(user.getUsername() + user.getUserID() + "_ImageState");
		ImageState imgState = null;
		if(isO == null){
			imgState = new ImageState();
		}
		else{
			imgState =(ImageState) isO;
		}
		return imgState;
	}
	
	/**
	 * Method to serialize the objects
	 * @param obj Object to serialize
	 * @param fileName The filename of the object to serialize
	 */
	private static void serializeObject(Object obj, String fileName) {
		OutputStream outFile;
		try {
			String filePath = "SavedData/" + fileName + ".ser";
			File oFCreator = new File(filePath);
			oFCreator.createNewFile();
			outFile = new BufferedOutputStream(new FileOutputStream(filePath));
			ObjectOutputStream output = new ObjectOutputStream(outFile);
			output.writeObject(obj);
			output.close();
			 
		} 
		catch (IOException e) {
			System.out.println("IOException in SerializeObject\n" + e.getMessage());
			e.printStackTrace();
		} 
	}
	
	/**
	 * Method to deserialize the objects
	 * @param fileName The filename that contains the object to deserialize
	 * @return the deserialized object
	 */
	private static Object getSerializedObject(String fileName){
		
			InputStream inFile = null;
			Object retObj;
			try {
				
				inFile = new BufferedInputStream(new FileInputStream("SavedData/" + fileName + ".ser"));
				ObjectInputStream input = new ObjectInputStream(inFile);
				retObj = input.readObject();
				input.close();

				
			} catch (IOException e) {
				return null;
			} catch (ClassNotFoundException e) {
				//close the input
				try {
					inFile.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return null;
			}
			
			
			return retObj;
		
	}

	@Override
	public void batchActionPerformed(BatchActions ba) {
		if(ba == BatchActions.BATCHDOWNLOADED){
			enableButtons();
			downloadBatchMenuOption.setEnabled(false);
		}
		
	}
	@Override
	public void batchActionPerformed(BatchActions ba, int row, int col) {
		// TODO Auto-generated method stub
	}
	
	
	
	

}

