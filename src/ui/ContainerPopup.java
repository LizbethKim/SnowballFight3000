package ui;

import gameworld.game.client.ClientGame;
import graphics.assets.Objects;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

	private static final int slotSize = 100;
	private static final int maxColumn = 3;

	private ClientGame client;
	private int selectedItem;
	private int maxItems;
	private List<Objects> items;
	private boolean interactable;

	public ContainerPopup(ClientGame cl, UI ui, String title,
			List<Objects> items, boolean interactable) {
		super(ui, title, ModalityType.APPLICATION_MODAL);
		this.client = cl;
		this.items = items;
		this.selectedItem = 1;
		this.maxItems = items.size();
		this.items = items;
		this.interactable = interactable;

		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		setupComponents();
		this.setLocationRelativeTo(ui);
		setTitle(title);
		addKeyListener(this);

		pack();
		setResizable(false);
		setVisible(true);
	}

	private void setupComponents() {
		int width =Math.min(slotSize * maxItems, slotSize * maxColumn); 
		int height = slotSize
				* ((int) Math.ceil((double) maxItems / maxColumn));
		JPanel containerPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				paintContainer(g);
			}
		};

		containerPanel.setPreferredSize(new Dimension(width, height));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,1));
		
		JButton close = new JButton("Close Chest");
		buttonPanel.setPreferredSize(new Dimension(width, slotSize/2));
		buttonPanel.add(close);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		add(containerPanel);
		add(buttonPanel);

	}

	public void paintContainer(Graphics g) {
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
				drawItem(items.get(i).imgs[0], xPos, yPos, slotSize, g);
			}
			if (column + 1 < maxColumn) {
				column++;
			} else {
				column = 0;
				row++;
			}
		}
	}

	private void drawItem(Image item, int xPos, int yPos, int size, Graphics g) {
		int itemXPos = xPos + size / 6;
		int itemYPos = yPos + size / 6;
		int itemSize = size - 2 * (size / 6);
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
				client.dropIntoContainer(1);
			} else if (key == KeyEvent.VK_2) {
				client.dropIntoContainer(2);
			} else if (key == KeyEvent.VK_3) {
				client.dropIntoContainer(3);
			} else if (key == KeyEvent.VK_4) {
				client.dropIntoContainer(4);
			} else if (key == KeyEvent.VK_5) {
				client.dropIntoContainer(5);
			} else if (key == KeyEvent.VK_6) {
				client.dropIntoContainer(6);
			} else if (key == KeyEvent.VK_7) {
				client.dropIntoContainer(7);
			} else if (key == KeyEvent.VK_8) {
				client.dropIntoContainer(8);
			} else if (key == KeyEvent.VK_9) {
				client.dropIntoContainer(9);
			}
		}
		repaint();
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
}
