package client;

import java.util.List;
import java.util.Map;

import ui.UI;
import gameworld.world.Board;
import gameworld.world.Direction;
import gameworld.world.Location;
import gameworld.world.Player;
import gameworld.world.Snowball;
import gameworld.world.SnowballFactory;
import graphics.assets.Objects;

/**
 * Class that modifies the state of the model on the clients.
 * @author Kelsey Jack 300275851
 *
 */
public class ClientUpdater {
	private SnowballFactory snowballFactory;
	private Board board;
	private Map<Integer, Player> playerIDs;
	private List<Snowball> projectiles;

	private BoardState bs;	// KTC to do update this after updating.
	private UI display;
	private ClientGame clientGame;

	public ClientUpdater(ClientGame g, Board b, Map<Integer, Player> players, List<Snowball> projectiles, BoardState bs, UI display) {
		this.clientGame = g;
		this.board = b;
		this.playerIDs = players;
		this.projectiles = projectiles;
		this.bs = bs;
		this.display = display;
		updateBoardState();
	}


	// Only for single-player mode
	public ClientUpdater( Board b, Map<Integer, Player> players, List<Snowball> projectiles, BoardState bs, UI display) {
		this.board = b;
		this.playerIDs = players;
		this.projectiles = projectiles;
		this.bs = bs;
		this.display = display;
		updateBoardState();
	}

	public void addPlayer(String name, int id) {
		if (playerIDs.get(id) == null) {
			System.out.println("Adding new player with name " + name + " and ID " + id);
			playerIDs.put(id, new Player(name, id));
		}
		updateBoardState();
	}

	public void createLocalPlayer(int id) {
		System.out.println("created player with id: " + id);
		clientGame.setID(id);
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

	public void updateBoardState() {
		Objects[][] items = board.itemEnums();
		for (Player p: playerIDs.values()) {
			if (p.getLocation() != null) {
				//items[p.getLocation().x][p.getLocation().y] = Objects.PLAYER1;
				if (p.getDirection() == Direction.NORTH) {
					items[p.getLocation().x][p.getLocation().y] = Objects.PLAYER_N;
				} else if (p.getDirection() == Direction.EAST) {
					items[p.getLocation().x][p.getLocation().y] = Objects.PLAYER_E;
				} else if (p.getDirection() == Direction.SOUTH) {
					items[p.getLocation().x][p.getLocation().y] = Objects.PLAYER_S;
				} else {
					items[p.getLocation().x][p.getLocation().y] = Objects.PLAYER_W;
				}
			}
		}
		bs.update(board.convertToEnums(), items);
		display.repaint();

	}

}
