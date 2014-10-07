package gameworld.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import server.Server;
import server.events.CreateLocalPlayerEvent;
import server.events.CreatePlayerEvent;
import server.events.MoveEvent;
import server.events.RemovePlayerEvent;
import server.events.TurnEvent;
import server.events.UpdateProjectilePositionsEvent;
import gameworld.world.Area;
import gameworld.world.Board;
import gameworld.world.Direction;
import gameworld.world.Location;
import gameworld.world.Player;
import gameworld.world.Snowball;
import gameworld.world.SnowballFactory;
import gameworld.world.SnowballFactory.SnowballType;
import gameworld.world.Team;

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
		if (board.canTraverse(l) && p != null && this.isFree(l)) {
			if (p.move(l)) {
				for (int id: playerIDs.keySet()) {
					server.queuePlayerUpdate(new MoveEvent(playerID, l), id);
				}
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

//	public Player playerAt(Location l) {
//		// Possibly not a good idea, depends on encapsulation and mutability of Player
//		return null;
//	}

	public void addPlayer(int playerID, String name, Team t) {
		Location spawnLoc = new Location(3,3); 	// KTC change to something meaningful later


		server.queuePlayerUpdate(new CreateLocalPlayerEvent(playerID, spawnLoc), playerID);
		Player p = new Player(name, t, playerID, spawnLoc);
		//server.queuePlayerUpdate(new MoveEvent(playerID, spawnLoc), playerID);
		playerIDs.put(playerID, p);
		for (int id: playerIDs.keySet()) {
			server.queuePlayerUpdate(new CreatePlayerEvent(id, playerIDs.get(id).name, playerIDs.get(id).getLocation()), playerID);
			server.queuePlayerUpdate(new CreatePlayerEvent(playerID, name, spawnLoc), id);
		}
	}

	public void removePlayer(int playerID) {
		playerIDs.remove(playerID);
		for (int id: playerIDs.keySet()) {
			server.queuePlayerUpdate(new RemovePlayerEvent(playerID), id);
		}
	}

	public void setServer(Server s) {
		this.server = s;
	}

	public void clockTick() {
		// KTC recode this prettier later
		Iterator<Snowball> it = projectiles.iterator();
		while (it.hasNext()) {
			Snowball s = it.next();
			s.clockTick();
			if (!board.canTraverse(s.getLocation())) {
				it.remove();
				continue;
			}
			for (Player p: playerIDs.values()) {
				if (p.getLocation().equals(s.getLocation())) {
					s.hit(p);
					// KTC update that the player was hit
					it.remove();
					break;
				}
			}
			// if it has collided with anything, make it hit that, and then
			// delete it from the list.
		}
		Location[] snowballLocs = new Location[projectiles.size()];
		int i = 0;
		for (Snowball s: projectiles) {
			snowballLocs[i] = s.getLocation();
			i++;
		}
		for(int id: playerIDs.keySet()) {
			server.queuePlayerUpdate(new UpdateProjectilePositionsEvent(snowballLocs), id);
		}
		// KTC update projectiles, possibly do time logic.
		// check for collisions and remove projectiles that have hit something.
	}

	private boolean isFree(Location l) {
		for (Player p: playerIDs.values()) {
			if (p.getLocation().equals(l)) {
				return false;
			}
		}
		return true;
	}

}
