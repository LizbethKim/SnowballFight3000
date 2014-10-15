package gameworld.game.client;

import gameworld.world.*;
import gameworld.world.Snowball.SnowballType;
import graphics.assets.Objects;

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
 * Current reprentation of the model on the client. Deals with the network and sends and receives updates to/from the server.
 *
 * @author Kelsey Jack 300275851
 *
 */
public class ClientGame {
	private Board board;
	private Map<Integer, Player> playerIDs;

	private Player player;
	private Client client;
	private ClientUpdater updater;

	private int playerID;

	private UI display;
	private long lastMovedTime;
	private long lastFiredTime;

	private BoardState boardState;
	private int selectedIndex = -1;	// index selected in inventory


	public ClientGame(String name, String IP, Team team, UI display) {
		this.client = new Client(IP);
		StoredGame sb = new LoadGame().loadGame(client.sendMapRequest());
		this.playerID = 0; // KTC (?)
		this.board = sb.getBoard();
		System.out.println(board);

		// this.board = Board.defaultBoard();

		boardState = new BoardState(board.convertToEnums(), board.itemEnums());
		playerIDs = new HashMap<Integer, Player>();
		this.display = display;
		client.sendNameAndTeam(name, team);
		player = new Player(name, team, 0, new NullLocation());

		ClientUpdater u = this.getUpdater();
		client.startReceiving(u, board.width(), board.height());
	}

	public ClientGame(String IP, UI display) {
		this.client = new Client(IP);
		StoredGame sb = new LoadGame().loadGame(client.sendMapRequest());
		this.playerID = 0;
		this.board = sb.getBoard();

		// KTC what?
	}

	public List<String> getPlayerList() {
		// KTC list of possible players
		return null;
	}

	public void loadPlayer(int index) {
		// KTC to do
	}

	public int getPlayerHealth() {
		return player.getHealth();
	}

	public List<Objects> getPlayerInventory() {
		return player.getInventoryEnums();
	}

	public Location getPlayerLocation() {
		return player.getLocation();
	}

	public Direction getPlayerDirection() {
		return player.getDirection();
	}

	public void move(Direction d) {
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

	public void rotateClockwise() {
		boardState.rotateClockwise();
		refresh();
	}

	public void rotateAnticlockwise() {
		boardState.rotateAnticlockwise();
		refresh();
	}

	public void throwSnowball() {
		if (!player.isFrozen() && System.currentTimeMillis() - lastFiredTime > player.getSnowballDelay()) {
			client.throwSnowball(player.getCanThrow());
			lastFiredTime = System.currentTimeMillis();
		}
	}

	public String inspectItem() throws NoItemException {
		Location facing = player.getLocationInFrontOf();
		if (board.containsLocation(facing) && board.tileAt(facing).getOn() != null) {
			return board.tileAt(facing).getOn().getDescription();
		}
		throw new NoItemException();
	}

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
	 * player's inventory
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

	public void useItem() {
		if (!player.isFrozen() && selectedIndex != -1 && player.getInventoryItems().get(selectedIndex) != null) {
			Item toUse = player.getInventoryItems().get(selectedIndex);
			if (toUse instanceof Powerup) {
				Powerup powerup = (Powerup)toUse;
				if (powerup.getPower() == Powerup.Power.HEALTH_POTION || powerup.getPower() == Powerup.Power.STRONG_HEALTH_POTION) {
					client.useItem(selectedIndex);
				} else {
					System.out.println(player);
					powerup.use(player);
					client.useItem(selectedIndex);
				}
			}
		}
	}

	public boolean selectedIsContainer(){
		if (selectedIndex != -1 && player.getInventoryItems().get(selectedIndex) instanceof Inventory) {
			return true;
		}
		return false;
	}

	public List<Objects> getContentsOfSelected() throws NotAContainerException {
		if (selectedIsContainer()) {
			return ((Inventory)player.getInventoryItems().get(selectedIndex)).getContentsAsEnums();
		}
		throw new NotAContainerException();
	}

	public void dropSelectedItem() {
		if (!player.isFrozen() && player.getInventoryItems().size() > selectedIndex
				&& player.getInventoryItems().get(selectedIndex) != null
				&& board.tileAt(player.getLocationInFrontOf()).isClear()) {
			client.dropItem(selectedIndex);
		}
	}

	/**
	 * Gets the contents of the inventory (if there is one) in front of where the
	 * player is standing. If there is none, it returns an empty list.
	 * @return An unmodifiable list of enums representing the objects in the inventory.
	 * @throws NotAContainerException
	 */
	public List<Objects> getContents() throws NotAContainerException {
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

	public void unfreezePlayer() {
		Location facing = player.getLocationInFrontOf();
		for(Player p: playerIDs.values()) {
			if (p.getLocation().equals(facing)) {
				client.unfreezePlayer(this.playerID);
			}
		}
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


	public BoardState getBoard() {
		return boardState;
	}

	public void refresh() {
		display.repaint();
	}

	public void setID(int ID) {
		this.playerID = ID;
		System.out.println("ID is " + ID);
		this.player.setID(ID);
		this.playerIDs.put(ID, this.player);
	}

	public void setLocalLocation(Location l) {
		this.player.setLocation(l);
	}

	public int getPlayerScore() {
		return this.player.getScore();
	}

	public Direction getOrientation(){
		return boardState.getDirection();
	}

	public void save(File file) {
		SaveGame saver = new SaveGame();
		List<Player> ps = new ArrayList<Player>();
		ps.addAll(playerIDs.values());
		StoredGame sg = new StoredGame(this.board, ps);
		saver.save(sg, file);
	}

	public ClientUpdater getUpdater() {
		if (this.updater != null) {
			return updater;
		}
		return new ClientUpdater(this, board, playerIDs,
				boardState, display, playerID);
	}

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
	public void toggleInvincibility () {
		// KTC think about
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

}
