package ui.gamewindow;

import gameworld.game.client.ClientGame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * The HUDPanel (Heads up display panel) displays or holds components that
 * display all the information about the players character including their
 * score, current health and inventory items
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class HUDPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2839333094835117084L;
	public static final String IMAGE_PATH = "src/ui/assets/";
	private static final Image healthBar = loadImage("HealthBar.png");
	private static final Image healthBase = loadImage("HealthBarBase.png");

	private static final double inventoryYProportion = 1.0 / 10.0;
	private static final double maxHealth = 100.0;

	private final double aspectRatio;

	private final ClientGame client;
	private InventoryPanel inventoryPanel;

	public HUDPanel(ClientGame cl, double aspectRatio) {
		this.client = cl;
		this.aspectRatio = aspectRatio;
	}

	/**
	 * sets up the inventory panel
	 */
	public void setupInventory() {
		inventoryPanel = new InventoryPanel(client);
		add(inventoryPanel);
		setInventoryBounds();
	}

	/**
	 * sets the size of the hud panel and bounds of the inventory panel based on
	 * the given width and height
	 */
	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		setInventoryBounds();
	}

	/**
	 * sets the bounds of the hud panel and inventory panel based on the given
	 * width and height
	 */
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		setInventoryBounds();
	}

	/**
	 * set the inventory bounds based on its proportions and the aspect ratio
	 */
	public void setInventoryBounds() {
		int xPos = 0;
		int yPos = (int) (getHeight() - getHeight() * inventoryYProportion);
		int width = (int) (getWidth() / aspectRatio);
		int height = getHeight() - yPos;
		inventoryPanel.setBounds(xPos, yPos, width, height);
	}

	@Override
	public void paintComponent(Graphics g) {
		// paints the health bar and score
		paintHealthBar(g);
		paintScore(g);
		paintItemDescription(g);
	}

	@Override
	public void paint(Graphics g) {
		// set the bounds
		setInventoryBounds();
		super.paint(g);

	}

	/**
	 * paints the player's health bar int he left hand corner of the hud
	 * 
	 * @param g
	 *            the graphics object to paint on
	 */
	private void paintHealthBar(Graphics g) {
		// get the size and position of the base of the health bar relative to
		// the size and aspect ratio of the game
		final int xPos = (int) (this.getWidth() / 20 / aspectRatio);
		final int yPos = this.getHeight() / 20;
		final int width = (int) (this.getWidth() / 3 / aspectRatio);
		final int height = this.getHeight() / 11;

		// get the position and size of the health itself
		final int healthXPos = xPos + (2 * width / 7);
		final int healthYPos = yPos + (height / 4);
		final double healthProportion = client.getPlayerHealth() / maxHealth;
		final int healthWidth = (int) (width * 7 / 10 * healthProportion);
		final int healthHeight = height * 3 / 5;

		// now draw the health base, and health if there is any left
		g.drawImage(healthBase, xPos, yPos, width, height, null);
		if (healthWidth > 0) {
			g.drawImage(healthBar, healthXPos, healthYPos, healthWidth,
					healthHeight, null);
		}
	}

	/**
	 * paints the score in the top right corner of the hud
	 * 
	 * @param g
	 *            the graphics object to paint on
	 */
	private void paintScore(Graphics g) {
		// get size and position relative to the size of the game and
		// aspect ratio
		final int xPos = (int) (14 * this.getWidth() / 20 / aspectRatio);
		final int yPos = 2 * this.getHeight() / 20;
		final int fontSize = this.getHeight() / 18;
		g.setColor(Color.white);
		g.setFont(new Font("Times", Font.BOLD, fontSize));
		g.setColor(Color.WHITE);
		g.drawString("Score: " + client.getPlayerScore(), xPos + 1, yPos);
		g.drawString("Score: " + client.getPlayerScore(), xPos - 1, yPos);
		g.drawString("Score: " + client.getPlayerScore(), xPos, yPos + 1);
		g.drawString("Score: " + client.getPlayerScore(), xPos, yPos - 1);
		g.setColor(Color.WHITE);
		g.drawString("Score: " + client.getPlayerScore(), xPos, yPos);

	}

	/**
	 * paints the description of the item last inspected
	 * @param g
	 */
	private void paintItemDescription(Graphics g) {
		final int xPos = (int) (this.getWidth() / 2 / aspectRatio);
		final int yPos = this.getHeight() / 2;
		setFont(new Font("Times", Font.PLAIN, 14));
		
		String descr = client.getLastInspected();
		g.drawString(descr, xPos, yPos);
	}

	/**
	 * Load an image from the file system, using a given filename.
	 * 
	 * @param filename
	 *            the name of the file to load
	 * @return the fetched image
	 */
	public static Image loadImage(String filename) {
		// loads in the images which are stored in the jar
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(IMAGE_PATH + filename));
			return img;
		} catch (IOException e) {
			// can't load an image so abort the game
			throw new RuntimeException("Unable to load image: " + filename);
		}

	}
}
