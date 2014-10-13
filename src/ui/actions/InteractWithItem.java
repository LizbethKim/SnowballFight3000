package ui.actions;

import java.io.IOException;
import java.util.List;

import ui.gamewindow.UI;
import ui.popups.ContainerPopup;
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
			//check that the client has a container in front
			client.getContents();
			String title = client.inspectItem();
			new ContainerPopup(client, parent, title, true);
		} catch (NoItemException | NotAContainerException e) {
			//do nothing
		}
	}

}
