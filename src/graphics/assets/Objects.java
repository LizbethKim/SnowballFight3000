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
 * @author jackkels + kimeliz1
 *
 */
public enum Objects {
	KEY("", "", "", ""), 	// EK add the image names here if you find some
	POWERUP("PowerUp.png", "PowerUp.png", "PowerUp.png", "PowerUp.png"),
	CHEST("", "", "", ""),
	MAP("", "", "", ""),
	TREE("PowerUp.png", "PowerUp.png", "PowerUp.png", "PowerUp.png"),
	TABLE("", "", "", ""),
	WALL_E_W("", "", "", ""),
	WALL_N_S("", "", "", ""),
	SNOWBALL("", "", "", ""),
	BUSH("PowerUp.png", "PowerUp.png", "PowerUp.png", "PowerUp.png"),
	PLAYER_N("PlayerNorth.png", "PlayerSouth.png", "PlayerWest.png", "PlayerEast.png"),
	PLAYER_E("PlayerEast.png", "PlayerWest.png", "PlayerNorth.png", "PlayerSouth.png"),
	PLAYER_S("PlayerSouth.png", "PlayerNorth.png", "PlayerEast.png", "PlayerWest.png"),
	PLAYER_W("PlayerWest.png", "PlayerEast.png", "PlayerSouth.png", "PlayerNorth.png"),
	PLAYER1("PlayerNorth.png", "PlayerSouth.png", "PlayerWest.png", "PlayerEast.png");

	public final Image[] imgs;	// KTC make unmodifiable

	/**
	 * Creates the enumeration and gives it the associated image.
	 * @param resourceName
	 */
	Objects(String resourceNameNorth, String resourceNameSouth, String resourceNameWest, String resourceNameEast) {
		try {
			imgs = new Image[4];
			imgs[0] = ImageIO.read(Objects.class.getResource(resourceNameNorth));
			imgs[1] = ImageIO.read(Objects.class.getResource(resourceNameSouth));
			imgs[2] = ImageIO.read(Objects.class.getResource(resourceNameEast));
			imgs[3] = ImageIO.read(Objects.class.getResource(resourceNameWest));
		}
		catch (IOException e) { throw new Error ("Error in image loading."); }
	}

}
