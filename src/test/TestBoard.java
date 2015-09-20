package test;

import system.board.Board;
import system.board.Player;
import system.board.PlayerColour;
import system.move.Move;
import system.piece.PieceType;

public class TestBoard extends Board{
	private Player black;
	private boolean result;
	private int milliSeconds;
	
	public TestBoard(Player white, Player black) 
	{
		super(white, black);
		this.black = black;
		this.milliSeconds= 0; 
		result = test01();
	}
		
	private boolean test01()
	{
			// ta tiden
		
		//Board bord = new Board(white, black);
		addPiece(4, 2, PlayerColour.Black, PieceType.King);
		addPiece(4, 3, PlayerColour.White, PieceType.Pawn);
		addPiece(2, 4, PlayerColour.Black, PieceType.Knight);
		addPiece(4, 5, PlayerColour.White, PieceType.Knight);
		addPiece(5, 5, PlayerColour.White, PieceType.King);
		addPiece(5, 7, PlayerColour.White, PieceType.Queen);
		
		
		// ser svart ett steg fram s� �r det b�st att ta h�sten.
		// ser svart tv� steg fram s� tar han bonden
		System.out.println(this);
		System.out.println((!fullTest()? "FAILED !!!": "passed") + " p� test 01.a");

		
		Move moveB = findBestMoveFor(black, 1);
		System.out.println(((moveB == null || moveB.getXmove() != 2 || moveB.getYmove() != 1)? "FAILED !!!": "passed") + " p� test 01.b\t" + moveB);

		moveB = findBestMoveFor(black, 2);
		System.out.println(((moveB == null || moveB.getXmove() != 2 || moveB.getYmove() != -1)? "FAILED !!!": "passed") + " p� test 01.c\t" + moveB);
		System.out.println((!fullTest()? "FAILED !!!": "passed") + " p� test 01.d");

		System.out.println(this);
			// I detta l�ge ska svart kunna se att den kan g�ra ett gaffelhot mot kung och dam.
			// Vit m�ste kunna r�nka ut att den d� m�ste v�lja att flytta kungen
			// D�rf�r �r r�tt svar: BNc5 -> d7
		moveB = findBestMoveFor(black, 3);
		System.out.println(((moveB == null || moveB.getXmove() != 1 || moveB.getYmove() != 2)? "FAILED !!!": "passed") + " p� test 01.e\t" + moveB);
		System.out.println();
		System.out.println((!fullTest()? "FAILED !!!": "passed") + " p� test 01.f");
		
		return true;
	}
	
	public boolean getResult()
	{
		return result;
	}
}
