package ui.actions;

import gameworld.game.client.ClientGame;
import gameworld.world.Direction;
import ui.gamewindow.UI;

/**
 * MoveLeft is a key action that attempts to move the player in the WEST
 * direction
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class MoveLeft extends KeyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2439862930717021266L;

	public MoveLeft(ClientGame cl, UI parent) {
		super(cl, parent);
	}

	@Override
	protected void execute() {
		client.move(Direction.WEST);
	}
}
