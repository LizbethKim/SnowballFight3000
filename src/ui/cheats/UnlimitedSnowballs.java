package ui.cheats;

import client.Client;

public class UnlimitedSnowballs extends CheatSwitch {

	public UnlimitedSnowballs(Client cl) {
		super(cl);
	}

	@Override
	protected void changeState() {
		//RB: client.toggleUnlimitedSnowballs();
	}

}
