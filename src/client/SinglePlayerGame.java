package client;

import gameworld.world.Board;
import gameworld.world.Direction;
import gameworld.world.Location;
import gameworld.world.Player;
import gameworld.world.Snowball;
import gameworld.world.SnowballFactory;
import gameworld.world.SnowballFactory.SnowballType;
import graphics.assets.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SinglePlayerGame {
	private Board board;
	private Map<Integer, Player> playerIDs;
	private List<Snowball> projectiles;
	private SnowballFactory snowballFactory;
	private Client client;

	public SinglePlayerGame(Board b) {
		this.board = b;
		client = new Client(100, board.convertToEnums(), board.itemEnums());
		playerIDs = new HashMap<Integer, Player>();
		playerIDs.put(client.getPlayerID(), new Player(1, new Location(2,2), "Bob"));
		projectiles = new ArrayList<Snowball>();

		updateClientBoard(client.getPlayerID());
	}

	// examples of methods that will be in here
	public void movePlayer (int playerID, Location l) {
		Player p = playerIDs.get(playerID);
		if (board.canTraverse(l) && p != null) {
			if (p.move(l)) {
				client.getBoard().updatePlayerLocation(playerID, l);
				client.refresh();
			}
		}
	}

	public void turnPlayer(int playerID, Direction d) {
		Player p = playerIDs.get(playerID);
		if (p != null) {
			p.setDirection(d);
			client.getBoard().updatePlayerDirection(playerID, d);
			client.refresh();
		}
	}

	public void pickUpItemAt (String player, Location l) {
		// KTC make this work
	}

	public void throwSnowball(int playerID) {
		Player thrower = playerIDs.get(playerID);
		if (board.tileAt(thrower.getLocation()).isSnow()) {
			projectiles.add(snowballFactory.makeSnowball(thrower.getLocation(), thrower.getDirection(), SnowballType.NORMAL));
		}
	}

	public Player playerAt(Location l) {
		// Possibly not a good idea, depends on encapsulation and mutability of Player
		return null;
	}

	public void clockTick() {
		for (Snowball s: projectiles) {
			s.clockTick();
			// if it has collided with anything, make it hit that, and then
			// delete it from the list.
		}
		// KTC update projectiles, possibly do time logic.
		// check for collisions and remove projectiles that have hit something.
	}


	public void updateClientBoard(int playerID) {
		Objects[][] items = board.itemEnums();
		for (Player p: playerIDs.values()) {

		}
		client.getBoard().update(board.convertToEnums(), board.itemEnums(), playerIDs.get(playerID).getDirection());
	}

}
