package ui.popups;

import gameworld.game.client.ClientGame;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * The load popup displays a dialog for loading a saved game, and prompts the
 * user to select a file
 * 
 * @author Ryan Burnell, 300279172
 */

public class LoadPopup extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2165647668694935966L;
	private ClientGame client;
	private List<String> playerOptions;
	private ButtonGroup playerChoice;

	public LoadPopup(ClientGame cl) {
		this.client = cl;
		this.playerOptions = client.getPlayerList();
		if (playerOptions == null || playerOptions.size() == 0) {
			showFailDialog("There are no players to load");
		} else {
		setLayout(new GridLayout(0, 1));
		setupButtons();
		showLoadDialog();
		}
	}

	/**
	 * sets up the player choices
	 */
	private void setupButtons() {
		//setup radio button group
		playerChoice = new ButtonGroup();
		
		//for each player choice
		for (int i = 0; i < playerOptions.size(); i++) {
			//get the name
			String name = playerOptions.get(i);
			
			//create a new radio button with the choice number
			JRadioButton button = new JRadioButton(name);
			button.setActionCommand(i + "");
			
			//now add button
			playerChoice.add(button);
			add(button);
		}
	}

	/**
	 * show a dialog to inform the player that the loading failed
	 * @param reason
	 */
	public static void showFailDialog(String reason) {
		JOptionPane.showMessageDialog(null, reason, "Load Failed",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * display a load dialog until a choice is chosen or the load is cancelled
	 */
	private void showLoadDialog() {
		int chosen = -1;
		// keep redisplaying dialog until cancelled or valid option chosen
		do {
			// display dialog
			chosen = JOptionPane.showOptionDialog(null, this,
					"Select a Player to Load", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.DEFAULT_OPTION, null, null, null);
		} while (playerChoice.getSelection() == null
				&& chosen != JOptionPane.CANCEL_OPTION);
		//if a correct choice was picked
		if (chosen == JOptionPane.OK_OPTION) {
			//call the client to load in the selected player
			int playerIndex = Integer.parseInt(playerChoice.getSelection()
					.getActionCommand());
			client.loadPlayer(playerIndex);
		}
	}

}
