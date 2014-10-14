package ui.actions;

import ui.gamewindow.UI;
import gameworld.game.client.ClientGame;

/**
 * RotateAntiClockwise is a key action that rotates the view in the anticlockwise direction
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class RotateAntiClockwise extends KeyAction{
	
	public RotateAntiClockwise(ClientGame cl, UI parent){
		super(cl, parent);
	}

	@Override
	protected void execute(){
		client.rotateAnticlockwise();
	}
}
