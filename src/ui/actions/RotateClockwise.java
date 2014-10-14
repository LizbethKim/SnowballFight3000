package ui.actions;

import ui.gamewindow.UI;
import gameworld.game.client.ClientGame;

/**
 * RotateClockwise is a key action that rotates the view in the clockwise direction
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class RotateClockwise extends KeyAction{
	
	public RotateClockwise(ClientGame cl, UI parent){
		super(cl, parent);
	}

	@Override
	protected void execute(){
		client.rotateClockwise();
	}
}
