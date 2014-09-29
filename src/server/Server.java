package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import server.events.UpdateEvent;

public class Server implements Runnable {
	private ServerSocket server;
	private BlockingQueue<RemotePlayer> updateQueue;
	private Map<Integer, RemotePlayer> playersByID;

	public Server() {
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
		RemotePlayer playerUpdates = playersByID.get(playerID);
		playerUpdates.queueEvent(event);
		playersByID.put(playerID, playerUpdates);
		updateQueue.offer(playerUpdates);
	}

	public int generatePlayerID() {
		//BF make this method less stupid
		return (int) (Math.random()*1000000);
	}

	@Override
	public void run() {
		while(true) {
			try {
				Socket newSocket = server.accept();
				newSocket.setTcpNoDelay(true); // stops TCP from combining packets, reduces latency
				int id = generatePlayerID();
				RemotePlayer newPlayer = new RemotePlayer(id, newSocket);
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
