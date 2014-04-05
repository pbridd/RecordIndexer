package client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import client.gui.synchronization.BatchState;
import client.gui.synchronization.BatchStateListener;


public class ImageComponent extends JComponent implements BatchStateListener{

	/**
	 * Automatically added by Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	private static Image NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
	
	private int w_originX;
	private int w_originY;
	private double scale;
	
	private boolean dragging;
	private int w_dragStartX;
	private int w_dragStartY;
	private int w_dragStartOriginX;
	private int w_dragStartOriginY;
	
	private String imagePath;
	private Image image;
	
	
	private List<DrawingShape> shapes;
	
	private BatchState bchS;

	public ImageComponent(BatchState bchS){
		this.bchS = bchS;
		bchS.addListener(this);
		shapes = new ArrayList<DrawingShape>();
		String tempImgPath = bchS.getImagePath();
		if(tempImgPath == null){
			imagePath = "";
		}
		else
			imagePath = tempImgPath;
		
		
		w_originX = (int) this.getWidth() / 2;
		w_originY = (int) this.getHeight() / 2;
		scale = 1.0;
		
		initDrag();
		
		this.setBackground(new Color(96, 96, 96));
		
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addComponentListener(componentAdapter);
		
		image = loadImage(imagePath);
		shapes.add(new DrawingImage(image, new Rectangle2D.Double(350, 50, image.getWidth(null), image.getHeight(null))));
		
		
	}
	
	//public methods
	/**
	 * Set the scale of the image
	 * @param newScale the new scale to set the image to
	 */
	public void setScale(double newScale){
		scale = newScale;
		this.repaint();
	}
	
	/**
	 * Set the origin of the image
	 * @param w_newOriginX	The new origin X to set
	 * @param w_newOriginY	The new origin Y to set
	 */
	public void setOrigin(int w_newOriginX, int w_newOriginY){
		w_originX = w_newOriginX;
		w_originY = w_newOriginY;
	}
	
	/**
	 * Loads an image from the server
	 * @param imageURL the URL of the image on the server
	 * @return an Image object
	 */
	private Image loadImage(String imageURL) {
		try {
			URL url = new URL(imageURL);
			return ImageIO.read(url);
		}
		catch (IOException e) {
			return NULL_IMAGE;
		}
	}
	
	/**
	 * Initializes the dragging variables to their starting values
	 */
	private void initDrag() {
		dragging = false;
		w_dragStartX = 0;
		w_dragStartY = 0;
		w_dragStartOriginX = 0;
		w_dragStartOriginY = 0;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		drawBackground(g2);
		
		g2.scale(scale,  scale);
		g2.translate(-w_originX, -w_originY);
		
		drawShapes(g2);
		
	}
	
	/**
	 * Draw the background
	 * @param g2
	 */
	private void drawBackground(Graphics2D g2){
		g2.setColor(getBackground());
		g2.fillRect(0, 0, getWidth(), getHeight());
	}
	
	private void drawShapes(Graphics2D g2){
		for(DrawingShape shape : shapes){
			shape.draw(g2);
		}
	}
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mousePressed(MouseEvent e) {
			int d_X = e.getX();
			int d_Y = e.getY();
			
			AffineTransform transform = new AffineTransform();
			transform.scale(scale, scale);
			transform.translate(-w_originX, -w_originY);
			
			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			try
			{
				transform.inverseTransform(d_Pt, w_Pt);
			}
			catch (NoninvertibleTransformException ex) {
				return;
			}
			int w_X = (int)w_Pt.getX();
			int w_Y = (int)w_Pt.getY();
			
			boolean hitShape = false;
			
			Graphics2D g2 = (Graphics2D)getGraphics();
			for (DrawingShape shape : shapes) {
				if (shape.contains(g2, w_X, w_Y)) {
					hitShape = true;
					break;
				}
			}
			
			if (hitShape) {
				dragging = true;		
				w_dragStartX = w_X;
				w_dragStartY = w_Y;		
				w_dragStartOriginX = w_originX;
				w_dragStartOriginY = w_originY;
			}
		}
		

		@Override
		public void mouseDragged(MouseEvent e) {		
			if (dragging) {
				int d_X = e.getX();
				int d_Y = e.getY();
				
				AffineTransform transform = new AffineTransform();
				transform.scale(scale, scale);
				transform.translate(-w_dragStartOriginX, -w_dragStartOriginY);
				
				Point2D d_Pt = new Point2D.Double(d_X, d_Y);
				Point2D w_Pt = new Point2D.Double();
				try
				{
					transform.inverseTransform(d_Pt, w_Pt);
				}
				catch (NoninvertibleTransformException ex) {
					return;
				}
				int w_X = (int)w_Pt.getX();
				int w_Y = (int)w_Pt.getY();
				
				int w_deltaX = w_X - w_dragStartX;
				int w_deltaY = w_Y - w_dragStartY;
				
				w_originX = w_dragStartOriginX - w_deltaX;
				w_originY = w_dragStartOriginY - w_deltaY;
				
				repaint();
			}
		}
		
		

		@Override
		public void mouseReleased(MouseEvent e) {
			initDrag();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			return;
		}	
	};
	
	private ComponentAdapter componentAdapter = new ComponentAdapter() {

		@Override
		public void componentHidden(ComponentEvent e) {
			return;
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			return;
		}

		@Override
		public void componentResized(ComponentEvent e) {
			//updateTextShapes();
		}

		@Override
		public void componentShown(ComponentEvent e) {
			return;
		}
	};

	@Override
	public void batchActionPerformed(BatchActions ba) {
		//Loads the image from the server if the image has changed
		if(ba == BatchActions.IMAGECHANGED){
			this.imagePath = bchS.getImagePath();
			this.image = loadImage(imagePath);
			
			shapes.clear();
			shapes.add(new DrawingImage(image, new Rectangle2D.Double(350, 50, image.getWidth(null), image.getHeight(null))));
			this.repaint();
		}
		
	}
	/////////////////
	//DRAWING SHAPE//
	/////////////////
	interface DrawingShape{
		boolean contains(Graphics2D g2, double x, double y);
		void draw(Graphics2D g2);
		Rectangle2D getBounds(Graphics2D g2);
	}
	
	/**
	 * A class to draw the downloaded image
	 * @author pbridd
	 *
	 */
	class DrawingImage implements DrawingShape {
		private Image image;
		private Rectangle2D rect;
		
		public DrawingImage(Image image, Rectangle2D rect){
			this.image = image;
			this.rect = rect;
		}
		
		@Override
		public boolean contains(Graphics2D g2, double x, double y){
			return rect.contains(x, y);
		}
		
		@Override
		public void draw(Graphics2D g2){
			g2.drawImage(image, (int)rect.getMinX(), (int)rect.getMinY(), (int)rect.getMaxX(), (int)rect.getMaxY(),
					0, 0, image.getWidth(null), image.getWidth(null), null);
		}
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2){
			return rect.getBounds2D();
		}
		
	}

	@Override
	public void batchActionPerformed(BatchActions ba, int row, int col) {
		// TODO Auto-generated method stub
		
	}
}
