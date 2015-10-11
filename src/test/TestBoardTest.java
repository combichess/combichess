package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import system.board.Board;
import system.board.PlayerColour;
import system.move.Move;
import system.piece.PieceType;

public class TestBoardTest {

	private BoardTester testBoard = null;
	
	@Before
	public void initObjects()
	{
		testBoard = new BoardTester();
		testBoard.clearAllData();
		testBoard.addPiece(4, 2, PlayerColour.Black, PieceType.King);
		testBoard.addPiece(4, 3, PlayerColour.White, PieceType.Pawn);
		testBoard.addPiece(2, 4, PlayerColour.Black, PieceType.Knight);
		testBoard.addPiece(4, 5, PlayerColour.White, PieceType.Knight);
		testBoard.addPiece(5, 5, PlayerColour.White, PieceType.King);
		testBoard.addPiece(5, 7, PlayerColour.White, PieceType.Queen);
	}
	
	
	/**
	 * ser svart ett steg fram så är det bäst att ta hästen.
	 * ser svart två steg fram så tar han bonden
	 */
	@Test
	public void test01() {
		Move moveB = testBoard.findBestMoveFor(PlayerColour.Black, 1);
		assertEquals("One move forward thinking black, step x", 2, moveB.getXmove());
		assertEquals("One move forward thinking black, step y", 1, moveB.getYmove());
	}
	
	/**
	 * 
	 */
	@Test
	public void test02() {
		Move moveB = testBoard.findBestMoveFor(PlayerColour.Black, 2);
		assertEquals("Two move forward thinking black, step x", 2, moveB.getXmove());
		assertEquals("Two move forward thinking black, step y", -1, moveB.getYmove());
	}
	
	/**
	 * I detta läge ska svart kunna se att den kan göra ett gaffelhot mot kung och dam.
	 * Vit måste kunna ränka ut att den då måste välja att flytta kungen
	 * Därför är rätt svar: BNc5 -> d7
	 */
	@Test
	public void test03() {
		Move moveB = testBoard.findBestMoveFor(PlayerColour.Black, 3);
		assertEquals("Three move forward thinking black, step x", 1, moveB.getXmove());
		assertEquals("Three move forward thinking black, step y", 2, moveB.getYmove());
	}
	
	@After
	public void destroy()
	{
		
	}
	
	
	
		// overriding Board to test Board
	public class BoardTester extends Board
	{
		BoardTester() {
			super();
			
		}
		
		public void clearAllData()
		{
			clearSetup();
		}
		
		public void addPiece(int xPos, int yPos, PlayerColour player, PieceType type)
		{
			super.addPiece(xPos, yPos, player, type);
		}
		
		public Move findBestMoveFor(PlayerColour col, int N)
		{
			return super.findBestMoveFor(col, N);
		}
	}
}


/*
 * 	// ta tiden
		
		//Board bord = new Board(white, black);
		addPiece(4, 2, PlayerColour.Black, PieceType.King);
		addPiece(4, 3, PlayerColour.White, PieceType.Pawn);
		addPiece(2, 4, PlayerColour.Black, PieceType.Knight);
		addPiece(4, 5, PlayerColour.White, PieceType.Knight);
		addPiece(5, 5, PlayerColour.White, PieceType.King);
		addPiece(5, 7, PlayerColour.White, PieceType.Queen);
		
		
		// ser svart ett steg fram så är det bäst att ta hästen.
		// ser svart två steg fram så tar han bonden
		System.out.println(this);
		System.out.println((!fullTest()? "FAILED !!!": "passed") + " på test 01.a");

		
		Move moveB = findBestMoveFor(black, 1);
		System.out.println(((moveB == null || moveB.getXmove() != 2 || moveB.getYmove() != 1)? "FAILED !!!": "passed") + " på test 01.b\t" + moveB);

		moveB = findBestMoveFor(black, 2);
		System.out.println(((moveB == null || moveB.getXmove() != 2 || moveB.getYmove() != -1)? "FAILED !!!": "passed") + " på test 01.c\t" + moveB);
		System.out.println((!fullTest()? "FAILED !!!": "passed") + " på test 01.d");

		System.out.println(this);
			// I detta läge ska svart kunna se att den kan göra ett gaffelhot mot kung och dam.
			// Vit måste kunna ränka ut att den då måste välja att flytta kungen
			// Därför är rätt svar: BNc5 -> d7
		moveB = findBestMoveFor(black, 3);
		System.out.println(((moveB == null || moveB.getXmove() != 1 || moveB.getYmove() != 2)? "FAILED !!!": "passed") + " på test 01.e\t" + moveB);
		System.out.println();
		System.out.println((!fullTest()? "FAILED !!!": "passed") + " på test 01.f");
		
		return true;
 * */
