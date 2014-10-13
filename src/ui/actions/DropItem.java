package ui.actions;
import ui.gamewindow.UI;
import gameworld.game.client.ClientGame;
import gameworld.world.Board;


public class DropItem extends KeyAction{

	public DropItem(ClientGame cl, UI parent){
		super(cl, parent);
	}

	@Override
	protected void execute() {
		if(client.getSelectedIndex() >= 0){
			client.dropSelectedItem();
		}
	}

}
