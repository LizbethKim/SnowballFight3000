package ui.actions;

import java.io.IOException;
import java.util.List;

import ui.ContainerPopup;
import ui.UI;
import gameworld.game.client.ClientGame;
import gameworld.game.client.NoItemException;
import gameworld.game.client.NotAContainerException;
import gameworld.world.Board;
import graphics.assets.Objects;

public class InteractWithItem extends KeyAction {

	public InteractWithItem(ClientGame cl, UI parent){
		super(cl, parent);
	}

	@Override
	protected void execute() {
		try {
			List<Objects> items = client.getContents();
			String title = client.inspectItem();
			new ContainerPopup(client, parent, title, items, true);
		} catch (NotAContainerException | NoItemException e) {
			//do nothing
		}
	}

}
