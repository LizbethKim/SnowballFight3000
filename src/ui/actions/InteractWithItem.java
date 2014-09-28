package ui.actions;
import client.ClientGame;
import gameworld.world.Board;


public class InteractWithItem extends KeyAction{

	public InteractWithItem(ClientGame cl){
		super(cl);
	}
	
	@Override
	protected void execute() {
		System.out.println("Interacting");
		//RB: client.interactWithItem();
	}

}
