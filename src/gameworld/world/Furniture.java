package gameworld.world;

import graphics.assets.Objects;

import java.util.EnumSet;

/**
 * A decorative object that can only be inspected, not interacted
 * with. Blocks the tile it's on.
 * @author Kelsey Jack 300275851
 *
 */
public class Furniture implements StaticEntity {

	private static EnumSet<Objects> FURNITURE = EnumSet.of(Objects.BUSH,
			Objects.TABLE, Objects.TREE, Objects.WALL_E_W, Objects.WALL_N_S,
			Objects.DOORNS, Objects.DOOREW,
			Objects.CHEST, Objects.CORNER_N_W, Objects.CORNER_N_E,
			Objects.CORNER_S_W, Objects.CORNER_S_E);

	private String description;
	private Objects type;

	public Furniture(String description, Objects type) {
		if (!FURNITURE.contains(type)) {
			throw new IllegalArgumentException("That type is not furniture.");
		}
		this.description = description;
		this.type = type;
	}

	@Override
	public boolean canMoveThrough() {
		return false;
	}

	public String getDescription() {
		return description;
	}

	public Objects asEnum() {
		return type;
	}

}
