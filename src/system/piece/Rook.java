package system.piece;

import system.board.Board;
import system.board.PlayerColour;
import system.move.Moves;

public class Rook extends Piece {

	public Rook(int xPos, int yPos, PlayerColour player) {
		super(xPos, yPos, player);
	}
	
	@Override
	public Moves getPossibleMoves(Board board)
	{
		Moves possibleMoves = new Moves();
		getPossibleStraightMoves(possibleMoves, board);
		return possibleMoves;
	}

	@Override
	public boolean isPieceThreateningPosition(int pos, Piece[] squares) {
		return super.isThreateningSquareStraight(pos, squares);
	}

	@Override
	public String getLetter() {
		return "R";
	}
}
