package ui.actions;

import gameworld.game.client.ClientGame;
import gameworld.world.Direction;
import ui.gamewindow.UI;

/**
 * MoveRight is a key action that attempts to move the player in the EAST
 * direction
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class MoveRight extends KeyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 315430495147662524L;

	public MoveRight(ClientGame cl, UI parent) {
		super(cl, parent);
	}

	@Override
	protected void execute() {
		client.move(Direction.EAST);
	}
}
