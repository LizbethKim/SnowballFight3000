package gameworld.world;

import graphics.assets.Objects;

/**
 *
 * @author Kelsey Jack 300275851
 *
 */
public class Door extends Furniture implements Lockable {
	private boolean locked;
	public final int id;

	public Door(String description, int id, Direction d) {
		super(description, Objects.DOOREW);
		if (d == Direction.NORTH || d == Direction.SOUTH) {
			this.type = Objects.DOORNS;
		}
		this.id = id;
		if (id == 0) {
			this.locked = false;
		} else {
			this.locked = true;
		}
	}

	@Override
	public boolean unlock(Key k) {
		if (k.id == this.id) {
			this.locked = false;
			return true;
		}
		return false;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public boolean canMoveThrough() {
		return !locked;
	}

	@Override
	public boolean isLocked() {
		return locked;
	}

	@Override
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
}
