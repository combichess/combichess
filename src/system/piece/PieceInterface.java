package system.piece;

import system.board.Board;
import system.move.Moves;

public interface PieceInterface {
	public Moves getPossibleMoves(Board board);
	//public List<Move> getPossibleMovesWithValue(Board board, int[] valueTable);
}
