package ui.actions;
import ui.UI;
import gameworld.game.client.ClientGame;
import gameworld.world.Board;


public class UseItem extends KeyAction{

	public UseItem(ClientGame cl, UI parent){
		super(cl, parent);
	}
	
	@Override
	protected void execute() {
		client.useItem();
	}

}
