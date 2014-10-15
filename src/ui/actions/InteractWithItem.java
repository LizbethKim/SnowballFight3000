package ui.actions;

import ui.gamewindow.UI;
import gameworld.game.client.ClientGame;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = -2500988752556843468L;

	public InteractWithItem(ClientGame cl, UI parent) {
		super(cl, parent);
	}

	@Override
	protected void execute() {
		//try to unfreeze the player
		boolean unfrozen = client.unfreezePlayer();
		if(!unfrozen){
		try {
			// check that the client has a container in front
			client.getContents();
			parent.openContainer(false);
		} catch (NotAContainerException e) {
		}
		}
	}

}
