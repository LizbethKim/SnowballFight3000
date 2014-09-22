import gameworld.world.Board;
import ui.actions.KeyAction;


public class FireSnowball extends KeyAction{

	public FireSnowball(Board b){
		super(b);
	}
	
	@Override
	protected void execute() {
		System.out.println("FireSnowball");
		//:TODO board.firesnowball()
	}

}
