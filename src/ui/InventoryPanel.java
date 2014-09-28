package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import client.Client;

public class InventoryPanel extends JPanel{
	private static final Image inventorySlot = HUDPanel.loadImage("InventorySlot.png");
	private static final Image hideItems = HUDPanel.loadImage("HideItems.png");
	private static final Image showItems = HUDPanel.loadImage("ShowItems.png");
	
	private static final double showHideXProportion = 1.0 / 20.0;
	private boolean inventoryHidden;
	
	private Client client;
	
	public InventoryPanel(Client cl){
		client = cl;
		inventoryHidden = false;
		setOpaque(false);
		super.setCursor(new Cursor(Cursor.HAND_CURSOR));
		setupListeners();
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		System.out.println("repainting inventory, height = "+getHeight()+", width = "+getWidth());
		if(getHeight() == 0 || getWidth() == 0)
			return;
		paintShowHideButton(g);
		if(!inventoryHidden){
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
		final int inventoryNumber = 9;
		int xPos = getShowHideWidth();
		int yPos = 0;
		final int size = this.getHeight();
		int fontSize = size / 6;
		Image slot = inventorySlot.getScaledInstance(size, size, 0);
		for (int i = 0; i < inventoryNumber; i++) {
			g.drawImage(slot, xPos, yPos, null, null);
			g.setFont(new Font("Times", Font.PLAIN, fontSize));
			int numberXPos = xPos + size / 12;
			int numberYPos = size / 30 + fontSize;
			g.setColor(Color.white);
			g.drawString("" + (i + 1), numberXPos, numberYPos);
			xPos += size;
		}
	}
	
	private int getShowHideWidth(){
			return Math.max(1, (int) (this.getWidth() * showHideXProportion));
	}
	
	private boolean onShowHideButton(int x, int y) {
		return y > 0 && x > 0 && y < getHeight()
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
	
	private void setupListeners() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dealWithClick(e);
			}
		});

		addMouseListener(new RightClickListener(client));
	}

}
