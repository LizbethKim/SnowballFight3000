package ui.actions;

import java.util.List;

import ui.ContainerPopup;
import ui.UI;
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
			try {
				List<Objects> items = client.getContentsOfSelected();
				String title = client.inspectItem();
				new ContainerPopup(client, parent, title, items, enabled);
			} catch (NotAContainerException | NoItemException e) {
				// do nothing
			}
		} else {
		client.useItem();
		}
	}

}
