package ui.popups;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import ui.gamewindow.UI;

public class InputPopup extends JPanel {
	private static final String blueTeam = "blue";
	private static final String redTeam = "red";

	private UI ui;

	private JTextField address;
	private JTextField name;
	private JRadioButton team1;
	private JRadioButton team2;
	private ButtonGroup teamChoice;

	public InputPopup(UI ui) {
		this.ui = ui;
		setLayout(new GridLayout(0, 2));
		setupPanel();
		showPopup();
	}

	private void setupPanel() {
		teamChoice = new ButtonGroup();

		team1 = new JRadioButton("Team Red");
		team1.setActionCommand(redTeam);
		team2 = new JRadioButton("Team Blue");
		team2.setActionCommand(blueTeam);
		// JPanel buttonPanel = new JPanel();
		// buttonPanel.setLayout(new );
		teamChoice.add(team1);
		teamChoice.add(team2);
		team1.setSelected(true);

		// buttonPanel.add(team1);
		// buttonPanel.add(team2);

		address = new JTextField("127.0.0.1");
		name = new JTextField("defaultname");

		add(new JLabel("Enter IP Address"));
		add(address);
		add(new JLabel("Enter Player Name"));
		add(name);
		add(team1);
		add(team2);

	}

	private boolean setupCompleted() {
		if (teamChoice.getSelection() == null) {
			return false;
		}
		if (address.getText() == null || address.getText().trim().length() == 0) {
			return false;
		}
		if (address.getText() == null || name.getText().trim().length() == 0) {
			return false;
		}
		return true;
	}

	private void showPopup() {
		int chosen = -1;
		// keep redisplaying dialog until cancelled or valid option chosen
		do {
			// dialog
			chosen = JOptionPane.showOptionDialog(ui, this,
					"Enter Name, IP Adress and Team",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.DEFAULT_OPTION,
					null, null, null);
		} while (!setupCompleted() && chosen != JOptionPane.CANCEL_OPTION);

		if (chosen == JOptionPane.OK_OPTION) {
			String playerName = name.getText();
			String ip = address.getText();

			if (teamChoice.getSelection().getActionCommand() == redTeam) {
				ui.startGame(playerName, ip, gameworld.world.Team.RED);
			} else {
				ui.startGame(playerName, ip, gameworld.world.Team.BLUE);
			}
		} else {
			// do nothing
		}
	}
}
