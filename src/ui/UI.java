package ui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
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

import client.Client;

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
	private Client client;
	private JPanel buttonPanel;
	private JPanel inventoryPanel;
	private JButton rotateViewLeft;
	private JButton rotateViewRight;
	private Canvas gameCanvas;
	private JPanel gamePanel;
	

	public UI() {

		// initialise UI
		super();
		
		client = new Client(0);
	//	setLayout(new BorderLayout());

		// setup components
		setupFileBar();
		setupKeyBindings();
		setupCanvas();

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
		//repaint();
	}

	/**
	 * sets up the window to respond to key presses using a key binding system
	 */
	private void setupKeyBindings() {
		// sets the window to respond to inputs from any internal components

		// add arrow keys and WASD for movement
		addKeyBinding("RightArrow", new MoveRight(client), KeyEvent.VK_RIGHT,
				KeyEvent.VK_D);
		addKeyBinding("LeftArrow", new MoveLeft(client), KeyEvent.VK_LEFT,
				KeyEvent.VK_A);
		addKeyBinding("UpArrow", new MoveUp(client), KeyEvent.VK_UP,
				KeyEvent.VK_S);
		addKeyBinding("DownArrow", new MoveDown(client), KeyEvent.VK_DOWN,
				KeyEvent.VK_W);
		addKeyBinding("SpaceBar", new FireSnowball(client), KeyEvent.VK_SPACE);

	}

	/**
	 * Sets up key bindings for given keyConstants
	 * 
	 * @param actionString
	 *            the action string to be used in the action map
	 * @param action
	 *            the action to be tied to the keys
	 * @param keyConstants
	 *            the key constants to tie to the action string
	 */
	private void addKeyBinding(String actionString, KeyAction action,
			int... keyConstants) {

		// get the input and action maps
		InputMap im = getRootPane().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getRootPane().getActionMap();

		// tie each key constant to the action string
		for (int key : keyConstants) {
			im.put(KeyStroke.getKeyStroke(key, 0), actionString);
		}
		// now tie the action to the action string
		am.put(actionString, action);
	}

	/**
	 * Sets up the file menu and listeners
	 */
	private void setupFileBar() {

		// setup menu and items
		JMenuBar menu = new JMenuBar();
		menu.setFocusable(false);
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
				g.drawRect(0, 0, getWidth(), getHeight());
				g.setColor(Color.GRAY);
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		gameCanvas.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		add(gameCanvas);
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
