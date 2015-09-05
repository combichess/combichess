package system.piece;

import java.util.List;

import system.board.Board;
import system.move.Move;

public interface PieceInterface {
	public List<Move> getPossibleMoves(Board board);
	//public List<Move> getPossibleMovesWithValue(Board board, int[] valueTable);
}
