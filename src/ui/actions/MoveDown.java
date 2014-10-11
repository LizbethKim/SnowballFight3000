package ui.actions;

import ui.UI;
import gameworld.game.client.ClientGame;
import gameworld.world.Board;
import gameworld.world.Direction;

public class MoveDown extends KeyAction{
	
	public MoveDown(ClientGame cl, UI parent){
		super(cl, parent);
	}

	@Override
	protected void execute(){
		client.move(Direction.SOUTH);
		System.out.println("moveDown");
		//TODO board.movedown()
	}
}
