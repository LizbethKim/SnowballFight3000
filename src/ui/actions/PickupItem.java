package ui.actions;
import ui.ContainerPopup;
import gameworld.game.client.ClientGame;
import gameworld.world.Board;


public class PickupItem extends KeyAction{

	public PickupItem(ClientGame cl){
		super(cl);
	}
	
	@Override
	protected void execute() {
		client.pickUpItem();
	}

}
