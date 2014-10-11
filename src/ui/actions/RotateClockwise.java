package ui.actions;

import ui.UI;
import gameworld.game.client.ClientGame;
import gameworld.world.Board;
import gameworld.world.Direction;

public class RotateClockwise extends KeyAction{
	
	public RotateClockwise(ClientGame cl, UI parent){
		super(cl, parent);
	}

	@Override
	protected void execute(){
		client.rotateClockwise();
		System.out.println("rotateClockwise");
	}
}
