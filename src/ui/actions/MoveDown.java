package ui.actions;

import ui.gamewindow.UI;
import gameworld.game.client.ClientGame;
import gameworld.world.Direction;

/**
 * MoveDown is a key action that attempts to move the player in the SOUTH direction
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class MoveDown extends KeyAction{
	
	public MoveDown(ClientGame cl, UI parent){
		super(cl, parent);
	}

	@Override
	protected void execute(){
		client.move(Direction.SOUTH);
	}
}
