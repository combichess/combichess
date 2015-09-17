package main;

// http://www.pageresource.com/clipart/entertainment/games/chess/
// http://zetcode.com/tutorials/javaswingtutorial/

import gui.Gui3;
import system.board.BoardWrapper;
import system.board.Player;
import test.Test;

public class Main {
	
	
	//public static int String cmdMove;
	
	public static void main(String [] args) throws InterruptedException
	{	
		Player white = new Player("Herr Alm");
		Player black = new Player("Fru Oppo");
		
		
		BoardWrapper bord = new BoardWrapper(white, black);
		Gui3 gui = new Gui3();

		Thread threadBoard = new Thread(bord, "t-board");
		Thread threadGui = new Thread(gui, "t-gui");
		
		System.out.println("MAIN: Trådar skapade");
		
		threadBoard.start();
		System.out.println("MAIN: Tråd board startad");
		
		threadGui.start();
		System.out.println("MAIN: Tråd gui startad, vänta på avslut");
		
		threadBoard.join();
		System.out.println("MAIN: Tråd board joinad");
		
		threadGui.join();
		System.out.println("MAIN: Tråd Gui joinad");
		System.out.println("MAIN: Programmet avslutas");
		
		Test test01 = new Test(new Player("Herr vit"), new Player("Herr svart"));
		System.out.println("Resultat från test01: " + (test01.getResult()? "Wohoo!": "Pipsvängen"));
	}
}
