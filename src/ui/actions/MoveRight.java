package ui.actions;

import ui.UI;
import gameworld.game.client.ClientGame;
import gameworld.world.Direction;

public class MoveRight extends KeyAction{
	
	public MoveRight(ClientGame cl, UI parent){
		super(cl, parent);
	}

	@Override
	protected void execute(){
		client.move(Direction.EAST);
		//TODO board.moveright()
	}
}
