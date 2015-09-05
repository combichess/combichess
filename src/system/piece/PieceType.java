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
	
	static public void setPieceValues(int[] newValues)
	{
		for (int i=0; i<6; i++)
			valueTable[i] = newValues[i];
	}
	
	static public void unsetPieceValues()
	{
		setPieceValues(new int[] {0, 0, 0, 0, 0, 0});
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
	//static protected String descriptiveLetter;
	
	private PieceType(int value) {
		this.value = value;
	}
	
    /*public int getValue() {
        return value;
    }*/
	public int getValue()
	{
		return valueTable[value];
	}
	
}
