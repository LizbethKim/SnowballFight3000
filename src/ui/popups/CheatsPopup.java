package ui.popups;

import gameworld.game.client.ClientGame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ui.cheats.CheatSwitch;
import ui.cheats.Invincibility;
import ui.cheats.NightVision;
import ui.cheats.OneHitKill;
import ui.cheats.UnlimitedSnowballs;
import ui.cheats.UnlimitedSpeed;

/**
 * The CheatsPopup is a pop up window that displays a list of available cheats
 * and allows for toggling of those cheats on and off
 * 
 * @author Ryan Burnell, 300279172
 * 
 */
public class CheatsPopup extends JPanel {

	private JPanel actions;
	private JPanel switches;
	private ClientGame client;

	public CheatsPopup(ClientGame cl) {
		this.client = cl;
		setLayout(new FlowLayout());
		actions = new JPanel();
		switches = new JPanel();
		actions.setLayout(new GridLayout(0, 1));
		switches.setLayout(new GridLayout(0, 1));

		setupLabels();
		add(actions);
		add(switches);
	}

	public void showCheats() {
		JOptionPane.showMessageDialog(null, this, "Enable/Disable Cheats",
				JOptionPane.PLAIN_MESSAGE);
	}

	private void addCheat(String cheat, CheatSwitch c) {
		JLabel a = new JLabel(cheat);
		a.setPreferredSize(new Dimension(250, 25));
		a.setFont(new Font("Verdana", Font.BOLD, 18));
		actions.add(a);
		switches.add(c);
	}

	private void setupLabels() {
		addCheat("Invincibility", new Invincibility(client));
		addCheat("Unlimited Fire Rate", new UnlimitedSnowballs(client));
		addCheat("Unlimited Speed", new UnlimitedSpeed(client));
		addCheat("One Hit Kill", new OneHitKill(client));
		addCheat("Night Vision", new NightVision(client));
	}
}
