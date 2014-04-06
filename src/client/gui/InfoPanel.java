package client.gui;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.gui.synchronization.BatchState;
import client.gui.synchronization.BatchStateListener;

public class InfoPanel extends JPanel implements BatchStateListener {
	//global variables
	private BatchState bchS;
	JEditorPane htmlPane;
	
	//constructors
	public InfoPanel(BatchState bchS){
		this.bchS = bchS;
	}
	
	private void createComponents(){
		htmlPane = new JEditorPane();
		htmlPane = new JEditorPane();
		htmlPane.setOpaque(true);
		htmlPane.setBackground(Color.white);
		htmlPane.setEditable(false);
		JScrollPane htmlScrollPane = new JScrollPane(htmlPane);
		htmlScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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
		}
		
	}

	@Override
	public void batchActionPerformed(BatchActions ba, int row, int col) {
		// TODO Auto-generated method stub
		
	}
	
	
}
