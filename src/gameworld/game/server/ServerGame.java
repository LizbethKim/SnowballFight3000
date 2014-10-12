package gameworld.game.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import server.Server;
import server.events.CreateLocalPlayerEvent;
import server.events.CreatePlayerEvent;
import server.events.FreezePlayerEvent;
import server.events.MoveEvent;
import server.events.PickUpItemEvent;
import server.events.RemoveItemEvent;
import server.events.RemovePlayerEvent;
import server.events.TurnEvent;
import server.events.UpdateHealthEvent;
import server.events.UpdateProjectilePositionsEvent;
import gameworld.world.Board;
import gameworld.world.Direction;
import gameworld.world.InanimateEntity;
import gameworld.world.Item;
import gameworld.world.Location;
import gameworld.world.Player;
import gameworld.world.Powerup;
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

	public void pickUpItem (int playerID) {
		Player p = this.playerIDs.get(playerID);
		if (p != null) {
			Location l = Location.locationInFrontOf(p.getLocation(), p.getDirection());
			InanimateEntity on = board.tileAt(l).getOn();
			if (on != null && on instanceof Item) {
				if(p.getInventory().addItem((Item)on)) {
					board.tileAt(l).removeOn();
					server.queuePlayerUpdate(new PickUpItemEvent(l), playerID);
					for (int id: playerIDs.keySet()) {
						server.queuePlayerUpdate(new RemoveItemEvent(l), id);
					}
				}
			}
		}
	}

	public void dropItem(int playerID, int index) {
		Player p = this.playerIDs.get(playerID);
		if (p != null) {
			Location l = Location.locationInFrontOf(p.getLocation(), p.getDirection());
			Item toDrop = p.getInventory().getContents().get(index);
			p.getInventory().removeItem(toDrop);
			// server.queuePlayerUpdate(new RemoveItemFromInventoryEvent(index), playerID);
			for (int id: playerIDs.keySet()) {
				// server.queuePlayerUpdate(new PlaceItemEvent(toDrop.serialize(), l), id);
			}
		}
	}

	public void useItem(int playerID, int index) {
		Player p = this.playerIDs.get(playerID);
		if (p != null) {
			if (index != -1 && p.getInventory().getContents().get(index) != null) {
				Item toUse = p.getInventory().getContents().get(index);
				if (toUse instanceof Powerup) {
					Powerup powerup = (Powerup)toUse;
					if (powerup.getPower() == Powerup.Power.HEALTH_POTION || powerup.getPower() == Powerup.Power.STRONG_HEALTH_POTION) {
						powerup.use(p);
						server.queuePlayerUpdate(new UpdateHealthEvent(p.getHealth()), playerID);
					} else {
						// server.queuePlayerUpdate(new RemoveItemFromInventoryEvent(index), playerID);
					}
				} else {
					// KTC later
				}
			}
		}
	}

	public void throwSnowball(int playerID) {
		System.out.println("Server throwing snowball");
		Player thrower = playerIDs.get(playerID);
		if (board.tileAt(thrower.getLocation()).isSnow()) {
			projectiles.add(snowballFactory.makeSnowball(thrower.getLocation(), thrower.getDirection(), SnowballType.NORMAL));
		}
	}

	public void addPlayer(int playerID, String name, Team t) {
		Location spawnLoc = new Location(3,3); 	// KTC change to something meaningful later
		server.queuePlayerUpdate(new CreateLocalPlayerEvent(playerID, spawnLoc), playerID);
		Player p = new Player(name, t, playerID, spawnLoc);
		//server.queuePlayerUpdate(new MoveEvent(playerID, spawnLoc), playerID);
		playerIDs.put(playerID, p);
		for (int id: playerIDs.keySet()) {
			Player other = playerIDs.get(id);
			server.queuePlayerUpdate(new CreatePlayerEvent(id, other.name, other.getLocation(), other.getTeam()), playerID);
			server.queuePlayerUpdate(new CreatePlayerEvent(playerID, name, spawnLoc, t), id);
		}
	}

	public void removePlayer(int playerID) {
		playerIDs.remove(playerID);
		for (int id: playerIDs.keySet()) {
			server.queuePlayerUpdate(new RemovePlayerEvent(playerID), id);
		}
		if (playerIDs.isEmpty()) {
			System.exit(0);
		}
	}

	public void setServer(Server s) {
		this.server = s;
	}

	public void clockTick() {
		Iterator<Snowball> it = projectiles.iterator();
		while (it.hasNext()) {
			Snowball s = it.next();
			s.clockTick();
			if (!board.containsLocation(s.getLocation()) || !board.canTraverse(s.getLocation())) {
				it.remove();
				continue;
			}
			for (Player p: playerIDs.values()) {
				if (p.getLocation().equals(s.getLocation())) {
					s.hit(p);
					server.queuePlayerUpdate(new UpdateHealthEvent(p.getHealth()), p.getID());
					if (p.isFrozen()) {
						for (int id: playerIDs.keySet()) {
							server.queuePlayerUpdate(new FreezePlayerEvent(p.getID()), id);
						}
					}
					it.remove();
					break;
				}
			}
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
		// KTC possibly do time logic too.
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
