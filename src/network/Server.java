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

	public Server(ServerGame g) {
		game = g;
		g.setServer(this);
		updateQueue = new LinkedBlockingQueue<RemotePlayer>();
		playersByID = new HashMap<Integer, RemotePlayer>();
		//start accepting thread
		new Thread(this).start();
		//hack to get around race condition
		while(server==null){
			try {
				Thread.currentThread().sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// set up location map size for packing
		LocationEvent.setWorldSize(game.getBoardWidth(), game.getBoardHeight());
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
		if(server==null) {
			try {
				server = new ServerSocket(6015);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(true) {
				try {
					Socket newSocket = server.accept();
					newSocket.setTcpNoDelay(true); // stops TCP from combining packets, reduces latency
					int id = generatePlayerID();
					RemotePlayer newPlayer = new RemotePlayer(id, newSocket, game, game.getBoardWidth(), game.getBoardHeight());
					playersByID.put(id, newPlayer);
					//create and start a new thread, running the socket worker code
					new Thread(newPlayer).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
