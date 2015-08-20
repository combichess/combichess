package piece;

import java.util.List;

import Board.Board;
import Move.Move;

public interface PieceInterface {
	public List<Move> getPossibleMoves(Board board);
	//public List<Move> getPossibleMovesWithValue(Board board, int[] valueTable);
}
