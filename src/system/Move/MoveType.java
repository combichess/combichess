package system.move;


public enum MoveType {
	STANDARD,
	DOUBLE_PAWN_MOVE,
	KING_SIDE_EN_PASSANT,	// dessa är väl menlösa då det räcker med EN_PASSANT
	QUEEN_SIDE_EN_PASSANT,
	//LEFT_PAWN_TAKE, 	ta bort dessa, annars kan de överlappa promotion 
	//RIGHT_PAWN_TAKE,
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
}

/*
	public final static int PROMOTION = 0x10;		// kan ske samtidigt som Left och right pawn take, därför är 0x13 och 0x14 tillåten
	
	public final static int CHECK = 0x100;
	
	// spelavslut
	public final static int DRAW = 0x1000;
	public final static int CHECK_MATE = 0x2000;
	public final static int STALE_MATE = 0x3000;
	
	private int value;
	
	public MoveType(int value)
	{
		this.value = value;
	}
	
	public String toString()
	{
		String str = "";
		switch(value & 0xF)
		{
		case LEFT_EN_PASSANT:
			break;
		case RIGHT_EN_PASSANT:
			break;
		case LEFT_PAWN_TAKE:
			break;
		case RIGHT_PAWN_TAKE:
			break;
		case DOUBLE_PAWN_MOVE:
			break;
		case QUEEN_SIDE_CASTLING:
			str += "o-x-oo";
			break;
		case KING_SIDE_CASTLING:
			str += "o-x-ooo";
			break;
		case PROMOTION:
			break;
		}
		return str;
	}
}
*/
