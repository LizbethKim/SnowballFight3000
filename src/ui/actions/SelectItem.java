package ui.actions;
import gameworld.game.client.ClientGame;
import gameworld.world.Board;


public class SelectItem extends KeyAction{
	private int inventoryNumber;

	public SelectItem(ClientGame cl, int inventoryNumber){
		super(cl);
		this.inventoryNumber = inventoryNumber;
	}
	
	@Override
	protected void execute() {
		System.out.println("SelectItem: "+inventoryNumber);
		client.setSelectedIndex(inventoryNumber);
	}

}
