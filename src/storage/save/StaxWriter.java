/**
 * 
 */
package storage.save;

import gameworld.world.Board;
import gameworld.world.Chest;
import gameworld.world.Door;
import gameworld.world.InanimateEntity;
import gameworld.world.Item;
import gameworld.world.Key;
import gameworld.world.Location;
import gameworld.world.Player;
import gameworld.world.Powerup;
import gameworld.world.Team;
import gameworld.world.Tile;
import graphics.assets.Objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import storage.StoredGame;

/**
 * Writes StoredGames into XML files using the StAX library.
 * @author Kate Henderson
 *
 */

public class StaxWriter {

	private static final String GAME = "game";
	private static final String PLAYERS = "players";
	private static final String PLAYER = "player";
	private static final String BOARD = "board";
	private static final String TILE = "tile";
	private static final String INVENTORY= "inventory";
	private static final String ITEM = "item";

	private static final String EMPTY = "";
	private static final String SPACE = " ";
	private static final String NEWLINE = "\n";

	private String filename;
	private Board board;
	private List<Player> players;
	private XMLEventWriter eventWriter;

	/**
	 * Writes the storedGame to an XML file
	 * @param g
	 * @param fp Path to the save file
	 * @return
	 */
	public String saveGame(StoredGame g, String fp){

		filename = fp+".xml"; //filename is current time in milliseconds
		board = g.getBoard();
		players = g.getPlayers();
		try {
			// create an XMLOutputFactory
			OutputStream out = new FileOutputStream(new File(filename));
			XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
			
			eventWriter = outputFactory.createXMLEventWriter(out);
			XMLEventFactory eventFactory = XMLEventFactory.newInstance();
			XMLEvent newline = eventFactory.createDTD(NEWLINE); //use for newlines

			// open game tag
			eventWriter.add(eventFactory.createStartElement(EMPTY,EMPTY, GAME));
			eventWriter.add(newline);

			if(players!=null){
				// open players tag
				eventWriter.add(eventFactory.createStartElement(EMPTY,EMPTY, PLAYERS));
				eventWriter.add(newline);

				// create player start and end elements
				StartElement playerStartElement = eventFactory.createStartElement(EMPTY,EMPTY, PLAYER);
				EndElement playerEndElement = eventFactory.createEndElement(EMPTY,EMPTY, PLAYER);
				StartElement inventoryStartElement = eventFactory.createStartElement(EMPTY,EMPTY, INVENTORY);
				EndElement inventoryEndElement = eventFactory.createEndElement(EMPTY,EMPTY, INVENTORY);

				for(Player p :players){
					eventWriter.add(playerStartElement); //open new player tag
					Characters playerContents = eventFactory.createCharacters(buildPlayerString(p));
					eventWriter.add(playerContents);
					eventWriter.add(inventoryStartElement);

					// KH I changed the way that I use the inventory, so I edited this
					for(Item item : p.getInventory().getContents()){
						createTag(ITEM, buildEntityString(item));
					}
					eventWriter.add(inventoryEndElement);
					eventWriter.add(playerEndElement);
					eventWriter.add(newline);
				}

				// close players tag
				eventWriter.add(eventFactory.createEndElement(EMPTY,EMPTY, PLAYERS));
				eventWriter.add(newline);
			}

			if(board!=null){
				// open board tag
				eventWriter.add(eventFactory.createStartElement(EMPTY,EMPTY, BOARD));
				//add board size
				int xMax = board.getXMax();
				int yMax = board.getYMax();
				Characters boardSize = eventFactory.createCharacters(String.format("%03d %03d", xMax, yMax)+NEWLINE);
				eventWriter.add(boardSize);

				//for each tile, make its tag and fill it
				for(int x=0;x<xMax;x++){
					for(int y=0;y<xMax;y++){
						eventWriter.add(eventFactory.createStartElement(EMPTY,EMPTY, TILE));
						Tile t = board.tileAt(new Location(x,y));
						String tileContents = buildTileString(t);
						eventWriter.add(eventFactory.createCharacters(tileContents));
						InanimateEntity onItem = t.getOn();
						if(onItem!=null && onItem.asEnum()==Objects.CHEST){
							System.out.println("we have a chest");
							eventWriter.add(eventFactory.createStartElement(EMPTY,EMPTY, INVENTORY));
							eventWriter.add(eventFactory.createCharacters(buildEntityString(onItem)));
							List<Item> itemsIn = ((Chest)t.getOn()).getContents();							
							for(Item it:itemsIn){
								System.out.println("in the chest");
								createTag(ITEM, buildEntityString(it));
							}
							eventWriter.add(eventFactory.createEndElement(EMPTY,EMPTY, INVENTORY));
						}else if(onItem!=null){
							createTag(ITEM, buildEntityString(onItem));
						}
						eventWriter.add(eventFactory.createEndElement(EMPTY,EMPTY, TILE));
						eventWriter.add(newline);
					}
				}

				// close board tag
				eventWriter.add(eventFactory.createEndElement(EMPTY,EMPTY, BOARD));
				eventWriter.add(newline);
			}

			//end the game tag
			eventWriter.add(eventFactory.createEndElement(EMPTY, EMPTY, GAME));
			eventWriter.add(newline);

			//end the document
			eventWriter.add(eventFactory.createEndDocument());
			eventWriter.close();
		} catch (FileNotFoundException | XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return filename;
	}
	
	/**
	 * Build a string description of a tile, including the item/s on it
	 * @param tile
	 * @return String description
	 * @throws XMLStreamException 
	 */
	private String buildTileString(Tile t) throws XMLStreamException {
		StringBuilder str = new StringBuilder();
		switch (t.getType()){
			case SNOW:
				str.append(0);break;
			case FLOOR:
				str.append(1);break;
			case GRASS:
				str.append(2);break;
			case DIRT:
				str.append(3);break;
			default:
				str.append(0);break;
		}
		//Area a = board.getAreaContaining(t.getCoords());
		//board.
		//if(a instanceof SpawnArea){
			//KH how the fluff do areas work
		//}
		str.append(String.format(" %03d %03d ", t.getCoords().x, t.getCoords().y));
		//str.append(board.getAreaContaining(t.getCoords()));
		return str.toString();
	}

	/**
	 * Builds a String for any item under the InanimateEntity category. Used for building tiles
	 *  and for building inventories. Calls itself recursively for chest contents
	 * @param item Inanimate Entity
	 * @return string description of item
	 * @throws XMLStreamException 
	 */
	private String buildEntityString(InanimateEntity item) throws XMLStreamException {
		StringBuilder str = new StringBuilder();
		Objects itemEnum = item.asEnum();
		str.append(itemEnum.name()+SPACE);
		switch (itemEnum){
		/*
		case BAG:
			str.append(item.asEnum().name());break;
		case REDFLAG:
			str.append("flag ");
			str.append(0);
			break;
		case BLUEFLAG:
			str.append("flag ");
			str.append(1);
			break;
		case MAP:
			str.append("map ");
			break;
		case WALL_E_W:
			str.append(Objects.WALL_E_W.name());break;
		case WALL_N_S:
			str.append("wallNS ");break;
		case TREE:
			str.append("tree ");break;
		case BUSH:
			str.append("bush ");break;
		case TABLE:
			str.append("table ");break;		
		case CHEST:
			str.append()
		*/
		case KEY:
			Key key = (Key)item;
			str.append(key.id+SPACE);
			str.append(key.getDescription());
			break;
		case HEALTH:
		case POWERUP:
			Powerup p = (Powerup)item;
			str.append(p.getPower().name());
			break;
		case DOOREW:
			Door doorew = (Door) item;
			str.append(doorew.id+SPACE);
			str.append(doorew.canMoveThrough());
			break;
		case DOORNS:
			Door doorns = (Door) item;
			str.append(doorns.id+SPACE);
			str.append(doorns.canMoveThrough());
			break;
		default:
			break;
		}
		return str.toString();
	}

	/**
	 * @param p
	 */
	private String buildPlayerString(Player p) {
		StringBuilder str = new StringBuilder();
		if(p.getTeam().equals(Team.values()[0])){
			str.append(0 + SPACE);
		}else if(p.getTeam().equals(Team.values()[1])){
			str.append(1 + SPACE);
		}
		str.append(String.format("%03d %03d ", p.getLocation().x, p.getLocation().y));
		str.append(p.name+NEWLINE);
		return str.toString();	
	}


	/**
	 * Used for creating innermost tags, like tile and inventory
	 * @param eventWriter
	 * @param name
	 * @param value
	 * @throws XMLStreamException
	 */
	private void createTag(String name, String value) throws XMLStreamException {

		XMLEventFactory eventFactory = XMLEventFactory.newInstance();

		// open tag
		StartElement sElement = eventFactory.createStartElement(EMPTY, EMPTY, name);
		eventWriter.add(sElement);

		// save the characters of content
		Characters characters = eventFactory.createCharacters(value);
		eventWriter.add(characters);

		// end tag
		EndElement eElement = eventFactory.createEndElement(EMPTY, EMPTY, name);
		eventWriter.add(eElement);

	}


} 

