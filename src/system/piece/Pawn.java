package system.piece;

import java.util.ArrayList;
import java.util.List;

import system.board.Board;
import system.board.PlayerColour;
import system.move.Move;

public class Pawn extends Piece {

	public Pawn(int xPos, int yPos, PlayerColour player) {
		super(xPos, yPos, player);
		type = PieceType.Pawn;	
	}

	@Override
	public List<Move> getPossibleMoves(Board board) {
		List<Move> nyLista = new ArrayList<Move>();
		
		 //Move(Piece pieceThatMoves, Piece affectedPiece, int toX, int toY) {
		int dy = (this.player == PlayerColour.White)? 1: -1;
		
		if (yPos+dy > 7 || yPos+dy < 0)
			return nyLista;
		
		Piece goToSquare;
		if (xPos > 0) {
			goToSquare = board.getPieceOnSquare(this.xPos - 1, this.yPos + dy);
			if (goToSquare != null && goToSquare.getPlayer() != player)
				nyLista.add(new Move(this, goToSquare, xPos - 1, yPos + dy, goToSquare.type.getValue()));
		}
		
		if (xPos<7) {
			goToSquare = board.getPieceOnSquare(this.xPos + 1, this.yPos + dy);
			if (goToSquare != null && goToSquare.getPlayer() != player)
				nyLista.add(new Move(this, goToSquare, xPos + 1, yPos + dy, goToSquare.type.getValue()));
		}
		
		goToSquare = board.getPieceOnSquare(this.xPos, this.yPos + dy);
		if (goToSquare == null) {
			nyLista.add(new Move(this, goToSquare, xPos, yPos + dy));
			
			if (numberOfMoves == 0)	// dubbeldrag framåt första draget
			{
				dy += dy;
				goToSquare = board.getPieceOnSquare(this.xPos, this.yPos + dy);
				nyLista.add(new Move(this, goToSquare, xPos, yPos + dy));
			}
		}
		
		return nyLista;
	}
}
