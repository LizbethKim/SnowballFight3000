package ui.actions;

import gameworld.world.Board;

public class MoveLeft extends KeyAction{
	
	public MoveLeft(Board b){
		super(b);
	}

	@Override
	protected void execute(){
		System.out.println("moveLeft");
		//TODO board.moveleft()
	}
}
