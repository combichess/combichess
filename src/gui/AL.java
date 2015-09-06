package gui;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;    
import javax.swing.JLabel;
import javax.swing.JPanel;

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

