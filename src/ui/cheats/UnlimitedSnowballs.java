package ui.cheats;

import client.ClientGame;

public class UnlimitedSnowballs extends CheatSwitch {

	public UnlimitedSnowballs(ClientGame cl) {
		super(cl);
	}

	@Override
	protected void changeState() {
		//RB: client.toggleUnlimitedSnowballs();
	}

}
