package ui.actions;

import ui.gamewindow.UI;
import gameworld.game.client.ClientGame;
import gameworld.game.client.NoItemException;

/**
 * InspectItem is a key action that attempts to inspect the item in front of the
 * player and displays some information about it if there is a valid item
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class InspectItem extends KeyAction {

	public InspectItem(ClientGame cl, UI parent) {
		super(cl, parent);
	}

	@Override
	protected void execute() {
			client.inspectItem();	
	}

}
