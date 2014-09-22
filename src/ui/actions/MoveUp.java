package ui.actions;

import gameworld.world.Board;

public class MoveUp extends KeyAction{
	
	public MoveUp(Board b){
		super(b);
	}

	@Override
	protected void execute(){
		System.out.println("moveUp");
		//TODO board.moveup()
	}
}
