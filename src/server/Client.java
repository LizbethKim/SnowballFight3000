package server;

import gameworld.world.Direction;
import gameworld.world.Location;
import gameworld.world.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import client.ClientUpdater;

public class Client implements Runnable {
	private final String host;

	private ClientUpdater updater;

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
		while (true) {
			try {
				// debugging code
				byte in = readFromSocket();
				// move player
				if (in == 0x01) {
					int id = readFromSocket();
					int x = readFromSocket();
					x += readFromSocket() << 8;
					int y = readFromSocket();
					y += readFromSocket() << 8;
					System.out.println("Moved player to: " + x + " " + y);
					updater.movePlayer(id, new Location(x, y));
				}
				// turn player
				else if (in == 0x02) {
					int id = readFromSocket();
					int dir = readFromSocket();
					updater.turnPlayer(id, Direction.values()[dir]);
				}
				// create player
				else if (in == 0x05) {
					int id = readFromSocket();
					String name = readString();
					// still pretty pissed about this
					updater.addPlayer(name, id);
				}
				// create local player
				else if (in == 0x06) {
					int id = readFromSocket();
					updater.createLocalPlayer(id);
				}


				System.out.println(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch(SocketClosedException e) {
				System.out.println("Socket is closed...");
				e.printStackTrace();
				return;
			}
		}
	}

	public void sendMove(Location l) {
		try {
			connection.getOutputStream().write(0x01);
			connection.getOutputStream().write(l.x);
			connection.getOutputStream().write(l.x>>8);
			connection.getOutputStream().write(l.y);
			connection.getOutputStream().write(l.y>>8);
			connection.getOutputStream().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendTurn(Direction d) {
		try {
			connection.getOutputStream().write(0x02);
			connection.getOutputStream().write(d.ordinal());
			connection.getOutputStream().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void sendName(String name) {
		try {
			connection.getOutputStream().write(0x06);
			connection.getOutputStream().write(name.length());
			for(int i=0;i<name.length();i++) {
				connection.getOutputStream().write(name.getBytes()[i]);
			}
			connection.getOutputStream().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMapRequest() {
		try {
			connection.getOutputStream().write(0x07);
			connection.getOutputStream().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startReceiving(ClientUpdater c) {
		this.updater = c;
		Thread t = new Thread(this);
		t.start();
	}

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
}
