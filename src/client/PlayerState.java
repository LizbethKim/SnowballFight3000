package client;

import gameworld.world.Location;
import graphics.assets.Objects;
import graphics.assets.Sprites;

import java.util.Collections;
import java.util.List;

import client.Client.Direction;

public class PlayerState {
	private List<Objects> inventory;
	private int health;
	private Location l;
	private Direction d; 	// the direction the player is facing
	private Sprites sprite;

	public void update(List<Objects> inventory, int health, Location l, Direction d) {
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

	public Direction getD() {
		return d;
	}

}
