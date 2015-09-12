package main;

// http://www.pageresource.com/clipart/entertainment/games/chess/
// http://zetcode.com/tutorials/javaswingtutorial/

import gui.Gui3;
import system.board.Board;
import system.board.Player;
import test.Test;

public class Command {
	
	
	public static void main(String [] args)
	{		
		Gui3 gui = new Gui3();
		gui.run();
		
		Player white = new Player("Herr Alm");
		Player black = new Player("Herr Oppo");
		
		Board bord = new Board(white, black);
		
		
		Test.test01();
	}
	
	private static void init() {
		
	}
}
