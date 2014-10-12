package graphics.assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Represents the different types of terrain a tile may have.
 * @author jackkels + kimeliz1
 *
 */
public enum Terrain {
	SNOW("snow.png"), 	// EK add the image names here if you find some
	FLOOR("Floor.png"), 
	GRASS("Grass.png"),
	DIRT("Dirt.png"),
	TESTTILE("TileTemplate.png"),
	ICE("Ice.png"),
	SAND("Sand.png"),
	WATER("Water.png"),
	NULLTILE("NullTile.png");
	
	public final BufferedImage img;

	/**
	 * Creates the enumeration and gives it the associated image.
	 * @param resourceName
	 */
	Terrain(String resourceName) {
		try { img = ImageIO.read(Objects.class.getResource(resourceName)); }
		catch (IOException e) { throw new Error ("Error in image loading."); }
	}
}
