package ui.actions;
import gameworld.game.client.ClientGame;
import gameworld.world.Board;


public class ThrowSnowball extends KeyAction{

	public ThrowSnowball(ClientGame cl){
		super(cl);
	}
	
	@Override
	protected void execute() {
		System.out.println("FireSnowball");
		client.throwSnowball();
	}

}
