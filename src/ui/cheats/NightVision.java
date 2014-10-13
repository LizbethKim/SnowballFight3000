package ui.cheats;

import gameworld.game.client.ClientGame;

public class NightVision extends CheatSwitch {

	public NightVision(ClientGame cl) {
		super(cl);
	}

	@Override
	protected void changeState() {
		client.toggleNightVision();
	}

}
