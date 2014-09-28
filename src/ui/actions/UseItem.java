package ui.actions;
import client.ClientGame;
import gameworld.world.Board;


public class UseItem extends KeyAction{
	private int inventoryNumber;

	public UseItem(ClientGame cl, int inventoryNumber){
		super(cl);
		this.inventoryNumber = inventoryNumber;
	}
	
	@Override
	protected void execute() {
		System.out.println("UseItem"+inventoryNumber);
		//:RB client.useItem(inventoryNumber);
	}

}
