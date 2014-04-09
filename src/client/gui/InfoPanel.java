package client.gui;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import client.gui.synchronization.BatchState;
import client.gui.synchronization.BatchStateListener;


public class InfoPanel extends JTabbedPane implements BatchStateListener, Serializable {
	
	/**
	 * Automatically Generated by Eclipse
	 */
	private static final long serialVersionUID = -5716555474574975761L;
	//global variables
	private BatchState bchS;
	JEditorPane htmlPane;
	
	//constructors
	public InfoPanel(BatchState bchS){
		this.bchS = bchS;
		bchS.addListener(this);
		createComponents();
	}
	
	private void createComponents(){
		htmlPane = new JEditorPane();
		htmlPane.setOpaque(true);
		htmlPane.setBackground(Color.gray);
		htmlPane.setEditable(false);
		JScrollPane htmlScrollPane = new JScrollPane(htmlPane);
		htmlScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		htmlPane.setContentType("text/html");
		this.add("Field Help", htmlPane);
		
		
	}
	
	private void loadPage(String url) {
	
        try {
            htmlPane.setPage(url);
        }
        catch (IOException ex) {
        	JOptionPane.showMessageDialog(htmlPane,
        		    "There was a problem when trying to display the fieldhelp page!\n"+ex.getMessage(), 
        		    "Error",
        		    JOptionPane.ERROR_MESSAGE);
        }
    }

	@Override
	public void batchActionPerformed(BatchActions ba) {
		if(ba == BatchActions.SELECTEDCOLCHANGED){
			String prelURL = bchS.getField(bchS.getSelectedCellCol()).getHelpHTML();
			String finURL = "http://" + bchS.getServer_host() + ":" + bchS.getServer_port() +"/"
					+ prelURL;
			loadPage(finURL);
		}
		if(ba == BatchActions.SELECTEDROWCHANGED){
			String prelURL = bchS.getField(bchS.getSelectedCellCol()).getHelpHTML();
			String finURL = "http://" + bchS.getServer_host() + ":" + bchS.getServer_port() +"/"
					+ prelURL;
			loadPage(finURL);
		}
		else if(ba == BatchActions.BATCHDOWNLOADED){
			//set the background
			if(bchS.getBatch() != null)
				htmlPane.setBackground(Color.white);
		}
		
	}

	@Override
	public void batchActionPerformed(BatchActions ba, int row, int col) {
		// purposely left blank--not needed for the current implementation
		
	}
	
	
}
