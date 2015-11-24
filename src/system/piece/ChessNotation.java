package system.piece;


// http://en.wikipedia.org/wiki/Chess_notation
// http://en.wikipedia.org/wiki/Descriptive_notation

public enum ChessNotation {
	ALGEBRAIC,
	LONG_ALGEBRAIC,
	FIGURINE_ALGEBRAIC,
	CONCISE_REVERSIBLE,
	SMITH,
	DESCRIPTIVE,
	COORDINATE,
	ICCF,
	MY_OWN_FULL_NOTATION;
	
	public String getCo(int x, int y)
	{
		return ((char)('a' + x)) + "" + (y+1);
	}
	
	public String getCo(int pos)
	{
		int x = pos%8;
		int y = pos/8;
		return ((char)('a' + x)) + "" + (y+1);
	}
}

/*
 * K = King
 * Q = Queen
 * N = Knight, Häst
 * 
 * B = Bishop, Löpare
 */