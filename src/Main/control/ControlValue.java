package main.control;

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
	
	private int value;
	
	ControlValue(int value)
	{
		this.value = value & (PIECE_RANGE|WHITE|BLACK);
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
		int tf = value & (0x7);  
		return (tf<1 || tf > 6)? UNDEFINED: tf;
	}
}
