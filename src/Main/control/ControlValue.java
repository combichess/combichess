package main.control;

// märklig klss egentligen, men skitsamma

public class ControlValue {
	public static int EMPTY = 1;
	public static int PAWN = 2;
	public static int KNIGHT = 3;
	public static int BISHOP = 4;
	public static int QUEEN = 5;
	public static int KING = 6;
	private static int PIECE_RANGE = 7;
	
	public static int UNDEFINED = 0x0;
	public static int WHITE = 0x8;
	public static int BLACK = 0x10;
	//private static int COLOUR_RANGE = 0x18; 
	
	public static int HORIZONTAL_POSITION = 0x100;
	public static int VERTICAL_POSITION = 0x1000;
	
	private static int HORIZONTAL_RANGE = 0x700;
	private static int VERTICAL_RANGE = 0x7000;
	
	private int value;
	
	ControlValue(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public int getColour()
	{
		return ((value&WHITE)>0? WHITE: ((value&BLACK)>0? BLACK: UNDEFINED));
	}
	
	public int getPiece()
	{
		int tf = value & PIECE_RANGE;  
		return (tf<1 || tf > 6)? UNDEFINED: tf;
	}
	
	public static int POS(int x, int y)
	{
		return x*HORIZONTAL_POSITION + y*VERTICAL_POSITION;
	}
	
	public int getHorizontalPosition() 
	{
		return (value*HORIZONTAL_RANGE) / HORIZONTAL_POSITION;
	}
	
	public int getVerticalPosition()
	{
		return (value*VERTICAL_RANGE) / VERTICAL_POSITION;
	}
}
