package graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import client.BoardState;
import graphics.assets.Sprites;
import graphics.assets.Terrain;

public class GraphicsPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2715513999456707521L;
	private Sprites players;
	private int width;
	private int height;
	private BufferedImage[][] board;
	private BoardState boardState;

	public GraphicsPane(int playerNum, int width, int height, BoardState boardState) {
		players = new Sprites();
		this.width = width;
		this.height = height;
		this.boardState = boardState;
		board = new BufferedImage[boardState.getArea().length][boardState.getArea()[0].length];
	}

	public void paintComponent(Graphics g) {
		Terrain[][] currentBoard = boardState.getArea();
		for (int i = 0; i < currentBoard.length; i++){
			for (int j = 0; j < currentBoard[0].length; j++){
				board[i][j] = currentBoard[i][j].img;
			}
		}
		width = getWidth();
		height = getHeight();
		g.fillRect(0, 0, width, height);
		System.out.println("Width + Tile Width | " + width + " "
				+ board[0][0].getWidth());
		System.out.println("Height + Tile Height | " + height + " "
				+ board[0][0].getHeight());
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				double x = (i * 0.5 * width / 10) - (j * 0.5 * width / 10)
						+ (board.length / 1.75) * (int) width / 13;
				double y = (i * 0.5 * height / 15) + (j * 0.5 * height / 15)
						+ (board[0].length / 3) * (int) height / 21;
				g.drawImage(board[i][j], (int) x, (int) y, (int) width / 10,
						(int) height / 15, null);
			}
		}

	}

}
