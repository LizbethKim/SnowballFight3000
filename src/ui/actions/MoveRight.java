package ui.actions;

import gameworld.game.client.ClientGame;
import gameworld.world.Direction;

public class MoveRight extends KeyAction{
	
	public MoveRight(ClientGame cl){
		super(cl);
	}

	@Override
	protected void execute(){
		client.move(Direction.EAST);
		//TODO board.moveright()
	}
}
