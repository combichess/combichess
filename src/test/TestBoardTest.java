package test;

/*import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;*/

import org.testng.annotations.*;

import system.board.PlayerColour;
import system.move.Move;
import system.piece.PieceType;

public class TestBoardTest {

	private BoardTester testBoard = null;
	
	@BeforeMethod
	public void initObjects()
	{
		testBoard = new BoardTester();
		testBoard.clearAllData();
		testBoard.addPiece(4, 2, PlayerColour.Black, PieceType.King, true);
		testBoard.addPiece(4, 3, PlayerColour.White, PieceType.Pawn, true);
		testBoard.addPiece(2, 4, PlayerColour.Black, PieceType.Knight, true);
		testBoard.addPiece(4, 5, PlayerColour.White, PieceType.Knight, true);
		testBoard.addPiece(5, 5, PlayerColour.White, PieceType.King, true);
		testBoard.addPiece(5, 7, PlayerColour.White, PieceType.Queen, true);
	}
	
	
	/**
	 * ser svart ett steg fram så är det bäst att ta hästen.
	 * ser svart två steg fram så tar han bonden
	 */
	@Test
	public void test01() {
		Move moveB = testBoard.findBestMoveFor(PlayerColour.Black, 1);
		assert(moveB.getXmove() == 2);
		assert(moveB.getYmove() == 1);
		//assert("One move forward thinking black, step x", 2, moveB.getXmove());
		//assertEquals("One move forward thinking black, step y", 1, moveB.getYmove());
	}
	
	/**
	 * 
	 */
	@Test
	public void test02() {
		Move moveB = testBoard.findBestMoveFor(PlayerColour.Black, 2);
		assert(moveB.getXmove() == 2);
		assert(moveB.getYmove() == -1);
		//assertEquals("Two move forward thinking black, step x", 2, moveB.getXmove());
		//assertEquals("Two move forward thinking black, step y", -1, moveB.getYmove());
	}
	
	/**
	 * I detta läge ska svart kunna se att den kan göra ett gaffelhot mot kung och dam.
	 * Vit måste kunna ränka ut att den då måste välja att flytta kungen
	 * Därför är rätt svar: BNc5 -> d7
	 */
	@Test
	public void test03() {
		Move moveB = testBoard.findBestMoveFor(PlayerColour.Black, 3);
		assert(moveB.getXmove() == 1);
		assert(moveB.getYmove() == 2);
		
		//assertEquals("Three move forward thinking black, step x", 1, moveB.getXmove());
		//assertEquals("Three move forward thinking black, step y", 2, moveB.getYmove());
	}
	
	@AfterMethod
	public void destroy()
	{
		
	}
}

