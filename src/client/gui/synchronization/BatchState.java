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
		}
		
		
		/**
		 * Processes the batch that was downloaded. This includes instantiating most of its objects, 
		 * 	storing the image of the batch on the local machine, and notifying all of its listeners that
		 * 	something has changed.
		 * @param server_host the hostname of the server (to download the image)
		 * @param server_port the port the server is running on (to download the image)
		 * @param result the imagebatch to process
		 */
		public void processDownloadedBatch(DownloadBatch_Result result, String server_host, String server_port) 
				throws ClientException{
			this.project = result.getProject();
			fireProjectHasChanged();
			this.batch = result.getBatch();
			fireBatchHasChanged();
			this.fields = result.getFields();
			fireFieldHasChanged(-1);
			
			String tempURL = server_host + ":" + server_port + "/" 
                    + batch.getImagePath();
			
			imagePath = tempURL;
			
			//TODO take out if not used
			//imagePathOnLocalMachine = processImageURL(tempURL);
			fireImageHasChanged();
			
		}
		
		
		/**
		 * This method downloads the image from the server and puts it in a local folder.
		 * @param imgURL The URL of the image to download
		 */
		//TODO remove if unused.
		private String processImageURL(String imgURL) throws ClientException{
			BufferedImage image = null;
			String imagePath = "localData/images/" + batch.getBatchID() + ".png";
			 try{
				 URL url = new URL(imgURL);
				 image = ImageIO.read(url);
				 ImageIO.write(image, "png",new File(imagePath));
			 }
			 catch(IOException e){
				 throw new ClientException(e.getMessage());
			 }
			 return imagePath;
		}
		//BatchStateListener notification methods
		/**
		 * Fires the method projectHasChanged on all of its listeners
		 */
		private void fireProjectHasChanged(){
			for(BatchStateListener b : listeners){
				b.batchActionPerformed(BatchActions.PROJECTHASCHANGED);
			}
		}
		
		/**
		 * Fires the method batchHasChanged on all of its listeners
		 */
		private void fireBatchHasChanged(){
			for(BatchStateListener b : listeners){
				b.batchActionPerformed(BatchActions.BATCHHASCHANGED);
			}
		}
		
		/**
		 * Fires the method fieldHasChanged on all of its listeners
		 * @param idx The index of the field that has changed
		 */
		private void fireFieldHasChanged(int idx){
			for(BatchStateListener b : listeners){
				b.batchActionPerformed(BatchActions.FIELDHASCHANGED);
			}
		}
		
		/**
		 * Fires the method selectedXHasChanged on all of its listeners
		 */
		private void fireSelectedXHasChanged(){
			for(BatchStateListener b : listeners){
				b.batchActionPerformed(BatchActions.SELECTEDXHASCHANGED);
			}
		}
		
		/**
		 * Fires the method selectedYHasChanged on all of its listeners
		 */
		private void fireSelectedYHasChanged(){
			for(BatchStateListener b : listeners){
				b.batchActionPerformed(BatchActions.SELECTEDYHASCHANGED);
			}
		}
		
		/**
		 * Fires the method dataValueHasChanged on all of its listeners
		 * @param xIdx the X index of the data that has changed
		 * @param yIdx the Y index of the data that has changed
		 */
		private void fireDataValueHasChanged(int xIdx, int yIdx){
			for(BatchStateListener b : listeners){
				b.batchActionPerformed(BatchActions.DATAVALUEHASCHANGED);
			}
		}
		
		private void fireImageHasChanged(){
			for(BatchStateListener b : listeners){
				b.batchActionPerformed(BatchActions.IMAGEHASCHANGED);
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
				fireSelectedXHasChanged();
			}
		}
		
		/**
		 * Set the currently selected cell Y Coordinate, and fire the action listeners
		 * @param idx The index of the selected Y cell
		 */
		public void setSelectedCellY(int idx){
			if(idx != selectedCellY){
				selectedCellY = idx;
				fireSelectedYHasChanged();
			}
		}
		
		
		/**
		 * Set the currently celected cell X and Y, using the methods already defined in this class
		 * @param xIdx
		 * @param yIdx
		 */
		public void setSelectedCell(int xIdx, int yIdx){
			setSelectedCellX(xIdx);
			setSelectedCellY(yIdx);
		}
		


}
