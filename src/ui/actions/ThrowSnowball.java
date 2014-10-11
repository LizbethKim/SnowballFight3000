package ui.actions;
import ui.UI;
import gameworld.game.client.ClientGame;
import gameworld.world.Board;


public class ThrowSnowball extends KeyAction{

	public ThrowSnowball(ClientGame cl, UI parent){
		super(cl, parent);
	}
	
	@Override
	protected void execute() {
		System.out.println("FireSnowball");
		client.throwSnowball();
	}

}
