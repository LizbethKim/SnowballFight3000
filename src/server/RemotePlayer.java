package server;

import gameworld.game.ServerGame;
import gameworld.world.Direction;
import gameworld.world.Location;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import client.ClientUpdater;
import server.events.CreateLocalPlayerEvent;
import server.events.UpdateEvent;

public class RemotePlayer implements Runnable {
	private int id;
	private Socket connection;
	private Queue<UpdateEvent> queuedEvents;
	private ServerGame game;

	public RemotePlayer(int id, Socket sock, ServerGame g) {
		this.game = g;
		this.id = id;
		this.connection = sock;
		queuedEvents = new LinkedList<UpdateEvent>();
	}

	public void queueEvent(UpdateEvent e) {
		queuedEvents.offer(e);
	}

	public void sendUpdates() {
		try {
			//write each queued event to output
			while(queuedEvents.size()>0) {
					queuedEvents.poll().writeTo(connection.getOutputStream());
			}
			//flush the data
			connection.getOutputStream().flush();
		} catch (IOException e) {
			System.out.println("Error writing data to "+id+"'s socket.");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			InputStream input = connection.getInputStream();
			while (!connection.isClosed()) {
				//switch by packet ID
				byte in =  readFromSocket();
				if(in==0x01) {
					readMove();
				}
				else if(in==0x02) {
					readTurn();
				}
				else if(in==0x05) {
					readName();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketClosedException e) {
			System.out.println("Socket forcibly closed.");
			if (!connection.isClosed()) {
				try {
					connection.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}

	private void readMove() throws IOException, SocketClosedException {
		int x = readFromSocket() + readFromSocket()>>8;
		int y = readFromSocket() + readFromSocket()>>8;
		game.movePlayer(id, new Location(x,y));
	}

	private void readTurn() throws IOException, SocketClosedException {
		int dir = readFromSocket();
		game.turnPlayer(id, Direction.values()[dir]);
	}

	private void readName() throws IOException, SocketClosedException {
		String name = readString();
		game.addPlayer(id, name);
		this.queueEvent(new CreateLocalPlayerEvent(id));
	}

	// TODO functions here will be made as needed as we develop the protocol

	private String readString() throws IOException, SocketClosedException {
		String output = "";
		int len = readFromSocket();
		for(int i=0;i<len;i++) {
			output = output + (char)readFromSocket();
		}
		return output;
	}

	private byte readFromSocket() throws IOException, SocketClosedException {
		//I hate java
		int input = connection.getInputStream().read();
		if (input == -1) {
			throw new SocketClosedException();
		}
		return (byte) input;
	}

	public int getID() {
		return id;
	}
	public Socket getConnection() {
		return connection;
	}
}
