package ui.actions;

import gameworld.game.client.ClientGame;
import gameworld.world.Direction;
import ui.gamewindow.UI;

/**
 * MoveUp is a key action that attempts to move the player in the NORTH
 * direction
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class MoveUp extends KeyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5343750905649613787L;

	public MoveUp(ClientGame cl, UI parent) {
		super(cl, parent);
	}

	@Override
	protected void execute() {
		client.move(Direction.NORTH);
	}
}
