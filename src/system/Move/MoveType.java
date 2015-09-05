package system.move;


public class MoveType {
	public final static int STANDARD = 0;
	public final static int LEFT_EN_PASSANT = 0x1;
	public final static int RIGHT_EN_PASSANT = 0x2;
	public final static int LEFT_PAWN_TAKE = 0x3;
	public final static int RIGHT_PAWN_TAKE = 0x4;
	public final static int DOUBLE_PAWN_MOVE = 0x5;
	public final static int LEFT_CASTLING = 0x6;
	public final static int RIGHT_CASTLING = 0x7;
	
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
		case LEFT_CASTLING:
			str += "o-x-oo";
			break;
		case RIGHT_CASTLING:
			str += "o-x-ooo";
			break;
		case PROMOTION:
			break;
		}
		return str;
	}
}

