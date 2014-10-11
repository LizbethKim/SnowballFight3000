package gameworld.world;

/**
 * Entity that cannot move itself.
 * @author Kelsey Jack 300275851
 *
 */
public interface InanimateEntity extends Entity {
	public boolean canMoveThrough();
	public String getDescription();
}
