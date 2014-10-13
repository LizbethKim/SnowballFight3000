package ui.cheats;

import gameworld.game.client.ClientGame;

public class UnlimitedSpeed extends CheatSwitch {

	public UnlimitedSpeed(ClientGame cl) {
		super(cl);
	}

	@Override
	protected void changeState() {
		client.toggleUnlimitedSpeed();
	}

}
