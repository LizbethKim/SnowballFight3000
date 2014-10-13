/**
 *
 */
package storage.load;

import gameworld.world.*;
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
	static final String ITEM = "item";

	static final String DELIMITER = "\\s+";
	
	private Board board;
	private Tile[][] tiles;
	private List<Player> players;
	private Player curPlayer;
	private Tile curTile;
	private PlayerInventory curInven;
	private boolean invenLoad;
	private boolean tileLoad;

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
						curInven = (PlayerInventory) curPlayer.getInventory();
						continue;
					}
					
					if (elemName.equals(ITEM)) {
						event = eventReader.nextEvent();
						String[] values = event.asCharacters().getData().split(DELIMITER);
						Item item = loadItem(values);
						if(invenLoad){
							curInven.addItem(item);
						}else if(tileLoad){
							curTile.place(item);	
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
	 * KH Finish this
	 * @param values 
	 * @return
	 */
	private Item loadItem(String[] values) {
		String name = values[0];
		Item item = null;
		switch(name){
		case ("bag"):
			item = new Bag();
		case ("flag"):
			int teamNum = Integer.parseInt(values[1]);
			item = new Flag(Team.values()[teamNum]);
		case ("key"):
			String description = parseDescription(2, values);
			item = new Key(description, Integer.parseInt(values[1]));
		case ("map"):
			item = new Map();
		case ("powerup"):
			item = new Powerup(Powerup.Power.valueOf(values[1]));
		case ("wall"):
			 //do things
		}
		return item;
	}


	/**
	 * @param i
	 * @param values
	 * @return
	 */
	private String parseDescription(int start, String[] values) {
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<values.length; i++) {
			builder.append(values[i]);
		    builder.append(" ");
		}
		return builder.toString();
	}

	private Location parseLoc(String xStr, String yStr){
		int x = Integer.parseInt(xStr);
		int y = Integer.parseInt(yStr);
		return new Location(x, y);
	}
}
