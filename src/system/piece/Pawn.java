package system.piece;

import java.util.ArrayList;
import java.util.List;

import system.board.Board;
import system.board.PlayerColour;
import system.move.Move;
import system.move.MoveType;

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
		
			// ta bort denna
		if (yPos+dy > 7 || yPos+dy < 0)
			return nyLista;
		
		Piece goToSquare;
		
		List<Integer> xSteps = new ArrayList<>();
		if (xPos > 0)
			xSteps.add(-1);
		if (xPos < 7)
			xSteps.add(1);
		
		for (int dx: xSteps)
		{
				// sidotagning för bonde
			goToSquare = board.getPieceOnSquare(this.xPos + dx, this.yPos + dy);
			if (goToSquare != null && goToSquare.getPlayer() != player)
			{
				if (goToSquare.getY() == 0 || goToSquare.getY() == 7)
					nyLista.add(new Move(this, goToSquare, xPos + dx, yPos + dy, PieceType.Pawn.getValue(), MoveType.PROMOTION));	// Promotion
				else
					nyLista.add(new Move(this, goToSquare, xPos + dx, yPos + dy, PieceType.Pawn.getValue()));
			}
			
				// En passant tagning?
			if (this.yPos == (this.player == PlayerColour.White? 4: 5))
			{
				goToSquare = board.getPieceOnSquare(xPos + dx, yPos + dy);
				Piece takenSquare = board.getPieceOnSquare(xPos + dx, yPos);
				
					// activity för takenSquare behöver inte kontrolleras då man hämtar piece från board, och board har inte inaktiva pieces
				if (goToSquare == null && takenSquare != null && takenSquare.getPlayer() != this.player 
						&& takenSquare.getPreviousMoveNumber() == board.getMoveNumber() - 1 && takenSquare.getType() == PieceType.Pawn
						&& board.getLastCommittedMove().getMoveType() == MoveType.DOUBLE_PAWN_MOVE)
				{
					nyLista.add(new Move(this, takenSquare, xPos + dx, yPos + dy, PieceType.Pawn.getValue(), 
							dx>0? MoveType.KING_SIDE_EN_PASSANT: MoveType.QUEEN_SIDE_EN_PASSANT));
				}
			}		
		}
		
		goToSquare = board.getPieceOnSquare(this.xPos, this.yPos + dy);
		if (goToSquare == null) {
			nyLista.add(new Move(this, goToSquare, xPos, yPos + dy));
			
			if (previousMoveNumber == Move.PREVIOUSLY_NEVER_MOVED)	// dubbeldrag framåt första draget
			{
				dy += dy;
				goToSquare = board.getPieceOnSquare(this.xPos, this.yPos + dy);
				if (goToSquare == null)
					nyLista.add(new Move(this, goToSquare, xPos, yPos + dy, MoveType.DOUBLE_PAWN_MOVE));
			}
		}
		
		return nyLista;
	}
}
