package ui.actions;

import gameworld.world.Board;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public abstract class KeyAction extends AbstractAction {

	protected Board board;

    public KeyAction(Board board) {
        this.board = board;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       execute();
    }
    
    protected abstract void execute();
}
