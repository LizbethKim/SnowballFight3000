package ui.actions;

import client.ClientGame;
import gameworld.world.Direction;
import gameworld.world.Board;

public class MoveRight extends KeyAction{
	
	public MoveRight(ClientGame cl){
		super(cl);
	}

	@Override
	protected void execute(){
		client.move(Direction.EAST);
		System.out.println("moveRight");
		//TODO board.moveright()
	}
}
