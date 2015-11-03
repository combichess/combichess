package test;

//import static org.junit.Assert.*;

import org.testng.annotations.*;

import java.util.List;

import system.board.PlayerColour;
import system.move.Move;
import system.move.MoveType;
import system.move.Moves;
import system.piece.Piece;
import system.piece.PieceType;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CheckAndPromotionTest {

	private BoardTester testBoard = null;
	private Piece blackPawn = null;
	private Piece whiteKing = null;
	private static boolean printStatus = true;
	
	//@BeforeTest
	@BeforeMethod
	public void initObjects()
	{
		System.out.println("kör init igen");
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
		blackPawn = testBoard.getPieceOnSquare(0, 1);
		whiteKing = testBoard.getPieceOnSquare(3, 0);
	}
	
	
	/**
	 * kolla möjliga drag från B2, B3 och B4 ska existera, flytta till B4
	 */
	@Test
	public void testPromotionAndCheck01() {
		
		Move blackMove3 = testBoard.findBestMoveFor(PlayerColour.Black, 3);
		Move whiteMove3 = testBoard.findBestMoveFor(PlayerColour.White, 3);
		print();
		
		System.out.println("BlackMove(3): " + blackMove3);
		System.out.println("WhiteMove(3): " + whiteMove3);
		System.out.println("black move is promotion: " + blackMove3.getMoveType().isPromotion());
		assert(blackMove3.getMoveType().isPromotion());
		testBoard.commitMove_(blackMove3);
		print();
		
		Moves allWhiteMoves = testBoard.getAllPossibleAllowedMovesFor(PlayerColour.White);
		assert(allWhiteMoves.size() == 0);	// == 0 means check mate or stale mate.
	}

	/**
	 * kolla så att man inte kan gå o sätta sig i schackat läge
	 */
	@Test
	public void testPromotionAndCheck02() {
		print();
		//Moves moves = whiteKing.getPossibleMoves(testBoard);
		Moves moves = testBoard.getAllPossibleAllowedMovesFor(whiteKing.getPlayer()).getMovesByPiece(whiteKing);
		
		
		assert(moves.size() == 2);
		Move moveLeft = moves.get(moves.get(0).getXmove() == -1? 0: 1);
		testBoard.commitMove_(moveLeft);
		print();


		moves = testBoard.getAllPossibleAllowedMovesFor(whiteKing.getPlayer()).getMovesByPiece(whiteKing);
		assert(moves.size() == 2);
		moveLeft = moves.get(moves.get(0).getXmove() == -1? 0: 1);
		testBoard.commitMove_(moveLeft);
		print();


		moves = blackPawn.getPossibleMoves(testBoard);
		Move promotionMove = moves.getRandomMove();
		testBoard.commitMove_(promotionMove);
		print();
		
		moves = testBoard.getAllPossibleAllowedMovesFor(PlayerColour.White);
		assert(moves.size() == 3);
		
		Moves whiteKingMoves = moves.getMovesByPiece(whiteKing);
		assert(whiteKingMoves.size() == 2);
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
