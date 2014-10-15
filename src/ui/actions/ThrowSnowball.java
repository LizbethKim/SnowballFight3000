package ui.actions;

import gameworld.game.client.ClientGame;
import ui.gamewindow.UI;

/**
 * ThrowSnowball is a key action that prompts the throwing of a snowball by the
 * player
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class ThrowSnowball extends KeyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2404746045177812699L;

	public ThrowSnowball(ClientGame cl, UI parent) {
		super(cl, parent);
	}

	@Override
	protected void execute() {
		client.throwSnowball();
	}

}
