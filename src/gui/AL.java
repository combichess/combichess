package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//exempelkod från:
//http://zetcode.com/tutorials/javaswingtutorial/firstprograms/

public class AL implements ActionListener{
	
	int xPos;
	int yPos;
	
	public AL(int x, int y) {
		xPos = x;
		yPos = y;
    }

	public void actionPerformed(ActionEvent arg0) {
		System.out.println("XPos: " + xPos + "\tyPos: " + yPos);
	}
}

