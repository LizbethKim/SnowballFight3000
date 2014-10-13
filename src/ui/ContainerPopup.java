package ui;

import gameworld.game.client.ClientGame;
import gameworld.game.client.NotAContainerException;
import graphics.assets.Objects;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ContainerPopup extends JDialog implements KeyListener {
	private static final Image containerSlot = HUDPanel
			.loadImage("ContainerSlot.png");
	private static final Image selectedContainerSlot = HUDPanel
			.loadImage("SelectedContainerSlot.png");
	private static final Image exitButton = HUDPanel
			.loadImage("CloseContainer.png");

	private static final int slotSize = 100;
	private static final int maxColumn = 3;

	private ClientGame client;
	private int selectedItem;
	private int maxItems;
	private List<Objects> items;
	private boolean interactable;
	private int width;
	private int height;
	private JPanel buttonPanel;
	private JPanel containerPanel;

	public ContainerPopup(ClientGame cl, UI ui, String title, boolean interactable) {
		super(ui, title, ModalityType.APPLICATION_MODAL);
		this.client = cl;
		this.selectedItem = 1;
		this.maxItems = items.size();
		this.interactable = interactable;
		refresh();

		width = Math.min(slotSize * maxItems, slotSize * maxColumn);
		height = slotSize * ((int) Math.ceil((double) maxItems / maxColumn));

		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		setupComponents();
		this.setLocationRelativeTo(ui);
		setTitle(title);
		addKeyListener(this);
		setupMotionListener();

		pack();
		setResizable(false);
		setVisible(true);
	}

	private void setupExitButton() {
		// setup panel
		buttonPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				paintExitButton(g);
			}
		};
		buttonPanel.setPreferredSize(new Dimension(width, slotSize / 2));

		// add mouse listener to exit on click
		buttonPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		add(buttonPanel);
	}

	private void setupContainerPanel() {
		containerPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				paintContainer(g);
			}
		};
		containerPanel.setPreferredSize(new Dimension(width, height));
		add(containerPanel);
	}

	private void setupComponents() {
		setupContainerPanel();
		setupExitButton();
	}

	private void paintExitButton(Graphics g) {
		g.drawImage(exitButton, 0, 0, width, slotSize / 2, null);
	}

	private void paintContainer(Graphics g) {
		Image slot = containerSlot.getScaledInstance(slotSize, slotSize, 0);
		Image selectedSlot = selectedContainerSlot.getScaledInstance(slotSize,
				slotSize, 0);
		int row = 0;
		int column = 0;
		for (int i = 0; i != maxItems; i++) {
			int xPos = column * slotSize;
			int yPos = row * slotSize;

			if (i + 1 == selectedItem) {
				g.drawImage(selectedSlot, xPos, yPos, null, null);
			} else {
				g.drawImage(slot, xPos, yPos, null, null);
			}

			if (items.get(i) != null) {
				Image item = items.get(i).imgs[0];
				drawItem(items.get(i).imgs[0], xPos, yPos, g);
			}
			if (column + 1 < maxColumn) {
				column++;
			} else {
				column = 0;
				row++;
			}
		}
	}

	private void refresh() {
		try {
			this.items = client.getContents();
			repaint();
		} catch (NotAContainerException e) {
			// do nothing
		}

	}

	private void drawItem(Image item, int xPos, int yPos, Graphics g) {
		int itemXPos = xPos + slotSize / 6;
		int itemYPos = yPos + slotSize / 6;
		int itemSize = slotSize - 2 * (slotSize / 6);
		g.drawImage(item, itemXPos, itemYPos, itemSize, itemSize, null);
	}

	private void dealWithKeyTyped(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
			changeSelection(-maxColumn);
		} else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
			changeSelection(maxColumn);
		} else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			changeSelection(-1);
		} else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			changeSelection(1);
		} else if (interactable) {
			if (key == KeyEvent.VK_Z) {
				client.takeItemFromContainer(selectedItem);
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
		refresh();
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
			public void mouseDragged(MouseEvent arg0) {
			}

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
