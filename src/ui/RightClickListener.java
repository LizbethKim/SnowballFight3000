package ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import client.Client;

public class RightClickListener extends MouseAdapter{
	private Client client;
	
	public RightClickListener(Client c){
		super();
		client = c;
	}
	
	public void mousePressed(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
        RightClickMenu menu = new RightClickMenu(client);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}
