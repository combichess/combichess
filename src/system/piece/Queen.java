package system.piece;

import java.util.ArrayList;
import java.util.List;

import system.board.Board;
import system.board.PlayerColour;
import system.move.Move;
import system.move.Moves;

public class Queen extends Piece {

	public Queen(int xPos, int yPos, PlayerColour player) {
		super(xPos, yPos, player);
		type = PieceType.Queen;
	}

	public Moves getPossibleMoves(Board board)
	{
		Moves possibleMoves = new Moves();
		getPossibleDiagonalMoves(possibleMoves, board);
		getPossibleStraightMoves(possibleMoves, board);
		return possibleMoves;
	}
	
	
}
