package ui.actions;

import client.Client;

public class RotateAntiClockwise extends KeyAction{
	
	public RotateAntiClockwise(Client cl){
		super(cl);
	}

	@Override
	protected void execute(){
		client.rotateAnticlockwise();
		System.out.println("rotateAnti");
	}
}
