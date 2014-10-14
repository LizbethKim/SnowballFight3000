package ui.cheats;

import gameworld.game.client.ClientGame;

/**
 * Invincibility is a cheat that toggles (near) infinite health for the player
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class Invincibility extends CheatSwitch {

	public Invincibility(ClientGame cl) {
		super(cl);
	}

	@Override
	protected void changeState() {
		client.toggleInvincibility();
	}

}
