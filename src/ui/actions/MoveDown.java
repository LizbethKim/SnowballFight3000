package ui.actions;

import gameworld.world.Board;

public class MoveDown extends KeyAction{
	
	public MoveDown(Board b){
		super(b);
	}

	@Override
	protected void execute(){
		System.out.println("moveDown");
		//TODO board.movedown()
	}
}
