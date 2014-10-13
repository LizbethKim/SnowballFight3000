package ui.actions;
import ui.gamewindow.UI;
import gameworld.game.client.ClientGame;
import gameworld.world.Board;


public class SelectItem extends KeyAction{
	private int inventoryNumber;

	public SelectItem(ClientGame cl, UI parent, int inventoryNumber){
		super(cl, parent);
		this.inventoryNumber = inventoryNumber;
	}
	
	@Override
	protected void execute() {
		client.setSelectedIndex(inventoryNumber-1);
	}

}
