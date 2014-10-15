package ui.actions;

import gameworld.game.client.ClientGame;
import ui.gamewindow.UI;

/**
 * RotateClockwise is a key action that rotates the view in the clockwise
 * direction
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class RotateClockwise extends KeyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7492641951459296996L;

	public RotateClockwise(ClientGame cl, UI parent) {
		super(cl, parent);
	}

	@Override
	protected void execute() {
		client.rotateClockwise();
	}
}
