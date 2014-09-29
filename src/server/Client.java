package server;

import gameworld.world.Direction;
import gameworld.world.Location;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import client.Updater;

public class Client implements Runnable {
	private final String host;
	
	private Updater updater;

	private Socket connection;

	public Client(String host) {
		this.host = host;
		try {
			connection = new Socket(host, 6015);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void run() {
		while(true) {
			try {
				// debugging code
				byte in = readFromSocket();
				//move packet
				if(in==0x01) {
					int id = readFromSocket();
					int x = readFromSocket()+readFromSocket()>>8;
					int y = readFromSocket()+readFromSocket()>>8;
					updater.movePlayer(id, new Location(x,y));
				}
				//turn player
				if(in==0x02) {
					int id = readFromSocket();
					int dir = readFromSocket();
					updater.turnPlayer(id, Direction.values()[dir]);
				}
				//create player
				if(in==0x05) {
					int id = readFromSocket();
					updater.addPlayer(id);
				}
				
				System.out.println(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
	}
	
	public void sendMove(Direction d) {
		try {
			connection.getOutputStream().write(0x01);
			connection.getOutputStream().write(d.ordinal());
			connection.getOutputStream().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startReceiving(Updater c) {
		this.updater = c;
		Thread t = new Thread(this);
		t.start();
	}

	private byte readFromSocket() throws IOException {
		int input = connection.getInputStream().read();
		if (input == -1) {
			throw new IOException("Socket closed");
		}
		return (byte) input;
	}
}
