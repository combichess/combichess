
package piece;

import java.util.ArrayList;
import java.util.List;

import Board.Board;
import Board.PlayerColour;
import Move.Move;

public class Bishop extends Piece {

	public Bishop(int xPos, int yPos, PlayerColour player) {
		super(xPos, yPos, player);
		type = PieceType.Bishop;
	}


	public List<Move> getPossibleMoves(Board board)
	{
		List<Move> possibleMoves = new ArrayList<Move>();
		getPossibleDiagonalMoves(possibleMoves, board);
		return possibleMoves;
	}
	
	
}
