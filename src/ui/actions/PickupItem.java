package ui.actions;

import gameworld.game.client.ClientGame;
import ui.gamewindow.UI;

/**
 * PickupItem is a key action that attempts to pick up the item in front of the
 * player
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class PickupItem extends KeyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6303878776381359267L;

	public PickupItem(ClientGame cl, UI parent) {
		super(cl, parent);
	}

	@Override
	protected void execute() {
		client.pickUpItem();
	}

}
