
package system.piece;

import java.util.ArrayList;
import java.util.List;

import system.board.Board;
import system.board.PlayerColour;
import system.move.Move;

public class Rook extends Piece {

	public Rook(int xPos, int yPos, PlayerColour player) {
		super(xPos, yPos, player);
		type = PieceType.Rook;
	}

	
	public List<Move> getPossibleMoves(Board board)
	{
		List<Move> possibleMoves = new ArrayList<Move>();
		getPossibleStraightMoves(possibleMoves, board);
		return possibleMoves;
	}
}
