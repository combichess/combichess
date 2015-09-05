package system.piece;

import java.util.ArrayList;
import java.util.List;

import system.board.Board;
import system.board.PlayerColour;
import system.move.Move;

public class Queen extends Piece {

	public Queen(int xPos, int yPos, PlayerColour player) {
		super(xPos, yPos, player);
		type = PieceType.Queen;
	}

	public List<Move> getPossibleMoves(Board board)
	{
		List<Move> possibleMoves = new ArrayList<Move>();
		getPossibleDiagonalMoves(possibleMoves, board);
		getPossibleStraightMoves(possibleMoves, board);
		return possibleMoves;
	}
	
	
}
