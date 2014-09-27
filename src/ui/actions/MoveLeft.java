package ui.actions;

import client.Client;
import gameworld.world.Direction;
import gameworld.world.Board;

public class MoveLeft extends KeyAction{
	
	public MoveLeft(Client cl){
		super(cl);
	}

	@Override
	protected void execute(){
		client.move(Direction.WEST);
		System.out.println("moveLeft");
		//TODO board.moveleft()
	}
}
