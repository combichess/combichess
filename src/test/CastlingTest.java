package test;

import org.testng.annotations.*;


import system.board.PlayerColour;
import system.move.Move;
import system.move.MoveType;
import system.move.Moves;
import system.piece.Bishop;
import system.piece.King;
import system.piece.Knight;
import system.piece.Pawn;
import system.piece.Piece;
import system.piece.PieceType;
import system.piece.Rook;

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
		
		testBoard.addPiece(0, 0, PlayerColour.White, Rook.class, false);
		testBoard.addPiece(4, 0, PlayerColour.White, King.class, false);
		testBoard.addPiece(7, 0, PlayerColour.White, Rook.class, false);
		
		testBoard.addPiece(7, 5, PlayerColour.Black, Knight.class, true);
		testBoard.addPiece(2, 5, PlayerColour.Black, Knight.class, true);
		testBoard.addPiece(1, 5, PlayerColour.Black, Knight.class, true);
		testBoard.addPiece(4, 5, PlayerColour.Black, Bishop.class, true);
		
		testBoard.addPiece(6, 7, PlayerColour.Black, King.class, true);
		testBoard.addPiece(7, 7, PlayerColour.Black, Rook.class, true);
		testBoard.addPiece(3, 6, PlayerColour.Black, Pawn.class, false);
		testBoard.addPiece(4, 6, PlayerColour.Black, Pawn.class, false);
		testBoard.addPiece(5, 6, PlayerColour.Black, Pawn.class, false);
		testBoard.addPiece(6, 6, PlayerColour.Black, Pawn.class, false);
		testBoard.addPiece(7, 6, PlayerColour.Black, Pawn.class, false);

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
		String preBoardString = testBoard.toString();
		
		Moves moves = whiteKing.getPossibleMoves(testBoard);
		assert(moves.size() == 4);
		
		Move moveKingSide = moves.getMovesByMoveType(MoveType.KING_SIDE_CASTLING).get(0);
		

			// Commit kingside castling
		String preKingSideString = testBoard.toString();
		testBoard.commitMove_(moveKingSide);

		assert(kingSideRook.getPreviousMoveNumber() != Move.PREVIOUSLY_NEVER_MOVED);
		assert(whiteKing.getPreviousMoveNumber() != Move.PREVIOUSLY_NEVER_MOVED);
		
			// Uncommit previous castling
		testBoard.uncommit();
		assert(kingSideRook.getPreviousMoveNumber() == Move.PREVIOUSLY_NEVER_MOVED);
		assert(whiteKing.getPreviousMoveNumber() == Move.PREVIOUSLY_NEVER_MOVED);

		String postKingSideString = testBoard.toString();
		assert(preKingSideString.compareTo(postKingSideString) == 0);
		
			// commit queenside castling
		moves = whiteKing.getPossibleMoves(testBoard);
		assert(moves.size() == 4);
		Move queenSideCastling = moves.getMovesByMoveType(MoveType.QUEEN_SIDE_CASTLING).get(0);
		
		testBoard.commitMove_(queenSideCastling);

		assert(queenSideRook.getPreviousMoveNumber() != Move.PREVIOUSLY_NEVER_MOVED);
		assert(whiteKing.getPreviousMoveNumber() != Move.PREVIOUSLY_NEVER_MOVED);
		
			// Uncommit previous castling
		testBoard.uncommit();
		assert(queenSideRook.getPreviousMoveNumber() == Move.PREVIOUSLY_NEVER_MOVED);
		assert(whiteKing.getPreviousMoveNumber() == Move.PREVIOUSLY_NEVER_MOVED);

		
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
	
	@Test
	public void testCastling03() {
		Move kingMove = whiteKing.getPossibleMoves(testBoard).getMovesToPos(whiteKing.getPosition()-1).get(0);
		testBoard.commitMove_(kingMove);
		int numOfMoves = whiteKing.getPossibleMoves(testBoard).size();
		assert(numOfMoves == 2);
		
		kingMove = whiteKing.getPossibleMoves(testBoard).getMovesToPos(whiteKing.getPosition()+1).get(0);
		testBoard.commitMove_(kingMove);
		numOfMoves = whiteKing.getPossibleMoves(testBoard).size();
		assert(numOfMoves == 2);
		
		testBoard.uncommit();
		testBoard.uncommit();
		numOfMoves = whiteKing.getPossibleMoves(testBoard).size();
		assert(numOfMoves == 4);		
	}
	
	@AfterTest
	public void destroy()
	{
	}
}
