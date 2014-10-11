package ui.actions;
import gameworld.game.client.ClientGame;
import gameworld.world.Board;


public class UseItem extends KeyAction{

	public UseItem(ClientGame cl){
		super(cl);
	}
	
	@Override
	protected void execute() {
		System.out.println("UsingItem");
		client.useItem();
	}

}
