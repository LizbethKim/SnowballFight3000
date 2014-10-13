package ui.actions;
import ui.ContainerPopup;
import ui.gamewindow.UI;
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
