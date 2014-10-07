package ui.cheats;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import ui.HUDPanel;
import client.ClientGame;

public abstract class CheatSwitch extends JPanel {
	private static final Image onSwitch = HUDPanel.loadImage("OnSwitch.png");
	private static final Image offSwitch = HUDPanel.loadImage("OffSwitch.png");
	private static final int SWITCH_WIDTH = 50;
	private static final int SWITCH_HEIGHT = 25;

	private boolean switchedOn;
	private ClientGame client;

	public CheatSwitch(ClientGame cl) {
		this.client = cl;
		this.switchedOn = false;
		setOpaque(false);
		setPreferredSize(new Dimension(SWITCH_WIDTH, SWITCH_HEIGHT));
		setupListener();
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (switchedOn) {
			g.drawImage(onSwitch, 0, 0, SWITCH_WIDTH, SWITCH_HEIGHT, null);
		} else {
			g.drawImage(offSwitch, 0, 0, SWITCH_WIDTH, SWITCH_HEIGHT, null);
		}
		super.paintComponent(g);
	}

	protected abstract void changeState();
	
	private void setupListener() {
		addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent arg0) {
				//do nothing
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
		});
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("switching");
				switchedOn = !switchedOn;
				changeState();
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
	}

}
