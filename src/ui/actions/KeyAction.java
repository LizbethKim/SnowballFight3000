package ui.actions;

import gameworld.world.Board;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import client.Client;

public abstract class KeyAction extends AbstractAction {

	protected Client client;

    public KeyAction(Client client) {
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       execute();
    }
    
    protected abstract void execute();
}
