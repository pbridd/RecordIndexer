package client.gui.synchronization;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import client.ClientException;
import client.gui.synchronization.BatchStateListener.BatchActions;
import shared.communication.DownloadBatch_Result;
import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;
import shared.model.IndexedData;

public class BatchState {
	//Global Variables
		private Project project;
		private Batch batch;
		private List<Field> fields;
		private List<BatchStateListener> listeners;
		private IndexedData[][] values;
		private int selectedCellX;
		private int selectedCellY;
		private String imagePath;
		
		
		
		//Constructors
		/**
		 * Default constructor, set batch to null and initialize fields
		 */
		public BatchState(){
			project = null;
			batch = null;
			fields = new ArrayList<Field>();
			listeners = new ArrayList<BatchStateListener>();
		}
		
		
		/**
		 * Processes the batch that was downloaded. This includes instantiating most of its objects, 
		 * 	storing the image of the batch on the local machine, and notifying all of its listeners that
		 * 	something  changed.
		 * @param server_host the hostname of the server (to download the image)
		 * @param server_port the port the server is running on (to download the image)
		 * @param result the imagebatch to process
		 */
		public void processDownloadedBatch(DownloadBatch_Result result, String server_host, int server_port) 
				throws ClientException{
			this.project = result.getProject();
			fireProjectChanged();
			this.batch = result.getBatch();
			fireBatchChanged();
			this.fields = result.getFields();
			fireFieldChanged(-1);
			
			String tempURL ="http://"+ server_host + ":" + server_port + "/" 
                    + batch.getImagePath();
			
			imagePath = tempURL;
			
			//TODO take out if not used
			//imagePathOnLocalMachine = processImageURL(tempURL);
			fireImageChanged();
			fireBatchDownloaded();
			
			//initialize all of the values to blank
			values = new IndexedData[project.getRecordsPerImage()][fields.size()];
			for(int i = 0; i < values.length; i++){
				for(int j = 0; j < values[0].length; j++){
					values[i][j] = new IndexedData(-1, "", -1, -1);
				}
			}
			
		}
		
		
		
		//BatchStateListener notification methods
		private void fireBatchDownloaded(){
			for(BatchStateListener b : listeners){
				b.batchActionPerformed(BatchActions.BATCHDOWNLOADED);
			}
		}
		/**
		 * Fires the method projectChanged on all of its listeners
		 */
		private void fireProjectChanged(){
			for(BatchStateListener b : listeners){
				b.batchActionPerformed(BatchActions.PROJECTCHANGED);
			}
		}
		
		/**
		 * Fires the method batchChanged on all of its listeners
		 */
		private void fireBatchChanged(){
			for(BatchStateListener b : listeners){
				b.batchActionPerformed(BatchActions.BATCHCHANGED);
			}
		}
		
		/**
		 * Fires the method fieldChanged on all of its listeners
		 * @param idx The index of the field that  changed
		 */
		private void fireFieldChanged(int idx){
			for(BatchStateListener b : listeners){
				b.batchActionPerformed(BatchActions.FIELDCHANGED);
			}
		}
		
		/**
		 * Fires the method selectedXChanged on all of its listeners
		 */
		private void fireSelectedXChanged(){
			for(BatchStateListener b : listeners){
				b.batchActionPerformed(BatchActions.SELECTEDXCHANGED);
			}
		}
		
		/**
		 * Fires the method selectedYChanged on all of its listeners
		 */
		private void fireSelectedYChanged(){
			for(BatchStateListener b : listeners){
				b.batchActionPerformed(BatchActions.SELECTEDYCHANGED);
			}
		}
		
		/**
		 * Fires the method dataValueChanged on all of its listeners
		 * @param xIdx the X index of the data that  changed
		 * @param yIdx the Y index of the data that  changed
		 */
		private void fireDataValueChanged(int row, int col){
			for(BatchStateListener b : listeners){
				b.batchActionPerformed(BatchActions.DATAVALUECHANGED);
			}
		}
		
		private void fireImageChanged(){
			for(BatchStateListener b : listeners){
				b.batchActionPerformed(BatchActions.IMAGECHANGED);
			}
		}
		
		//Encapsulated Object Modifier Methods
		/**
		* Adds a field to the encapsulated List of fields
		* @param f The field to be added
		*/
		public void addField(Field f){
			fields.add(f);
		}

		
		//Getter and Setter Methods
		/**
		 * @return the project
		 */
		public Project getProject() {
			return project;
		}

		/**
		 * @return the fields
		 */
		public List<Field> getFields() {
			return fields;
		}
		
		/**
		 * @param i the index of the field to get
		 * @return the field name
		 */
		public Field getField(int i){
			return fields.get(i);
		}



		/**
		 * @return the batch
		 */
		public Batch getBatch() {
			return batch;
		}


		/**
		 * @return the values
		 */
		public IndexedData[][] getValues() {
			return values;
		}


		/**
		 * @return the selectedCellX
		 */
		public int getSelectedCellX() {
			return selectedCellX;
		}


		/**
		 * @return the selectedCellY
		 */
		public int getSelectedCellY() {
			return selectedCellY;
		}


		/**
		 * @return the imagePathOnLocalMachine
		 */
		public String getImagePath() {
			return imagePath;
		}
		
		/**
		 * Set the currently selected cell X coordinate, and fire the action listeners
		 * @param idx The index of the selected X cell
		 */
		public void setSelectedCellX(int idx){
			if(idx != selectedCellX){
				selectedCellX = idx;
				fireSelectedXChanged();
			}
		}
		
		/**
		 * Set the currently selected cell Y Coordinate, and fire the action listeners
		 * @param idx The index of the selected Y cell
		 */
		public void setSelectedCellY(int idx){
			if(idx != selectedCellY){
				selectedCellY = idx;
				fireSelectedYChanged();
			}
		}
		
		
		/**
		 * Set the currently celected cell X and Y, using the methods already defined in this class
		 * @param xIdx
		 * @param yIdx
		 */
		public void setSelectedCell(int row, int col){
			setSelectedCellX(row);
			setSelectedCellY(col);
		}
		
		/**
		 * Add a listener to this batchstate object
		 * @param BatchStateListener the batch state listener to add
		 */
		public void addListener(BatchStateListener bl){
			listeners.add(bl);
		}
		
		public void setValueAt(String val, int row, int col){
			values[row][col].setDataValue(val);
			this.fireDataValueChanged(row, col);
		}
		
		


}
