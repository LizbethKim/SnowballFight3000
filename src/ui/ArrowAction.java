package ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class ArrowAction extends AbstractAction {

	private String cmd;

    public ArrowAction(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (cmd.equalsIgnoreCase("LeftArrow")) {
            System.out.println("The left arrow was pressed!");
        } else if (cmd.equalsIgnoreCase("RightArrow")) {
            System.out.println("The right arrow was pressed!");
        } else if (cmd.equalsIgnoreCase("UpArrow")) {
            System.out.println("The up arrow was pressed!");
        } else if (cmd.equalsIgnoreCase("DownArrow")) {
            System.out.println("The down arrow was pressed!");
        }
    }
}
