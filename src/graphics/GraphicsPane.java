package graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import gameworld.game.client.BoardState;
import gameworld.world.Direction;
import gameworld.world.Location;
import gameworld.world.NullLocation;
import graphics.assets.Objects;
import graphics.assets.Terrain;

public class GraphicsPane extends JPanel {

	/**
	 * This is the graphics pane that deals with rendering the game and state
	 * @author Elizabeth Kim kimeliz1 (300302456)
	 */
	private static final long serialVersionUID = -2715513999456707521L;
	private double width;
	private double height;
	private double startingWidth;
	private double startingHeight;
	private BoardState boardState;
	private BufferedImage nightShade;
	private BufferedImage duskShade;
	private BufferedImage sunShade;

	/**
	 * Separation between board and graphics means that the board cannot be modified
	 * by the graphics portion of the game
	 * @param boardState Takes in a board state object from board
	 */
	public GraphicsPane(BoardState boardState) {
		this.boardState = boardState;
		startingWidth = 700;
		startingHeight = 700;
		try {
			nightShade = ImageIO.read(Objects.class.getResource("NightShade.png"));
			duskShade = ImageIO.read(Objects.class.getResource("DuskShade.png"));
			sunShade = ImageIO.read(Objects.class.getResource("SunSet.png"));
		} catch (IOException e) {
			System.out.println("Dis not good m8");
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		//2d Array of terrain tiles
		Terrain[][] currentBoard = boardState.getArea();
		//2d Array of Objects
		Objects[][] currentObjects = boardState.getObjects();

		width = getWidth();
		height = getHeight();
		g.fillRect(0, 0, (int) width, (int) height); //Generic background for colour
		Direction d = boardState.getDirection(); //Player's view orientation
		Location playerDir = boardState.getPlayerCoords(); //Helps with centering the board to the player
		if (!(playerDir instanceof NullLocation)){
			//Draws the board based on the four different views you can have
			switch (d) {
			case SOUTH:
				drawSouth(currentBoard, currentObjects, g);
				break;
			case EAST:
				drawEast(currentBoard, currentObjects, g);
				break;
			case WEST:
				drawWest(currentBoard, currentObjects, g);
				break;
			default:
				drawNorth(currentBoard, currentObjects, g);
				break;
			}
		} else {
			System.out.println("LOADING IMAGE HERE"); //Insert Loading Music
		}
		int lightState = boardState.getLight();
		switch(lightState){
		case 0: g.drawImage(nightShade, 0, 0, (int) width, (int) height, null); //Night vision
		break;
		case 1: g.drawImage(duskShade, 0, 0, (int) width, (int) height, null); //Dusk vision
		break;
		case 3: g.drawImage(sunShade, 0, 0, (int) width, (int) height, null);
		break;
		default: break;
		}
	}

	/**
	 * Drawing the board with the "north" direction being north.
	 * @param currentBoard Terrain data given from boardstate
	 * @param currentObjects Object data given from boardstate
	 * @param g graphics object to draw on :)
	 */
	private void drawNorth(Terrain[][] currentBoard,
			Objects[][] currentObjects, Graphics g) {
		Location playerDir = boardState.getPlayerCoords();
		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				//Isometric formula for x, i *0.5 * Width - j * 0.5 * Width, including offset to get
				//the screen centered in the middle
				double x = ((i + 5.5 - playerDir.x) * 0.5 * width / 10)
						- ((j + 5.5 - playerDir.y) * 0.5 * width / 10)
						+ (10 / 1.75) * (int) width / 13;
				//Isometric formula for y, i * 0.5 * Height + j * 0.5 * Height including offset to get
				//the screen centered in the middle
				double y = ((i + 5.5 - playerDir.x) * 0.5 * height / 15)
						+ ((j + 5.5 - playerDir.y) * 0.5 * height / 15)
						+ (10 / 3) * (int) height / 21;
				//Drawing constrained to screen ratios to handle different resolutions and resizing :)
				if (currentBoard[i][j] != null)
					g.drawImage(
							currentBoard[i][j].img,
							(int) x,
							(int) y,
							(int) width/10,
							(int) height/15,
							null);
				else {
					System.out.println("Error!");
				}
			}
		}

		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				//If an object exists on this tile
				if (currentObjects[i][j] != null) {
					//Same isometric formula for x, y values, except modified for the image size.
					//Image width scaled - tile width should give the difference between the two, /2 to center
					//in the middle of the tile (half the difference on each side of the tile).
					double x = ((i + 5.5 - playerDir.x) * 0.5 * width / 10)
							- ((j + 5.5 - playerDir.y) * 0.5 * width / 10)
							+ (10 / 1.75) * (int) width / 13
							- (currentObjects[i][j].imgs[0].getWidth(null) * (width / startingWidth) - width/10)/2;
					//Same isometric formula for x, y values except modified for image height.
					//We want the image to sit about 75% down the tile to look "centered". Adding
					//0.75 of the tile height then subtracting the image height will give the desired
					//effect
					double y = ((i + 5.5 - playerDir.x) * 0.5 * height / 15)
							+ ((j + 5.5 - playerDir.y) * 0.5 * height / 15)
							+ (10 / 3) * (int) height / 21
							+ (0.75 * height / 15) - (currentObjects[i][j].imgs[0].getHeight(null) * (height / startingHeight));
					g.drawImage(
							currentObjects[i][j].imgs[0],
							(int) x,
							(int) y,
							(int) (currentObjects[i][j].imgs[0]
									.getWidth(null) * (width / startingWidth)),
							(int) (currentObjects[i][j].imgs[0]
									.getHeight(null) * (height / startingHeight)),
							null);
				}
			}
		}
	}

	/**
	 * Drawing the board with the "south" direction being north
	 * @param currentBoard Terrain data given from board state
	 * @param currentObjects Object data given from board state
	 * @param g Graphics object to draw on :)
	 */
	private void drawSouth(Terrain[][] currentBoard,
			Objects[][] currentObjects, Graphics g) {
		//Same logic as above but reversed x, y in respect to board array to draw "upside down"
		Location playerDir = boardState.getPlayerCoords();
		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				double x = ((i - currentBoard.length + 6 + (playerDir.x)) * 0.5 * width / 10)
						- ((j - currentBoard[0].length + 6 + (playerDir.y)) * 0.5 * width / 10)
						+ (10 / 1.75) * (int) width / 13;
				double y = ((i - currentBoard.length + 6 + (playerDir.x)) * 0.5 * height / (15))
						+ ((j - currentBoard[0].length + 6 + (playerDir.y)) * 0.5 * height / (15))
						+ (10 / 3) * (int) height / 21;
				if (currentBoard[currentBoard.length - 1 - i][currentBoard[0].length
						- 1 - j] != null)
					g.drawImage(
							currentBoard[currentBoard.length - 1 - i][currentBoard[0].length
									- 1 - j].img, (int) x, (int) y,
							(int) width / 10, (int) height / 15, null);
				else {
					System.out.println("Error!");
				}
			}
		}

		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				if (currentObjects[currentObjects.length - 1 - i][currentObjects[0].length - 1 - j] != null) {
					double x = ((i - currentBoard.length + 6 + (playerDir.x)) * 0.5 * width / 10)
							- ((j - currentBoard[0].length + 6 + (playerDir.y)) * 0.5 * width / 10)
							+ (10 / 1.75) * (int) width / 13
							- (currentObjects[currentObjects.length - 1 - i][currentObjects[0].length - 1 - j].imgs[1].getWidth(null) * (width / startingWidth) - width/10)/2;
					double y = ((i - currentBoard.length + 6 + (playerDir.x)) * 0.5 * height / 15)
							+ ((j - currentBoard[0].length + 6 + (playerDir.y)) * 0.5 * height / 15)
							+ (10 / 3) * (int) height / 21
							+ (0.75 * height / 15) - (currentObjects[currentObjects.length - 1 - i][currentObjects[0].length - 1 - j].imgs[1].getHeight(null) * (height / startingHeight));
					g.drawImage(
							currentObjects[currentObjects.length - 1 - i][currentObjects[0].length - 1 - j].imgs[1],
							(int) x,
							(int) y,
							(int) (currentObjects[currentObjects.length - 1 - i][currentObjects[0].length - 1 - j].imgs[1]
									.getWidth(null) * (width / startingWidth)),
							(int) (currentObjects[currentObjects.length - 1 - i][currentObjects[0].length - 1 - j].imgs[1]
									.getHeight(null) * (height / startingHeight)),
							null);
				}
			}
		}
	}
	
	/**
	 * Drawing the board with the "east" direction being north
	 * @param currentBoard Terrain data from board state
	 * @param currentObjects Object data from board state
	 * @param g Graphics object to draw on :)
	 */
	private void drawEast(Terrain[][] currentBoard,
			Objects[][] currentObjects, Graphics g) {
		//Starts getting a little trickier here. You have to flip the j and i for x, y, while
		//j corresponds to the inverse position in the arrays! *Whew* So much math!
		Location playerDir = boardState.getPlayerCoords();
		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				double x = ((j + 6 - currentBoard[0].length + playerDir.y) * 0.5 * width / 10)
						- ((i + 5 - playerDir.x) * 0.5 * width / 10)
						+ (10 / 1.75) * (int) width / 13;
				double y = ((j + 6 - currentBoard[0].length + playerDir.y) * 0.5 * height / (15))
						+ ((i + 5 - playerDir.x) * 0.5 * height / (15))
						+ (10 / 3) * (int) height / 21;
				if (currentBoard[i][currentBoard[0].length
						- 1 - j] != null)
					g.drawImage(
							currentBoard[i][currentBoard[0].length
									- 1 - j].img, (int) x, (int) y,
							(int) width / 10, (int) height / 15, null);
				else {
					System.out.println("Error!");
				}
			}
		}

		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				if (currentObjects[i][currentObjects[0].length - 1 - j] != null) {
					double x = ((j + 6 - currentBoard[0].length + playerDir.y) * 0.5 * width / 10)
							- ((i + 5 - playerDir.x) * 0.5 * width / 10)
							+ (10 / 1.75) * (int) width / 13
							- (currentObjects[i][currentObjects[0].length - 1 - j].imgs[2].getWidth(null) * (width / startingWidth) - width/10)/2;
					double y = ((j + 6 - currentBoard[0].length + playerDir.y) * 0.5 * height / 15)
							+ ((i + 5 - playerDir.x) * 0.5 * height / 15)
							+ (10 / 3) * (int) height / 21
							+ (0.75 * height / 15) - (currentObjects[i][currentObjects[0].length - 1 - j].imgs[2].getHeight(null) * (height / startingHeight));
					g.drawImage(
							currentObjects[i][currentObjects[0].length - 1 - j].imgs[2],
							(int) x,
							(int) y,
							(int) (currentObjects[i][currentObjects[0].length - 1 - j].imgs[2]
									.getWidth(null) * (width / startingWidth)),
							(int) (currentObjects[i][currentObjects[0].length - 1 - j].imgs[2]
									.getHeight(null) * (height / startingHeight)),
							null);
				}
			}
		}
	}
	
	/**
	 * Drawing the board with "west" direction as north
	 * @param currentBoard Terrain data passed in from board state
	 * @param currentObjects Object data passed in from board state
	 * @param g Graphics object to draw on
	 */
	private void drawWest(Terrain[][] currentBoard,
			Objects[][] currentObjects, Graphics g) {
		//This is the opposite of east, so easy to figure out when you figure out east :)
		Location playerDir = boardState.getPlayerCoords();
		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				double x = ((j + 5 - playerDir.y) * 0.5 * width / 10)
						- ((i - currentBoard.length + 6 + playerDir.x) * 0.5 * width / 10)
						+ (10 / 1.75) * (int) width / 13;
				double y = ((j + 5 - playerDir.y) * 0.5 * height / (15))
						+ ((i - currentBoard.length + 6 + playerDir.x) * 0.5 * height / (15))
						+ (10 / 3) * (int) height / 21;
				if (currentBoard[currentBoard.length - 1 - i][j] != null)
					g.drawImage(
							currentBoard[currentBoard.length - 1 - i][j].img, (int) x, (int) y,
							(int) width / 10, (int) height / 15, null);
				else {
					System.out.println("Error!");
				}
			}
		}

		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				if (currentObjects[currentObjects.length - 1 - i][j] != null) {
					double x = ((j + 5 - playerDir.y) * 0.5 * width / 10)
							- ((i - currentBoard.length + 6 + playerDir.x) * 0.5 * width / 10)
							+ (10 / 1.75) * (int) width / 13
							- (currentObjects[currentObjects.length - 1 - i][j].imgs[3].getWidth(null) * (width / startingWidth) - width/10)/2;
					double y = ((j + 5 - playerDir.y) * 0.5 * height / 15)
							+ ((i - currentBoard.length + 6 + playerDir.x) * 0.5 * height / 15)
							+ (10 / 3) * (int) height / 21
							+ (0.75 * height / 15) - (currentObjects[currentObjects.length - 1 - i][j].imgs[3].getHeight(null) * (height / startingHeight));
					g.drawImage(
							currentObjects[currentObjects.length - 1 - i][j].imgs[3],
							(int) x,
							(int) y,
							(int) (currentObjects[currentObjects.length - 1 - i][j].imgs[3]
									.getWidth(null) * (width / startingWidth)),
							(int) (currentObjects[currentObjects.length - 1 - i][j].imgs[3]
									.getHeight(null) * (height / startingHeight)),
							null);
				}
			}
		}
	}
}
