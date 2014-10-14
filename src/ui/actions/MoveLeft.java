package ui.actions;

import ui.gamewindow.UI;
import gameworld.game.client.ClientGame;
import gameworld.world.Direction;

/**
 * MoveLeft is a key action that attempts to move the player in the WEST direction
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class MoveLeft extends KeyAction{
	
	public MoveLeft(ClientGame cl, UI parent){
		super(cl, parent);
	}

	@Override
	protected void execute(){
		client.move(Direction.WEST);
	}
}
