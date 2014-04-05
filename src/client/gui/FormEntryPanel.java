package client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import client.gui.synchronization.BatchState;
import client.gui.synchronization.BatchStateListener;

@SuppressWarnings("serial")
public class FormEntryPanel extends JPanel implements BatchStateListener, ActionListener {
	
	BatchState bchS;
	JList<String> recordList;
    List<JTextField> inputFields;

	
	public FormEntryPanel(BatchState bchS){
		this.bchS = bchS;
		bchS.addListener(this);
	}
	
	private void createComponents(){
		this.setLayout(new BorderLayout());
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		recordList = new JList<String>(listModel);
		recordList.setPreferredSize(new Dimension(50, this.getHeight()));
		//add items to the list
		
		for(int i = 1; i <= bchS.getProject().getRecordsPerImage(); i++){
			listModel.addElement(i+"");
		}
		//add this to the layout
		this.add(new JScrollPane(recordList), BorderLayout.WEST);
		
		
		
		//Make forms JPanel
		JPanel formsJPanel = new JPanel();
		formsJPanel.setLayout(new BoxLayout(formsJPanel, BoxLayout.Y_AXIS));
		//make the forms
		for(int i = 0; i < bchS.getFields().size(); i++){
			JLabel tempLabel = new JLabel(bchS.getField(i).getFieldName());
			JTextField tempTextField = new JTextField();
			tempTextField.addActionListener(this);
			JPanel tempPanel = new JPanel();
			tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.X_AXIS));
			
			//layout the things we just created
			tempPanel.add(tempLabel);
			tempPanel.add(tempTextField);
			formsJPanel.add(tempPanel);
		}
		
		this.add(new JScrollPane(formsJPanel), BorderLayout.CENTER);
		
	}

	@Override
	public void batchActionPerformed(BatchActions ba) {
		if(ba == BatchActions.BATCHDOWNLOADED){
			createComponents();
		}
		
	}

	@Override
	public void batchActionPerformed(BatchActions ba, int row, int col) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		for(JTextField j : inputFields){
			if(arg0.getSource() == j){
				//TODO implement the rest!
			}
		}
		
	}
	

}
