package ui.actions;
import ui.gamewindow.UI;
import ui.popups.ContainerPopup;
import gameworld.game.client.ClientGame;
import gameworld.world.Board;


public class PickupItem extends KeyAction{

	public PickupItem(ClientGame cl, UI parent){
		super(cl, parent);
	}
	
	@Override
	protected void execute() {
		client.pickUpItem();
	}

}
