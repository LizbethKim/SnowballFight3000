package ui.cheats;

import client.Client;

public class Invincibility extends CheatSwitch {

	public Invincibility(Client cl) {
		super(cl);
	}

	@Override
	protected void changeState() {
		//RB: client.toggleInvincibility();
	}

}
