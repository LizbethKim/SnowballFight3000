package ui.actions;

import client.ClientGame;
import gameworld.world.Board;
import gameworld.world.Direction;

public class RotateClockwise extends KeyAction{
	
	public RotateClockwise(ClientGame cl){
		super(cl);
	}

	@Override
	protected void execute(){
		client.rotateClockwise();
		System.out.println("rotateClockwise");
	}
}
