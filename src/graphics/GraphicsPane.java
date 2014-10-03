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
		switch (d){
		case SOUTH: drawSouth(currentBoard, currentObjects, g);
		break;
		default: drawNorth(currentBoard, currentObjects, g);
		break;
		}
	}
	
	private void drawNorth(Terrain[][] currentBoard, Objects[][] currentObjects, Graphics g){
		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				double x = (i * 0.5 * width / currentBoard.length)
						- (j * 0.5 * width / currentBoard.length)
						+ (currentBoard.length / 1.75) * (int) width / 13;
				double y = (i * 0.5 * height / (currentBoard[0].length * 1.5))
						+ (j * 0.5 * height / (currentBoard[0].length * 1.5))
						+ (currentBoard[0].length / 3) * (int) height / 21;
				if (currentBoard[i][j] != null)
					g.drawImage(currentBoard[i][j].img, (int) x, (int) y,
							(int) width / 10, (int) height / 15, null);
				else {
					System.out.println("THIS SHIT IS NULL NIGGA");
				}
			}
		}

		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = -1; j < currentBoard[0].length -1; j++) {
				if (currentObjects[i][j + 1] != null) {
					double x = (i * 0.5 * width / currentBoard.length)
							- (j * 0.5 * width / currentBoard.length)
							+ (currentBoard.length / 1.75)
							* (int) width
							/ 13
							- (0.25 * width / currentBoard.length)
							- (0.125 * currentObjects[i][j + 1].imgs[0]
									.getWidth(null) * (width / startingWidth));
					double y = (i * 0.5 * height / (currentBoard[0].length * 1.5))
							+ (j * 0.5 * height / (currentBoard[0].length * 1.5))
							+ (currentBoard[0].length / 3)
							* (int) height
							/ 21
							+ (0.25 * height / currentBoard[0].length * 1.5)
							- (currentObjects[i][j + 1].imgs[0]
									.getHeight(null) * (height / startingHeight) - 0.5*currentBoard[i][j + 1].img.getHeight(null));
					g.drawImage(
							currentObjects[i][j + 1].imgs[0],
							(int) x,
							(int) y,
							(int) (currentObjects[i][j + 1].imgs[0]
									.getWidth(null) * (width / startingWidth)),
							(int) (currentObjects[i][j + 1].imgs[0]
									.getHeight(null) * (height / startingHeight)),
							null);
				}
			}
		}
	}
	
	private void drawSouth(Terrain[][] currentBoard, Objects[][] currentObjects, Graphics g){
		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				double x = (i * 0.5 * width / currentBoard.length)
						- (j * 0.5 * width / currentBoard.length)
						+ (currentBoard.length / 1.75) * (int) width / 13;
				double y = (i * 0.5 * height / (currentBoard[0].length * 1.5))
						+ (j * 0.5 * height / (currentBoard[0].length * 1.5))
						+ (currentBoard[0].length / 3) * (int) height / 21;
				if (currentBoard[currentBoard.length - 1 - i][currentBoard[0].length - 1 - j] != null)
					g.drawImage(currentBoard[currentBoard.length - 1 - i][currentBoard[0].length - 1 - j].img, (int) x, (int) y,
							(int) width / 10, (int) height / 15, null);
				else {
					System.out.println("THIS SHIT IS NULL NIGGA");
				}
			}
		}

		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = -1; j < currentBoard[0].length -1; j++) {
				if (currentObjects[currentObjects.length - 1 - i][currentObjects[0].length - 1 - (j + 1)] != null) {
					double x = (i * 0.5 * width / currentBoard.length)
							- (j * 0.5 * width / currentBoard.length)
							+ (currentBoard.length / 1.75)
							* (int) width
							/ 13
							- (0.25 * width / currentBoard.length)
							- (0.125 * currentObjects[currentObjects.length - 1 - i][currentObjects[0].length - 1 - (j + 1)].imgs[0]
									.getWidth(null) * (width / startingWidth));
					double y = (i * 0.5 * height / (currentBoard[0].length * 1.5))
							+ (j * 0.5 * height / (currentBoard[0].length * 1.5))
							+ (currentBoard[0].length / 3)
							* (int) height
							/ 21
							+ (0.25 * height / currentBoard[0].length * 1.5)
							- (currentObjects[currentObjects.length - 1 - i][currentObjects[0].length - 1 - (j + 1)].imgs[0]
									.getHeight(null) * (height / startingHeight) - 0.5 * currentBoard[i][j + 1].img.getHeight(null));
					g.drawImage(
							currentObjects[currentObjects.length - i - 1][currentObjects[0].length - (j + 1) - 1].imgs[0],
							(int) x,
							(int) y,
							(int) (currentObjects[currentObjects.length - 1 - i][currentObjects[0].length - 1 - (j + 1)].imgs[0]
									.getWidth(null) * (width / startingWidth)),
							(int) (currentObjects[currentObjects.length - 1 - i][currentObjects[0].length - 1- (j + 1)].imgs[0]
									.getHeight(null) * (height / startingHeight)),
							null);
				}
			}
		}
	}

}
