package ui.actions;

import gameworld.game.client.ClientGame;
import gameworld.world.Board;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ui.gamewindow.UI;

public abstract class KeyAction extends AbstractAction {

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
    
    protected abstract void execute();
}
