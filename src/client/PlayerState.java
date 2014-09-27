package client;

import gameworld.world.Direction;
import gameworld.world.Location;
import graphics.assets.Objects;
import graphics.assets.Sprites;

import java.util.Collections;
import java.util.List;


/**
 * Gives information about the state of the player to the GUI.
 * Is updated by the client when updates are sent through the network.
 * @author Kelsey Jack 300275851
 *
 */
public class PlayerState {
	private List<Objects> inventory;
	private int health;
	private Location l;
	private Direction d; 	// the direction the player is facing
	private Sprites sprite;

	protected void update(List<Objects> inventory, int health, Location l, Direction d) {
		this.inventory = inventory;
		this.health = health;
		this.l = l;
	}

	public List<Objects> getInventory() {
		return Collections.unmodifiableList(inventory);
	}

	public int getHealth() {
		return health;
	}

	public Location getL() {
		return l;
	}

	public Sprites getSprite() {
		return sprite;
	}

	public void setSprite(Sprites sprite) {
		this.sprite = sprite;
	}

	public Direction getDirection() {
		return d;
	}

}
