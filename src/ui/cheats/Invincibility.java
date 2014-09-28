package ui.cheats;

import client.ClientGame;

public class Invincibility extends CheatSwitch {

	public Invincibility(ClientGame cl) {
		super(cl);
	}

	@Override
	protected void changeState() {
		//RB: client.toggleInvincibility();
	}

}
