package ui.actions;

import ui.gamewindow.UI;
import gameworld.game.client.ClientGame;

/**
 * UseItem is a key action that uses the currently selected item in the players
 * inventory. If this is a container it will open it to display its contents, if
 * it is a power-up it will activate it, otherwise it will do nothing
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class UseItem extends KeyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4879185215676441421L;

	public UseItem(ClientGame cl, UI parent) {
		super(cl, parent);
	}

	@Override
	protected void execute() {
		if (client.selectedIsContainer()) {
			// System.out.println("is container");
			parent.openContainer(true);// new ContainerPopup(client, parent, "", true);
			System.out.println("popup done");
		} else {
			client.useItem();
		}
	}

}
