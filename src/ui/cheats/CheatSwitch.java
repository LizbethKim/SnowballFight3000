package ui.cheats;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import ui.HUDPanel;
import client.Client;

public abstract class CheatSwitch extends JPanel {
	private static final Image onSwitch = HUDPanel.loadImage("OnSwitch.png");
	private static final Image offSwitch = HUDPanel.loadImage("OffSwitch.png");
	private static final int SWITCH_WIDTH = 50;
	private static final int SWITCH_HEIGHT = 30;

	private boolean switchedOn;
	private Client client;

	public CheatSwitch(Client cl) {
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
