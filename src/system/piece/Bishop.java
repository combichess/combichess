package system.piece;

import java.util.ArrayList;
import java.util.List;

import system.board.Board;
import system.board.PlayerColour;
import system.move.Move;
import system.move.Moves;

public class Bishop extends Piece {

	public Bishop(int xPos, int yPos, PlayerColour player) {
		super(xPos, yPos, player);
		type = PieceType.Bishop;
	}


	public Moves getPossibleMoves(Board board)
	{
		Moves possibleMoves = new Moves();
		getPossibleDiagonalMoves(possibleMoves, board);
		return possibleMoves;
	}

}
