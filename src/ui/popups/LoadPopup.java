package ui.popups;

import java.io.File;

import javax.swing.JFileChooser;

/**
 * The load popup displays a dialog for loading a saved game, and prompts the
 * user to select a file
 * 
 * @author Ryan Burnell, 300279172
 */

public class LoadPopup {
	public static File loadFile() {
		JFileChooser fileChooser = new JFileChooser();
		int chosen = JFileChooser.ERROR_OPTION;
		while (chosen != JFileChooser.APPROVE_OPTION) {
			chosen = fileChooser.showOpenDialog(null);
		}
		return fileChooser.getSelectedFile();
	}

}
