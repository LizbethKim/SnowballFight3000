package ui.actions;
import ui.ContainerPopup;
import gameworld.game.client.ClientGame;
import gameworld.game.client.NotAChestException;
import gameworld.world.Board;


public class InteractWithItem extends KeyAction{

	public InteractWithItem(ClientGame cl){
		super(cl);
	}
	
	@Override
	protected void execute() {
		try {
			if(client.getContents() != null){
				new ContainerPopup(client.getContents()).show();
			}
		} catch (NotAChestException e) {
		}
	}

}
