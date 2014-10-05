package graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import client.BoardState;
import gameworld.world.Direction;
import graphics.assets.Objects;
import graphics.assets.Terrain;

public class GraphicsPane extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -2715513999456707521L;
	private double width;
	private double height;
	private double startingWidth;
	private double startingHeight;
	private BoardState boardState;

	public GraphicsPane(int playerNum, BoardState boardState) {
		this.boardState = boardState;
		startingWidth = 700;
		startingHeight = 700;
	}

	public void paintComponent(Graphics g) {
		Terrain[][] currentBoard = boardState.getArea();
		Objects[][] currentObjects = boardState.getObjects();

		width = getWidth();
		height = getHeight();
		g.fillRect(0, 0, (int) width, (int) height);
		Direction d = boardState.getDirection();
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
	}

	private void drawNorth(Terrain[][] currentBoard,
			Objects[][] currentObjects, Graphics g) {
		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				double x = (i * 0.5 * width / 10)
						- (j * 0.5 * width / 10)
						+ (10 / 1.75) * (int) width / 13;
				double y = (i * 0.5 * height / 15)
						+ (j * 0.5 * height / 15)
						+ (10 / 3) * (int) height / 21;
				System.out.println(i + " i " + j + " j " + x + " x " + y + " y ");
				if (currentBoard[i][j] != null)
					g.drawImage(
							currentBoard[i][j].img,
							(int) x,
							(int) y,
							(int) width/10,
							(int) height/15,
							null);
				else {
					System.out.println("THIS SHIT IS NULL NIGGA");
				}
			}
		}

		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				if (currentObjects[i][j] != null) {
					double x = (i * 0.5 * width / 10)
							- (j * 0.5 * width / 10)
							+ (10 / 1.75) * (int) width / 13
							- (currentObjects[i][j].imgs[0].getWidth(null) * (width / startingWidth) - width/10)/2;
					double y = (i * 0.5 * height / 15)
							+ (j * 0.5 * height / 15)
							+ (10 / 3) * (int) height / 21
							+ (0.75 * height / 15) - (currentObjects[i][j].imgs[0].getHeight(null) * (height / startingHeight));
					System.out.println(i + " i " + j + " j " + x + " x " + y + " y ");
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

	private void drawSouth(Terrain[][] currentBoard,
			Objects[][] currentObjects, Graphics g) {
		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				double x = (i * 0.5 * width / 10)
						- (j * 0.5 * width / 10)
						+ (10 / 1.75) * (int) width / 13;
				double y = (i * 0.5 * height / (15))
						+ (j * 0.5 * height / (15))
						+ (10 / 3) * (int) height / 21;
				if (currentBoard[currentBoard.length - 1 - i][currentBoard[0].length
						- 1 - j] != null)
					g.drawImage(
							currentBoard[currentBoard.length - 1 - i][currentBoard[0].length
									- 1 - j].img, (int) x, (int) y,
							(int) width / 10, (int) height / 15, null);
				else {
					System.out.println("THIS SHIT IS NULL NIGGA");
				}
			}
		}

		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				if (currentObjects[currentObjects.length - 1 - i][currentObjects[0].length - 1 - j] != null) {
					double x = (i * 0.5 * width / 10)
							- (j * 0.5 * width / 10)
							+ (10 / 1.75) * (int) width / 13
							- (currentObjects[currentObjects.length - 1 - i][currentObjects[0].length - 1 - j].imgs[1].getWidth(null) * (width / startingWidth) - width/10)/2;
					double y = (i * 0.5 * height / 15)
							+ (j * 0.5 * height / 15)
							+ (10 / 3) * (int) height / 21
							+ (0.75 * height / 15) - (currentObjects[currentObjects.length - 1 - i][currentObjects[0].length - 1 - j].imgs[1].getHeight(null) * (height / startingHeight));
					System.out.println(i + " i " + j + " j " + x + " x " + y + " y ");
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
	
	private void drawEast(Terrain[][] currentBoard,
			Objects[][] currentObjects, Graphics g) {
		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				double x = (i * 0.5 * width / 10)
						- (j * 0.5 * width / 10)
						+ (10 / 1.75) * (int) width / 13;
				double y = (i * 0.5 * height / (15))
						+ (j * 0.5 * height / (15))
						+ (10 / 3) * (int) height / 21;
				if (currentBoard[i][currentBoard[0].length
						- 1 - j] != null)
					g.drawImage(
							currentBoard[i][currentBoard[0].length
									- 1 - j].img, (int) x, (int) y,
							(int) width / 10, (int) height / 15, null);
				else {
					System.out.println("THIS SHIT IS NULL NIGGA");
				}
			}
		}

		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				if (currentObjects[i][currentObjects[0].length - 1 - j] != null) {
					double x = (i * 0.5 * width / 10)
							- (j * 0.5 * width / 10)
							+ (10 / 1.75) * (int) width / 13
							- (currentObjects[i][currentObjects[0].length - 1 - j].imgs[2].getWidth(null) * (width / startingWidth) - width/10)/2;
					double y = (i * 0.5 * height / 15)
							+ (j * 0.5 * height / 15)
							+ (10 / 3) * (int) height / 21
							+ (0.75 * height / 15) - (currentObjects[i][currentObjects[0].length - 1 - j].imgs[2].getHeight(null) * (height / startingHeight));
					System.out.println(i + " i " + j + " j " + x + " x " + y + " y ");
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
	
	private void drawWest(Terrain[][] currentBoard,
			Objects[][] currentObjects, Graphics g) {
		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				double x = (i * 0.5 * width / 10)
						- (j * 0.5 * width / 10)
						+ (10 / 1.75) * (int) width / 13;
				double y = (i * 0.5 * height / (15))
						+ (j * 0.5 * height / (15))
						+ (10 / 3) * (int) height / 21;
				if (currentBoard[currentBoard.length - 1 - i][j] != null)
					g.drawImage(
							currentBoard[currentBoard.length - 1 - i][j].img, (int) x, (int) y,
							(int) width / 10, (int) height / 15, null);
				else {
					System.out.println("THIS SHIT IS NULL NIGGA");
				}
			}
		}

		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				if (currentObjects[currentObjects.length - 1 - i][j] != null) {
					double x = (i * 0.5 * width / 10)
							- (j * 0.5 * width / 10)
							+ (10 / 1.75) * (int) width / 13
							- (currentObjects[currentObjects.length - 1 - i][j].imgs[3].getWidth(null) * (width / startingWidth) - width/10)/2;
					double y = (i * 0.5 * height / 15)
							+ (j * 0.5 * height / 15)
							+ (10 / 3) * (int) height / 21
							+ (0.75 * height / 15) - (currentObjects[currentObjects.length - 1 - i][j].imgs[3].getHeight(null) * (height / startingHeight));
					System.out.println(i + " i " + j + " j " + x + " x " + y + " y ");
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
