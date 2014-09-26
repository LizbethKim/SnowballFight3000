package client;

import graphics.assets.Objects;

import java.util.Collections;
import java.util.List;

public class PlayerState {
	private List<Objects> inventory;
	private int health;

	public void update(List<Objects> inventory, int health) {
		this.inventory = inventory;
		this.health = health;
	}

	public List<Objects> getInventory() {
		return Collections.unmodifiableList(inventory);
	}

	public int getHealth() {
		return health;
	}
}
