package ui.popups;

import gameworld.game.client.ClientGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * The RightClickMenu launches a right-click pop-up and passes the selected action
 * to the client
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class RightClickMenu extends JPopupMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4185069777237371759L;
	private ClientGame client;
	private JMenuItem drop;
	private JMenuItem use;
	private JMenuItem inspect;

	public RightClickMenu(ClientGame cl) {
		this.client = cl;
		setupMenu();
	}

	/**
	 * sets up a menu with drop, use and inspect options
	 */
	private void setupMenu() {
		drop = new JMenuItem("Drop Item");
		drop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client.dropSelectedItem();
			}
		});

		use = new JMenuItem("Use Item");
		use.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client.useSelectedItem();
			}
		});

		inspect = new JMenuItem("Inspect Item");
		inspect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		//add the options
		add(drop);
		add(use);
		add(inspect);

	}
}
