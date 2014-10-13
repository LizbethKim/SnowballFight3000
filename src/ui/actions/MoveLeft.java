package ui.actions;

import ui.gamewindow.UI;
import gameworld.game.client.ClientGame;
import gameworld.world.Direction;
import gameworld.world.Board;

public class MoveLeft extends KeyAction{
	
	public MoveLeft(ClientGame cl, UI parent){
		super(cl, parent);
	}

	@Override
	protected void execute(){
		client.move(Direction.WEST);
	}
}
