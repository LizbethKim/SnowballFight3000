package ui.popups;

import gameworld.game.client.ClientGame;
import gameworld.game.client.NotAContainerException;
import graphics.assets.Objects;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;

import ui.gamewindow.HUDPanel;
import ui.gamewindow.UI;

/**
 * The ContainerPoup displays the contents of a container, and allows the user
 * to select and take items out of the container our put them in. Items are
 * navigated with the arrow keys.
 *
 * @author Ryan Burnell, 300279172
 *
 */

public class ContainerPopup extends JDialog implements KeyListener {
	// images to be drawn
	private static final Image containerSlot = HUDPanel
			.loadImage("ContainerSlot.png");
	private static final Image selectedContainerSlot = HUDPanel
			.loadImage("SelectedContainerSlot.png");
	private static final Image exitButton = HUDPanel
			.loadImage("CloseContainer.png");

	// the size of each slot
	private static final int SLOT_SIZE = 100;
	// the maximum slots to be drawn in a column
	private static final int MAX_COLUMN = 3;

	// fields
	private ClientGame client;
	private int selectedItem;
	private int maxItems;
	private List<Objects> items;
	private boolean inventoryContainer;
	private int width;
	private int height;
	private JPanel buttonPanel;
	private JPanel containerPanel;

	public ContainerPopup(ClientGame cl, UI ui, String title,
			boolean interactable) {
		// initialise fields
		super(ui, title, ModalityType.APPLICATION_MODAL);
		this.client = cl;
		this.selectedItem = 1;
		this.inventoryContainer = interactable;
		refreshItems();
		this.maxItems = items.size();
		this.width = width();
		this.height = height();
		setTitle(title);
		this.setLocationRelativeTo(ui);

		setupPopup();
	}

	/**
	 * sets up the components and listeners and displays the pop-up
	 */
	private void setupPopup() {
		// sets layout and adds components
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		setupComponents();

		// setup listeners
		addKeyListener(this);
		setupMotionListener();

		// now display
		pack();
		setResizable(false);
		setVisible(true);
	}

	/**
	 * get the width of the panel
	 *
	 * @return the width
	 */
	private int width() {
		// return the slot size multiplied by the number of columns
		return Math.min(SLOT_SIZE * maxItems, SLOT_SIZE * MAX_COLUMN);
	}

	/**
	 * get the width of the panel
	 *
	 * @return the width
	 */
	private int height() {
		// return the size of the slot multiplied by the number of rows
		return SLOT_SIZE * ((int) Math.ceil((double) maxItems / MAX_COLUMN));
	}

	/**
	 * adds the button which will exit the dialog when clicked
	 */
	private void setupExitButton() {
		// setup panel
		buttonPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				paintExitButton(g);
			}
		};
		buttonPanel.setPreferredSize(new Dimension(width, SLOT_SIZE / 2));

		// add mouse listener to exit on click
		buttonPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		add(buttonPanel);
	}

	/**
	 * sets up the panel for displaying the container slots
	 */
	private void setupContainerPanel() {
		containerPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				// paint container slots when panel is drawn
				paintContainer(g);
			}
		};
		containerPanel.setPreferredSize(new Dimension(width, height));
		add(containerPanel);
	}

	/**
	 * sets up the container panel and exit button
	 */
	private void setupComponents() {
		setupContainerPanel();
		setupExitButton();
	}

	/**
	 * draw the exit button
	 *
	 * @param g
	 *            the graphics object to draw on
	 */
	private void paintExitButton(Graphics g) {
		g.drawImage(exitButton, 0, 0, width, SLOT_SIZE / 2, null);
	}

	/**
	 * paint all the container slots and any items inside them
	 *
	 * @param g
	 *            the graphics object to draw on
	 */
	private void paintContainer(Graphics g) {
		// creat the images
		Image slot = containerSlot.getScaledInstance(SLOT_SIZE, SLOT_SIZE, 0);
		Image selectedSlot = selectedContainerSlot.getScaledInstance(SLOT_SIZE,
				SLOT_SIZE, 0);

		// initial slot
		int row = 0;
		int column = 0;

		// draw each slot and its item
		for (int i = 0; i != maxItems; i++) {
			// get the position of the slot
			int xPos = column * SLOT_SIZE;
			int yPos = row * SLOT_SIZE;

			// if this slot is selected
			if (i + 1 == selectedItem) {
				// draw with the selected image
				g.drawImage(selectedSlot, xPos, yPos, null, null);
			} else {
				// otherwise draw with normal image
				g.drawImage(slot, xPos, yPos, null, null);
			}

			// draw the item if there is one
			if (items.get(i) != null) {
				Image item = items.get(i).imgs[0];
				drawItem(item, xPos, yPos, g);
			}

			// now increment the row and column
			if (column + 1 < MAX_COLUMN) {
				column++;
			} else {
				column = 0;
				row++;
			}
		}
	}

	/**
	 * tries to set the items field to the contents of the desired container,
	 * disposing of the popup if there is no container
	 */
	private void refreshItems() {
		try {
			//if we are trying to open a container from the inventory
			if (inventoryContainer) {
				//get its contents
				this.items = client.getContentsOfSelected();
			} else {
				//otherwise we're trying to open a container in front of us
				//so get its contents
				this.items = client.getContents();
			}
			repaint();
		} catch (NotAContainerException e) {
			//no container so dispose of dialog
			dispose();

		}
	}

	private void drawItem(Image item, int xPos, int yPos, Graphics g) {
		int itemXPos = xPos + SLOT_SIZE / 6;
		int itemYPos = yPos + SLOT_SIZE / 6;
		int itemSize = SLOT_SIZE - 2 * (SLOT_SIZE / 6);
		g.drawImage(item, itemXPos, itemYPos, itemSize, itemSize, null);
	}

	private void dealWithKeyTyped(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
			changeSelection(-MAX_COLUMN);
		} else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
			changeSelection(MAX_COLUMN);
		} else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			changeSelection(-1);
		} else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			changeSelection(1);
		} else if (!inventoryContainer) {
			if (key == KeyEvent.VK_Z) {
				client.takeItemFromContainer(selectedItem - 1);
			} else if (key == KeyEvent.VK_1) {
				client.dropIntoContainer(0);
			} else if (key == KeyEvent.VK_2) {
				client.dropIntoContainer(1);
			} else if (key == KeyEvent.VK_3) {
				client.dropIntoContainer(2);
			} else if (key == KeyEvent.VK_4) {
				client.dropIntoContainer(3);
			} else if (key == KeyEvent.VK_5) {
				client.dropIntoContainer(4);
			} else if (key == KeyEvent.VK_6) {
				client.dropIntoContainer(5);
			} else if (key == KeyEvent.VK_7) {
				client.dropIntoContainer(6);
			} else if (key == KeyEvent.VK_8) {
				client.dropIntoContainer(7);
			} else if (key == KeyEvent.VK_9) {
				client.dropIntoContainer(8);
			}
		}
		refreshItems();
	}

	private void changeSelection(int selectionChange) {
		int newSelection = selectedItem + selectionChange;
		if (newSelection > 0 && newSelection <= maxItems) {
			selectedItem = newSelection;
			System.out.println("changing selection to:" + newSelection);
		}
	}

	// KeyListener Methods

	@Override
	public void keyPressed(KeyEvent arg0) {
		dealWithKeyTyped(arg0);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		dealWithKeyTyped(arg0);
	}

	private void setupMotionListener() {
		// add mouse motion listener to change cursor when hovered over
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if (getContentPane().getComponentAt(
						new Point(e.getX(), e.getY())) == buttonPanel) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				} else {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
	}
}
