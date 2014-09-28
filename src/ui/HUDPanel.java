package ui;

import gameworld.world.Board;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import client.Client;

public class HUDPanel extends JPanel {
	public static final String IMAGE_PATH = "src/ui/HUDAssets/";
	private static final Image healthBar = loadImage("HealthBar.png");
	private static final Image healthBase = loadImage("HealthBarBase.png");

	private static final double inventoryYProportion = 1.0 / 10.0;
	private static final double maxHealth = 100.0;

	private boolean inventoryHidden = false;
	private final double aspectRatio;

	private final Client client;
	private InventoryPanel inventoryPanel;

	public HUDPanel(Client cl, double aspectRatio) {
		this.client = cl;
		this.aspectRatio = aspectRatio;
	}

	public void setupInventory(int width, int height) {
		inventoryPanel = new InventoryPanel(client);
		add(inventoryPanel);
		setInventoryBounds();
		//inventoryPanel.setPreferredSize(new Dimension(bounds.width, bounds.height));
		
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		setInventoryBounds();
		System.out.println("resizing inventory, height = "+inventoryPanel.getHeight()+", width = "+inventoryPanel.getWidth());
		
	}
	
	public void inventorySize(){
		System.out.println("inventory size is: height = "+inventoryPanel.getHeight()+", width = "+inventoryPanel.getWidth());
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		setInventoryBounds();
	}

	private void setInventoryBounds() {
		int xPos = 0;
		System.out.println("height = "+getHeight());
		int yPos = (int) (getHeight() - getHeight()*inventoryYProportion);
		int width = (int) (getWidth() / aspectRatio);
		int height = getHeight() - yPos;
		System.out.println("YPos = "+yPos);
		inventoryPanel.setBounds(xPos, yPos, width, height);
	}
	
	@Override public void paintComponent(Graphics g){
		System.out.println("repainting HUD");
		paintHealthBar(g);
	}
	
	@Override public void paint(Graphics g){
		System.out.println("calling paint on HUD");
		setInventoryBounds();
		super.paint(g);
		
	}

	private void paintHealthBar(Graphics g) {
		final int xPos = (int) (this.getWidth() / 20 / aspectRatio);
		final int yPos = this.getHeight() / 20;
		final int width = (int) (this.getWidth() / 3 / aspectRatio);
		final int height = this.getHeight() / 11;
		final int healthXPos = xPos + (2 * width / 7);
		final int healthYPos = yPos + (height / 4);
		final double healthProportion = client.getPlayer().getHealth()
				/ maxHealth;
		final int healthWidth = (int) (width * 7 / 10 * healthProportion);
		final int healthHeight = height * 3 / 5;

		g.drawImage(healthBase, xPos, yPos, width, height, null);
		if (healthWidth > 0) {
			g.drawImage(healthBar, healthXPos, healthYPos, healthWidth,
					healthHeight, null);
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
