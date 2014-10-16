package gameworld.game.client;

import gameworld.world.*;
import gameworld.world.Snowball.SnowballType;
import graphics.assets.Entities;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import network.Client;
import storage.LoadGame;
import storage.SaveGame;
import storage.StoredGame;
import ui.gamewindow.UI;

/**
 * Current representation of the model on the client.
 * Deals with the network and sends and receives updates to/from the server.
 *
 * @author Kelsey Jack 300275851
 *
 */
public class ClientGame {
	private Board board;
	private Map<Integer, Player> playerIDs;

	private Player player;
	private int playerID;

	private Client client;
	private ClientUpdater updater;
	private UI display;
	private BoardState boardState;

	private long lastMovedTime;
	private long lastFiredTime;
	private int selectedIndex = -1;		// index selected in inventory
	private String lastInspected = "";


	/**
	 * Creates a new ClientGame with user input passed by the UI
	 * @param name
	 * @param IP
	 * @param team
	 * @param display
	 */
	public ClientGame(String name, String IP, Team team, UI display) {
		this.client = new Client(IP);
		StoredGame sb = new LoadGame().loadGame(client.sendMapRequest());
		this.playerID = 0;
		this.board = sb.getBoard();

		boardState = new BoardState(board.convertToEnums(), board.itemEnums());
		playerIDs = new HashMap<Integer, Player>();
		this.display = display;
		client.sendNameAndTeam(name, team);
		if(sb.getPlayers().isEmpty()){
			player = new Player(name, team, 0, new NullLocation());
		}else{
			player = sb.getPlayers().get(0);
		}
		ClientUpdater u = this.getUpdater();
		client.startReceiving(u, board.width(), board.height());
	}

	// Methods to send requests to the server if appropriate

	/**
	 * Sends a request to the server to move the player in the given direction.
	 * @param d
	 */
	public void move(Direction d) {
		this.lastInspected = "";
		Direction up = boardState.getDirection();
		d = Direction.values()[(d.ordinal() - up.ordinal() + 4) % 4];
		if (!player.isFrozen() && System.currentTimeMillis() - lastMovedTime > player.getStepDelay()) {
			if (player.getDirection() == d) {
				Location newLoc = player.getLocationInFrontOf();
				if (board.containsLocation(newLoc) && this.isFree(newLoc)) {
					if (board.canTraverse(newLoc)) {
						client.sendMove(newLoc);
					} else if (board.tileAt(newLoc).getOn() instanceof Door) {
						if (this.unlock((Lockable)board.tileAt(newLoc).getOn(), newLoc)) {
							client.sendMove(newLoc);
						}
					}
				}

			} else {
				client.sendTurn(d);
			}
		lastMovedTime = System.currentTimeMillis();
		}
	}

	/**
	 * Sends a request to the server to throw a snowball
	 */
	public void throwSnowball() {
		if (!player.isFrozen() && System.currentTimeMillis() - lastFiredTime > player.getSnowballDelay()) {
			client.throwSnowball(player.getCanThrow());
			lastFiredTime = System.currentTimeMillis();
		}
	}

	/**
	 * Returns a string description of the item in front of the local player
	 */
	public void inspectItem(){
		Location facing = player.getLocationInFrontOf();
		if (board.containsLocation(facing) && board.tileAt(facing).getOn() != null) {
			this.lastInspected =  board.tileAt(facing).getOn().getDescription();
		}
	}

	/**
	 * Requests at the server for the player to pick up the item in front of them, if
	 * there is one.
	 */
	public void pickUpItem() {
		Location facing = player.getLocationInFrontOf();
		if (!player.isFrozen() && board.containsLocation(facing)) {
			if (board.tileAt(facing).getOn() != null) {
				client.pickUpItem();
			}
		}
	}

	/**
	 * Removes the item at the specified index from the container
	 * in front of the player in the world. Places it in the
	 * player's inventory.
	 * @param index
	 */
	public void takeItemFromContainer(int index) {
		Location facing = player.getLocationInFrontOf();
		if (board.containsLocation(facing)) {
			if (board.tileAt(facing).getOn() != null && board.tileAt(facing).getOn() instanceof Inventory) {
				client.takeFromContainer(index);
			}
		}
	}

	/**
	 * Drops the item at the index position in the player's inventory into
	 * the container in front of them in the world
	 */
	public void dropIntoContainer(int index) {
		if (index < player.getInventoryItems().size()
				&& board.tileAt(player.getLocationInFrontOf()).getOn() instanceof Inventory) {
			client.dropItem(index);
		}
	}

	/**
	 * Sends a request to use the item selected in the player inventory.
	 */
	public void useSelectedItem() {
		if (!player.isFrozen() && selectedIndex != -1 && player.getInventoryItems().get(selectedIndex) != null) {
			Item toUse = player.getInventoryItems().get(selectedIndex);
			if (toUse instanceof Powerup) {
				Powerup powerup = (Powerup)toUse;
				if (powerup.getPower() == Powerup.Power.HEALTH_POTION || powerup.getPower() == Powerup.Power.STRONG_HEALTH_POTION) {
					client.useItem(selectedIndex);
				} else {
					powerup.use(player);
					client.useItem(selectedIndex);
				}
			}
		}
	}

	/**
	 * Send a request to the server to drop the selected item in the player inventory
	 */
	public void dropSelectedItem() {
		if (!player.isFrozen() && player.getInventoryItems().size() > selectedIndex
				&& player.getInventoryItems().get(selectedIndex) != null
				&& board.tileAt(player.getLocationInFrontOf()).isClear()) {
			client.dropItem(selectedIndex);
		}
	}

	/**
	 * Attempts to find a player in front of this one, and sends
	 * an unfreeze request to the server if one is found
	 * @return If a request was sent
	 */
	public boolean unfreezePlayer() {
		Location facing = player.getLocationInFrontOf();
		for(Player p: playerIDs.values()) {
			if (p.getLocation().equals(facing)) {
				client.unfreezePlayer(this.playerID);
				return true;
			}
		}
		return false;
	}

	/**
	 * Save current game into the given file
	 * @param file
	 */
	public void save(File file) {
		SaveGame saver = new SaveGame();
		List<Player> ps = new ArrayList<Player>();
		ps.addAll(playerIDs.values());
		StoredGame sg = new StoredGame(this.board, ps);
		saver.save(sg, file);
	}

	// Methods to pass information to the UI

	/**
	 * @return Whether the selected item in the inventory is a container
	 */
	public boolean selectedIsContainer(){
		if (selectedIndex != -1 && selectedIndex < player.getInventoryItems().size() && player.getInventoryItems().get(selectedIndex) instanceof Inventory) {
			return true;
		}
		return false;
	}

	/**
	 * @return The contents of the selected container in the inventory if it is one.
	 * @throws NotAContainerException
	 */
	public List<Entities> getContentsOfSelected() throws NotAContainerException {
		if (selectedIsContainer()) {
			return ((Inventory)player.getInventoryItems().get(selectedIndex)).getContentsAsEnums();
		}
		throw new NotAContainerException();
	}

	/**
	 * Gets the contents of the inventory (if there is one) in front of where the
	 * player is standing. If there is none, it returns an empty list.
	 * @return An unmodifiable list of enums representing the objects in the inventory.
	 * @throws NotAContainerException
	 */
	public List<Entities> getContents() throws NotAContainerException {
		Location containerLoc = Location.locationInFrontOf(player.getLocation(), player.getDirection());
		if (board.containsLocation(containerLoc)) {
			InanimateEntity on = board.tileAt(containerLoc).getOn();
			if (on != null && on instanceof Inventory) {
				if (on instanceof Lockable && ((Lockable)on).isLocked()) {
					if (this.unlock((Lockable)on, containerLoc)) {
							return ((Inventory)on).getContentsAsEnums();
					}
				} else {
					return ((Inventory)on).getContentsAsEnums();
				}
			}
		}
		throw new NotAContainerException();
	}

	// getters and setters

	public void toggleUnlimitedSpeed () {
		if (this.player.getStepDelay() != 0) {
			this.player.setStepDelay(0);
		} else {
			this.player.setStepDelay(Player.DEFAULT_STEP_DELAY);
		}
	}
	public void toggleUnlimitedFireRate () {
		if (this.player.getSnowballDelay() != 0) {
			this.player.setSnowballDelay(0);
		} else {
			this.player.setSnowballDelay(Player.DEFAULT_SNOWBALL_DELAY);
		}
	}

	public void toggleOneHitKill () {
		if (this.player.getCanThrow() == SnowballType.ONE_HIT) {
			this.player.setCanThrow(SnowballType.NORMAL);
		} else {
			this.player.setCanThrow(SnowballType.ONE_HIT);
		}
	}
	public void toggleNightVision() {
		boardState.toggleNightVision();
	}

	public void rotateClockwise() {
		boardState.rotateClockwise();
		refresh();
	}

	public void rotateAnticlockwise() {
		boardState.rotateAnticlockwise();
		refresh();
	}


	public List<Entities> getPlayerInventory() {
		return player.getInventoryEnums();
	}

	public Location getPlayerLocation() {
		return player.getLocation();
	}

	public Direction getPlayerDirection() {
		return player.getDirection();
	}

	public int getPlayerID() {
		return playerID;
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public int getPlayerHealth() {
		return player.getHealth();
	}

	public BoardState getBoard() {
		return boardState;
	}

	public void refresh() {
		display.repaint();
	}

	public void setID(int ID) {
		this.playerID = ID;
		this.player.setID(ID);
		this.playerIDs.put(ID, this.player);
	}

	public void setLocalLocation(Location l) {
		if(this.player.getLocation() instanceof NullLocation){
			this.player.setLocation(l);
		}
	}

	public int getPlayerScore() {
		return this.player.getScore();
	}

	public Direction getOrientation(){
		return boardState.getDirection();
	}

	public String getLastInspected() {
		return lastInspected;
	}

	public ClientUpdater getUpdater() {
		if (this.updater != null) {
			return updater;
		}
		return new ClientUpdater(this, board, playerIDs,
				boardState, display, playerID);
	}

	// Helper methods
	private boolean isFree(Location l) {
		for (Player p: playerIDs.values()) {
			if (p.getLocation().equals(l)) {
				return false;
			}
		}
		return true;
	}

	private boolean unlock(Lockable lock, Location at) {
		if (player.holdsKey(lock.getID())) {
			int index = player.getKeyIndex(lock.getID());
			if (lock.unlock((Key)player.getInventoryItems().get(index))) {
				client.useItem(index);
				return true;
			}
		}
		return false;
	}

	// For multiplayer loading. Didn't get round to implementing.
	public ClientGame(String IP, UI display) {
		this.client = new Client(IP);
		StoredGame sb = new LoadGame().loadGame(client.sendMapRequest());
		this.playerID = 0;
		this.board = sb.getBoard();
	}

	public List<String> getPlayerList() {
		return null;
	}

	public void loadPlayer(int index) {
	}

}
