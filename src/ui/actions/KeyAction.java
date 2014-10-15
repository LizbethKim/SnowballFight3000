package ui.actions;

import gameworld.game.client.ClientGame;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ui.gamewindow.UI;

/**
 * The KeyAction is an abstract class for the actions bound to keys on the
 * keyboard and lays out the required framework for responding to the key press
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public abstract class KeyAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6176244764427062087L;
	protected ClientGame client;
	protected UI parent;

	public KeyAction(ClientGame client, UI parent) {
		this.client = client;
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		execute();
	}

	/**
	 * complete the action
	 */
	protected abstract void execute();
}
