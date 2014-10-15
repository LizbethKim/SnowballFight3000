package gameworld.world;

import graphics.assets.Entities;

import java.util.EnumSet;

/**
 * A decorative object that can only be inspected, not interacted
 * with. Blocks the tile it's on.
 * @author Kelsey Jack 300275851
 *
 */
public class Furniture implements StaticEntity {

	private static EnumSet<Entities> FURNITURE = EnumSet.of(Entities.BUSH,
			Entities.TABLE, Entities.TREE, Entities.WALL_E_W, Entities.WALL_N_S,
			Entities.DOORNS, Entities.DOOREW,
			Entities.CHEST, Entities.CORNER_N_W, Entities.CORNER_N_E,
			Entities.CORNER_S_W, Entities.CORNER_S_E);

	private String description;
	protected Entities type;

	public Furniture(String description, Entities type) {
		if (!FURNITURE.contains(type)) {
			throw new IllegalArgumentException("That type is not furniture.");
		}
		this.description = description;
		this.type = type;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public boolean canMoveThrough() {
		return false;
	}

	@Override
	public Entities asEnum() {
		return type;
	}

}
