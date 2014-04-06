package client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.gui.synchronization.BatchState;
import client.gui.synchronization.BatchStateListener;

@SuppressWarnings("serial")
public class FormEntryPanel extends JPanel implements BatchStateListener, ActionListener, ListSelectionListener, FocusListener {
	
	BatchState bchS;
	JList<String> recordList;
    List<JTextField> inputFields;

	
	public FormEntryPanel(BatchState bchS){
		this.bchS = bchS;
		bchS.addListener(this);
		inputFields = new ArrayList<JTextField>();
	}
	
	private void createComponents(){
		this.setLayout(new BorderLayout());
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		recordList = new JList<String>(listModel);
		recordList.setPreferredSize(new Dimension(200, this.getHeight()));
		//add items to the list and set the selection model
		recordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		for(int i = 1; i <= bchS.getProject().getRecordsPerImage(); i++){
			listModel.addElement(i+"");
		}
		recordList.setSelectedIndex(0);
		//add this to the layout
		this.add(new JScrollPane(recordList), BorderLayout.WEST);
		recordList.addListSelectionListener(this);
		
		
		//Make forms JPanel
		JPanel formsJPanel = new JPanel();
		formsJPanel.setLayout(new BoxLayout(formsJPanel, BoxLayout.X_AXIS));
		//make the forms
		
		JPanel tempLabelsPanel = new JPanel();
		JPanel tempFormsPanel = new JPanel();
		tempLabelsPanel.setLayout(new BoxLayout(tempLabelsPanel, BoxLayout.Y_AXIS));
		tempFormsPanel.setLayout(new BoxLayout(tempFormsPanel, BoxLayout.Y_AXIS));
		
		for(int i = 0; i < bchS.getFields().size(); i++){
			JLabel tempLabel = new JLabel(bchS.getField(i).getFieldName());
			JTextField tempTextField = new JTextField();
			tempTextField.addActionListener(this);
			tempTextField.addFocusListener(this);
			inputFields.add(tempTextField);
			
			tempLabelsPanel.add(tempLabel);
			tempLabelsPanel.add(Box.createGlue());
			
			tempTextField.setPreferredSize(new Dimension(300, 20));
			tempTextField.setMaximumSize(new Dimension(300, 20));
			
			tempFormsPanel.add(tempTextField);
			tempFormsPanel.add(Box.createGlue());
			
			
		}
		formsJPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		formsJPanel.add(tempLabelsPanel);
		formsJPanel.add(Box.createRigidArea(new Dimension(90, 0)));
		formsJPanel.add(tempFormsPanel);
		formsJPanel.add(Box.createGlue());
		
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
		if(ba == BatchActions.DATAVALUECHANGED){
			if(row == recordList.getSelectedIndex()){
				inputFields.get(col).setText(bchS.getValues()[row][col].getDataValue());
			}
		}
		if(ba == BatchActions.SELECTEDROWCHANGED){
			recordList.setSelectedIndex(bchS.getSelectedCellRow());
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//purposely left blank
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if(arg0.getSource() == recordList){
			int selIdx = recordList.getSelectedIndex();
			if(selIdx < 0)
				return;
			for(int i = 0; i < inputFields.size(); i++){
				inputFields.get(i).setText(bchS.getValues()[recordList.getSelectedIndex()][i].getDataValue());
			}
			bchS.setSelectedCellRow(recordList.getSelectedIndex());
		}
		
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		for(int i = 0; i < inputFields.size(); i++){
			JTextField j = inputFields.get(i);
			if(arg0.getSource() == j){
				bchS.setSelectedCell(recordList.getSelectedIndex(), i);
			}
		}
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		for(int i = 0; i < inputFields.size(); i++){
			JTextField j = inputFields.get(i);
			if(arg0.getSource() == j){
				bchS.setValueAt(j.getText(),recordList.getSelectedIndex() , i);
			}
		}
	}
		
	
	
	

}
