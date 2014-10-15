package network;

import gameworld.game.server.ServerGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import network.events.LocationEvent;
import network.events.UpdateEvent;

public class Server implements Runnable {
	private ServerSocket server;
	private BlockingQueue<RemotePlayer> updateQueue;
	private Map<Integer, RemotePlayer> playersByID;

	private ServerGame game;

	/**
	 * @param serverGame the ServerGame object for updates to be called on
	 */
	public Server(ServerGame serverGame) {
		game = serverGame;
		game.setServer(this);
		updateQueue = new LinkedBlockingQueue<RemotePlayer>();
		playersByID = new HashMap<Integer, RemotePlayer>();
		//start accepting thread
		new Thread(this).start();
		//hack to get around race condition
		while(server==null){
			try {
				Thread.currentThread().sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// set up location map size for packing
		LocationEvent.setWorldSize(game.getBoardWidth(), game.getBoardHeight());
	}

	/**
	 * Queues an event to be sent to a player
	 * @param event the event to be sent
	 * @param playerID the ID of the player the event is being sent to
	 */
	public void queuePlayerUpdate(UpdateEvent event, int playerID) {
		RemotePlayer playerUpdates = playersByID.get(playerID);
		playerUpdates.queueEvent(event);
		playersByID.put(playerID, playerUpdates);
		updateQueue.offer(playerUpdates);
	}

	/**
	 * Generates a new, unused player ID
	 * @return a new, unused player ID
	 */
	public int generatePlayerID() {
		int id = (int) (Math.random()*255);
		while(playersByID.containsKey(id)) {
			id = (int) (Math.random()*255);
		}
		return id;
	}

	@Override
	public void run() {
		//hack to make server multithreaded
		if(server==null) {
			try {
				server = new ServerSocket(6015);
			} catch (IOException e) {
				e.printStackTrace();
			}
			while(true) {
				try {
					//accept connection and create new player
					Socket newSocket = server.accept();
					newSocket.setTcpNoDelay(true); // stops TCP from combining packets, reduces latency
					int id = generatePlayerID();
					RemotePlayer newPlayer = new RemotePlayer(id, newSocket, game, game.getBoardWidth(), game.getBoardHeight());
					playersByID.put(id, newPlayer);
					//create and start a new thread, running the socket worker code
					new Thread(newPlayer).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			//will be run the second time
			sendLoop();
		}
	}

	private void sendLoop() {
		while(true) {
			try {
				//get next player and get them to send queued updates
				//still returns null sometimes? apparently waiting 1000 days isn't enough...
				RemotePlayer p = updateQueue.poll(1000,TimeUnit.DAYS);
				if(p!=null)
					p.sendUpdates();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
