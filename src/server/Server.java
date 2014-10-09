package server;

import gameworld.game.ServerGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import server.events.UpdateEvent;

public class Server implements Runnable {
	private ServerSocket server;
	private BlockingQueue<RemotePlayer> updateQueue;
	private Map<Integer, RemotePlayer> playersByID;

	private ServerGame game;

	public Server(ServerGame g) {
		game = g;
		g.setServer(this);
		try {
			server = new ServerSocket(6015);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateQueue = new LinkedBlockingQueue<RemotePlayer>();
		playersByID = new HashMap<Integer, RemotePlayer>();
	}

	public void sendLoop() {
		// BF, could I (Kelsey) please get you to tick the game here too? Doesn't make
		// sense to have two loops when one will do.
		// Just call game.tick() every x milliseconds.
		while(true) {
			try {
				//get next player and get them to send queued updates
				updateQueue.poll(1000,TimeUnit.DAYS).sendUpdates();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void queuePlayerUpdate(UpdateEvent event, int playerID) {
		//System.out.println("Queued event: "+event);
		RemotePlayer playerUpdates = playersByID.get(playerID);
		playerUpdates.queueEvent(event);
		playersByID.put(playerID, playerUpdates);
		updateQueue.offer(playerUpdates);
	}

	public int generatePlayerID() {
		//BF make this method less stupid
		int id = (int) (Math.random()*1000000);
		while(playersByID.containsKey(id)) {
			id = (int) (Math.random()*1000000);
		}
		return id;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Socket newSocket = server.accept();
				newSocket.setTcpNoDelay(true); // stops TCP from combining packets, reduces latency
				int id = generatePlayerID();
				RemotePlayer newPlayer = new RemotePlayer(id, newSocket, game);
				playersByID.put(id, newPlayer);
				//create and start a new thread, running the socket worker code
				new Thread(newPlayer).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
