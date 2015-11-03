package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

	// dum klass. Används enbart för att åter få kontakt med Gui:et efter en knapptryckning, men fattar inte hur jag annars ska göra :( 
public class AL implements ActionListener{
	
	int buttonId;
	final Gui gui;
	
	public AL(int buttonId, final Gui gui) {
		this.buttonId = buttonId;
		this.gui = gui;
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.buttonIdClick(buttonId);
	}
}

