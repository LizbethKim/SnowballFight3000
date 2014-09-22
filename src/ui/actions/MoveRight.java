package ui.actions;

import gameworld.world.Board;

public class MoveRight extends KeyAction{
	
	public MoveRight(Board b){
		super(b);
	}

	@Override
	protected void execute(){
		System.out.println("moveRight");
		//TODO board.moveright()
	}
}
