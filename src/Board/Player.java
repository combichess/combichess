package Board;

import piece.PieceType;

public class Player {
	private String name;
	private PlayerColour colour;
	private int valueTable[];
	
	public Player(String name, PlayerColour colour)
	{
		this.name = name;
		this.colour = colour;
		valueTable = new int[] {1, 3, 3, 5, 9, 100};	// {bonde, häst, löpare, torn, dam, kingen är ovärderlig}
		// 		standardtabell används på wikipedia:
		// http://en.wikipedia.org/wiki/Chess_piece_relative_value#Standard_valuations	
	}
	
	public Player(String name, PlayerColour colour, int valueTable[])
	{
		this.name = name;
		this.colour = colour;
		this.valueTable = new int[6];
		
		for (int i=0; i<6; i++)
			this.valueTable[i] = valueTable[i];
	}
	
	public int[] getValueTable()
	{
		return valueTable;
	}
	
	public int getValue(PieceType type)
	{
		return valueTable[type.getValue()];
	}
	
	public String toString()
	{
		return (colour == PlayerColour.White? "White: ": "Black: ") + name;
	}
	
	public PlayerColour getColour()
	{
		return colour;
	}
}
