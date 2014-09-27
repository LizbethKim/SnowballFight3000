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
	private int width;
	private int height;
	private BoardState boardState;

	public GraphicsPane(int playerNum, BoardState boardState) {
		this.boardState = boardState;
	}

	public void paintComponent(Graphics g) {
		Terrain[][] currentBoard = boardState.getArea();

		width = getWidth();
		height = getHeight();
		g.fillRect(0, 0, width, height);
		System.out.println("Width + Tile Width | " + width + " "
				+ currentBoard[0][0].img.getWidth());
		System.out.println("Height + Tile Height | " + height + " "
				+ currentBoard[0][0].img.getHeight());
		for (int i = 0; i < currentBoard.length; i++) {
			for (int j = 0; j < currentBoard[0].length; j++) {
				double x = (i * 0.5 * width / 10) - (j * 0.5 * width / 10)
						+ (currentBoard.length / 1.75) * (int) width / 13;
				double y = (i * 0.5 * height / 15) + (j * 0.5 * height / 15)
						+ (currentBoard[0].length / 3) * (int) height / 21;
				g.drawImage(currentBoard[i][j].img, (int) x, (int) y, (int) width / 10,
						(int) height / 15, null);
			}
		}
		
		Objects[][] currentObjects = boardState.getObjects();
		for (int i = 0; i < currentObjects.length; i++){
			for (int j = 0; j < currentObjects[0].length; j++){
				double x = (i * 0.5 * width / 10) - (j * 0.5 * width / 10)
						+ (currentObjects.length / 1.75) * (int) width / 13;
				double y = (i * 0.5 * height / 15) + (j * 0.5 * height / 15)
						+ (currentObjects[0].length / 3) * (int) height / 21;
				g.drawImage(currentObjects[i][j].img, (int) x, (int) y, (int) width / 10,
						(int) height / 15, null);
			}
		}
	}

}
