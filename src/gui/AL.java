package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AL implements ActionListener{
	
	int buttonId;
	final Gui gui;
	
	public AL(int buttonId, final Gui gui) {
		this.buttonId = buttonId;
		this.gui = gui;
    }

	public void actionPerformed(ActionEvent arg0) {
		gui.buttonIdClick(buttonId);
	}
}

