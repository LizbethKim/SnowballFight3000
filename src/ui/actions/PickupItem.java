package ui.actions;
import ui.gamewindow.UI;
import gameworld.game.client.ClientGame;

/**
 * PickupItem is a key action that attempts to pick up the item in front of the player
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class PickupItem extends KeyAction{

	public PickupItem(ClientGame cl, UI parent){
		super(cl, parent);
	}
	
	@Override
	protected void execute() {
		client.pickUpItem();
	}

}
