package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
}
