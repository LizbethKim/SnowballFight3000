package ui.actions;
import client.Client;
import gameworld.world.Board;


public class FireSnowball extends KeyAction{

	public FireSnowball(Client cl){
		super(cl);
	}
	
	@Override
	protected void execute() {
		System.out.println("FireSnowball");
		client.throwSnowball();
		//:TODO board.firesnowball()
	}

}
