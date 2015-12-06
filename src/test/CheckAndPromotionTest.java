package test;

import org.testng.annotations.*;

import system.board.PlayerColour;
import system.move.Move;
import system.move.Moves;
import system.piece.King;
import system.piece.Pawn;
import system.piece.Piece;
import system.piece.PieceType;
import system.piece.Rook;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CheckAndPromotionTest {

	private BoardTester testBoard = null;
	private Piece blackPawn = null;
	private Piece whiteKing = null;
	
	//@BeforeTest
	@BeforeMethod
	public void initObjects()
	{
		testBoard = new BoardTester();
		testBoard.clearAllData();
		
		testBoard.addPiece(2, 1, PlayerColour.White, Pawn.class, false);
		testBoard.addPiece(3, 1, PlayerColour.White, Pawn.class, false);
		testBoard.addPiece(4, 1, PlayerColour.White, Pawn.class, false);
		testBoard.addPiece(5, 1, PlayerColour.White, Pawn.class, false);
		testBoard.addPiece(6, 1, PlayerColour.White, Pawn.class, false);
		testBoard.addPiece(7, 1, PlayerColour.White, Pawn.class, false);
		testBoard.addPiece(3, 0, PlayerColour.White, King.class, true);
		testBoard.addPiece(5, 0, PlayerColour.White, Rook.class, true);
		testBoard.addPiece(2, 5, PlayerColour.White, Rook.class, true);
		
		testBoard.addPiece(0, 1, PlayerColour.Black, Pawn.class, true);
		testBoard.addPiece(6, 7, PlayerColour.Black, King.class, true);
		testBoard.addPiece(7, 7, PlayerColour.Black, Rook.class, true);
		testBoard.addPiece(3, 6, PlayerColour.Black, Pawn.class, false);
		testBoard.addPiece(4, 6, PlayerColour.Black, Pawn.class, false);
		testBoard.addPiece(5, 6, PlayerColour.Black, Pawn.class, false);
		testBoard.addPiece(6, 6, PlayerColour.Black, Pawn.class, false);
		testBoard.addPiece(7, 6, PlayerColour.Black, Pawn.class, false);

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
		//print();
		
		assert(blackMove3.getMoveType().isPromotion());
		testBoard.commitMove_(blackMove3);
		//print();
		
		Moves allWhiteMoves = testBoard.getAllPossibleAllowedMovesFor(PlayerColour.White);
		assert(allWhiteMoves.size() == 0);	// == 0 means check mate or stale mate.
	}

	/**
	 * kolla så att man inte kan gå o sätta sig i schackat läge
	 */
	@Test
	public void testPromotionAndCheck02() {
		//print();
		//Moves moves = whiteKing.getPossibleMoves(testBoard);
		Moves moves = testBoard.getAllPossibleAllowedMovesFor(whiteKing.getPlayer()).getMovesByPiece(whiteKing);
		
		
		assert(moves.size() == 2);
		int xMove = (moves.get(0).getToPos()%7) - (moves.get(0).getFromPos()%7);
		Move moveLeft = moves.get(xMove == -1? 0: 1);
		testBoard.commitMove_(moveLeft);
		//print();


		moves = testBoard.getAllPossibleAllowedMovesFor(whiteKing.getPlayer()).getMovesByPiece(whiteKing);
		assert(moves.size() == 2);
		xMove = (moves.get(0).getToPos()%7) - (moves.get(0).getFromPos()%7);
		moveLeft = moves.get(xMove == -1? 0: 1);
		testBoard.commitMove_(moveLeft);
		//print();


		moves = blackPawn.getPossibleMoves(testBoard);
		Move promotionMove = moves.getRandomMove();
		testBoard.commitMove_(promotionMove);
		//print();
		
		moves = testBoard.getAllPossibleAllowedMovesFor(PlayerColour.White);
		assert(moves.size() == 3);
		
		Moves whiteKingMoves = moves.getMovesByPiece(whiteKing);
		assert(whiteKingMoves.size() == 2);
	}
	
	 
	@AfterTest
	public void destroy()
	{
	}
}
