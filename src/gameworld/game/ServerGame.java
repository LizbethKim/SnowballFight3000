package gameworld.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.Server;
import server.events.CreateLocalPlayerEvent;
import server.events.CreatePlayerEvent;
import server.events.MoveEvent;
import server.events.TurnEvent;
import gameworld.world.Board;
import gameworld.world.Direction;
import gameworld.world.Location;
import gameworld.world.Player;
import gameworld.world.Snowball;
import gameworld.world.SnowballFactory;
import gameworld.world.SnowballFactory.SnowballType;

/**
 * Main game logic class. Interacts with the network on the server side.
 * @author Kelsey Jack 300275851
 *
 */
public class ServerGame {
	public static final long MOVE_DELAY = 200;

	private Board board;
	private Map<Integer, Player> playerIDs;
	private List<Snowball> projectiles;
	private SnowballFactory snowballFactory;
	private Server server;

	public ServerGame(Board b) {
		this.board = b;
		this.playerIDs = new HashMap<Integer, Player>();
		this.projectiles = new ArrayList<Snowball>();
		this.snowballFactory = new SnowballFactory();
	}

	// examples of methods that will be in here
	public void movePlayer (int playerID, Location l) {
		Player p = playerIDs.get(playerID);
		if (board.canTraverse(l) && p != null) {
			if (p.move(l)) {
				for (int id: playerIDs.keySet()) {
					server.queuePlayerUpdate(new MoveEvent(playerID, l), id);
				}
				// KTC only here do you send out an update. (TO ALL CLIENTS).
				// This will consist of the player id and the new position.
				// on the other end, it will search for the playerState that has this ID and update it.
			}
		}
	}

	public void turnPlayer(int playerID, Direction d) {
		Player p = playerIDs.get(playerID);
		if (p != null) {
			p.setDirection(d);
			for (int id: playerIDs.keySet()) {
				server.queuePlayerUpdate(new TurnEvent(playerID, d), id);
			}
			// KTC send back the update. this will consist of the player id and the new direction.
		}
	}

	public void pickUpItemAt (int playerID, Location l) {
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

	public void addPlayer(int playerID, String name) {
		server.queuePlayerUpdate(new CreateLocalPlayerEvent(playerID), playerID);
		playerIDs.put(playerID, new Player(name, playerID));
		for (int id: playerIDs.keySet()) {
			server.queuePlayerUpdate(new CreatePlayerEvent(playerID, name), playerID);
		}
	}


	public void setServer(Server s) {
		this.server = s;
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
}
