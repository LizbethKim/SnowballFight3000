package ui;


import graphics.assets.Objects;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ContainerPopup extends JPanel {
	private static final Image containerSlot = HUDPanel
			.loadImage("ContainerSlot.png");

	private static final int slotSize = 100;
	private static final int maxColumn = 3;
	
	private int maxItems;
	private List<Objects> items;

	public ContainerPopup(List<Objects> items) {
		this.maxItems = items.size();
		this.items = items;
		
		setPreferredSize(new Dimension(slotSize*(maxColumn), slotSize*(maxItems/maxColumn)));
	}

	@Override
	public void paintComponent(Graphics g){
		Image slot = containerSlot.getScaledInstance(slotSize, slotSize, 0);
		int row = 0;
		int column = 0;
		for(int i = 0; i != maxItems; i++){
			int xPos = column*slotSize;
			int yPos = row*slotSize;
			g.drawImage(slot, xPos, yPos, null, null);
			if(items.get(i) != null){
			Image item = items.get(i).imgs[0];
			drawItem(items.get(i).imgs[0], xPos, yPos, slotSize, g);
			}
			if(column < maxColumn){
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
	
	public void show() {
		JOptionPane.showMessageDialog(null, this, "Contents of Chest",
				JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void main(String[] args){
		List<Objects> objects = new ArrayList<Objects>();
		objects.add(Objects.KEY);
		objects.add(Objects.REDFLAG);
		objects.add(Objects.BLUEFLAG);
		objects.add(Objects.KEY);
		new ContainerPopup(objects).show();
	}

}
