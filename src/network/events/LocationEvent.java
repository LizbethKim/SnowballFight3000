package network.events;

import gameworld.world.Location;

import java.io.IOException;
import java.io.OutputStream;


/**
 * This class contains code for writing locations to an OutputStream
 * @author frizzebryd
 *
 */
public abstract class LocationEvent implements UpdateEvent {

	private static int worldWidth = -1;
	private static int worldHeight = -1;
	private static int locationLen;

	/**
	 * Writes a location to an OutputStream
	 * @param out the OutputStream for the location to be written to
	 * @param location the location to be written to the output stream
	 * @throws IOException
	 */
	protected void writeLocation(OutputStream out, Location location) throws IOException {
		if(location.x>worldWidth || location.y>worldHeight) {
			throw new RuntimeException("Location out of bounds exception: "+location);
		}
		int output = location.x+location.y*(worldWidth);
		// write the number of required bytes
		for(int i=0;i<(locationLen/8)+1;i++) {
			out.write(output>>(i*8));
		}

	}
	/**
	 * sets the world size for location bitpacking
	 * @param width the width of the map
	 * @param height the height of the map
	 */
	public static void setWorldSize(int width, int height) {
		worldWidth=width;
		worldHeight=height;
		//get minimum number of bits required to encode a location
		int maxLocation = worldWidth*worldHeight;
		for(locationLen=0;maxLocation>0;locationLen++){
			maxLocation=maxLocation>>1;
		}
	}
}
