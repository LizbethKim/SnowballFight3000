package ui.cheats;

import client.Client;

public class OneHitKill extends CheatSwitch {

	public OneHitKill(Client cl) {
		super(cl);
	}

	@Override
	protected void changeState() {
		//RB: client.toggleOneHitKill();
	}

}
