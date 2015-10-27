package system.piece;

import java.util.List;

import system.board.Board;
import system.move.Move;
import system.move.Moves;

public interface PieceInterface {
	public Moves getPossibleMoves(Board board);
	//public List<Move> getPossibleMovesWithValue(Board board, int[] valueTable);
}
