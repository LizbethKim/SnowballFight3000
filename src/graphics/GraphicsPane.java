package graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import client.BoardState;
import graphics.assets.Objects;
import graphics.assets.Sprites;
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
		width = getWidth();
		height = getHeight();
		g.fillRect(0, 0, (int)width, (int)height);
	/*	System.out.println("Width + Tile Width | " + width + " "
				+ currentBoard[0][0].img.getWidth());
		System.out.println("Height + Tile Height | " + height + " "
				+ currentBoard[0][0].img.getHeight());*/
		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				double x = (i * 0.5 * width / currentBoard.length) - (j * 0.5 * width / currentBoard.length)
						+ (currentBoard.length / 1.75) * (int) width / 13;
				double y = (i * 0.5 * height / (currentBoard[0].length*1.5)) + (j * 0.5 * height / (currentBoard[0].length*1.5))
						+ (currentBoard[0].length / 3) * (int) height / 21;
				g.drawImage(currentBoard[i][j].img, (int) x, (int) y, (int) width / 10,
						(int) height / 15, null);
			}
		}
		
		Objects[][] currentObjects = boardState.getObjects();
		for (int i = 0; i < currentBoard.length; i++){
			for (int j = -1; j < currentBoard[0].length -1; j++){
				double x = (i * 0.5 * width / currentBoard.length) - (j * 0.5 * width / currentBoard.length)
						+ (currentBoard.length / 1.75) * (int) width / 13 - (0.25 * width/currentBoard.length);
				double y = (i * 0.5 * height / (currentBoard[0].length*1.5)) + (j * 0.5 * height / (currentBoard[0].length * 1.5))
						+ (currentBoard[0].length / 3) * (int) height / 21 - (0.25 * height/(currentBoard[0].length*1.5));
				if (currentObjects[i][j + 1] != null){
					g.drawImage(currentObjects[i][j + 1].img, (int) x, (int) y, (int) (currentObjects[i][j + 1].img.getWidth(null)* (width/startingWidth)),(int) (currentObjects[i][j + 1].img.getHeight(null)* (height/startingHeight)), null);
				}
			}
		}
	}

}
