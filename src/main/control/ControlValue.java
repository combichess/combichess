package main.control;

// märklig klss egentligen, men skitsamma

public class ControlValue {
	public final static char PAWN = 'P';
	public final static char KNIGHT = 'N';
	public final static char BISHOP = 'B';
	public final static char ROOK = 'R';
	public final static char QUEEN = 'Q';
	public final static char KING = 'K';
	
	public final static char UNDEFINED = ' ';
	public final static char WHITE = 'W';
	public final static char BLACK = 'B';
	
	private String value;
	
	public ControlValue(String value)
	{
		if (value.length() != 2) {
			this.value = "  ";
			return;
		}
		
		value = value.toUpperCase();
		char first = value.charAt(0);
		char second = value.charAt(1);

		switch (first)
		{
		case WHITE:
		case BLACK:
			break;
		default:
			first = UNDEFINED;
		}
		
		switch (second)
		{
		case PAWN:
		case KNIGHT:
		case BISHOP:
		case ROOK:
		case QUEEN:
		case KING:
			break;
		default:
			second = UNDEFINED; 
			break;
		}
		
		this.value = first + "" + second;
	}
	
	public String getColour()
	{
		return (value.length()<2)? null: value.substring(0, 1);
	}
	
	public String getPiece()
	{
		return (value.length()<2)? null: value.substring(1, 2);
	}
	
	public String toString()
	{
		return value;
	}
}
