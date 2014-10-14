package ui.popups;

import gameworld.game.client.ClientGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ui.gamewindow.InventoryPanel;

/**
 * The RightClickListener is responsible for listening to mouse inputs on the
 * inventory panel and selecting the clicked item or launching the right click menu
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class RightClickListener extends MouseAdapter {
	private ClientGame client;
	private InventoryPanel panel;

	public RightClickListener(ClientGame c, InventoryPanel panel) {
		super();
		this.client = c;
		this.panel = panel;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int selected = getSelectedItem(e.getX(), e.getY());

		if (selected > 0) {
			client.setSelectedIndex(selected - 1);
			if (e.isPopupTrigger()) {
				doPop(e, selected);
			}
		}
	}

	private int getSelectedItem(int x, int y) {
		int size = panel.getHeight();
		int xStart = panel.getShowHideWidth();
		if (x < xStart) {
			return -1;
		}
		return (int) Math.ceil((double) (x - xStart) / size);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int selected = getSelectedItem(e.getX(), e.getY());

		if (selected > 0) {
			client.setSelectedIndex(selected - 1);
			if (e.isPopupTrigger()) {
				doPop(e, selected);
			}
		}
	}

	private void doPop(MouseEvent e, int selected) {
		RightClickMenu menu = new RightClickMenu(client, selected);
		menu.show(e.getComponent(), e.getX(), e.getY());
	}
}
