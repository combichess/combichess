package test;

//import static org.junit.Assert.*;

import org.testng.annotations.*;

import java.util.List;

import system.board.PlayerColour;
import system.move.Move;
import system.move.Moves;
import system.piece.Piece;
import system.piece.PieceType;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CheckAndPromotionTest {

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
	private static boolean setupIsExecuted = false;
	
	private static boolean printStatus = true;
	
	@BeforeTest
	public void initObjects()
	{
		if (!setupIsExecuted) {
			testBoard = new BoardTester();
			testBoard.clearAllData();
			
			testBoard.addPiece(2, 1, PlayerColour.White, PieceType.Pawn, false);
			testBoard.addPiece(3, 1, PlayerColour.White, PieceType.Pawn, false);
			testBoard.addPiece(4, 1, PlayerColour.White, PieceType.Pawn, false);
			testBoard.addPiece(5, 1, PlayerColour.White, PieceType.Pawn, false);
			testBoard.addPiece(6, 1, PlayerColour.White, PieceType.Pawn, false);
			testBoard.addPiece(7, 1, PlayerColour.White, PieceType.Pawn, false);
			testBoard.addPiece(3, 0, PlayerColour.White, PieceType.King, true);
			testBoard.addPiece(5, 0, PlayerColour.White, PieceType.Rook, true);
			testBoard.addPiece(2, 5, PlayerColour.White, PieceType.Rook, true);
			
			testBoard.addPiece(0, 1, PlayerColour.Black, PieceType.Pawn, true);
			testBoard.addPiece(6, 7, PlayerColour.Black, PieceType.King, true);
			testBoard.addPiece(7, 7, PlayerColour.Black, PieceType.Rook, true);
			testBoard.addPiece(3, 6, PlayerColour.Black, PieceType.Pawn, false);
			testBoard.addPiece(4, 6, PlayerColour.Black, PieceType.Pawn, false);
			testBoard.addPiece(5, 6, PlayerColour.Black, PieceType.Pawn, false);
			testBoard.addPiece(6, 6, PlayerColour.Black, PieceType.Pawn, false);
			testBoard.addPiece(7, 6, PlayerColour.Black, PieceType.Pawn, false);

			PieceType.setPieceValues(new int[] {1, 3, 4, 5, 9, 100});
			setupIsExecuted = true;
		}
	}
	
	
	/**
	 * kolla möjliga drag från B2, B3 och B4 ska existera, flytta till B4
	 */
	@Test
	public void testPromotionAndCheck01() {
		
		Move blackMove3 = testBoard.findBestMoveFor(PlayerColour.Black, 3);
		Move whiteMove3 = testBoard.findBestMoveFor(PlayerColour.White, 3);
		//Move blackMove4 = testBoard.findBestMoveFor(PlayerColour.Black, 4);
		//Move whiteMove4 = testBoard.findBestMoveFor(PlayerColour.White, 4);
		//Move blackMove5 = testBoard.findBestMoveFor(PlayerColour.Black, 5);
		//Move whiteMove5 = testBoard.findBestMoveFor(PlayerColour.White, 5);

		System.out.println("BlackMove(3): " + blackMove3);
		System.out.println("WhiteMove(3): " + whiteMove3);
		/*System.out.println("BlackMove(4): " + blackMove4);
		System.out.println("WhiteMove(4): " + whiteMove4);
		System.out.println("BlackMove(5): " + blackMove5);
		System.out.println("WhiteMove(5): " + whiteMove5);*/

		print();

		System.out.println("WhiteMove(3): " + whiteMove3);
		testBoard.commitMove_(blackMove3);
		
		blackMove3 = testBoard.findBestMoveFor(PlayerColour.Black, 3);
		
		//assert(blackMove5.getMoveType().isPromotion());
		System.out.println("black move is promotion: " + blackMove3.getMoveType().isPromotion());
		print();
		
		//List<Integer> blackMoves = testBoard.getPossibleMoves(PlayerColour.Black);
		List<Move> blackMoves = testBoard.getPieceOnSquare(0,  1).getPossibleMoves(testBoard);
		
		Move blackPromotionMove = null;
		for (Move move: blackMoves)
		{
			if (move.getMoveType().isPromotion())
				blackPromotionMove = move;
		}
		System.out.println("possible moves: " + blackPromotionMove);
		testBoard.commitMove_(blackMove3);
		assert(true);
	}

	/**
	 * kolla så att man inte kan gå o sätta sig i schackat läge
	 */
	@Test
	public void testPromotionAndCheck02() {
		Piece whiteKing = testBoard.getPieceOnSquare(3, 0);
		print();
		//Moves moves = whiteKing.getPossibleMoves(testBoard);
		Moves moves = testBoard.getAllPossibleAllowedMovesFor(whiteKing.getPlayer()).getMovesByPiece(whiteKing);
		
		
		assert(moves.size() == 2);
		Move moveLeft = moves.get(moves.get(0).getXmove() == -1? 0: 1);
		testBoard.commitMove_(moveLeft);
		print();
		
		//moves = whiteKing.getPossibleMoves(testBoard);		
		moves = testBoard.getAllPossibleAllowedMovesFor(whiteKing.getPlayer()).getMovesByPiece(whiteKing);
		assert(moves.size() == 2);
		moveLeft = moves.get(moves.get(0).getXmove() == -1? 0: 1);
		testBoard.commitMove_(moveLeft);
		print();
		
		//moves = whiteKing.getPossibleMoves(testBoard);
		moves = testBoard.getAllPossibleAllowedMovesFor(whiteKing.getPlayer()).getMovesByPiece(whiteKing);
		assert(moves.size() == 2);
		moveLeft = moves.get(moves.get(0).getXmove() == -1? 0: 1);
		testBoard.commitMove_(moveLeft);
		print();
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
