package ui.actions;
import ui.UI;
import gameworld.game.client.ClientGame;
import gameworld.game.client.NoItemException;
import gameworld.world.Board;


public class InspectItem extends KeyAction{

	public InspectItem(ClientGame cl, UI parent){
		super(cl, parent);
	}
	
	@Override
	protected void execute() {
		System.out.println("Inspect");
		try {
			client.inspectItem();
		} catch (NoItemException e) {
			//do nothing
		}
		//:TODO board.firesnowball()
	}

}
