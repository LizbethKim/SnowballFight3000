package ui.actions;
import client.ClientGame;
import gameworld.world.Board;


public class InspectItem extends KeyAction{

	public InspectItem(ClientGame cl){
		super(cl);
	}
	
	@Override
	protected void execute() {
		System.out.println("Inspect");
		//client.inspectItem(null);
		//:TODO board.firesnowball()
	}

}
