package server;

import gameworld.game.client.ClientUpdater;
import gameworld.world.Bag;
import gameworld.world.Direction;
import gameworld.world.Flag;
import gameworld.world.Item;
import gameworld.world.Key;
import gameworld.world.Location;
import gameworld.world.Player;
import gameworld.world.Powerup;
import gameworld.world.Powerup.Power;
import gameworld.world.Team;
import graphics.assets.Objects;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {
	private final String host;

	private ClientUpdater updater;

	private Socket connection;

	private Byte mapBytes[];

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
					Location loc = readLocation();
					updater.movePlayer(id, loc);
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
					Location loc = readLocation();
					Team team = Team.values()[readFromSocket()];
					updater.addPlayer(name,team, id, loc);
				}
				// create local player
				else if (in == 0x06) {
					int id = readFromSocket();
					Location loc = readLocation();
					updater.createLocalPlayer(id, loc);
				}
				// read map file
				else if (in == 0x07) {
					int len = readFromSocket();
					len += readFromSocket()<<8;
					len += readFromSocket()<<16;
					len += readFromSocket()<<24;
					mapBytes = new Byte[len];
					for(int i=0;i<len;i++) {
						mapBytes[i] = (byte) readFromSocket();
					}
					this.notify();
				}
				// update projectile positions
				else if (in == 0x08) {
					int numProjectiles = readFromSocket();
					Location projectileLocations[] = new Location[numProjectiles]; 
					for(int i=0;i<numProjectiles;i++) {
						projectileLocations[i] = readLocation();
					}
					updater.updateProjectiles(projectileLocations);
				}
				// remove player
				else if (in == 0x09) {
					int id = readFromSocket();
					updater.removePlayer(id);
				}
				// take damage
				else if (in == 0x0A) {
					int hp = readFromSocket();
					updater.updatePlayerHealth(hp);
					// BF I added it in, hope that's ok - Kelsey
				}
				// freeze player
				else if (in == 0x0B) {
					int id = readFromSocket();
					updater.freezePlayer(id);
				}
				// add item to inventory
				else if (in == 0x0C) {
					Location loc = readLocation();
					updater.pickupItemAt(loc);
				}
				// remove item
				else if (in == 0x0D) {
					Location loc = readLocation();
					updater.removeItemAt(loc);
				}
				// item placed
				else if (in == 0x0f) {
					Item item = readItem();
					Location loc = readLocation();
					updater.placeItem(item, loc);
				}
				// remove item from inventory
				else if (in == 0x10) {
					int index = readFromSocket();
					updater.removeFromInventory(index);
				}
				// invalid name/team info
				else if (in == 0x11) {
					// BF add code here
				}
				// valid name/team info
				else if (in == 0x12) {
					// BF add code here
				}
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


	public void throwSnowball() {
		try {
			connection.getOutputStream().write(0x08);
			connection.getOutputStream().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pickUpItem() {
		try {
			connection.getOutputStream().write(0x0C);
			connection.getOutputStream().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dropItem(int index) {
		try {
			connection.getOutputStream().write(0x0D);
			connection.getOutputStream().write(index);
			connection.getOutputStream().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void useItem(int index) {
		try {
			connection.getOutputStream().write(0x0E);
			connection.getOutputStream().write(index);
			connection.getOutputStream().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void sendNameAndTeam(String name, Team team) {
		try {
			connection.getOutputStream().write(0x06);
			connection.getOutputStream().write(name.length());
			for(int i=0;i<name.length();i++) {
				connection.getOutputStream().write(name.getBytes()[i]);
			}
			connection.getOutputStream().write(team.ordinal());
			connection.getOutputStream().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Byte[] sendMapRequest() {
		try {
			connection.getOutputStream().write(0x07);
			connection.getOutputStream().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapBytes;
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

	private Location readLocation() throws IOException, SocketClosedException {
		int x = readFromSocket();
		x += readFromSocket() << 8;
		int y = readFromSocket();
		y += readFromSocket() << 8;
		return new Location(x,y);
	}
	
	private Item readItem() throws IOException, SocketClosedException {
		Objects object = Objects.values()[readFromSocket()];
		if(object==Objects.KEY) {
			int id = readFromSocket();
			String description = readString();
			return new Key(description, id);
		} else if(object==Objects.BAG) {
			return new Bag();
		} else if(object==Objects.BLUEFLAG) {
			return new Flag(Team.BLUE);
		} else if(object==Objects.REDFLAG) {
			return new Flag(Team.RED);
		} else if(object==Objects.POWERUP) {
			Power p = Power.values()[readFromSocket()];
			return new Powerup(p);
		} else {
			System.out.println("Invalid item recieved! "+object);
		}
		return null;
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
