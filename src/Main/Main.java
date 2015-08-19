package Main;

import Board.Board;
import Board.Player;
import Board.PlayerColour;
import Move.Move;

public class Main {


	public static void main(String [] args)
	{
		Player white = new Player("Herr Alm", PlayerColour.White);
		Player black = new Player("Herr Oppo", PlayerColour.Black);
		
		Board bord = new Board(white, black);
		
		bord.standardSetup();
		
		System.out.println(bord.toString());
		
		//List<Move> moves = bord.getAllPossibleMovesFor(PlayerColour.Black);
		System.out.println("A");
		Move move = bord.findBestMove(PlayerColour.White, 1);

		System.out.println("B");
		System.out.println(move);
	}
}
