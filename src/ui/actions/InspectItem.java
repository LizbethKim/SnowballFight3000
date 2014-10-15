package ui.actions;

import gameworld.game.client.ClientGame;
import ui.gamewindow.UI;

/**
 * InspectItem is a key action that attempts to inspect the item in front of the
 * player and displays some information about it if there is a valid item
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class InspectItem extends KeyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1062358383800508831L;

	public InspectItem(ClientGame cl, UI parent) {
		super(cl, parent);
	}

	@Override
	protected void execute() {
		client.inspectItem();
	}

}
