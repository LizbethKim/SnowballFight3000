package ui.actions;

import client.Client;
import client.Client.Direction;
import gameworld.world.Board;

public class MoveRight extends KeyAction{
	
	public MoveRight(Client cl){
		super(cl);
	}

	@Override
	protected void execute(){
		client.move(Direction.EAST);
		System.out.println("moveRight");
		//TODO board.moveright()
	}
}
