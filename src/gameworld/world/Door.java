package gameworld.world;

import graphics.assets.Entities;

/**
 * A door is furniture that can be locked. If it is locked, it is not possible
 * to move through the door.
 * @author Kelsey Jack 300275851
 *
 */
public class Door extends Furniture implements Lockable {
	private boolean locked;
	public final int id;

	/**
	 * @param description
	 * @param d The direction it faces
	 * @param ID The ID a key must have to unlock it.
	 * @param locked Whether the door is locked initially
	 */
	public Door(String description, Direction d, int ID, boolean locked) {
		super(description, Entities.DOOREW);
		if (d == Direction.NORTH || d == Direction.SOUTH) {
			this.type = Entities.DOORNS;
		}
		this.id = ID;
		this.locked = locked;
	}

	@Override
	public boolean unlock(Key k) {
		if (k.ID == this.id) {
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
