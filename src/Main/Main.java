package main;

// http://www.pageresource.com/clipart/entertainment/games/chess/
// http://zetcode.com/tutorials/javaswingtutorial/


import gui.Gui;
import system.board.BoardWrapper;
import system.board.Player;

public class Main {
	
	
	//public static int String cmdMove;
	
	public static void main(String [] args) throws InterruptedException
	{
		
		startGame();
	}
	
	public static void startGame() throws InterruptedException
	{
		BoardWrapper bord = new BoardWrapper();
		Gui gui = new Gui();

		Thread threadBoard = new Thread(bord, "t-board");
		Thread threadGui = new Thread(gui, "t-gui");
		
		System.out.println("MAIN: Tr�dar skapade");
		
		threadBoard.start();
		System.out.println("MAIN: Tr�d board startad");
		
		threadGui.start();
		System.out.println("MAIN: Tr�d gui startad, v�nta p� avslut");
		
		threadBoard.join();
		System.out.println("MAIN: Tr�d board joinad");
		
		threadGui.join();
		System.out.println("MAIN: Tr�d Gui joinad");
		System.out.println("MAIN: Programmet avslutas");
		
		//Test test01 = new Test(new Player("Herr vit"), new Player("Herr svart"));
		//System.out.println("Resultat fr�n test01: " + (test01.getResult()? "Wohoo!": "Pipsv�ngen"));
	}
}
