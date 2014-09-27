package ui.actions;
import client.Client;
import gameworld.world.Board;


public class InteractWithItem extends KeyAction{

	public InteractWithItem(Client cl){
		super(cl);
	}
	
	@Override
	protected void execute() {
		System.out.println("Interacting");
		//RB: client.interactWithItem();
	}

}
