package ui.actions;

import gameworld.game.client.ClientGame;
import gameworld.world.Board;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public abstract class KeyAction extends AbstractAction {

	protected ClientGame client;

    public KeyAction(ClientGame client) {
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       execute();
    }
    
    protected abstract void execute();
}
