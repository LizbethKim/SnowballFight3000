package ui.popups;

import gameworld.game.client.ClientGame;

import java.awt.GridLayout;
import java.io.File;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
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

	private void setupButtons() {
		playerChoice = new ButtonGroup();
		for (int i = 0; i < playerOptions.size(); i++) {
			String name = playerOptions.get(i);
			JRadioButton button = new JRadioButton(name);
			button.setActionCommand(i + "");
			playerChoice.add(button);
			add(button);
		}
	}

	public static void showFailDialog(String reason) {
		JOptionPane.showMessageDialog(null, reason, "Load Failed",
				JOptionPane.ERROR_MESSAGE);
	}

	private void showLoadDialog() {
		int chosen = -1;
		// keep redisplaying dialog until cancelled or valid option chosen
		do {
			// dialog
			chosen = JOptionPane.showOptionDialog(null, this,
					"Select a Player to Load", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.DEFAULT_OPTION, null, null, null);
		} while (playerChoice.getSelection() == null
				&& chosen != JOptionPane.CANCEL_OPTION);
		if (chosen == JOptionPane.OK_OPTION) {
			int playerIndex = Integer.parseInt(playerChoice.getSelection()
					.getActionCommand());
			client.loadPlayer(playerIndex);
		}
	}

	/*
	 * public static File loadFile() { JFileChooser fileChooser = new
	 * JFileChooser(); int chosen = JFileChooser.ERROR_OPTION; while (chosen !=
	 * JFileChooser.APPROVE_OPTION) { chosen = fileChooser.showOpenDialog(null);
	 * } return fileChooser.getSelectedFile(); }
	 */

}
