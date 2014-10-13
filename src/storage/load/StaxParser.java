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
	private Inventory curInven;
	private boolean playerLoad=false;
	private boolean tileLoad=false;
	private boolean chestLoad=false;
	

	/**
	 * Parses the given file into a StoredGame ready to be played
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
						curPlayer = new Player(values[3], team, 10, loc);
						playerLoad=true;
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
						curTile = new Tile(loc,Terrain.values()[terrain], null);
						tileLoad=true;
						continue;
					}

					if (elemName.equals(INVENTORY)) {
						if(playerLoad){
							curInven = (PlayerInventory) curPlayer.getInventory();
						}else if(tileLoad){
							event = eventReader.nextEvent();
							String[] values = event.asCharacters().getData().split(DELIMITER);
							curInven = (Inventory)loadEntitiy(values);
						}						
						continue;
					}
					
					if (elemName.equals(ITEM)) {
						event = eventReader.nextEvent();
						String[] values = event.asCharacters().getData().split(DELIMITER);
						InanimateEntity item = loadEntitiy(values);
						if(playerLoad){
							curInven.addItem((Item)item);
						}else if(chestLoad){
							curInven.addItem((Item)item);
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
						playerLoad=false;
						continue;
					}
					if (elemName.equals(INVENTORY)){
						if(chestLoad==true && tileLoad==true){
							curTile.place((Chest)curInven);
							chestLoad=false;
						}
						continue;
					}
					if (elemName.equals(GAME)) {
						System.out.println("GAME LOADED");
						break;
					}
					if (elemName.equals(TILE)) {
						tileLoad = false;
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
	 * Loading an item from its tag into an actual InanimateEntity. Also loads all its properties.
	 * @param values A string array, usually from the characters within a particular tag
	 * @return entity The item represented as an
	 */
	private InanimateEntity loadEntitiy(String[] values) {
		String name = values[0];
		InanimateEntity item = null;

		if(name.equals(Objects.KEY.name())){
			item = new Key(parseDescription(2, values), Integer.parseInt(values[1]));
		}else if(name.equals(Objects.BAG.name())){
			item = new Bag();
		}else if(name.equals(Objects.CHEST.name())){
			chestLoad = true;
			item = new Chest(parseDescription(1, values));
		}else if(name.equals(Objects.REDFLAG.name())){
			item = new Flag(Team.RED);
		}else if(name.equals(Objects.BLUEFLAG.name())){
			item = new Flag(Team.BLUE);
		}else if(name.equals(Objects.DOOREW.name())){
			item = new Door(parseDescription(3, values),Integer.parseInt(values[1]) ,Direction.EAST );
		}else if(name.equals(Objects.DOORNS.name())){
			item = new Door(parseDescription(3, values),Integer.parseInt(values[1]) ,Direction.NORTH );
		}else if(name.equals(Objects.POWERUP.name()) || name.equals(Objects.HEALTH.name())){
			item = new Powerup(Powerup.Power.valueOf(values[1]));
		}else{
			Objects type = Objects.valueOf(name);
			item = new Furniture(parseDescription(1, values), type);
		}
		
		return item;
	}


	/**
	 * Parses the leftover part of the values array into a single string. Used to load item descriptions.
	 * @param start The starting index of the description
	 * @param values The array of strings to load
	 * @return description A single string description
	 */
	private String parseDescription(int start, String[] values) {
		StringBuilder builder = new StringBuilder();
		for(int i=start; i<values.length; i++) {
			builder.append(values[i]);
		    builder.append(" ");
		}
		return builder.toString();
	}

	/**
	 * Parses two integer strings into a location.
	 * @param xStr String of x value
	 * @param yStr String of y value
	 * @return location new Location of the points
	 */
	private Location parseLoc(String xStr, String yStr){
		int x = Integer.parseInt(xStr);
		int y = Integer.parseInt(yStr);
		return new Location(x, y);
	}
}
