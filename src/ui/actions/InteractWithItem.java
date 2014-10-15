package ui.actions;

import ui.gamewindow.UI;
import ui.popups.ContainerPopup;
import gameworld.game.client.ClientGame;
import gameworld.game.client.NoItemException;
import gameworld.game.client.NotAContainerException;

/**
 * InteractWithItem is a key action that interacts with the item in front of the
 * player, launching a ContainerPopup if the item in front is a container,
 * otherwise attempting to interact with it
 *
 * @author Ryan Burnell, 300279172
 *
 */

public class InteractWithItem extends KeyAction {

	public InteractWithItem(ClientGame cl, UI parent) {
		super(cl, parent);
	}

	@Override
	protected void execute() {
		try {
			// check that the client has a container in front
			client.getContents();
			parent.openContainer(false);
		} catch (NotAContainerException e) {
			client.unfreezePlayer();
		}
	}

}
