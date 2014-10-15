package ui.actions;

import ui.gamewindow.UI;
import gameworld.game.client.ClientGame;

/**
 * ThrowSnowball is a key action that prompts the throwing of a snowball by the player
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
