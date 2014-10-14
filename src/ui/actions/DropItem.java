package ui.actions;
import ui.gamewindow.UI;
import gameworld.game.client.ClientGame;

/**
 * DropItem is a key action that drops the selected item from the player's inventory
 * 
 * @author Ryan Burnell, 300279172
 * 
 */
public class DropItem extends KeyAction{

	public DropItem(ClientGame cl, UI parent){
		super(cl, parent);
	}

	@Override
	protected void execute() {
		if(parent.getSelectedIndex() >= 0){
			//client.dropItem();
		}
	}

}
