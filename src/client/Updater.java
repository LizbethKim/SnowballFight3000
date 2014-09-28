package client;

import java.util.List;
import java.util.Map;

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
public class Updater {
	private SnowballFactory snowballFactory;
	private Board board;
	private Map<Integer, Player> playerIDs;
	private List<Snowball> projectiles;

	private BoardState bs;	// KTC to do update this after updating.

	public Updater(Board b, Map<Integer, Player> players, List<Snowball> projectiles) {
		this.board = b;
		this.playerIDs = players;
		this.projectiles = projectiles;
		this.bs = new BoardState();
		updateBoardState();
	}

	public void addPlayer(int id, Player p) {
		playerIDs.put(id, p);
	}

	public void movePlayer(int id, Location l) {
		Player p = playerIDs.get(id);
		if (p != null) {
			p.move(l);
		}
		updateBoardState();
	}

	public void turnPlayer(int id, Direction d) {
		Player p = playerIDs.get(id);
		if (p != null) {
			p.setDirection(d);
		}
		updateBoardState();
	}

	public void updateBoardState() {
		Objects[][] items = board.itemEnums();
		for (Player p: playerIDs.values()) {
			System.out.println("a player! (" + p.getLocation().x + ", " + p.getLocation().y + ")");
			items[p.getLocation().x][p.getLocation().y] = Objects.PLAYER1;
//			if (p.getDirection() == Direction.NORTH) { KTC uncomment later
//				items[p.getLocation().x][p.getLocation().y] = Objects.PLAYER_N;
//			} else if (p.getDirection() == Direction.EAST) {
//				items[p.getLocation().x][p.getLocation().y] = Objects.PLAYER_E;
//			} else if (p.getDirection() == Direction.EAST) {
//				items[p.getLocation().x][p.getLocation().y] = Objects.PLAYER_S;
//			} else {
//				items[p.getLocation().x][p.getLocation().y] = Objects.PLAYER_W;
//			}
		}
		bs.update(board.convertToEnums(), items);
	}

	// KTC etc.
}
