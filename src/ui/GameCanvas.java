package ui;

import gameworld.world.Board;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;

import client.Client;



public class GameCanvas extends Canvas {
	public static final String IMAGE_PATH = "src/ui/HUDAssets/";
	private static final Image healthBar = loadImage("HealthBar.png");
	private static final Image healthBase = loadImage("HealthBarBase.png");
	private static final Image inventorySlot = loadImage("InventorySlot.png");
	private static final Image hideItems = loadImage("HideItems.png");
	private static final Image showItems = loadImage("ShowItems.png");
	
	private static final double showHideXProportion = 1.0/20.0;
	private static final double inventoryYProportion = 1.0/10.0;
	
	
	
	private final Client client;
	private boolean inventoryHidden = false;
	

	public GameCanvas(Client cl) {
		this.client = cl;
		super.setCursor(new Cursor(Cursor.HAND_CURSOR));
		setupListeners();
		
		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		HashSet<String> availableNames = new HashSet();

		for (String name : env.getAvailableFontFamilyNames()) {
			availableNames.add(name);
		}
	}

	/**
	 * draws the board and players
	 */
	public void paint(Graphics g) {
		paintHealthBar(g);
		paintShowHideButton(g);
		if(!inventoryHidden){
		paintInventory(g);
		}
	}
	
	
	
	private void paintShowHideButton(Graphics g){
		final int width = (int)(this.getWidth()*showHideXProportion);
		final int height = width*2;
		final int xPos = 0;
		final int yPos = this.getHeight()-height;
		Image showHideButton;
		if(inventoryHidden){
		showHideButton = showItems.getScaledInstance(width, height, 0);
		} else {
		showHideButton = hideItems.getScaledInstance(width, height, 0);
		}	
		g.drawImage(showHideButton, xPos, yPos, null, null);
	}
	
	private void paintInventory(Graphics g){
		final int inventoryNumber = 10;
		int xPos = getShowHideWidth();
		final int size = (this.getWidth()-xPos)/inventoryNumber;
		final int yPos = this.getHeight()-size;
		
		Image slot = inventorySlot.getScaledInstance(size, size, 0);
		for(int i = 0; i < inventoryNumber; i++){
			g.drawImage(slot, xPos, yPos, null, null);
			xPos += size;
		}
	}
	
	private void paintHealthBar(Graphics g){
		final int healthRemaining = 1;
		
		final int xPos = this.getWidth()/20;
		final int yPos = this.getHeight()/20;
		final int width = this.getWidth()/3;
		final int height = this.getHeight()/11;
		final int healthXPos = xPos+(width/4);
		final int healthYPos = yPos+(height/4);
		final int xDifference = healthXPos-xPos;
		final int healthWidth = (width - xDifference)/healthRemaining;
		final int healthHeight = height*3/5;
		
		
		Image base = healthBase.getScaledInstance(width, height, 0);
		Image bar = healthBar.getScaledInstance(healthWidth, healthHeight, 0);
		g.drawImage(base, xPos, yPos, null, null);
		g.drawImage(bar, healthXPos, healthYPos, null, null);
	}
	
	private void setupListeners(){
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dealWithClick(e);
			}
		});
	}
	
	private int getInventoryYPos(){
		return this.getHeight()-(int)(this.getHeight()*inventoryYProportion);
	}
	
	private int getShowHideWidth(){
		return (int)(this.getWidth()*showHideXProportion);
	}
	
	private void dealWithClick(MouseEvent e){
		System.out.println("dealing with click");
		int x = e.getX();
		int y = e.getY();
		if(y>0 && x>0 && y > getInventoryYPos() && x < getShowHideWidth()){
			System.out.println("show/hide");
			inventoryHidden = !inventoryHidden;
			repaint();
		}
	}
	
	/**
	 * Load an image from the file system, using a given filename.
	 * 
	 * @param filename
	 * @return
	 */
	public static Image loadImage(String filename) {
		// using the URL means the image loads when stored
		// in a jar or expanded into individual files.
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(IMAGE_PATH + filename));
			return img;
		} catch (IOException e) {
			// we've encountered an error loading the image. There's not much we
			// can actually do at this point, except to abort the game.
			throw new RuntimeException("Unable to load image: " + filename);
		}

	}
}
