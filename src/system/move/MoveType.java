package system.move;


public enum MoveType {
	STANDARD,
	DOUBLE_PAWN_MOVE,
	KING_SIDE_EN_PASSANT,	// dessa är väl menlösa då det räcker med EN_PASSANT
	QUEEN_SIDE_EN_PASSANT,
	QUEEN_SIDE_CASTLING,
	KING_SIDE_CASTLING,
	PROMOTION,			// anger att möjlighet finns till promotion, detta värde returneras från pawn.getPossibleMoves(), men duger inte till board.commitMove() 
	PROMOTION_QUEEN, 
	PROMOTION_KNIGHT,
	PROMOTION_ROOK,
	PROMOTION_BISHOP;
	
	public boolean isPromotion()
	{
		switch(this)
		{
		case PROMOTION:
		case PROMOTION_QUEEN:
		case PROMOTION_BISHOP:
		case PROMOTION_KNIGHT:
		case PROMOTION_ROOK:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isCastling()
	{
		switch(this) {
		case QUEEN_SIDE_CASTLING:
		case KING_SIDE_CASTLING:
			return true;
		default:
			return false;
		}
	}
}
