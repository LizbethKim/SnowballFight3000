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
 * Class that modifies the state of the model on the clients.
 * @author Kelsey Jack 300275851
 *
 */
public class ClientUpdater {
	private Board board;
	private Map<Integer, Player> playerIDs;
	private int playerID;

	private BoardState bs;
	private UI display;
	private ClientGame clientGame;
	private Location[] snowballPositions = new Location[1];

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
			System.out.println("Adding new player with name " + name + " and ID " + id);
			playerIDs.put(id, new Player(name, t, id, l));
		}
		updateBoardState();
	}

	public void createLocalPlayer(int id, Location l) {
		this.playerID = id;
		clientGame.setID(id);
		clientGame.setLocalLocation(l);
		this.updateBoardState();
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
		this.updateBoardState();
	}

	public void updateBoardState() {
		Objects[][] items = board.itemEnums();
		for (Location l: snowballPositions) {
			if (l != null) {
				items[l.x][l.y] = Objects.SNOWBALL;
			}
		}
		for (Player p: playerIDs.values()) {
			if (p.getLocation() != null) {
				if (p.getTeam() == Team.RED) {
					if (p.getDirection() == Direction.NORTH) {
						items[p.getLocation().x][p.getLocation().y] = Objects.REDPLAYER_N;
					} else if (p.getDirection() == Direction.EAST) {
						items[p.getLocation().x][p.getLocation().y] = Objects.REDPLAYER_E;
					} else if (p.getDirection() == Direction.SOUTH) {
						items[p.getLocation().x][p.getLocation().y] = Objects.REDPLAYER_S;
					} else {
						items[p.getLocation().x][p.getLocation().y] = Objects.REDPLAYER_W;
					}
				} else {
					if (p.getDirection() == Direction.NORTH) {
						items[p.getLocation().x][p.getLocation().y] = Objects.BLUEPLAYER_N;
					} else if (p.getDirection() == Direction.EAST) {
						items[p.getLocation().x][p.getLocation().y] = Objects.BLUEPLAYER_E;
					} else if (p.getDirection() == Direction.SOUTH) {
						items[p.getLocation().x][p.getLocation().y] = Objects.BLUEPLAYER_S;
					} else {
						items[p.getLocation().x][p.getLocation().y] = Objects.BLUEPLAYER_W;
					}
				}
			}
		}
		if (playerIDs.get(playerID) == null) {
			bs.update(board.convertToEnums(), items, new NullLocation());		// KTC temp
			System.out.println("Player is null");
		} else {
			bs.update(board.convertToEnums(), items, playerIDs.get(playerID).getLocation());
		}
		display.repaint();

	}

}
