package ui.cheats;

import gameworld.game.client.ClientGame;

/**
 * UnlimietSpeed is a cheat that toggles the player movement speed limit off
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class UnlimitedSpeed extends CheatSwitch {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8130731122309477631L;

	public UnlimitedSpeed(ClientGame cl) {
		super(cl);
	}

	@Override
	protected void changeState() {
		client.toggleUnlimitedSpeed();
	}

}
