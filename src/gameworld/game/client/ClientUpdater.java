package gameworld.game.client;

import java.util.Map;

import ui.UI;
import gameworld.world.Area;
import gameworld.world.Board;
import gameworld.world.Direction;
import gameworld.world.Inventory;
import gameworld.world.Item;
import gameworld.world.Location;
import gameworld.world.Lockable;
import gameworld.world.NullLocation;
import gameworld.world.Player;
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

	public void addPlayer(String name, Team t, int id, Location l) {
		if (playerIDs.get(id) == null) {
			playerIDs.put(id, new Player(name, t, id, l));
		}
		updateBoardState();
	}

	public void createLocalPlayer(int id, Location l) {
		this.playerID = id;
		clientGame.setID(id);
		clientGame.setLocalLocation(l);
		updateBoardState();
	}

	public void movePlayer(int id, Location l) {
		Player p = playerIDs.get(id);
		if (p != null) {
			p.move(l);
		}
		updateBoardState();
	}

	public void removePlayer(int id) {
		this.playerIDs.remove(id);
		updateBoardState();
	}

	public void turnPlayer(int id, Direction d) {
		Player p = playerIDs.get(id);
		if (p != null) {
			p.setDirection(d);
		}
		updateBoardState();
	}

	public void updatePlayerHealth(int health) {
		Player p = playerIDs.get(playerID);
		if (p != null) {
			p.setHealth(health);
		}
	}

	public void pickupItem(Item i) {
		Player p = this.playerIDs.get(playerID);
		if (p != null) {
			p.addItemToInventory(i);
		}
	}

	public void removeFromInventory(int index) {
		Player p = playerIDs.get(playerID);
		if (p != null) {
			p.removeFromInventory(index);
		}
		updateBoardState();
	}

	public void freezePlayer(int id) {
		Player p = playerIDs.get(id);
		if (p != null) {
			p.setHealth(0);
		}
	}

	public void removeItemAt(Location l) {
		if (board.containsLocation(l)) {
			board.removeItemAt(l);
		}
		updateBoardState();
	}

	public void removeFromContainerAt(Location l, int index) {
		if (board.containsLocation(l) && board.tileAt(l).getOn() instanceof Inventory) {
			Inventory i = (Inventory)board.tileAt(l).getOn();
			if (index < i.size()) {
				i.removeItem(i.getContents().get(index));
			}
		}
		updateBoardState();
	}


	public void disconnectFromServer() {
		// KTC potentially do something else
		System.exit(0);
	}

	public void updateProjectiles(Location[] snowballPositions) {
		this.snowballPositions = snowballPositions;
		updateBoardState();
	}

	public void placeItem(Item i, Location l) {
		if (board.tileAt(l).isClear()) {
			board.tileAt(l).place(i);
		} else if (board.tileAt(l).getOn() instanceof Inventory) {
			((Inventory)board.tileAt(l).getOn()).addItem(i);
		}
	}
	
	public void unlock(Location l) {
		if (board.containsLocation(l) && board.tileAt(l).getOn() != null 
				&& board.tileAt(l).getOn() instanceof Lockable) {
			((Lockable)board.tileAt(l).getOn()).setLocked(false);
		}
	}
	
	public void updateScore(int score, int id) {
		this.playerIDs.get(playerID).setScore(score);
	}
	
	public void updateTime(int time) {
		bs.setTime(time);
	}
	
	public void endGame (Team winners) {
		display.endGame();	// KTC ask Ryan to take the winning team
	}

	public void receivePlayerValidity (boolean isValid) {
		if (!isValid) {
			// KTC make the user select a different player.
		}
	}
	
	// Updates the boardState after other methods are called.
	private void updateBoardState() {
		if (playerIDs.get(playerID) == null || !board.containsLocation(playerIDs.get(playerID).getLocation())) {
			return;
		}

		Area toRender = board.getAreaContaining(playerIDs.get(playerID).getLocation());

		Objects[][] items = board.itemEnumsInArea(toRender);
		for (Location l: snowballPositions) {
			if (l != null && toRender.containsLoc(l)) {
				items[l.x][l.y] = Objects.SNOWBALL;
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

	
}
