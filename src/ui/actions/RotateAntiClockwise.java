package ui.actions;

import client.ClientGame;

public class RotateAntiClockwise extends KeyAction{
	
	public RotateAntiClockwise(ClientGame cl){
		super(cl);
	}

	@Override
	protected void execute(){
		client.rotateAnticlockwise();
		System.out.println("rotateAnti");
	}
}
