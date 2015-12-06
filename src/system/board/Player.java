package system.board;

public class Player {
	private int valueTable[];
	
	public Player()
	{
		valueTable = new int[] {1, 3, 3, 5, 9, 100};	// {bonde, häst, löpare, torn, dam, kingen är ovärderlig}
		// http://en.wikipedia.org/wiki/Chess_piece_relative_value#Standard_valuations	
	}
	
	public Player(int valueTable[])
	{
		this.valueTable = new int[6];
		
		for (int i=0; i<6; i++)
			this.valueTable[i] = valueTable[i];
	}
	
	public int[] getValueTable()
	{
		return valueTable;
	}
}
