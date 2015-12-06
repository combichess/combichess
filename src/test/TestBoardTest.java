package test;

import org.testng.annotations.*;

import system.board.PlayerColour;
import system.move.Move;
import system.piece.King;
import system.piece.Knight;
import system.piece.Pawn;
import system.piece.Queen;

public class TestBoardTest {

	private BoardTester testBoard = null;
	
	@BeforeMethod
	public void initObjects()
	{
		testBoard = new BoardTester();
		testBoard.clearAllData();
		testBoard.addPiece(4, 2, PlayerColour.Black, King.class, true);
		testBoard.addPiece(4, 3, PlayerColour.White, Pawn.class, true);
		testBoard.addPiece(2, 4, PlayerColour.Black, Knight.class, true);
		testBoard.addPiece(4, 5, PlayerColour.White, Knight.class, true);
		testBoard.addPiece(5, 5, PlayerColour.White, King.class, true);
		testBoard.addPiece(5, 7, PlayerColour.White, Queen.class, true);
	}
	
	
	/**
	 * ser svart ett steg fram så är det bäst att ta hästen.
	 * ser svart två steg fram så tar han bonden
	 */
	@Test
	public void test01() {
		Move moveB = testBoard.findBestMoveFor(PlayerColour.Black, 1);
		int dPos = moveB.getToPos() - moveB.getFromPos();
		assert(dPos == 2 + 1*8);
	}
	
	/**
	 * 
	 */
	@Test
	public void test02() {
		Move moveB = testBoard.findBestMoveFor(PlayerColour.Black, 2);
		int dPos = moveB.getToPos() - moveB.getFromPos();
		assert(dPos == 2 - 1*8);
	}
	
	/**
	 * I detta läge ska svart kunna se att den kan göra ett gaffelhot mot kung och dam.
	 * Vit måste kunna ränka ut att den då måste välja att flytta kungen
	 * Därför är rätt svar: BNc5 -> d7
	 */
	@Test
	public void test03() {
		Move moveB = testBoard.findBestMoveFor(PlayerColour.Black, 3);
		int dPos = moveB.getToPos() - moveB.getFromPos();
		assert(dPos == 1 + 2*8);
	}
	
	@AfterMethod
	public void destroy()
	{
		
	}
}

