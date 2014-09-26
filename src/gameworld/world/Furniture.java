package gameworld.world;

import graphics.assets.Objects;

import java.util.EnumSet;

public class Furniture implements StaticEntity {
	
	private static EnumSet<Objects> FURNITURE = EnumSet.of(Objects.BUSH, 
			Objects.TABLE, Objects.TREE, Objects.WALL_E_W, Objects.WALL_N_S);
	
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

	public Objects getType() {
		return type;
	}
}
