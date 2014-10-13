package ui.popups;

import java.io.File;

import javax.swing.JFileChooser;

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
