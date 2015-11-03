package test;

//import static org.junit.Assert.*;

import org.testng.annotations.*;

import java.util.List;

import system.board.PlayerColour;
import system.move.Move;
import system.move.MoveType;
import system.piece.Piece;
import system.piece.PieceType;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
	private static Piece blackQueen = null;
	private static boolean setupIsExecuted = false;
	
	private static boolean printStatus = true;
	
	//@BeforeTest
	@BeforeClass
	public void initObjects()
	{
		if (!setupIsExecuted) {
			testBoard = new BoardTester();
			testBoard.clearAllData();
			testBoard.standardSetup();
			PieceType.setPieceValues(new int[] {1, 3, 4, 5, 9, 100});
			setupIsExecuted = true;
		}
	}
	
	
	/**
	 * kolla möjliga drag från B2, B3 och B4 ska existera, flytta till B4
	 */
	@Test(priority=1)
	public void testPawn01() {
		assert(PieceType.Pawn.getValue() == 1);
		
		whiteBPawn = testBoard.getPieceOnSquare(1, 1);
		List<Move> whitePawnMoveList = whiteBPawn.getPossibleMoves(testBoard);
		System.out.println(whitePawnMoveList);
		assert(whitePawnMoveList.size() == 2);
		//assertEquals("Vit bonde ska ha två flyttmöjligheter från b2", 2, whitePawnMoveList.size());
		
		Move move1, move2;
		
		if (whitePawnMoveList.get(0).getToPos() == 17) {
			move1 = whitePawnMoveList.get(0);
			move2 = whitePawnMoveList.get(1);
		} else {
			move1 = whitePawnMoveList.get(1);
			move2 = whitePawnMoveList.get(0);
		}
		
		//assertEquals("Vit bonde ska kunna flytta till b3", 1 + 2*8, move1.getToPos());
		//assertEquals("Vit bonde ska kunna flytta till b4", 1 + 3*8, move2.getToPos());
		assert(1 + 2*8 == move1.getToPos());
		assert(1 + 3*8 == move2.getToPos());
		
		testBoard.commitMove_(move2);
		
		print();
	}
	
	/**
	 * kolla möjliga drag från A7, A6 och A5 ska existera
	 */
	@Test(priority=2)
	public void testPawn02() {
		blackAPawn = testBoard.getPieceOnSquare(0, 6);
		List<Move> blackPawnMoveList = blackAPawn.getPossibleMoves(testBoard);
		System.out.println(blackPawnMoveList);
		//assertEquals("Svart bonde ska ha två flyttmöjligheter från b6", 2, blackPawnMoveList.size());
		assert(2 == blackPawnMoveList.size());
		
		Move move1, move2;
		
		if (blackPawnMoveList.get(0).getToPos() == 0 + 5*8) {
			move1 = blackPawnMoveList.get(0);
			move2 = blackPawnMoveList.get(1);
		} else {
			move1 = blackPawnMoveList.get(1);
			move2 = blackPawnMoveList.get(0);
		}
		
		//assertEquals("Vit bonde ska kunna flytta till b3", 0 + 5*8, move1.getToPos());
		//assertEquals("Vit bonde ska kunna flytta till b4", 0 + 4*8, move2.getToPos());
		assert(0 + 5*8 == move1.getToPos());
		assert(0 + 4*8 == move2.getToPos());
		
		testBoard.commitMove_(move2);
		
		print();
	}
	
	/**
	 * kolla möjliga drag från B4, B5 och A5 ska existera
	 * B4->B5        C7->C5
	 */
	@Test(priority=3)
	public void testPawn03() {
		List<Move> whitePawnMoveList = whiteBPawn.getPossibleMoves(testBoard);
		System.out.println(whitePawnMoveList);
		//assertEquals("Vit bonde ska ha två flyttmöjligheter från b4", 2, whitePawnMoveList.size());
		assert(2 == whitePawnMoveList.size());
		
		Move moveLeft, move1;
		if (whitePawnMoveList.get(0).getToPos() == 0 + 4*8) {
			moveLeft = whitePawnMoveList.get(0);
			move1 = whitePawnMoveList.get(1);
		} else {
			moveLeft = whitePawnMoveList.get(1);
			move1 = whitePawnMoveList.get(0);
		}
		
		//assertEquals("Vit bonde ska kunna flytta till a5", 0 + 4*8, moveLeft.getToPos());
		//assertEquals("Vit bonde ska kunna flytta till b5", 1 + 4*8, move1.getToPos());
		assert(0 + 4*8 == moveLeft.getToPos());
		assert(1 + 4*8 == move1.getToPos());
		
		testBoard.commitMove_(move1);
		
		blackCPawn = testBoard.getPieceOnSquare(2, 6);
		List<Move> blackPawnMoveList = blackCPawn.getPossibleMoves(testBoard);
		if (blackPawnMoveList.get(0).getToPos() == 2 + 4*8)
			testBoard.commitMove_(blackPawnMoveList.get(0));
		else
			testBoard.commitMove_(blackPawnMoveList.get(1));
		print();
	}
	
	/**
	 * kolla att svart B-bonde bara kan gå ett steg 
	 * B5->C6
	 * kolla att C5 inte existerar
	 */
	@Test(priority=4)
	public void testPawn04() {
		Piece blackBPawn = testBoard.getPieceOnSquare(1, 6);
		List<Move> blackPawnMoveList = blackBPawn.getPossibleMoves(testBoard);
		
		//assertEquals("Only one move for B-Pawn", 1, blackPawnMoveList.size());
		//assertEquals("Only to B6", 1+5*8, blackPawnMoveList.get(0).getToPos());
		
		assert(1 == blackPawnMoveList.size());
		assert(1+5*8 == blackPawnMoveList.get(0).getToPos());
		
		List<Move> whitePawnMoveList = whiteBPawn.getPossibleMoves(testBoard);
		System.out.println(whitePawnMoveList);
		//assertEquals("Vit bonde ska ha två flyttmöjligheter från b5", 2, whitePawnMoveList.size());
		assert(2 == whitePawnMoveList.size());
		
		Move moveEP, move1;
		if (whitePawnMoveList.get(0).getToPos() == 2 + 5*8) {
			moveEP = whitePawnMoveList.get(0);
			move1 = whitePawnMoveList.get(1);
		} else {
			moveEP = whitePawnMoveList.get(1);
			move1 = whitePawnMoveList.get(0);
		}
		
		//assertEquals("Vit bonde ska kunna utföra En Passant till C6", 2 + 5*8, moveEP.getToPos());
		//assertEquals("Vit bonde ska kunna flytta till b5", 1 + 5*8, move1.getToPos());
		assert(2 + 5*8 == moveEP.getToPos());
		assert(1 + 5*8 == move1.getToPos());

		print();
		testBoard.commitMove_(moveEP);
		//assertFalse("C-bonde är inaktiv", blackCPawn.getActivity());
		assert(!blackCPawn.getActivity());
		
		//blackCPawn = testBoard.getPieceOnSquare(2, 6);
		print();
		System.out.println("moveEP: " + moveEP);
		assert(moveEP.getValue() > 0);
		
		
		// kolla så att value av en-passant-draget är positivt 
	}
	

	/**
	 * uncommitMove
	 * kolla att uncommit fungerar för en-passant drag.
	 * 
	 */
	@Test(priority=5)
	public void testPawn05() {
		testBoard.uncommit();
		print();
		
		Piece blackCPawn2 = testBoard.getPieceOnSquare(2, 4);
		assert(blackCPawn2 != null);
		assert(blackCPawn2 == blackCPawn);
		assert(blackCPawn.getActivity());
		
		List<Move> whitePawnMoveList = whiteBPawn.getPossibleMoves(testBoard);
		Move EPmove = null;
		for (Move move: whitePawnMoveList)
			if (move.getMoveType() == MoveType.KING_SIDE_EN_PASSANT)
				EPmove = move;
		
		assert(EPmove != null);
		assert(EPmove.getToPos() == 2 + 5*8);
		testBoard.commitMove_(EPmove);
	}
	
	
	/**
	 * 
	 */
	@Test(priority=6)
	public void testPawn06()
	{
		blackQueen = testBoard.getPieceOnSquare(3, 7);
		assert(blackQueen != null);
		assert(blackQueen.getType() == PieceType.Queen);
		
		List<Move> queenMoves = blackQueen.getPossibleMoves(testBoard);
		Move moveToMake = null;
		for (Move move: queenMoves)
			if (move.getToPos() == 1 + 5*8)
				moveToMake = move;
		
		assert(queenMoves.size() == 2);
		assert(moveToMake != null);
		
		testBoard.commitMove_(moveToMake);
		
		List<Move> bPawnMoves = whiteBPawn.getPossibleMoves(testBoard);
		moveToMake = null;
		for (Move move: bPawnMoves)
			if (move.getToPos() == 1 + 6*8)
				moveToMake = move;
		assert(moveToMake != null);
		assert(bPawnMoves.size() == 3);
		
		testBoard.commitMove_(moveToMake);

		print();
	}
	
	/**
	 * Promote Pawn at C8
	 * Uncommit Move, check status
	 * Redo Promote move
	 */
	@Test(priority=7)
	public void testPawn07()
	{
		Move queenMove = new Move(blackQueen, null, 7, blackQueen.getY());
		testBoard.commitMove_(queenMove);
		List<Move> whiteMoves = whiteBPawn.getPossibleMoves(testBoard);
		Move moveToMake = null;
		for (Move move: whiteMoves)
			if (move.getToPos() == 2 + 7*8)
				moveToMake = move;
		Piece takenBlackBishop = moveToMake.getAffectedPiece();
		
		assert(moveToMake.getMoveType() == MoveType.PROMOTION);
		moveToMake.setPromotionType(MoveType.PROMOTION_KNIGHT);
		testBoard.commitMove_(moveToMake);
		
		Piece pieceAfterPromotion = testBoard.getPieceOnSquare(2, 7);
		assert(pieceAfterPromotion != null);
		assert(pieceAfterPromotion.getActivity() == true);
		assert(pieceAfterPromotion.getType() == PieceType.Knight);
		assert(takenBlackBishop.getActivity() == false);
		assert(whiteBPawn.getActivity() == false);
		print();
		
		testBoard.uncommit();
		
		assert(whiteBPawn.getActivity() == true);
		assert(whiteBPawn.getPosition() == 1 + 6*8);
		assert(takenBlackBishop.getActivity() == true);
		assert(takenBlackBishop.getPosition() == 2 + 7*8);
		assert(testBoard.getPieceOnSquare(2, 7) == takenBlackBishop);
		print();
		
		moveToMake.setPromotionType(MoveType.PROMOTION_QUEEN);
		assert(moveToMake.getMoveType() == MoveType.PROMOTION_QUEEN);
		testBoard.commitMove_(moveToMake);	// redo promotion
		assert(testBoard.getPieceOnSquare(2, 7).getType() == PieceType.Queen);
		print();
	}
	
	/**
	 * check that Black is mate
	 */
	@Test(priority=8)
	public void testPawn08()
	{
		List<Integer> blackMoves = testBoard.getPossibleMoves(PlayerColour.Black);
		assert(blackMoves.size() == 0);
	}
	
	private void print()
	{
		if (printStatus)
			System.out.println(testBoard.toString());
	}
	 
	@AfterTest
	public void destroy()
	{
	}
}
