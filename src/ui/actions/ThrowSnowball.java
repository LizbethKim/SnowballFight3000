package ui.actions;
import client.Client;
import gameworld.world.Board;


public class ThrowSnowball extends KeyAction{

	public ThrowSnowball(Client cl){
		super(cl);
	}
	
	@Override
	protected void execute() {
		System.out.println("FireSnowball");
		client.throwSnowball();
	}

}
