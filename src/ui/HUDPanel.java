package ui;

import gameworld.world.Board;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
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
	private static final Image inventorySlot = loadImage("InventorySlot.png");
	private static final Image hideItems = loadImage("HideItems.png");
	private static final Image showItems = loadImage("ShowItems.png");

	private static final double showHideXProportion = 1.0 / 20.0;
	private static final double inventoryYProportion = 1.0 / 10.0;
	private static final double maxHealth = 100.0;

	private final Client client;
	private boolean inventoryHidden = false;
	private final double aspectRatio;

	public HUDPanel(Client cl, double aspectRatio) {
		this.client = cl;
		this.aspectRatio = aspectRatio;
		setupListeners();
		// this.set
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
		if (!inventoryHidden) {
			paintInventory(g);
		}
	}

	private void paintShowHideButton(Graphics g) {
		final int width = (int) (this.getWidth() * showHideXProportion / aspectRatio);
		final int height = getHeight() - getInventoryYPos();
		final int xPos = 0;
		final int yPos = getInventoryYPos();
		Image showHideButton;
		if (inventoryHidden) {
			showHideButton = showItems.getScaledInstance(width, height, 0);
		} else {
			showHideButton = hideItems.getScaledInstance(width, height, 0);
		}
		g.drawImage(showHideButton, xPos, yPos, null, null);
	}

	private void paintInventory(Graphics g) {
		final int inventoryNumber = 9;
		int xPos = getShowHideWidth();
		final int size = this.getHeight() - getInventoryYPos();
		int fontSize = size / 6;
		Image slot = inventorySlot.getScaledInstance(size, size, 0);
		for (int i = 0; i < inventoryNumber; i++) {
			g.drawImage(slot, xPos, getInventoryYPos(), null, null);
			g.setFont(new Font("Times", Font.PLAIN, fontSize));
			int numberXPos = xPos + size / 12;
			int numberYPos = getInventoryYPos() + size / 30 + fontSize;
			g.setColor(Color.white);
			g.drawString("" + (i + 1), numberXPos, numberYPos);
			xPos += size;
		}
	}

	private void paintHealthBar(Graphics g) {
		final int xPos = (int)(this.getWidth() / 20 / aspectRatio);
		final int yPos = this.getHeight() / 20;
		final int width = (int)(this.getWidth() / 3 / aspectRatio);
		final int height = this.getHeight() / 11;
		final int healthXPos = xPos + (2*width / 7);
		final int healthYPos = yPos + (height / 4);
		final double healthProportion = client.getPlayer().getHealth() / maxHealth;
		final int healthWidth = (int)(width * 7/10 * healthProportion);
		final int healthHeight = height * 3 / 5;

		g.drawImage(healthBase, xPos, yPos, width, height, null);
		if (healthWidth > 0) {
			g.drawImage(healthBar, healthXPos, healthYPos, healthWidth, healthHeight, null);
		}

	}

	private void setupListeners() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dealWithClick(e);
			}
		});

		addMouseMotionListener(new MotionListener());

	}

	private void updateCursor(MouseEvent e) {
		if (onShowHideButton(e.getX(), e.getY())) {
			super.setCursor(new Cursor(Cursor.HAND_CURSOR));
		} else {
			super.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	private int getInventoryYPos() {
		return this.getHeight()
				- (int) (this.getHeight() * inventoryYProportion);
	}

	private int getShowHideWidth() {
		return (int) (this.getWidth() * showHideXProportion);
	}

	private boolean onShowHideButton(int x, int y) {
		return y > 0 && x > 0 && y > getInventoryYPos()
				&& x < getShowHideWidth();
	}

	private void dealWithClick(MouseEvent e) {
		// RB: Fix mouse hover when window is resized
		System.out.println("dealing with click");
		int x = e.getX();
		int y = e.getY();
		if (onShowHideButton(x, y)) {
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

	private class MotionListener implements MouseMotionListener {
		@Override
		public void mouseMoved(MouseEvent e) {
			updateCursor(e);
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// do nothing
		}
	}
}
