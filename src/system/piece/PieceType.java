package system.piece;

public class PieceType {
	
	private static int PawnValue;
	private static int KnightValue;
	private static int BishopValue;
	private static int RookValue;
	private static int QueenValue;
	private static int KingValue;
	
	@Deprecated
	static private int kingSizeMoreThan = 0;
	@Deprecated
	static private int kingSizeLessThan = 0;
	
	static public void setPieceValues(int[] newValues)
	{
		PawnValue = newValues[0];
		KnightValue = newValues[1];
		BishopValue = newValues[2];
		RookValue = newValues[3];
		QueenValue = newValues[4];
		KingValue = newValues[5];
			
		kingSizeLessThan = newValues[5] + 2*newValues[4];
		kingSizeMoreThan = newValues[5] - 2*newValues[4];
	}
	
	static public boolean isMoveValueKingSize(int moveValue)
	{
		return (moveValue < kingSizeLessThan) && (moveValue > kingSizeMoreThan);
	}
	
	static public int getValue(@SuppressWarnings("rawtypes") Class pieceClass)
	{		
		if (pieceClass.equals(Pawn.class)) {
			return PawnValue;
		} else if (pieceClass.equals(Knight.class)) {
			return KnightValue;
		} else if (pieceClass.equals(Bishop.class)) {
			return BishopValue;
		} else if (pieceClass.equals(Rook.class)) {
			return RookValue;
		} else if (pieceClass.equals(Queen.class)) {
			return QueenValue;
		} else if (pieceClass.equals(King.class)) {
			return KingValue;
		}

		return -1;
	}
	
}
