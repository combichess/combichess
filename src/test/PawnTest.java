package test;

import static org.junit.Assert.*;


import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import system.board.PlayerColour;
import system.move.Move;
import system.piece.Piece;
import system.piece.PieceType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PawnTest {

	/*
	 * Junit-test för bonden av schackbonden (pawn-tests, double step, en passant och uncommit)

	kolla möjliga drag från B2, B3 och B4 ska existera
	kolla möjliga drag från A7, A6 och A5 ska existera
	B2->B4        A7->A5
	kolla möjliga drag från B4, B5 och A5 ska existera
	B4->B5        C7->C5
	kolla möjliga drag från B5, B4 och C4 men inte A4 ska existera
	B5->C6
	kolla att C5 inte existerar
	uncommitMove
	kolla att B5 och C5 är identisk med tidigare.
	B5->C6        C8->A6
	C6->C7        A6->B5
	kolla att det finns 3 lägen gånger 4 möjligheter till promotion
	kolla så att det höga värden på dragen
	C7->C8        D8->xC8
	uncommit moves tillbaka till startposition och jämför med startposition
	 * */
	
	private static BoardTester testBoard = null;
	private static Piece whiteBPawn = null;
	private static Piece blackAPawn = null;
	private static Piece blackCPawn = null;
	private static boolean setupIsExecuted = false;
	
	@Before
	public void initObjects()
	{
		if (!setupIsExecuted) {
			testBoard = new BoardTester();
			testBoard.clearAllData();
			testBoard.standardSetup();
			setupIsExecuted = true;
		}
	}
	
	
	/**
	 * kolla möjliga drag från B2, B3 och B4 ska existera, flytta till B4
	 */
	@Test
	public void test01() {
		whiteBPawn = testBoard.getPieceOnSquare(1, 1);
		List<Move> whitePawnMoveList = whiteBPawn.getPossibleMoves(testBoard);
		System.out.println(whitePawnMoveList);
		assertEquals("Vit bonde ska ha två flyttmöjligheter från b2", 2, whitePawnMoveList.size());
		
		Move move1, move2;
		
		if (whitePawnMoveList.get(0).getToPos() == 17) {
			move1 = whitePawnMoveList.get(0);
			move2 = whitePawnMoveList.get(1);
		} else {
			move1 = whitePawnMoveList.get(1);
			move2 = whitePawnMoveList.get(0);
		}
		
		assertEquals("Vit bonde ska kunna flytta till b3", 1 + 2*8, move1.getToPos());
		assertEquals("Vit bonde ska kunna flytta till b4", 1 + 3*8, move2.getToPos());
		
		testBoard.commitMove_(move2);
		
		System.out.println(testBoard.toString());
	}
	
	/**
	 * kolla möjliga drag från A7, A6 och A5 ska existera
	 */
	@Test
	public void test02() {
		blackAPawn = testBoard.getPieceOnSquare(0, 6);
		List<Move> blackPawnMoveList = blackAPawn.getPossibleMoves(testBoard);
		System.out.println(blackPawnMoveList);
		assertEquals("Svart bonde ska ha två flyttmöjligheter från b6", 2, blackPawnMoveList.size());
		
		Move move1, move2;
		
		if (blackPawnMoveList.get(0).getToPos() == 0 + 5*8) {
			move1 = blackPawnMoveList.get(0);
			move2 = blackPawnMoveList.get(1);
		} else {
			move1 = blackPawnMoveList.get(1);
			move2 = blackPawnMoveList.get(0);
		}
		
		assertEquals("Vit bonde ska kunna flytta till b3", 0 + 5*8, move1.getToPos());
		assertEquals("Vit bonde ska kunna flytta till b4", 0 + 4*8, move2.getToPos());
		
		testBoard.commitMove_(move2);
		
		System.out.println(testBoard.toString());
	}
	
	/**
	 * kolla möjliga drag från B4, B5 och A5 ska existera
	 * B4->B5        C7->C5
	 */
	@Test
	public void test03() {
		List<Move> whitePawnMoveList = whiteBPawn.getPossibleMoves(testBoard);
		System.out.println(whitePawnMoveList);
		assertEquals("Vit bonde ska ha två flyttmöjligheter från b4", 2, whitePawnMoveList.size());
		
		Move moveLeft, move1;
		if (whitePawnMoveList.get(0).getToPos() == 0 + 4*8) {
			moveLeft = whitePawnMoveList.get(0);
			move1 = whitePawnMoveList.get(1);
		} else {
			moveLeft = whitePawnMoveList.get(1);
			move1 = whitePawnMoveList.get(0);
		}
		
		assertEquals("Vit bonde ska kunna flytta till a5", 0 + 4*8, moveLeft.getToPos());
		assertEquals("Vit bonde ska kunna flytta till b5", 1 + 4*8, move1.getToPos());
		
		testBoard.commitMove_(move1);
		
		blackCPawn = testBoard.getPieceOnSquare(2, 6);
		List<Move> blackPawnMoveList = blackCPawn.getPossibleMoves(testBoard);
		if (blackPawnMoveList.get(0).getToPos() == 2 + 4*8)
			testBoard.commitMove_(blackPawnMoveList.get(0));
		else
			testBoard.commitMove_(blackPawnMoveList.get(1));
		System.out.println(testBoard.toString());
	}
	
	/**
	 * kolla att svart B-bonde bara kan gå ett steg 
	 * B5->C6
	 * kolla att C5 inte existerar
	 * uncommitMove
	 * kolla att B5 och C5 är identisk med tidigare.
	 */
	@Test
	public void test04() {
		Piece blackBPawn = testBoard.getPieceOnSquare(1, 6);
		List<Move> blackPawnMoveList = blackBPawn.getPossibleMoves(testBoard);
		assertEquals("Only one move for B-Pawn", 1, blackPawnMoveList.size());
		assertEquals("Only to B6", 1+5*8, blackPawnMoveList.get(0).getToPos());
		
		List<Move> whitePawnMoveList = whiteBPawn.getPossibleMoves(testBoard);
		System.out.println(whitePawnMoveList);
		assertEquals("Vit bonde ska ha två flyttmöjligheter från b5", 2, whitePawnMoveList.size());
		
		Move moveEP, move1;
		if (whitePawnMoveList.get(0).getToPos() == 2 + 5*8) {
			moveEP = whitePawnMoveList.get(0);
			move1 = whitePawnMoveList.get(1);
		} else {
			moveEP = whitePawnMoveList.get(1);
			move1 = whitePawnMoveList.get(0);
		}
		
		assertEquals("Vit bonde ska kunna utföra En Passant till C6", 2 + 5*8, moveEP.getToPos());
		assertEquals("Vit bonde ska kunna flytta till b5", 1 + 5*8, move1.getToPos());
		
		testBoard.commitMove_(moveEP);
		assertFalse("C-bonde är inaktiv", blackCPawn.getActivity());
		
		blackCPawn = testBoard.getPieceOnSquare(2, 6);
		System.out.println(testBoard.toString());
	}
	
	
	
	@After
	public void destroy()
	{
	}
}
