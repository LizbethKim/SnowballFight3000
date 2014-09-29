package ui;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ContainerPopup extends JPanel {
	private static final Image containerSlot = HUDPanel
			.loadImage("ContainerSlot.png");

	private static final int slotSize = 100;
	private static final int containerSize = 9;
	private static final int maxColumn = 3;

	public ContainerPopup() {
		setPreferredSize(new Dimension(slotSize*(maxColumn), slotSize*(containerSize/maxColumn)));
		//setLayout(new GridLayout(0,3));
	}

	@Override
	public void paintComponent(Graphics g){
		Image slot = containerSlot.getScaledInstance(slotSize, slotSize, 0);
		int row = 0;
		int column = 0;
		for(int i = 0; i != containerSize; i++){
			g.drawImage(slot, column*slotSize, row*slotSize, null, null);
			if(column < maxColumn){
				column++;
			} else {
				column = 0;
				row++;
			}
		}
	}
	
	public void show() {
		JOptionPane.showMessageDialog(null, this, "Contents of Chest",
				JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void main(String[] args){
		new ContainerPopup().show();
	}

}
