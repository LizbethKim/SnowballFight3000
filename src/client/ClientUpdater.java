package client;

import java.util.Map;

import ui.UI;
import gameworld.world.Board;
import gameworld.world.Direction;
import gameworld.world.Location;
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

	// Only for single-player mode
	public ClientUpdater( Board b, Map<Integer, Player> players, BoardState bs, UI display) {
		this.board = b;
		this.playerIDs = players;
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
		updateBoardState();	// KTC call this inside a loop, perhaps?
	}

	public void updateProjectiles(Location[] snowballPositions) {
		this.snowballPositions = snowballPositions;
		updateBoardState();
	}

	// Updates the boardState after other methods are called.
	private void updateBoardState() {
		Objects[][] items = board.itemEnums();
		for (Location l: snowballPositions) {
			if (l != null) {
				items[l.x][l.y] = Objects.SNOWBALL;
			}
		}
		for (Player p: playerIDs.values()) {
			if (p.getLocation() != null) {
				items[p.getLocation().x][p.getLocation().y] = p.asEnum();
			}
		}
		if (playerIDs.get(playerID) == null) {
			bs.update(board.convertToEnums(), items, new NullLocation());
		} else {
			bs.update(board.convertToEnums(), items, playerIDs.get(playerID).getLocation());
		}
		display.repaint();
	}

}
