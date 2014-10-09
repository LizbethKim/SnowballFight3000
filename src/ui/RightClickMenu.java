package ui;

import gameworld.game.client.ClientGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class RightClickMenu extends JPopupMenu {
	private ClientGame client;
	private JMenuItem drop;
	private JMenuItem use;
	private JMenuItem inspect;
	
	public RightClickMenu(ClientGame cl){
		this.client = cl;
		setupMenu();
	}
	
	private void setupMenu(){
		drop = new JMenuItem("Drop Item");
		drop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		use = new JMenuItem("Use Item");
		inspect = new JMenuItem("Inspect Item");
		add(drop);
		add(use);
		add(inspect);
		
		
	}
}
