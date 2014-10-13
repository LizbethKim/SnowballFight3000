package ui.actions;

import java.util.List;

import ui.ContainerPopup;
import ui.gamewindow.UI;
import gameworld.game.client.ClientGame;
import gameworld.game.client.NoItemException;
import gameworld.game.client.NotAContainerException;
import gameworld.world.Board;
import graphics.assets.Objects;

public class UseItem extends KeyAction {

	public UseItem(ClientGame cl, UI parent) {
		super(cl, parent);
	}

	@Override
	protected void execute() {
		if (client.selectedIsContainer()) {
		//	System.out.println("is container");
			new ContainerPopup(client, parent, "", false);
			System.out.println("popup done");
		} else {
			client.useItem();
		}
	}

}
