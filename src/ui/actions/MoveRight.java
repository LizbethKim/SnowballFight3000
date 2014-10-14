package ui.actions;

import ui.gamewindow.UI;
import gameworld.game.client.ClientGame;
import gameworld.world.Direction;

/**
 * MoveRight is a key action that attempts to move the player in the EAST direction
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class MoveRight extends KeyAction{
	
	public MoveRight(ClientGame cl, UI parent){
		super(cl, parent);
	}

	@Override
	protected void execute(){
		client.move(Direction.EAST);
	}
}
