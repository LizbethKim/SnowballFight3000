package ui.actions;

import ui.UI;
import gameworld.game.client.ClientGame;

public class RotateAntiClockwise extends KeyAction{
	
	public RotateAntiClockwise(ClientGame cl, UI parent){
		super(cl, parent);
	}

	@Override
	protected void execute(){
		client.rotateAnticlockwise();
	}
}
