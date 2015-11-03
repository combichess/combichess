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
public class CastlingTest {

	private BoardTester testBoard = null;
	private Piece whiteKing = null;
	private Piece queenSideRook = null;
	private Piece kingSideRook = null;
	
	private Piece blackBKnight = null;
	private Piece blackCKnight = null;
	private Piece blackEBishop = null;
	private Piece blackHKnight = null;
	
	private static boolean printStatus = true;
	
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
		
		testBoard.addPiece(0, 0, PlayerColour.White, PieceType.Rook, false);
		testBoard.addPiece(4, 0, PlayerColour.White, PieceType.King, false);
		testBoard.addPiece(7, 0, PlayerColour.White, PieceType.Rook, false);
		
		testBoard.addPiece(7, 5, PlayerColour.Black, PieceType.Knight, true);
		testBoard.addPiece(2, 5, PlayerColour.Black, PieceType.Knight, true);
		testBoard.addPiece(1, 5, PlayerColour.Black, PieceType.Knight, true);
		testBoard.addPiece(4, 5, PlayerColour.Black, PieceType.Bishop, true);
		
		testBoard.addPiece(6, 7, PlayerColour.Black, PieceType.King, true);
		testBoard.addPiece(7, 7, PlayerColour.Black, PieceType.Rook, true);
		testBoard.addPiece(3, 6, PlayerColour.Black, PieceType.Pawn, false);
		testBoard.addPiece(4, 6, PlayerColour.Black, PieceType.Pawn, false);
		testBoard.addPiece(5, 6, PlayerColour.Black, PieceType.Pawn, false);
		testBoard.addPiece(6, 6, PlayerColour.Black, PieceType.Pawn, false);
		testBoard.addPiece(7, 6, PlayerColour.Black, PieceType.Pawn, false);

		PieceType.setPieceValues(new int[] {1, 3, 4, 5, 9, 100});
		whiteKing = testBoard.getPieceOnSquare(4, 0);
		kingSideRook = testBoard.getPieceOnSquare(7, 0);
		queenSideRook = testBoard.getPieceOnSquare(7, 0);
		
		blackBKnight = testBoard.getPieceOnSquare(1, 5);
		blackCKnight = testBoard.getPieceOnSquare(2, 5);
		blackEBishop = testBoard.getPieceOnSquare(4, 5);
		blackHKnight = testBoard.getPieceOnSquare(7, 5);
	}
	
	
	/**
	 * Kolla att det ens är möjligt med promotion
	 * Kolla att moves innehåller två castlings
	 */
	@Test
	public void testCastling01() {
		print();
		Moves moves = whiteKing.getPossibleMoves(testBoard);
		System.out.println(moves);
		assert(moves.size() == 4);
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
