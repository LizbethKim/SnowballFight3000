package network.events;

import gameworld.world.Location;
import gameworld.world.Snowball.SnowballType;

import java.io.IOException;
import java.io.OutputStream;

public class UpdateProjectilePositionsEvent extends LocationEvent {

	private Location projectiles[];
	private SnowballType types[];

	public UpdateProjectilePositionsEvent(Location projectiles[], SnowballType types[]) {
		this.projectiles = projectiles;
		this.types=types;
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
			writeLocation(out,projectiles[i]);
			out.write(types[i].ordinal());
		}
		
	}

}
