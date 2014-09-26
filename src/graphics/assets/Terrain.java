package graphics.assets;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Represents the different types of terrain a tile may have.
 * @author jackkels
 *
 */
public enum Terrain {
	SNOW(""), 	// EK add the image names here if you find some
	FLOOR(""), 
	GRASS("");
	
	public final Image img;

	/**
	 * Creates the enumeration and gives it the associated image.
	 * @param resourceName
	 */
	Terrain(String resourceName) {
		try { img = ImageIO.read(Objects.class.getResource(resourceName)); }
		catch (IOException e) { throw new Error ("Error in image loading."); }
	}
}
