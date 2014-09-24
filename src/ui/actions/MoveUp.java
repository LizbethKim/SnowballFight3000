package ui.actions;

import client.Client;
import client.Client.Direction;
import gameworld.world.Board;

public class MoveUp extends KeyAction{
	
	public MoveUp(Client cl){
		super(cl);
	}

	@Override
	protected void execute(){
		client.move(Direction.NORTH);
		System.out.println("moveUp");
		//TODO board.moveup()
	}
}
