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
		
		// ser svart ett steg fram s� �r det b�st att ta h�sten.
		// ser svart tv� steg fram s� tar han bonden
		
		System.out.println(bord);
		
		Move moveB = bord.findBestMove(PlayerColour.Black, 2);
		System.out.println(bord);
		if (moveB == null)
		{
			System.out.println("moveB f�r inte vara null");
			return false;
		}
		
		if (moveB.getXmove() != 2 || moveB.getYmove() != 1){
			System.out.println("failed p� test 01.A");
		}

		System.out.println(bord);
		System.out.println(moveB);
		bord.commitMove(moveB);
		bord.uncommitLastMove();
		
		
		
		System.out.println(bord);
		
		return true;
	}
}
