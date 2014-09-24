package ui;

import gameworld.world.Board;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
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
	
	private final Client client;

	public GameCanvas(Client cl) {
		this.client = cl;
		super.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
	}
	
	private void paintHealthBar(Graphics g){
		final int xPos = this.getWidth()/10;
		final int yPos = this.getHeight()/10;
		final int width = this.getWidth()/5;
		final int height = this.getHeight()/14;
		final int healthXPos = xPos+(height/4);
		final int healthYPos = yPos+(width/4);
		
		
		Image base = healthBase.getScaledInstance(width, height, 0);
		Image bar = healthBar.getScaledInstance(width, height, 0);
		g.drawImage(base, xPos, yPos, null, null);
		g.drawImage(bar, healthXPos, healthYPos, null, null);
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
