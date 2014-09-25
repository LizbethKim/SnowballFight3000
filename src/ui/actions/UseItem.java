package ui.actions;
import client.Client;
import gameworld.world.Board;


public class UseItem extends KeyAction{
	private int inventoryNumber;

	public UseItem(Client cl, int inventoryNumber){
		super(cl);
		this.inventoryNumber = inventoryNumber;
	}
	
	@Override
	protected void execute() {
		System.out.println("UseItem"+inventoryNumber);
		//:RB client.useItem(inventoryNumber);
	}

}
