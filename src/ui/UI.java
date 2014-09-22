package ui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gameworld.world.Board;
import ui.actions.*;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

/**
 * The UI class encompasses the application window to display the game. It
 * listens to all user inputs and sends them to the board where they can be
 * dealt with. It holds the canvas for the game to be displayed on and controls
 * the pop-up windows related to the game.
 * 
 * @author Ryan Burnell, 300279172
 * 
 */
public class UI extends JFrame {

	public static final int GAME_WIDTH = 500;
	public static final int GAME_HEIGHT = 500;

	// Fields
	private Board board;
	private JPanel buttonPanel;
	private JPanel inventoryPanel;
	private JButton rotateViewLeft;
	private JButton rotateViewRight;
	private Canvas gameCanvas;
	private JLayeredPane gamePanel;

	/*
	 * ========================================================= PLEASE MODIFY
	 * This is here so code compiles, I just don't uncompilable code at the
	 * moment. But feel free to rename EVERYTHING, including the package.
	 * -Kelsey =========================================================
	 */

	public UI() {

		// initialise UI
		super();
		setLayout(new BorderLayout());

		// setup components
		setupFileBar();
		setupButtonPanel();
		setupGamePanel();
		setupKeyBindings();

		// set initial size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension scrnsize = toolkit.getScreenSize();
		setBounds((scrnsize.width - getWidth()) / 2,
				(scrnsize.height - getHeight()) / 2, getWidth(), getHeight());

		// pack and Display window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(true);
		setVisible(true);
	}

	/**
	 * sets up the window to respond to key presses using a key binding system
	 */
	private void setupKeyBindings() {
		// sets the window to respond to inputs from any internal components
		InputMap im = getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getRootPane().getActionMap();

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RightArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LeftArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UpArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DownArrow");

		am.put("RightArrow", new MoveRight(board));
		am.put("LeftArrow", new MoveLeft(board));
		am.put("UpArrow", new MoveUp(board));
		am.put("DownArrow", new MoveDown(board));
	}

	/**
	 * sets up a layered panel which will hold the game window and also the
	 * inventory bar overlayed
	 */
	private void setupGamePanel() {
		// creates the canvas an inventory bars
		setupCanvas();
		setupInventoryBar();

		// setup pane
		gamePanel = new JLayeredPane();
		gamePanel.setPreferredSize(new Dimension(500, 500));
		gamePanel.setBorder(BorderFactory
				.createTitledBorder("Snowball Fight 3000"));

		// add the canvas and inventory
		gamePanel.add(gameCanvas, new Integer(0));
		gamePanel.add(inventoryPanel, new Integer(1));

		add(gamePanel, BorderLayout.CENTER);
	}

	/**
	 * Sets up the bar that will hold the inventory items
	 */
	private void setupInventoryBar() {
		final int panelHeight = (int) (GAME_HEIGHT * 0.98);

		// setup buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setOpaque(false);

		// temporary buttons as first implementation
		buttonPanel.add(new JButton("Item"));
		buttonPanel.add(new JButton("Item2"));
		buttonPanel.add(new JButton("Item3"));
		buttonPanel.add(new JButton("Item4"));

		inventoryPanel = new JPanel();
		inventoryPanel.setLayout(new BorderLayout());
		inventoryPanel.setOpaque(false);
		inventoryPanel.setSize(GAME_WIDTH, panelHeight);

		inventoryPanel.add(buttonPanel, BorderLayout.SOUTH);
		// add(inventoryPanel, BorderLayout.SOUTH);
	}

	/**
	 * Sets up the file menu and listeners
	 */
	private void setupFileBar() {

		// setup menu and items
		JMenuBar menu = new JMenuBar();
		final JMenu fileMenu = new JMenu("File");
		final JMenuItem newGame = new JMenuItem("New Game");
		final JMenuItem quit = new JMenuItem("Quit");

		// add menu and items
		menu.add(fileMenu);
		fileMenu.add(newGame);
		fileMenu.add(quit);

		setJMenuBar(menu);
		// setup listeners for the menu
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(EXIT_ON_CLOSE);
			}
		});
	}

	/**
	 * temporary method to set up the canvas until the proper graphical window
	 * is developed
	 */
	private void setupCanvas() {
		gameCanvas = new Canvas() {
			public void paint(Graphics g) {
				g.fillRect(0, 0, this.getHeight(), this.getWidth());
			}
		};
		gameCanvas.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		// add(gameCanvas, BorderLayout.CENTER);
	}

	/**
	 * Sets up the lefthand panel that contains the buttons to do actions
	 */
	private void setupButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0, 1));

		// setup buttons
		rotateViewLeft = new JButton("Rotate Left");
		rotateViewLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// :TODO rotate view left
				System.out.println("Testing... Rotate Left");
			}
		});

		rotateViewRight = new JButton("Rotate Right");
		rotateViewRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// :TODO rotate view left
				System.out.println("Testing... Rotate Right");
			}
		});

		buttonPanel.add(rotateViewLeft);
		buttonPanel.add(rotateViewRight);
		add(buttonPanel, BorderLayout.WEST);
	}

	public void repaint() {
		gamePanel.repaint();
	}

	public static void main(String[] args) {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}

			}
		} catch (Exception e) {
			// do nothing
		}
		UI tester = new UI();
	}

}
