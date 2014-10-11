package ui.actions;

import java.util.List;

import ui.ContainerPopup;
import gameworld.game.client.ClientGame;
import gameworld.game.client.NotAContainerException;
import gameworld.world.Board;
import graphics.assets.Objects;

public class InteractWithItem extends KeyAction {

	public InteractWithItem(ClientGame cl) {
		super(cl);
	}

	@Override
	protected void execute() {
		try {
			List<Objects> items = client.getContents();
			new ContainerPopup(client, items, true);
		} catch (NotAContainerException e) {
			//do nothing
		}
	}

}
