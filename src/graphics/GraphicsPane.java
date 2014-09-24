package graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import graphics.assets.Sprites;

public class GraphicsPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2715513999456707521L;
	private Sprites players;
	private int width;
	private int height;
	private BufferedImage img;
	private BufferedImage[][] boardTest;

	public GraphicsPane(int playerNum, int width, int height) {
		players = new Sprites();
		this.width = width;
		this.height = height;

		try {
			System.out.println(getClass().getResource(
					"/graphics/assets/Tile.png"));
			img = ImageIO.read(getClass().getResource(
					"/graphics/assets/Tile.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		boardTest = new BufferedImage[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				boardTest[i][j] = img;
			}
		}
	}

	public void paintComponent(Graphics g) {
		width = getWidth();
		height = getHeight();
		g.fillRect(0, 0, width, height);
		System.out.println("Width + Tile Width | " + width + " "
				+ img.getWidth());
		System.out.println("Height + Tile Height | " + height + " "
				+ img.getHeight());
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				double x = (i * 0.5 * width / 10) - (j * 0.5 * width / 10)
						+ (boardTest.length / 1.75) * (int) width / 13;
				double y = (i * 0.5 * height / 15) + (j * 0.5 * height / 15)
						+ (boardTest[0].length / 3) * (int) height / 21;
				g.drawImage(img, (int) x, (int) y, (int) width / 10,
						(int) height / 15, null);
			}
		}

	}

}
