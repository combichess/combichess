package system.piece;

import system.board.Board;
import system.move.Moves;

/**
 * Does not take en passant move in concern because it only affects pawns
 * @param pos
 * @param squares
 * @return
 */
public interface PieceInterface {
	public Moves getPossibleMoves(Board board);
	public boolean isPieceThreateningPosition(int pos, Piece[] squares);
	//public List<Move> getPossibleMovesWithValue(Board board, int[] valueTable);
}
