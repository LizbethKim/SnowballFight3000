package ui.cheats;

import gameworld.game.client.ClientGame;

/**
 * NightVision is a cheat that toggles night vision, which allows the player to
 * see even when the world is in night time
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class NightVision extends CheatSwitch {

	public NightVision(ClientGame cl) {
		super(cl);
	}

	@Override
	protected void changeState() {
		client.toggleNightVision();
	}

}
