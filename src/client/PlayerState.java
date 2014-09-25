package client;

import java.util.Collections;
import java.util.List;

import client.BoardState.Objects;

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
