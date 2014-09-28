package graphics.assets;

import gameworld.world.Direction;

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
	KEY("", "", "", "", Direction.NORTH), 	// EK add the image names here if you find some
	POWERUP("PowerUp.png", "PowerUp.png", "PowerUp.png", "PowerUp.png", Direction.NORTH),
	CHEST("", "", "", "", Direction.NORTH),
	MAP("", "", "", "", Direction.NORTH),
	TREE("PowerUp.png", "PowerUp.png", "PowerUp.png", "PowerUp.png", Direction.NORTH),
	TABLE("", "", "", "", Direction.NORTH),
	WALL_E_W("", "", "", "", Direction.NORTH),
	WALL_N_S("", "", "", "", Direction.NORTH),
	SNOWBALL("", "", "", "", Direction.NORTH),
	BUSH("PowerUp.png", "PowerUp.png", "PowerUp.png", "PowerUp.png", Direction.NORTH),
	PLAYER1("PlayerHoldingPosition.png", "PlayerHoldingPosition.png", "PlayerHoldingPosition.png", "PlayerHoldingPosition.png", Direction.NORTH);

	public final Image north;
	public final Image south;
	public final Image east;
	public final Image west;
	public final Direction d;

	/**
	 * Creates the enumeration and gives it the associated image.
	 * @param resourceName
	 */
	Objects(String resourceNameNorth, String resourceNameSouth, String resourceNameWest, String resourceNameEast, Direction d) {
		try {
			north = ImageIO.read(Objects.class.getResource(resourceNameNorth));
			south = ImageIO.read(Objects.class.getResource(resourceNameSouth));
			east = ImageIO.read(Objects.class.getResource(resourceNameEast));
			west = ImageIO.read(Objects.class.getResource(resourceNameWest));

			this.d = d;
		}
		catch (IOException e) { throw new Error ("Error in image loading."); }
	}
}
