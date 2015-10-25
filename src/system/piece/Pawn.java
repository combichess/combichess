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
		int yPosPlus = yPos + dy;
		
			// ta bort denna
		if (yPosPlus > 7 || yPos+dy < 0)
			return nyLista;
		
		Piece goToSquare;
		
		List<Integer> xSteps = new ArrayList<>();
		if (xPos > 0)
			xSteps.add(-1);
		if (xPos < 7)
			xSteps.add(1);
		
		for (int dx: xSteps)
		{
			int xPosPlus = this.xPos + dx;
			
				// sidotagning för bonde
			goToSquare = board.getPieceOnSquare(xPosPlus, yPosPlus);
			if (goToSquare != null && goToSquare.player != player)
			{
				if (goToSquare.yPos == 0 || goToSquare.yPos == 7) {
					//nyLista.add(new Move(this, goToSquare, xPosPlus, yPosPlus, PieceType.Pawn.getValue(), MoveType.PROMOTION));	// Promotion
					nyLista.add(new Move(this, goToSquare, xPosPlus, yPosPlus, goToSquare.type.getValue(), MoveType.PROMOTION));	// Promotion
				} else {
					// nyLista.add(new Move(this, goToSquare, xPosPlus, yPosPlus, PieceType.Pawn.getValue()));
					nyLista.add(new Move(this, goToSquare, xPosPlus, yPosPlus, goToSquare.type.getValue()));
				}
			}
			
				// En passant tagning?
			if (this.yPos == (this.player == PlayerColour.White? 4: 5))
			{
				goToSquare = board.getPieceOnSquare(xPosPlus, yPosPlus);
				Piece takenSquare = board.getPieceOnSquare(xPosPlus, yPos);
				
					// activity för takenSquare behöver inte kontrolleras då man hämtar piece från board, och board har inte inaktiva pieces
				if (goToSquare == null && takenSquare != null && takenSquare.getPlayer() != this.player 
						&& takenSquare.getPreviousMoveNumber() == board.getMoveNumber() - 1 && takenSquare.getType() == PieceType.Pawn
						&& board.getLastCommittedMove().getMoveType() == MoveType.DOUBLE_PAWN_MOVE)
				{
					nyLista.add(new Move(this, takenSquare, xPosPlus, yPosPlus, PieceType.Pawn.getValue(), 
							dx>0? MoveType.KING_SIDE_EN_PASSANT: MoveType.QUEEN_SIDE_EN_PASSANT));
				}
			}
		}
		
		goToSquare = board.getPieceOnSquare(this.xPos, yPosPlus);
		if (goToSquare == null) {
			
			switch(yPos+dy) 
			{
			case 0:
			case 7:	// promotion
				nyLista.add(new Move(this, goToSquare, xPos, yPosPlus, PieceType.Queen.getValue() - PieceType.Pawn.getValue(), MoveType.PROMOTION_QUEEN));
				break;
			default:
				nyLista.add(new Move(this, goToSquare, xPos, yPosPlus));
				break;
			}
			
			
			if (previousMoveNumber == Move.PREVIOUSLY_NEVER_MOVED)	// dubbeldrag framåt första draget
			{
				// yPosPlus = yPos + 2*dy
				yPosPlus += dy;
				goToSquare = board.getPieceOnSquare(this.xPos, yPosPlus);
				if (goToSquare == null)
					nyLista.add(new Move(this, goToSquare, xPos, yPosPlus, MoveType.DOUBLE_PAWN_MOVE));
			}
		}
		
		return nyLista;
	}
}
