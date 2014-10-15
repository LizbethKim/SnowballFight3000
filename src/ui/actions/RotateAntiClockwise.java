package ui.actions;

import gameworld.game.client.ClientGame;
import ui.gamewindow.UI;

/**
 * RotateAntiClockwise is a key action that rotates the view in the
 * anticlockwise direction
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class RotateAntiClockwise extends KeyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3500405530788557555L;

	public RotateAntiClockwise(ClientGame cl, UI parent) {
		super(cl, parent);
	}

	@Override
	protected void execute() {
		client.rotateAnticlockwise();
	}
}
