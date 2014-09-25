package ui.actions;
import client.Client;
import gameworld.world.Board;


public class InspectItem extends KeyAction{

	public InspectItem(Client cl){
		super(cl);
	}
	
	@Override
	protected void execute() {
		System.out.println("Inspect");
		//client.inspectItem(null);
		//:TODO board.firesnowball()
	}

}
