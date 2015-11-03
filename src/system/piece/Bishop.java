package system.piece;

import system.board.Board;
import system.board.PlayerColour;
import system.move.Moves;

public class Bishop extends Piece {

	public Bishop(int xPos, int yPos, PlayerColour player) {
		super(xPos, yPos, player);
		type = PieceType.Bishop;
	}


	@Override
	public Moves getPossibleMoves(Board board)
	{
		Moves possibleMoves = new Moves();
		getPossibleDiagonalMoves(possibleMoves, board);
		return possibleMoves;
	}

}
