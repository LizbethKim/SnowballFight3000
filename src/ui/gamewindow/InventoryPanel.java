package ui.gamewindow;

import gameworld.game.client.ClientGame;
import graphics.assets.Entities;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JPanel;

import ui.popups.RightClickListener;

/**
 * The InventoryPanel is a class that displays the inventory of the player, and
 * allows for selecting, picking up and dropping of items from the inventory
 * 
 * @author Ryan Burnell, 300279172
 * 
 */
public class InventoryPanel extends JPanel {
	// load in images
	private static final Image inventorySlot = HUDPanel
			.loadImage("InventorySlot.png");
	private static final Image selectedSlot = HUDPanel
			.loadImage("SelectedSlot.png");
	private static final Image hideItems = HUDPanel.loadImage("HideItems.png");
	private static final Image showItems = HUDPanel.loadImage("ShowItems.png");

	// proportion constant
	private static final double showHideXProportion = 1.0 / 20.0;

	// fields
	private boolean inventoryHidden;
	private ClientGame client;

	public InventoryPanel(ClientGame cl) {
		// setup panel and listeners
		client = cl;
		inventoryHidden = false;
		setOpaque(false);
		super.setCursor(new Cursor(Cursor.HAND_CURSOR));
		setupListeners();

	}

	/**
	 * paints the show/hide button and inventory slots (if shown)
	 * 
	 * @param g
	 *            the graphics object to draw on
	 */
	@Override
	public void paintComponent(Graphics g) {
		// check the panel has a real size
		if (getHeight() == 0 || getWidth() == 0)
			return;

		// paint the show hide button and inventory if not hidden
		paintShowHideButton(g);
		if (!inventoryHidden) {
			paintInventory(g);
			super.paintComponent(g);
		}
	}

	/**
	 * paints the show/hide button
	 * 
	 * @param g
	 *            the graphics object to draw on
	 */
	private void paintShowHideButton(Graphics g) {
		// get bounds of the button
		final int width = getShowHideWidth();
		final int height = getHeight();
		final int xPos = 0;
		final int yPos = 0;
		Image showHideButton;

		if (inventoryHidden) {
			// if hidden set image to the show button
			showHideButton = showItems.getScaledInstance(width, height, 0);
		} else {
			// otherwise set image to the hide button
			showHideButton = hideItems.getScaledInstance(width, height, 0);
		}

		// now draw
		g.drawImage(showHideButton, xPos, yPos, null, null);
	}

	/**
	 * paints the inventory slots and any items in the inventory
	 * 
	 * @param g
	 *            the graphics object to draw on
	 */
	private void paintInventory(Graphics g) {
		// get the inventory
		List<Entities> items = client.getPlayerInventory();

		// set number to max size of inventory
		final int inventoryNumber = items.size();

		// first slot is drawn at the end of the show/hide button
		int xPos = getShowHideWidth();
		int yPos = 0;

		// get size based on height of inventory panel
		final int size = this.getHeight();

		// scale images to required size
		Image slot = inventorySlot.getScaledInstance(size, size, 0);
		Image selected = selectedSlot.getScaledInstance(size, size, 0);

		// for each possible inventory slot
		for (int i = 1; i <= inventoryNumber; i++) {

			// if selected paint the selected slot
			if (i == client.getSelectedIndex() + 1) {
				drawSlot(selected, xPos, yPos, size, i, g);
			} else {
				// otherwise paint normal slot
				drawSlot(slot, xPos, yPos, size, i, g);
			}

			// if there is an item in the slot
			if (items.get(i - 1) != null) {
				// draw it
				drawItem(xPos, yPos, size, items.get(i - 1), g);
			}
			// increment position by the size of the slots
			xPos += size;
		}
	}

	/**
	 * draws the item at the given position
	 * 
	 * @param slotXPos
	 *            the x position of the slot to draw in
	 * @param slotYPos
	 *            the y position of the slot to draw in
	 * @param size
	 *            the size to draw the item
	 * @param item
	 *            the item object
	 * @param g
	 *            the graphics object to draw on
	 */
	private void drawItem(int slotXPos, int slotYPos, int size, Entities item,
			Graphics g) {
		// get the position and size to draw the item inside the slot
		int itemXPos = slotXPos + size / 6;
		int itemYPos = slotYPos + size / 6;
		int itemSize = size - 2 * (size / 6);
		// get the image of the item
		Image itemImage = item.imgs[0];
		// now draw
		g.drawImage(itemImage, itemXPos, itemYPos, itemSize, itemSize, null);
	}

	/**
	 * draws the slot given its image, position, size and the number of the slot
	 * 
	 * @param image
	 *            the slot image
	 * @param xPos
	 *            the x position to draw at
	 * @param yPos
	 *            the y position to draw at
	 * @param size
	 *            the size to draw
	 * @param inventoryNum
	 *            the number of the slot
	 * @param g
	 */
	private void drawSlot(Image image, int xPos, int yPos, int size,
			int inventoryNum, Graphics g) {
		// set font size relative to the size of the slot
		int fontSize = size / 6;
		// draw the slot image
		g.drawImage(image, xPos, yPos, null, null);

		// now get the position and set colour and font of the text
		int numberXPos = xPos + size / 12;
		int numberYPos = size / 30 + fontSize;
		g.setFont(new Font("Times", Font.PLAIN, fontSize));
		g.setColor(Color.white);
		// finally draw the text
		g.drawString("" + (inventoryNum), numberXPos, numberYPos);
	}

	/**
	 * get the width of the show/hide button
	 * 
	 * @return returns the width of the show/hide button
	 */
	public int getShowHideWidth() {
		// if the window is too small return 1, else return the show/hide width
		return Math.max(1, (int) (this.getWidth() * showHideXProportion));
	}

	/**
	 * returns boolean based on whether the point at the given coordinates is
	 * within the show/hide button
	 * 
	 * @param x
	 *            x coordinate of the point
	 * @param y
	 *            y coordinate of the point
	 * @return true if the point is within the show/hide width bounds otherwise
	 *         false
	 */
	public boolean onShowHideButton(int x, int y) {
		return y > 0 && x > 0 && y < getHeight() && x < getShowHideWidth();
	}
	
	/**
	 * returns the index of the inventory item at a given position
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 * @return the index of the item
	 */
	public int getSelectedItem(int x, int y) {
		//get the size and starting position of the items
		int size = getHeight();
		int itemsXStart = getShowHideWidth();
		//if the point is not on one of them return nonsense value
		if (x < itemsXStart || x > x*size + itemsXStart) {
			return -1;
		}
		//else return the index of the item
		return (int) Math.ceil((double) (x - itemsXStart) / size);
	}
	
	/**
	 * toggles the state of the show/hide button
	 */
	public void toggleShowHide(){
		inventoryHidden = !inventoryHidden;
	}

	/**
	 * adds the mouse listener and mouse motion listener for responding to user
	 * inputs
	 */
	private void setupListeners() {

		//add motion listener which changes the cursor to a hand when over the show/hide button
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// do nothing
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if (onShowHideButton(e.getX(), e.getY())) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				} else {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}

		});

		//add listener to deal with clicks on the inventory
		addMouseListener(new RightClickListener(client, this));
	}

}
