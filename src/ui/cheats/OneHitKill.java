package ui.cheats;

import gameworld.game.client.ClientGame;

public class OneHitKill extends CheatSwitch {

	public OneHitKill(ClientGame cl) {
		super(cl);
	}

	@Override
	protected void changeState() {
		//RB: client.toggleOneHitKill();
	}

}
