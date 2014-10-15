package gameworld.world;

/**
 * Entity that cannot move itself.
 * @author Kelsey Jack 300275851
 *
 */
public interface InanimateEntity extends Entity {
	/**
	 * @return Whether the player can walk through the entity
	 */
	public boolean canMoveThrough();

	/**
	 * @return A description of the entity.
	 */
	public String getDescription();
}
