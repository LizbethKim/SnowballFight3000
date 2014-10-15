package ui.actions;

import gameworld.game.client.ClientGame;
import ui.gamewindow.UI;

/**
 * DropItem is a key action that drops the selected item from the player's
 * inventory
 * 
 * @author Ryan Burnell, 300279172
 * 
 */
public class DropItem extends KeyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4624859352156965025L;

	public DropItem(ClientGame cl, UI parent) {
		super(cl, parent);
	}

	@Override
	protected void execute() {
		// checks whether an item is selected, then drops it
		if (client.getSelectedIndex() >= 0) {
			client.dropSelectedItem();
		}
	}

}
