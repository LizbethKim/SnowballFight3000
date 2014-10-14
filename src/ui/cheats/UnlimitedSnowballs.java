package ui.cheats;

import gameworld.game.client.ClientGame;

/**
 * UnlimitedSnowballs is a cheat that toggles infinite firing speed of snowballs
 * for the player
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class UnlimitedSnowballs extends CheatSwitch {

	public UnlimitedSnowballs(ClientGame cl) {
		super(cl);
	}

	@Override
	protected void changeState() {
		client.toggleUnlimitedFireRate();
	}

}
