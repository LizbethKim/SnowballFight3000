package ui.actions;

import ui.UI;
import gameworld.game.client.ClientGame;
import gameworld.world.Direction;
import gameworld.world.Board;

public class MoveUp extends KeyAction{
	
	public MoveUp(ClientGame cl, UI parent){
		super(cl, parent);
	}

	@Override
	protected void execute(){
		client.move(Direction.NORTH);
	}
}
