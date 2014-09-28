package graphics.assets;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Represents the types of objects that can be rendered on a tile
 *  - this can be furniture, items, etc. WALL_E_W is a wall going east to west,
 *  WALL_N_S is a wall going north to south. RB, also stores the associated images,
 *  accessed through the img field.
 *  KTC EK we may need to add extra wall types, such as corners/doors.
 * @author jackkels
 *
 */
public enum Objects {
	KEY(""), 	// EK add the image names here if you find some
	POWERUP("PowerUp.png"),
	CHEST(""),
	MAP(""),
	TREE("PowerUp.png"),
	TABLE(""),
	WALL_E_W(""),
	WALL_N_S(""),
	SNOWBALL(""),
	BUSH("PowerUp.png"),
	PLAYER1("PlayerHoldingPosition.png");

	public final Image img;

	/**
	 * Creates the enumeration and gives it the associated image.
	 * @param resourceName
	 */
	Objects(String resourceName) {
		try { img = ImageIO.read(Objects.class.getResource(resourceName)); }
		catch (IOException e) { throw new Error ("Error in image loading."); }
	}
}
