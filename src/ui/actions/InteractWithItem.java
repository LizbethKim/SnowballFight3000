package ui.actions;
import ui.ContainerPopup;
import client.ClientGame;
import gameworld.world.Board;


public class InteractWithItem extends KeyAction{

	public InteractWithItem(ClientGame cl){
		super(cl);
	}
	
	@Override
	protected void execute() {
		new ContainerPopup().show();
		//RB: client.interactWithItem();
	}

}
