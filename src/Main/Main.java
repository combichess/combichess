package Main;

import piece.PieceType;
import Board.Board;
import Board.Player;
import Board.PlayerColour;
import Move.Move;

public class Main {


	public static void main(String [] args)
	{
		/*
		Player white = new Player("Herr Alm");
		Player black = new Player("Herr Oppo");

		
		Board bord = new Board(white, black);
		bord.standardSetup();		
		System.out.println(bord.toString());
		System.out.println("A");
		Move move = bord.findBestMove(PlayerColour.White, 1);

		System.out.println("B");
		System.out.println(move);*/
		
		
		test01();
	}
	
	
	
	public static boolean test01()
	{
		Player white = new Player("Herr Alm");
		Player black = new Player("Herr Oppo");
		Board bord = new Board(white, black);
		bord.addPiece(4, 2, PlayerColour.Black, PieceType.King);
		bord.addPiece(4, 3, PlayerColour.White, PieceType.Pawn);
		bord.addPiece(2, 4, PlayerColour.Black, PieceType.Knight);
		bord.addPiece(4, 5, PlayerColour.White, PieceType.Knight);
		bord.addPiece(5, 5, PlayerColour.White, PieceType.King);
		
		// ser svart ett steg fram så är det bäst att ta hästen.
		// ser svart två steg fram så tar han bonden
		System.out.println(bord);
		
		Move moveB = bord.findBestMove(PlayerColour.Black, 1);
		System.out.println(((moveB == null || moveB.getXmove() != 2 || moveB.getYmove() != 1)? "failed": "passed") + " på test 01.a\t" + moveB);

		moveB = bord.findBestMove(PlayerColour.Black, 2);
		System.out.println(((moveB == null || moveB.getXmove() != 2 || moveB.getYmove() != -1)? "failed": "passed") + " på test 01.b\t" + moveB);

		
		return true;
	}
}
