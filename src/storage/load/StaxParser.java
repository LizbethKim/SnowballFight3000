/**
 *
 */
package storage.load;

import gameworld.world.Board;
import gameworld.world.Furniture;
import gameworld.world.InanimateEntity;
import gameworld.world.Inventory;
import gameworld.world.Item;
import gameworld.world.Location;
import gameworld.world.Player;
import gameworld.world.PlayerInventory;
import gameworld.world.Team;
import gameworld.world.Tile;
import graphics.assets.Objects;
import graphics.assets.Terrain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import storage.StoredGame;



/**
 * Handles the parsing of the XML file using StAX into a board and player list, ready to be loaded in game
 * @author Kate Henderson
 *
 */

public class StaxParser {
	static final String GAME = "game";
	static final String PLAYERS = "players";
	static final String PLAYER = "player";
	static final String BOARD = "board";
	static final String TILE = "tile";
	static final String INVENTORY= "inventory";

	static final String DELIMITER = "\\s+";

	private Board board;
	private Tile[][] tiles;
	private List<Player> players;
	private Player curPlayer;
	private Tile curTile;

	/**
	 * @param file
	 */
	public StoredGame parse(File file) {
		try {
			// XMLInputFactory, used to process input
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Starts streaming the file
			InputStream in = new FileInputStream(file);
			//Make the event reader to detect the XML
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					String elemName = startElement.getName().getLocalPart();

					if (elemName.equals(GAME)) {
						//Could add Name of the game or something here
						continue;
					}

					if (elemName.equals(PLAYERS)) {
						players = new ArrayList<>();
						continue;
					}

					if (elemName.equals(PLAYER)) {
						event = eventReader.nextEvent();
						String[] values = event.asCharacters().getData().split(DELIMITER);
						int teamNum = Integer.parseInt(values[0]);
						Team team = Team.values()[teamNum];
						Location loc = parseLoc(values[1], values[2]);
						curPlayer = new Player(values[4], team, 10, loc);
						continue;
					}

					if (elemName.equals(BOARD)) {
						event = eventReader.nextEvent();
						String[] values = event.asCharacters().getData().split(DELIMITER);
						Location boardSize = parseLoc(values[0],values[1]);
						tiles = new Tile[boardSize.x][boardSize.y];
						continue;
					}

					if (elemName.equals(TILE)) {
						event = eventReader.nextEvent();
						String[] values = event.asCharacters().getData().split(DELIMITER);
						int terrain = Integer.parseInt(values[0]);
						Location loc = parseLoc(values[1],values[2]);
						InanimateEntity entity = null;
						if(values.length>3){	//there is something on the tile
							int obj = Integer.parseInt(values[3]);

							if (obj==4)
								entity = new Furniture("default tree", Objects.values()[4]);
						}
						curTile = new Tile(loc,Terrain.values()[terrain], entity);
						continue;
					}

					if (elemName.equals(INVENTORY)) {
						if(eventReader.peek().isCharacters()){	//Inventory could be empty, so check if anything is there
							event = eventReader.nextEvent();
							String[] values = event.asCharacters().getData().split(DELIMITER);
							PlayerInventory inven = (PlayerInventory) curPlayer.getInventory();
							for(int i = 0;i<6;i++){
								inven.addItem(findItem(values[0]));
							}
						}
						if(eventReader.peek().isStartElement()){
							elemName = event.asStartElement().getName().getLocalPart();
						}
						
						continue;
					}

				}

				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					String elemName = endElement.getName().getLocalPart();
					if (elemName.equals(PLAYER)) {
						players.add(curPlayer);
						continue;
					}
					if (elemName.equals(GAME)) {
						System.out.println("GAME LOADED");
						break;
					}
					if (elemName.equals(TILE)) {
						tiles[curTile.getCoords().x][curTile.getCoords().y] = curTile;
						continue;
					}
					if (elemName.equals(BOARD)) {
						board = new Board(tiles);
						continue;
					}
				}

			}
		} catch (FileNotFoundException | XMLStreamException e) {
			e.printStackTrace();
		}

		return new StoredGame(board, players);

	}

	/**
	 * @param string
	 * @return
	 */
	private Item findItem(String string) {
		
		return null;
	}

	private Location parseLoc(String xStr, String yStr){
		int x = Integer.parseInt(xStr);
		int y = Integer.parseInt(yStr);
		return new Location(x, y);
	}
}
