package ui.actions;

import client.ClientGame;
import gameworld.world.Direction;
import gameworld.world.Board;

public class MoveUp extends KeyAction{
	
	public MoveUp(ClientGame cl){
		super(cl);
	}

	@Override
	protected void execute(){
		client.move(Direction.NORTH);
		System.out.println("moveUp");
		//TODO board.moveup()
	}
}
