package graphics.assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Represents the different types of terrain a tile may have.
 * A lot of the tiles were found on google
 * @author Kelsey Jack 300275851 + Elizabeth Kim kimeliz1 300302456
 */
public enum Terrain {
	SNOW("snow.png"),
	FLOOR("Floor.png"),
	GRASS("Grass.png"),
	DIRT("Dirt.png"),
	TESTTILE("TileTemplate.png"),
	ICE("Ice.png"),
	SAND("Sand.png"),
	WATER("Water.png"),
	NULLTILE("NullTile.png"),
	RED("RedTile.png"),
	BLUE("BlueTile.png");

	public final BufferedImage img;

	/**
	 * Creates the enumeration and gives it the associated image.
	 * @param resourceName
	 */
	Terrain(String resourceName) {
		try { img = ImageIO.read(Entities.class.getResource(resourceName)); }
		catch (IOException e) { throw new Error ("Error in image loading."); }
	}
}
