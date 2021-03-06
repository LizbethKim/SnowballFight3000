package network;

import gameworld.game.server.ServerGame;
import gameworld.world.Direction;
import gameworld.world.Location;
import gameworld.world.Snowball.SnowballType;
import gameworld.world.Team;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentLinkedQueue;

import network.events.UpdateEvent;

/**
 * This class handles a players connection, calling
 * updates on a ServerGame object as they are recieved,
 * and handles queueing and sending of UpdateEvents to
 * the player
 * @author Bryden Frizzell
 *
 */
public class RemotePlayer implements Runnable {
	private int id;
	private Socket connection;
	private ConcurrentLinkedQueue<UpdateEvent> queuedEvents;
	private ServerGame game;
	private int locationLen;
	private int mapWidth;

	/**
	 * @param id the id of the player this represents
	 * @param sock the socket connected to the player
	 * @param game the ServerGame object for updates to be called on
	 * @param mapWidth the width of the map, used for de-packing locations
	 * @param mapHeight the height of the map, used for de-packing locations
	 */
	public RemotePlayer(int id, Socket sock, ServerGame game, int mapWidth, int mapHeight) {
		this.game = game;
		this.id = id;
		this.connection = sock;
		this.mapWidth=mapWidth;
		int maxLocation = mapWidth*mapHeight;
		for(locationLen=0;maxLocation>0;locationLen++){
			maxLocation=maxLocation>>1;
		}
		queuedEvents = new ConcurrentLinkedQueue<UpdateEvent>();
	}

	/**
	 * queues an event to be sent to the server,
	 * call sendUpdates to send queued events
	 * @param event the event to be queued to be sent to the server
	 */
	public void queueEvent(UpdateEvent event) {
		if (event != null) queuedEvents.offer(event);
	}

	/**
	 * sends queued updates to the client
	 */
	public void sendUpdates() {
		if(connection.isClosed()) {
			System.out.println("Why are you updating a closed socket?");
			return;
		}
		try {
			//write each queued event to output
			while(queuedEvents.size()>0) {
				UpdateEvent event = queuedEvents.poll();
				if(event!=null){
					event.writeTo(connection.getOutputStream());
				}
			}
			//flush the data
			connection.getOutputStream().flush();
		} catch (IOException e) {
			System.out.println("Error writing data to "+id+"'s socket.");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			InputStream input = connection.getInputStream();
			while (!connection.isClosed()) {
				//switch by packet ID
				int in =  readFromSocket();
				if(in==0x01) {
					readMove();
				}
				else if(in==0x02) {
					readTurn();
				}
				else if(in==0x06) {
					readNameAndTeam();
				}
				else if(in==0x07) {
					game.getFile(id);
				}
				else if(in==0x08) {
					SnowballType type = SnowballType.values()[readFromSocket()];
					game.throwSnowball(id, type);
				}
				else if(in==0x09) {
					game.removePlayer(id);
				}
				else if(in==0x0C) {
					game.pickUpItem(id);
				}
				else if(in==0x0D) {
					int index = readFromSocket();
					game.dropItem(id, index);
				}
				else if(in==0x0E) {
					int index = readFromSocket();
					game.useItem(id, index);
				}
				else if(in==0x10) {
					int index = readFromSocket();
					game.takeFromContainer(id, index);
				}
				else if(in==0x19) {
					int index = readFromSocket();
					game.unfreezePlayer(index);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SocketClosedException e) {
			System.out.println("Socket forcibly closed.");
			if (!connection.isClosed()) {
				try {
					connection.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		game.removePlayer(id);
	}

	private void readMove() throws IOException, SocketClosedException {
		//pack location
		int input = 0;
		for(int i=0;i<(locationLen/8)+1;i++) {
			input += (((int)readFromSocket())<<(i*8));
		}
		int x = input%mapWidth;
		int y = input/mapWidth;
		game.movePlayer(id, new Location(x,y));
	}

	private void readTurn() throws IOException, SocketClosedException {
		int dir = readFromSocket();
		game.turnPlayer(id, Direction.values()[dir]);
	}

	private void readNameAndTeam() throws IOException, SocketClosedException {
		String name = readString();
		Team team = Team.values()[readFromSocket()];
		game.addPlayer(id, name, team);
	}

	private String readString() throws IOException, SocketClosedException {
		String output = "";
		//read length
		int len = readFromSocket();
		//read string
		for(int i=0;i<len;i++) {
			output = output + (char)readFromSocket();
		}
		return output;
	}

	private int readFromSocket() throws IOException, SocketClosedException {
		//I hate java
		if(connection.isInputShutdown()){
			System.out.println("First");
			throw new SocketClosedException();
		}
		int input;
		try{
			input = connection.getInputStream().read();
		}catch(SocketException e) {
			System.out.println("Second");
			throw new SocketClosedException();
		}
		if (input == -1) {
			System.out.println("Third");
			throw new SocketClosedException();
		}
		return input;
	}

	public int getID() {
		return id;
	}
	public Socket getConnection() {
		return connection;
	}
}
