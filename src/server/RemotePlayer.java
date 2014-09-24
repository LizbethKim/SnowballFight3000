package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import server.events.UpdateEvent;

public class RemotePlayer implements Runnable {
	private int id;
	private Socket connection;
	private Queue<UpdateEvent> queuedEvents;
	
	public RemotePlayer(int id, Socket sock) {
		this.id = id;
		this.connection = connection;
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
			InputStream in = connection.getInputStream();
			while (!connection.isClosed()) {
				//switch by packet ID
				switch (readFromSocket()) {
				case 1:
				
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

	private byte readFromSocket() throws IOException, SocketClosedException {
		//I hate java
		int input = connection.getInputStream().read();
		if (input == -1) {
			throw new SocketClosedException();
		}
		return (byte) input;
	}

	// TODO functions here will be made as needed as we develop the protocol

	private String readStr(InputStream in) throws IOException,
			SocketClosedException {
		String s = "";
		while (true) {
			char next = (char) readFromSocket();
			if ((byte) next == 0) {
				break;
			}
			s = s + next;
		}
		return s;
	}
	
	public int getID() {
		return id;
	}
	public Socket getConnection() {
		return connection;
	}
}
