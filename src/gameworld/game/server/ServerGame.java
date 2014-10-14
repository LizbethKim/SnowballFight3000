package gameworld.game.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import server.Server;
import server.events.*;
import gameworld.world.*;

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
	private int time = 12;	// 24 hour time
	private long lastHourTime;
	private final long millisPerHour = 10000;

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
				if(p.addItemToInventory((Item)on)) {
					board.tileAt(l).clear();
					server.queuePlayerUpdate(new PickUpItemEvent((Item)on), playerID);
					for (int id: playerIDs.keySet()) {
						server.queuePlayerUpdate(new RemoveItemEvent(l), id);
					}
				}
			}
		}
	}

	public void takeFromContainer(int playerID, int index) {
		Player p = this.playerIDs.get(playerID);
		if (p != null) {
			Location on = p.getLocationInFrontOf();
			if (board.tileAt(on).getOn() != null && board.tileAt(on).getOn() instanceof Inventory) {
				Inventory takingFrom = (Inventory)board.tileAt(on).getOn();
				if (index < takingFrom.size()) {
					Item toTake = takingFrom.getContents().get(index);
					if (p.addItemToInventory(toTake)) {
						takingFrom.removeItem(toTake);
						server.queuePlayerUpdate(new PickUpItemEvent(toTake), playerID);
						for (int id: playerIDs.keySet()) {
							server.queuePlayerUpdate(new RemoveFromContainerEvent(on, index), id);
						}
					}
				}
			}
		}
	}

	public void dropItem(int playerID, int index) {
		Player p = this.playerIDs.get(playerID);
		if (p != null) {
			Location l = p.getLocationInFrontOf();
			if (index < p.getInventoryItems().size()) {
				Item toPlace = p.removeFromInventory(index);
				Tile toPlaceOn = board.tileAt(l);
				if (toPlaceOn instanceof FlagTile && toPlaceOn.place(toPlace)) {
					Team won = Team.values()[(((FlagTile)toPlaceOn).getTeam().ordinal() + 1) % 2];
					System.out.println("The " + (won == Team.BLUE ? "blue" :"red") +  " team won!");
					server.queuePlayerUpdate(new RemoveFromInventoryEvent(index), playerID);
					for (int id: playerIDs.keySet()) {
						server.queuePlayerUpdate(new PlaceItemEvent(l, toPlace), id);
						server.queuePlayerUpdate(new EndGameEvent(won), playerID);
					}
				} else if (toPlaceOn.isClear() && toPlaceOn.place(toPlace)) {
					server.queuePlayerUpdate(new RemoveFromInventoryEvent(index), playerID);
					for (int id: playerIDs.keySet()) {
						server.queuePlayerUpdate(new PlaceItemEvent(l, toPlace), id);
					}
				} else if (toPlaceOn.getOn() instanceof Inventory && ((Inventory)toPlaceOn.getOn()).addItem(toPlace)) {
					server.queuePlayerUpdate(new RemoveFromInventoryEvent(index), playerID);
					for (int id: playerIDs.keySet()) {
						server.queuePlayerUpdate(new PlaceItemEvent(l, toPlace), id);
					}
				}
			}
		}
	}


	public void useItem(int playerID, int index) {
		Player p = this.playerIDs.get(playerID);
		if (p != null) {
			if (index != -1 && p.getInventoryItems().get(index) != null) {
				Item toUse = p.getInventoryItems().get(index);
				if (toUse instanceof Powerup) {
					Powerup powerup = (Powerup)toUse;
					if (powerup.getPower() == Powerup.Power.HEALTH_POTION || powerup.getPower() == Powerup.Power.STRONG_HEALTH_POTION) {
						powerup.use(p);
						server.queuePlayerUpdate(new UpdateHealthEvent(p.getHealth()), playerID);
					}
					server.queuePlayerUpdate(new RemoveFromInventoryEvent(index), playerID);
				} else if (toUse instanceof Key) {
					this.unlock(p.getLocationInFrontOf());
					server.queuePlayerUpdate(new RemoveFromInventoryEvent(index), playerID);
				}
			}
		}
	}

	private void unlock(Location l) {
		if (board.containsLocation(l) && board.tileAt(l).getOn() != null 
				&& board.tileAt(l).getOn() instanceof Lockable) {
			((Lockable)board.tileAt(l).getOn()).setLocked(false);
			for (int id: playerIDs.keySet()) {
				server.queuePlayerUpdate(new UnlockEvent(l), id);
			}
		}
	}
	
	public void throwSnowball(int playerID, Snowball.SnowballType type) {
		Player thrower = playerIDs.get(playerID);
		if (board.tileAt(thrower.getLocation()).isSnow()) {
			projectiles.add(snowballFactory.makeSnowball(thrower.getLocation(), thrower.getDirection(), type));
		}
	}
	
	public void unfreezePlayer(int playerID) {
		Player p = this.playerIDs.get(playerID);
		if (p!= null) {
			Location l = p.getLocationInFrontOf();
			Player toUnfreeze = null;
			for (Player player: playerIDs.values()) {
				if (player.getLocation().equals(l)) {
					toUnfreeze = player;
					break;
				}
			}
			if (toUnfreeze != null && p.getTeam() == toUnfreeze.getTeam()) {
				toUnfreeze.damage(-20);
				for (int id: playerIDs.keySet()) {
					server.queuePlayerUpdate(new UnfreezePlayerEvent(toUnfreeze.getID()), id);
				}
			}
		}
	}

	public void addPlayer(int playerID, String name, Team t) {
		Area spawnArea = board.getSpawnArea(t);
		Location spawnLoc = new NullLocation();
		for (Tile tile: spawnArea.getTiles()) {
			if (tile.isClear()) {
				spawnLoc = tile.getCoords();
				if (isFree(spawnLoc)) {
					System.out.println(spawnLoc);
					break;
				}
			}
		}
		server.queuePlayerUpdate(new CreateLocalPlayerEvent(playerID, spawnLoc), playerID);
		Player p = new Player(name, t, playerID, spawnLoc);
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
					it.remove();
					server.queuePlayerUpdate(new UpdateHealthEvent(p.getHealth()), p.getID());
					if (p.isFrozen()) {
						this.freezePlayer(p);
					}
					break;
				}
			}
		}
		Location[] snowballLocs = new Location[projectiles.size()];
		Snowball.SnowballType[] snowballTypes = new Snowball.SnowballType[projectiles.size()];
		int i = 0;
		for (Snowball s: projectiles) {
			snowballLocs[i] = s.getLocation();
			snowballTypes[i] = s.type;
			i++;
		}
		for(int id: playerIDs.keySet()) {
			server.queuePlayerUpdate(new UpdateProjectilePositionsEvent(snowballLocs, snowballTypes), id);
			Player p = playerIDs.get(id);
			if (!p.isFrozen()) {
				p.incrementScore(1);
				server.queuePlayerUpdate(new UpdateScoreEvent(p.getScore()), id);
			}
		}
		if (System.currentTimeMillis() - lastHourTime > millisPerHour) {
			this.time = (this.time + 1)%24;
			for (int id: playerIDs.keySet()) {
				server.queuePlayerUpdate(new UpdateTimeEvent(time), id);
			}
			this.lastHourTime = System.currentTimeMillis();
		}
	}
	
	private void freezePlayer(Player p) {
		List<Item> itemsInInventory = p.getInventoryItems();
		for (int i = 0; i < itemsInInventory.size(); i++) {
			server.queuePlayerUpdate(new RemoveFromInventoryEvent(0), p.getID());
		}
		List<Location> dropLocs = p.getSurroundingLocations();
		int i = 0;
		for (Item item: itemsInInventory) {
			while (!(board.containsLocation(dropLocs.get(i))
					&& board.tileAt(dropLocs.get(i)).isTraversable() 
					&& this.isFree(dropLocs.get(i)))) {
				i++;
			}
			for (int id: playerIDs.keySet()) {
				server.queuePlayerUpdate(new PlaceItemEvent(dropLocs.get(i), item), id);
			}
			this.board.tileAt(dropLocs.get(i)).place(item);
			i++;
		}
		for (int id: playerIDs.keySet()) {
			server.queuePlayerUpdate(new FreezePlayerEvent(p.getID()), id);
		}
		p.emptyInventory();
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