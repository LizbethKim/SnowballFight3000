package ui.actions;

import gameworld.game.client.ClientGame;
import gameworld.world.Direction;
import gameworld.world.Board;

public class MoveLeft extends KeyAction{
	
	public MoveLeft(ClientGame cl){
		super(cl);
	}

	@Override
	protected void execute(){
		client.move(Direction.WEST);
		System.out.println("moveLeft");
		//TODO board.moveleft()
	}
}
