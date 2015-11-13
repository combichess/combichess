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
		queenSideRook = testBoard.getPieceOnSquare(0, 0);
		
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

		String preBoardString = testBoard.toString();
		
		Moves moves = whiteKing.getPossibleMoves(testBoard);
		assert(moves.size() == 4);
		System.out.println(moves);
		
		Move moveKingSide = moves.getMovesByMoveType(MoveType.KING_SIDE_CASTLING).get(0);
		
		System.out.println(moveKingSide);

			// Commit kingside castling
		String preKingSideString = testBoard.toString();
		testBoard.commitMove_(moveKingSide);
		print();
		assert(kingSideRook.getPreviousMoveNumber() != Move.PREVIOUSLY_NEVER_MOVED);
		assert(whiteKing.getPreviousMoveNumber() != Move.PREVIOUSLY_NEVER_MOVED);
		
			// Uncommit previous castling
		testBoard.uncommit();
		assert(kingSideRook.getPreviousMoveNumber() == Move.PREVIOUSLY_NEVER_MOVED);
		assert(whiteKing.getPreviousMoveNumber() == Move.PREVIOUSLY_NEVER_MOVED);
		print();
		String postKingSideString = testBoard.toString();
		assert(preKingSideString.compareTo(postKingSideString) == 0);
		
			// commit queenside castling
		moves = whiteKing.getPossibleMoves(testBoard);
		assert(moves.size() == 4);
		Move queenSideCastling = moves.getMovesByMoveType(MoveType.QUEEN_SIDE_CASTLING).get(0);
		
		testBoard.commitMove_(queenSideCastling);
		print();
		assert(queenSideRook.getPreviousMoveNumber() != Move.PREVIOUSLY_NEVER_MOVED);
		assert(whiteKing.getPreviousMoveNumber() != Move.PREVIOUSLY_NEVER_MOVED);
		
			// Uncommit previous castling
		testBoard.uncommit();
		assert(queenSideRook.getPreviousMoveNumber() == Move.PREVIOUSLY_NEVER_MOVED);
		assert(whiteKing.getPreviousMoveNumber() == Move.PREVIOUSLY_NEVER_MOVED);
		print();
		
		String postBoardString = testBoard.toString();
		assert(preBoardString.compareTo(postBoardString) == 0);
	}


	@Test
	public void testCastling02() {
		Moves kingMoves;
		boolean kingSideMoveExist;
		boolean queenSideMoveExist;

		/*		BNb6 -> BNc4 -> BNd2
		White kan bara göra queenside castling*/
		Move move = blackBKnight.getPossibleMoves(testBoard).getMovesToPos("C4").get(0);
		testBoard.commitMove_(move);
		move = blackBKnight.getPossibleMoves(testBoard).getMovesToPos("D2").get(0);
		testBoard.commitMove_(move);
		kingMoves = whiteKing.getPossibleMoves(testBoard);
		kingSideMoveExist = kingMoves.getMovesByMoveType(MoveType.KING_SIDE_CASTLING).size() > 0;
		queenSideMoveExist = kingMoves.getMovesByMoveType(MoveType.QUEEN_SIDE_CASTLING).size() > 0;
		assert(!kingSideMoveExist && queenSideMoveExist);
		testBoard.uncommit();
		testBoard.uncommit();

		/*		BNc6 -> BNd4 -> BNe2
		inga castlings*/
		move = blackCKnight.getPossibleMoves(testBoard).getMovesToPos("D4").get(0);
		testBoard.commitMove_(move);
		move = blackCKnight.getPossibleMoves(testBoard).getMovesToPos("E2").get(0);
		testBoard.commitMove_(move);
		kingMoves = whiteKing.getPossibleMoves(testBoard);
		kingSideMoveExist = kingMoves.getMovesByMoveType(MoveType.KING_SIDE_CASTLING).size() > 0;
		queenSideMoveExist = kingMoves.getMovesByMoveType(MoveType.QUEEN_SIDE_CASTLING).size() > 0;
		assert(!kingSideMoveExist && !queenSideMoveExist);
		testBoard.uncommit();
		testBoard.uncommit();


		/*BBe6 -> BBa1
		White kan bara göra kingside castling*/
		move = blackEBishop.getPossibleMoves(testBoard).getMovesToPos("a2").get(0);
		testBoard.commitMove_(move);
		kingMoves = whiteKing.getPossibleMoves(testBoard);
		kingSideMoveExist = kingMoves.getMovesByMoveType(MoveType.KING_SIDE_CASTLING).size() > 0;
		queenSideMoveExist = kingMoves.getMovesByMoveType(MoveType.QUEEN_SIDE_CASTLING).size() > 0;
		assert(kingSideMoveExist && queenSideMoveExist);
		testBoard.uncommit();

		/*BNh6 -> BNg4 -> BNf2
		White kan bara göra kingside castling*/
		move = blackHKnight.getPossibleMoves(testBoard).getMovesToPos("G4").get(0);
		testBoard.commitMove_(move);
		move = blackHKnight.getPossibleMoves(testBoard).getMovesToPos("F2").get(0);
		testBoard.commitMove_(move);
		kingMoves = whiteKing.getPossibleMoves(testBoard);
		kingSideMoveExist = kingMoves.getMovesByMoveType(MoveType.KING_SIDE_CASTLING).size() > 0;
		queenSideMoveExist = kingMoves.getMovesByMoveType(MoveType.QUEEN_SIDE_CASTLING).size() > 0;
		assert(kingSideMoveExist && !queenSideMoveExist);
		testBoard.uncommit();
		testBoard.uncommit();
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
