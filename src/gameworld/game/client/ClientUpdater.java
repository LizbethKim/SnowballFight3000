package gameworld.game.client;

import java.util.Map;

import ui.gamewindow.UI;
import gameworld.world.Area;
import gameworld.world.Board;
import gameworld.world.Direction;
import gameworld.world.Inventory;
import gameworld.world.Item;
import gameworld.world.Location;
import gameworld.world.Lockable;
import gameworld.world.NullLocation;
import gameworld.world.Player;
import gameworld.world.Snowball.SnowballType;
import gameworld.world.Team;
import graphics.assets.Objects;

/**
 * Class that modifies the state of the model on the clients. Used by the Client
 * component of the network to update according to commands from the server.
 * @author Kelsey Jack 300275851
 *
 */
public class ClientUpdater {
	private Board board;
	private Map<Integer, Player> playerIDs;
	private int playerID;
	private Location[] snowballPositions = new Location[1];
	private SnowballType[] snowballTypes = new SnowballType[1];
	private ClientGame clientGame;
	private BoardState bs;
	private UI display;

	public ClientUpdater(ClientGame g, Board b, Map<Integer, Player> players, BoardState bs, UI display, int playerID) {
		this.clientGame = g;
		this.board = b;
		this.playerIDs = players;
		this.playerID = playerID;
		this.bs = bs;
		this.display = display;
		updateBoardState();
	}

	/**
	 * Adds a player with the given parameters to the model
	 * @param name
	 * @param t
	 * @param id
	 * @param l
	 */
	public void addPlayer(String name, Team t, int id, Location l) {
		if (playerIDs.get(id) == null) {
			playerIDs.put(id, new Player(name, t, id, l));
		}
		updateBoardState();
	}

	/**
	 * Sets the local player id and location.
	 * @param id
	 * @param l
	 */
	public void createLocalPlayer(int id, Location l) {
		this.playerID = id;
		clientGame.setID(id);
		clientGame.setLocalLocation(l);
		updateBoardState();
	}

	/**
	 * Moves the player with the given id to the given location.
	 * @param id
	 * @param l
	 */
	public void movePlayer(int id, Location l) {
		Player p = playerIDs.get(id);
		if (p != null) {
			p.move(l);
		}
		updateBoardState();
	}

	/**
	 * Remove the player with the given id from the model
	 * @param id
	 */
	public void removePlayer(int id) {
		this.playerIDs.remove(id);
		updateBoardState();
	}

	/**
	 * Turns the player with the given id to face the given direction.
	 * @param id
	 * @param d
	 */
	public void turnPlayer(int id, Direction d) {
		Player p = playerIDs.get(id);
		if (p != null) {
			p.setDirection(d);
		}
		updateBoardState();
	}

	/**
	 * Changes the local player health to the given value
	 * @param health The new health of the player
	 */
	public void updatePlayerHealth(int health) {
		Player p = playerIDs.get(playerID);
		if (p != null) {
			p.setHealth(health);
		}
	}

	/**
	 * Adds the given item to the local player's inventory
	 * @param i
	 */
	public void pickupItem(Item i) {
		Player p = this.playerIDs.get(playerID);
		if (p != null) {
			p.addItemToInventory(i);
		}
	}

	/**
	 * Removes the item at the given index in the local player's inventory
	 * @param index
	 */
	public void removeFromInventory(int index) {
		Player p = playerIDs.get(playerID);
		if (p != null) {
			p.removeFromInventory(index);
		}
		updateBoardState();
	}

	/**
	 * Freezes the player with the given id on the local model.
	 * @param id ID of the player to freeze.
	 */
	public void freezePlayer(int id) {
		Player p = playerIDs.get(id);
		if (p != null) {
			p.setHealth(0);
		}
	}

	/**
	 * Unfreezes the player with the given id on the local model.
	 * @param id ID of the player to unfreeze.
	 */
	public void unFreezePlayer(int id) {
		Player p = playerIDs.get(id);
		if (p != null) {
			p.setHealth(20);
		}
	}

	/**
	 * Places the given item to the given location.
	 * @param i Item to place
	 * @param l Location to place it at
	 */
	public void placeItem(Item i, Location l) {
		if (board.tileAt(l).isClear()) {
			board.tileAt(l).place(i);
		} else if (board.tileAt(l).getOn() instanceof Inventory) {
			((Inventory)board.tileAt(l).getOn()).addItem(i);
		}
	}

	/**
	 * Removes the item at the given location from the local model.
	 * @param l
	 */
	public void removeItemAt(Location l) {
		if (board.containsLocation(l)) {
			board.removeItemAt(l);
		}
		updateBoardState();
	}

	/**
	 * Removes the item at the given index from the container at the
	 * given location on the local model.
	 * @param l
	 * @param index
	 */
	public void removeFromContainerAt(Location l, int index) {
		if (board.containsLocation(l) && board.tileAt(l).getOn() instanceof Inventory) {
			Inventory i = (Inventory)board.tileAt(l).getOn();
			if (index < i.size()) {
				i.removeItem(i.getContents().get(index));
			}
		}
		updateBoardState();
	}

	/**
	 * Exits the program if the client is disconnected from the server
	 */
	public void disconnectFromServer() {
		System.exit(0);
	}

	/**
	 * Updates the positions and types of the snowballs
	 * @param snowballPositions
	 * @param snowballTypes
	 */
	public void updateProjectiles(Location[] snowballPositions, SnowballType[] snowballTypes) {
		this.snowballPositions = snowballPositions;
		this.snowballTypes = snowballTypes;
		updateBoardState();
	}


	/**
	 * Unlocks the container at the given location
	 * @param l
	 */
	public void unlock(Location l) {
		if (board.containsLocation(l) && board.tileAt(l).getOn() != null
				&& board.tileAt(l).getOn() instanceof Lockable) {
			((Lockable)board.tileAt(l).getOn()).setLocked(false);
		}
	}

	/**
	 * Updates the score of the player with the given id
	 * @param score
	 * @param id
	 */
	public void updateScore(int score, int id) {
		this.playerIDs.get(playerID).setScore(score);
	}

	/**
	 * Updates the time to the given hours
	 * @param time
	 */
	public void updateTime(int time) {
		bs.setTime(time);
	}

	/**
	 * Ends the game, with the given team as the winners.
	 * @param winners
	 */
	public void endGame (Team winners) {
		System.out.println("winning!");
		display.endGame(winners == Team.RED ? "Red Team"  : "Blue Team");
	}


	// Updates the boardState after other methods are called.
	private void updateBoardState() {
		if (playerIDs.get(playerID) == null || !board.containsLocation(playerIDs.get(playerID).getLocation())) {
			return;
		}

		Area toRender = board.getAreaContaining(playerIDs.get(playerID).getLocation());

		Objects[][] items = board.itemEnumsInArea(toRender);
		for (int i = 0; i < snowballPositions.length; i++) {
			Location l = snowballPositions[i];
			if (l != null && toRender.containsLoc(l)) {
				items[l.x][l.y] = (snowballTypes[i] == SnowballType.FLAMING ? Objects.FIRESNOWBALL : Objects.SNOWBALL);
			}
		}
		for (Player p: playerIDs.values()) {
			if (p.getLocation() != null && toRender.containsLoc(p.getLocation())) {
				items[p.getLocation().x][p.getLocation().y] = p.asEnum();
			}
		}
		if (playerIDs.get(playerID) == null) {
			bs.update(board.convertToEnums(), items, new NullLocation());
		} else {
			bs.update(board.convertToEnumsInArea(toRender),
					items, playerIDs.get(playerID).getLocation());
		}
		display.repaint();
	}

	// For loading multiplayer game. Not implemented.

	public void receivePlayerValidity (boolean isValid) {
		if (!isValid) {
		}
	}



}
