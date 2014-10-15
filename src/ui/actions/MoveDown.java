package ui.actions;

import gameworld.game.client.ClientGame;
import gameworld.world.Direction;
import ui.gamewindow.UI;

/**
 * MoveDown is a key action that attempts to move the player in the SOUTH
 * direction
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class MoveDown extends KeyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -660615412929122211L;

	public MoveDown(ClientGame cl, UI parent) {
		super(cl, parent);
	}

	@Override
	protected void execute() {
		client.move(Direction.SOUTH);
	}
}
