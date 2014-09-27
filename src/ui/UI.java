package ui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gameworld.world.Board;
import graphics.GraphicsPane;
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

import client.BoardState;
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
	private JPanel hudPanel;
	private JPanel graphicsPanel;
	private JLayeredPane gamePanel;

	public UI(Client cl) {

		// initialise UI
		super();
		client = cl;
	//	setLayout(new BorderLayout());

		// setup components
	//	setupAspectRatio();
		setupFileBar();
		setupKeyBindings();
		setupCanvas();
		setupGraphics();
		setupGamePanel();

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
	
	private void setupGamePanel(){
	//	final int defaultWidth = 500;
		//final int defaultHeight = 500;
		
	gamePanel = new JLayeredPane();
	gamePanel.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
	gamePanel.add(hudPanel);
	gamePanel.add(graphicsPanel);
	//gamePanel.setBounds(0,0,GAME_WIDTH, GAME_HEIGHT);
	graphicsPanel.setBounds(0,0,GAME_WIDTH, GAME_HEIGHT);
	hudPanel.setBounds(0,0,GAME_WIDTH, GAME_HEIGHT);
	add(gamePanel);
	}
	
	private void setupGraphics(){
		graphicsPanel = new GraphicsPane(2, client.getBoard()); //temporaryBoardState);
	}

	private void setupAspectRatio(){
		addComponentListener(new ComponentAdapter() {
		      @Override
		      public void componentResized(ComponentEvent e) {
		      System.out.println("Resized");
		      int width = e.getComponent().getWidth();
		      int height = e.getComponent().getHeight();
		      int newSize = Math.max(width, height);
		      setSize(newSize, newSize);
		      gamePanel.setBounds(0,0,newSize, newSize);
		      graphicsPanel.setBounds(0,0,newSize, newSize);
		      hudPanel.setBounds(0,0,newSize, newSize);
		    }
		  });
	}
	
	/**
	 * sets up the window to respond to key presses using a key binding system
	 */
	private void setupKeyBindings() {
		// sets the window to respond to inputs from any internal components

		// add arrow keys and WASD for movement
		addKeyBinding("MoveRight", new MoveRight(client), KeyEvent.VK_RIGHT,
				KeyEvent.VK_D);
		addKeyBinding("MoveLeft", new MoveLeft(client), KeyEvent.VK_LEFT,
				KeyEvent.VK_A);
		addKeyBinding("MoveUp", new MoveUp(client), KeyEvent.VK_UP,
				KeyEvent.VK_S);
		addKeyBinding("MoveDown", new MoveDown(client), KeyEvent.VK_DOWN,
				KeyEvent.VK_W);
		addKeyBinding("ThrowSnowball", new ThrowSnowball(client), KeyEvent.VK_SPACE);
		addKeyBinding("Inspect", new InspectItem(client), KeyEvent.VK_I);
		addKeyBinding("Interact", new InteractWithItem(client), KeyEvent.VK_Q);
		
		//setup item hotkeys
		addKeyBinding("UseItem1", new UseItem(client, 1), KeyEvent.VK_1);
		addKeyBinding("UseItem2", new UseItem(client, 2), KeyEvent.VK_2);
		addKeyBinding("UseItem3", new UseItem(client, 3), KeyEvent.VK_3);
		addKeyBinding("UseItem4", new UseItem(client, 4), KeyEvent.VK_4);
		addKeyBinding("UseItem5", new UseItem(client, 5), KeyEvent.VK_5);
		addKeyBinding("UseItem6", new UseItem(client, 6), KeyEvent.VK_6);
		addKeyBinding("UseItem7", new UseItem(client, 7), KeyEvent.VK_7);
		addKeyBinding("UseItem8", new UseItem(client, 8), KeyEvent.VK_8);
		addKeyBinding("UseItem9", new UseItem(client, 9), KeyEvent.VK_9);

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
		hudPanel = new HUDPanel(client);
		hudPanel.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		hudPanel.setOpaque(false);
		//add(gameCanvas);
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
		UI tester = new UI(new Client(1));
	}

}
