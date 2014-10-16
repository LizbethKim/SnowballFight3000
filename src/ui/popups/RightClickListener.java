package ui.popups;

import gameworld.game.client.ClientGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ui.gamewindow.InventoryPanel;

/**
 * The RightClickListener is responsible for listening to mouse inputs on the
 * inventory panel and selecting the clicked item or launching the right click
 * menu
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

	/**
	 * responds to a mouse click by toggling the show/hide button, selecting the
	 * item and/or launching the right click menu depending on the position of
	 * the click
	 * 
	 * @param e the MouseEvent from the click
	 */
	private void respondToClick(MouseEvent e) {
		// get position of click
		int x = e.getX();
		int y = e.getY();
		// if the click was on the show hide button, toggle it
		if (panel.onShowHideButton(x, y)) {
			panel.toggleShowHide();
		} else {
			// otherwise if on an item, select it
			int selected = panel.getSelectedItem(x, y);
			if (selected > 0) {
				client.setSelectedIndex(selected - 1);
			}
		}
	}
	
	private void respondToRightClick(MouseEvent e){
		if (e.isPopupTrigger()) {
			System.out.println("showing right click");
			// now launch right-click popup if appropriate
			showPopup(e);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		respondToClick(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		respondToRightClick(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		respondToRightClick(e);
	}

	/**
	 * Display a right click menu at the position of the mouse click
	 * @param e the mouse event
	 */
	private void showPopup(MouseEvent e) {
		RightClickMenu menu = new RightClickMenu(client);
		menu.show(e.getComponent(), e.getX(), e.getY());
	}
}
