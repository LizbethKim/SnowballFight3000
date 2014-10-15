package ui.actions;

import gameworld.game.client.ClientGame;
import ui.gamewindow.UI;

/**
 * SelectItem is a key action that selects the inventory slot corresponding to
 * the number pressed
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class SelectItem extends KeyAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7095515531210410466L;
	private int inventoryNumber;

	public SelectItem(ClientGame cl, UI parent, int inventoryNumber) {
		super(cl, parent);
		this.inventoryNumber = inventoryNumber;
	}

	@Override
	protected void execute() {
		// select the item adjusting for the zero indexing in storage
		client.setSelectedIndex(inventoryNumber - 1);
	}

}
