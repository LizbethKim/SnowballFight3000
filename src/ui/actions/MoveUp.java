package ui.actions;

import ui.gamewindow.UI;
import gameworld.game.client.ClientGame;
import gameworld.world.Direction;

/**
 * MoveUp is a key action that attempts to move the player in the NORTH direction
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class MoveUp extends KeyAction{
	
	public MoveUp(ClientGame cl, UI parent){
		super(cl, parent);
	}

	@Override
	protected void execute(){
		client.move(Direction.NORTH);
	}
}
