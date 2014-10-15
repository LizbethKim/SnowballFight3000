package gameworld.world;

/**
 * Can be locked. Has a key associated with it.
 * @author Kelsey Jack 300275851
 */
public interface Lockable extends StaticEntity {
	/**
	 * Attempts to unlock the Lockable with the given Key
	 * @param k
	 * @return Whether the Lockable was successfully unlocked
	 */
	public boolean unlock(Key k);

	/**
	 * @return If the Lockable is locked
	 */
	public boolean isLocked();

	/**
	 * @param locked Whether it should be locked.
	 */
	public void setLocked(boolean locked);

	/**
	 * @return The ID of the Key that would unlock it
	 */
	public int getID();

}
