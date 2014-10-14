package ui.cheats;

import gameworld.game.client.ClientGame;

/**
 * OneHitKill is a cheat that toggles insta-kill (very high damage) for the player's snowball
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class OneHitKill extends CheatSwitch {

	public OneHitKill(ClientGame cl) {
		super(cl);
	}

	@Override
	protected void changeState() {
		client.toggleOneHitKill();
	}

}
