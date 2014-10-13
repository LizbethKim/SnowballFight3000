package graphics.assets;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Represents the types of objects that can be rendered on a tile
 *  - this can be furniture, items, etc. WALL_E_W is a wall going east to west,
 *  WALL_N_S is a wall going north to south. RB, also stores the associated images,
 *  accessed through the img field.
 * EK we may need to add extra wall types, such as corners/doors.
 * @author Kelsey Jack (300275851) + kimeliz1
 *
 */
public enum Objects {
	KEY("key.png", "key.png", "key.png", "key.png"),
	REDFLAG("RedFlag.png", "RedFlag.png", "RedFlag.png", "RedFlag.png"),
	BLUEFLAG("BlueFlag.png", "BlueFlag.png", "BlueFlag.png", "BlueFlag.png"),
	POWERUP("PowerUp.png", "PowerUp.png", "PowerUp.png", "PowerUp.png"),
	CHEST("TreasureChest.png", "TreasureChest.png", "TreasureChest.png", "TreasureChest.png"),
	BAG("Bag.png", "Bag.png", "Bag.png", "Bag.png"),
	MAP("", "", "", ""),
	TREE("tree.png", "tree.png", "tree.png", "tree.png"),
	TABLE("", "", "", ""),
	WALL_E_W("WallEW.png", "WallEW.png", "WallNS.png", "WallNS.png"),
	WALL_N_S("WallNS.png", "WallNS.png", "WallEW.png", "WallEW.png"),
	CORNER_S_E("CornerSE.png", "CornerNW.png", "CornerNE.png", "CornerSW.png"),
	CORNER_S_W("CornerSW.png", "CornerNE.png", "CornerSE.png", "CornerNW.png"),
	CORNER_N_W("CornerNW.png", "CornerSE.png", "CornerSW.png", "CornerNE.png"),
	CORNER_N_E("CornerNE.png", "CornerSW.png", "CornerNW.png", "CornerSE.png"),
	SNOWBALL("Snowball.png", "Snowball.png", "Snowball.png", "Snowball.png"),
	BUSH("Bush.png", "Bush.png", "Bush.png", "Bush.png"),
	REDPLAYER_N("RedPlayerNorth.png", "RedPlayerSouth.png", "RedPlayerWest.png", "RedPlayerEast.png"),
	REDPLAYER_E("RedPlayerEast.png", "RedPlayerWest.png", "RedPlayerNorth.png", "RedPlayerSouth.png"),
	REDPLAYER_S("RedPlayerSouth.png", "RedPlayerNorth.png", "RedPlayerEast.png", "RedPlayerWest.png"),
	REDPLAYER_W("RedPlayerWest.png", "RedPlayerEast.png", "RedPlayerSouth.png", "RedPlayerNorth.png"),
	BLUEPLAYER_N("BluePlayerNorth.png", "BluePlayerSouth.png", "BluePlayerWest.png", "BluePlayerEast.png"),
	BLUEPLAYER_E("BluePlayerEast.png", "BluePlayerWest.png", "BluePlayerNorth.png", "BluePlayerSouth.png"),
	BLUEPLAYER_S("BluePlayerSouth.png", "BluePlayerNorth.png", "BluePlayerEast.png", "BluePlayerWest.png"),
	BLUEPLAYER_W("BluePlayerWest.png", "BluePlayerEast.png", "BluePlayerSouth.png", "BluePlayerNorth.png"),
	DOORNS("DoorS.png", "DoorS.png", "DoorW.png", "DoorW.png"),
	DOOREW("DoorW.png", "DoorW.png", "DoorS.png", "DoorS.png"),
	HEALTH("HealthPotion.png", "HealthPotion.png", "HealthPotion.png", "HealthPotion.png"),
	SNOWMAN("snowman.png", "snowman.png", "snowman.png", "snowman.png");

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
