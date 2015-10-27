package system.piece;

public enum PieceType {
	Pawn(0),
	Knight(1),
	Bishop(2),
	Rook(3),
	Queen(4),
	King(5);
	
	private final int value;
	
	static private int[] valueTable = new int[] {0, 0, 0, 0, 0, 0};
	static private int kingSizeMoreThan = 0;
	static private int kingSizeLessThan = 0;
	
	static public void setPieceValues(int[] newValues)
	{
		for (int i=0; i<6; i++)
			valueTable[i] = newValues[i];
		
		kingSizeLessThan = valueTable[5] + 2*valueTable[4];
		kingSizeMoreThan = valueTable[5] - 2*valueTable[4];
	}
	
	static public void unsetPieceValues()
	{
		setPieceValues(new int[] {0, 0, 0, 0, 0, 0});
		kingSizeMoreThan = 0;
		kingSizeLessThan = 0;
	}
	
	static public boolean isMoveValueKingSize(int moveValue)
	{
		return (moveValue < kingSizeLessThan) && (moveValue > kingSizeMoreThan);
	}
	
	public String getLetter()
	{
		String str = "";
		switch(this)
		{
		case Knight:
			str = "N";
			break;
		case Bishop:
			str = "B";
			break;
		case Rook:
			str = "R";
			break;
		case Queen:
			str = "Q";
			break;
		case King:
			str = "K";
			break;
		default:
			break;
		}
		return str;
	}
	
	private PieceType(int value) {
		this.value = value;
	}
	
	public int getValue()
	{
		return valueTable[value];
	}
	
}
