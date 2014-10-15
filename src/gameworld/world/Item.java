package gameworld.world;

/**
 * Items can be picked up and moved around by players.
 * Inventories can contain items, but not other entities.
 * @author Kelsey Jack 300275851
 *
 */
public abstract class Item implements InanimateEntity {
	protected String description;

	@Override
	public boolean canMoveThrough () {
		return true;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
