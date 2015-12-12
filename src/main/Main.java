package main;

// http://www.pageresource.com/clipart/entertainment/games/chess/
// http://zetcode.com/tutorials/javaswingtutorial/

import gui.Gui;
import system.board.BoardWrapper;

public class Main {
	
	public static void main(String [] args) throws InterruptedException
	{
		BoardWrapper bord = new BoardWrapper();
		Gui gui = new Gui();

		Thread threadBoard = new Thread(bord, "t-board");
		threadBoard.start();
		gui.start();
		threadBoard.join();
	}
}
