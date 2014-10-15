package ui.popups;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import ui.gamewindow.UI;

/**
 * The Input popup is responsible for getting and passing the inputs from the
 * user to the client including name, IP address of the desired server and the
 * team of the player
 * 
 * @author Ryan Burnell, 300279172
 * 
 */

public class InputPopup extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1238937909701994743L;
	private static final String blueTeam = "blue";
	private static final String redTeam = "red";

	private UI ui;

	private JTextField address;
	private JTextField name;
	private JRadioButton team1;
	private JRadioButton team2;
	private ButtonGroup teamChoice;
	private boolean loadGame;

	public InputPopup(UI ui, boolean loadGame) {
		this.ui = ui;
		this.loadGame = loadGame;
		setLayout(new GridLayout(0, 2));
		setupPanel();
		showPopup();
	}

	private void setupPanel() {
		address = new JTextField("127.0.0.1");
		add(new JLabel("Enter IP Address"));
		add(address);

		if (!loadGame) {
			teamChoice = new ButtonGroup();

			team1 = new JRadioButton("Team Red");
			team1.setActionCommand(redTeam);
			team2 = new JRadioButton("Team Blue");
			team2.setActionCommand(blueTeam);
			teamChoice.add(team1);
			teamChoice.add(team2);
			team1.setSelected(true);


			name = new JTextField("defaultname");

			add(new JLabel("Enter Player Name"));
			add(name);
			add(team1);
			add(team2);
		}

	}

	private boolean setupCompleted() {
		if (address.getText() == null || address.getText().trim().length() == 0) {
			return false;
		}
		if (!loadGame) {
			if (teamChoice.getSelection() == null) {
				return false;
			}
			if (address.getText() == null
					|| name.getText().trim().length() == 0) {
				return false;
			}
		}
		return true;
	}

	private void showPopup() {
		int chosen = -1;
		// keep redisplaying dialog until cancelled or valid option chosen
		do {
			// dialog
			chosen = JOptionPane.showOptionDialog(ui, this,
					"Enter Setup Information", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.DEFAULT_OPTION, null, null, null);
		} while (!setupCompleted() && chosen != JOptionPane.CANCEL_OPTION);

		if (chosen == JOptionPane.OK_OPTION) {
			String ip = address.getText();
			if (loadGame) {
				ui.setupClient(ip);
				ui.loadGame();
			} else {
				String playerName = name.getText();
				if (teamChoice.getSelection().getActionCommand() == redTeam) {
					ui.setupClient(playerName, ip, gameworld.world.Team.RED);
				} else {
					ui.setupClient(playerName, ip, gameworld.world.Team.BLUE);
				}
				ui.removeSetup();
				ui.startGame();
			}
		} else {
			// do nothing
		}
	}
}
