package ui;

import gameworld.game.client.ClientGame;
import graphics.assets.Objects;

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

public class InventoryPanel extends JPanel {
	private static final Image inventorySlot = HUDPanel
			.loadImage("InventorySlot.png");
	private static final Image selectedSlot = HUDPanel
			.loadImage("SelectedSlot.png");
	private static final Image hideItems = HUDPanel.loadImage("HideItems.png");
	private static final Image showItems = HUDPanel.loadImage("ShowItems.png");

	private static final double showHideXProportion = 1.0 / 20.0;
	private boolean inventoryHidden;

	private ClientGame client;

	public InventoryPanel(ClientGame cl) {
		client = cl;
		inventoryHidden = false;
		setOpaque(false);
		super.setCursor(new Cursor(Cursor.HAND_CURSOR));
		setupListeners();

	}

	@Override
	public void paintComponent(Graphics g) {
		if (getHeight() == 0 || getWidth() == 0)
			return;
		paintShowHideButton(g);
		if (!inventoryHidden) {
			paintInventory(g);
			super.paintComponent(g);
		}
	}

	private void paintShowHideButton(Graphics g) {
		final int width = getShowHideWidth();
		final int height = getHeight();
		final int xPos = 0;
		final int yPos = 0;
		Image showHideButton;
		if (inventoryHidden) {
			showHideButton = showItems.getScaledInstance(width, height, 0);
		} else {
			showHideButton = hideItems.getScaledInstance(width, height, 0);
		}
		g.drawImage(showHideButton, xPos, yPos, null, null);
	}

	private void paintInventory(Graphics g) {
		List<Objects> items = client.getPlayerInventory();
		// System.out.println("INVENTORY SIZE IS:           "+ items.size());
		final int inventoryNumber = items.size();
		int xPos = getShowHideWidth();
		int yPos = 0;
		final int size = this.getHeight();
		Image slot = inventorySlot.getScaledInstance(size, size, 0);
		Image selected = selectedSlot.getScaledInstance(size, size, 0);
		for (int i = 1; i <= inventoryNumber; i++) {
			if (client.getSelectedIndex() == i) {
				drawSlot(selected, xPos, yPos, size, i, g);
			} else{
				drawSlot(slot, xPos, yPos, size, i, g);
			}
			if (i < items.size() && items.get(i) != null) {

				drawItem(xPos, yPos, size, items.get(i - 1), g);
			}
			xPos += size;
		}
	}

	private void drawItem(int xPos, int yPos, int size, Objects item, Graphics g) {
		int itemXPos = xPos + size / 6;
		int itemYPos = yPos + size / 6;
		int itemSize = size - 2 * (size / 6);
		Image itemImage = item.imgs[0];

		g.drawImage(itemImage, itemXPos, itemYPos, itemSize, itemSize, null);
	}

	private void drawSlot(Image slot, int xPos, int yPos, int size,
			int inventoryNum, Graphics g) {
		int fontSize = size / 6;
		g.drawImage(slot, xPos, yPos, null, null);
		g.setFont(new Font("Times", Font.PLAIN, fontSize));
		int numberXPos = xPos + size / 12;
		int numberYPos = size / 30 + fontSize;
		g.setColor(Color.white);
		g.drawString("" + (inventoryNum), numberXPos, numberYPos);
	}

	private int getShowHideWidth() {
		return Math.max(1, (int) (this.getWidth() * showHideXProportion));
	}

	private boolean onShowHideButton(int x, int y) {
		return y > 0 && x > 0 && y < getHeight() && x < getShowHideWidth();
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

	private void setupListeners() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dealWithClick(e);
			}
		});

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

		addMouseListener(new RightClickListener(client));
	}

}
