package test;

import system.board.Board;
import system.board.Player;
import system.board.PlayerColour;
import system.move.Move;
import system.piece.PieceType;

public class Test {

	public Test() 
	{
		
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
		bord.addPiece(5, 7, PlayerColour.White, PieceType.Queen);
		
		
		// ser svart ett steg fram s� �r det b�st att ta h�sten.
		// ser svart tv� steg fram s� tar han bonden
		System.out.println(bord);
		System.out.println((!bord.fullTest()? "FAILED !!!": "passed") + " p� test 01.a");

		
		Move moveB = bord.findBestMoveFor(black, 1);
		System.out.println(((moveB == null || moveB.getXmove() != 2 || moveB.getYmove() != 1)? "FAILED !!!": "passed") + " p� test 01.b\t" + moveB);

		moveB = bord.findBestMoveFor(black, 2);
		System.out.println(((moveB == null || moveB.getXmove() != 2 || moveB.getYmove() != -1)? "FAILED !!!": "passed") + " p� test 01.c\t" + moveB);
		System.out.println((!bord.fullTest()? "FAILED !!!": "passed") + " p� test 01.d");

		System.out.println(bord);
			// I detta l�ge ska svart kunna se att den kan g�ra ett gaffelhot mot kung och dam.
			// Vit m�ste kunna r�nka ut att den d� m�ste v�lja att flytta kungen
			// D�rf�r �r r�tt svar: BNc5 -> d7
		moveB = bord.findBestMoveFor(black, 3);
		System.out.println(((moveB == null || moveB.getXmove() != 1 || moveB.getYmove() != 2)? "FAILED !!!": "passed") + " p� test 01.e\t" + moveB);
		System.out.println(bord);
		System.out.println((!bord.fullTest()? "FAILED !!!": "passed") + " p� test 01.f");
		
		return true;
	}
}
