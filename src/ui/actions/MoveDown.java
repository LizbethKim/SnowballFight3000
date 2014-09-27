package ui.actions;

import client.Client;
import gameworld.world.Board;
import gameworld.world.Direction;

public class MoveDown extends KeyAction{
	
	public MoveDown(Client cl){
		super(cl);
	}

	@Override
	protected void execute(){
		client.move(Direction.SOUTH);
		System.out.println("moveDown");
		//TODO board.movedown()
	}
}
