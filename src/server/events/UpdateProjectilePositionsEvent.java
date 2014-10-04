package server.events;

import gameworld.world.Location;

import java.io.IOException;
import java.io.OutputStream;

public class UpdateProjectilePositionsEvent implements UpdateEvent {

	private Location projectiles[];

	public UpdateProjectilePositionsEvent(Location projectiles[]) {
		this.projectiles = projectiles;
		if(projectiles.length > 255) {
			throw new RuntimeException("UpdateProjectilePositionsEvent constructor given more than 255 projectiles");
		}
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.write(0x08);
		out.write(projectiles.length);
		for(int i=0;i<projectiles.length;i++) {
			out.write(projectiles[i].x);
			out.write(projectiles[i].x>>8);
			out.write(projectiles[i].y);
			out.write(projectiles[i].y>>8);
		}
		
	}

}
