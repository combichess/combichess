package system.piece;


import system.board.Board;
import system.board.PlayerColour;
import system.move.Moves;

public class Queen extends Piece {

	public Queen(int xPos, int yPos, PlayerColour player) {
		super(xPos, yPos, player);
	}

	@Override
	public Moves getPossibleMoves(Board board)
	{
		Moves possibleMoves = new Moves();
		getPossibleDiagonalMoves(possibleMoves, board);
		getPossibleStraightMoves(possibleMoves, board);
		return possibleMoves;
	}
	
	@Override
	public boolean isPieceThreateningPosition(int pos, Piece[] squares) {
		return (super.isThreateningSquareStraight(pos, squares) ||
				super.isThreateningSquareDiagonally(pos, squares)); 
	}

	@Override
	public String getLetter() {
		return "Q";
	}
	
	
}
