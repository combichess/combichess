package Board;

public enum PlayerColour {
	White,
	Black;
	
	
	public PlayerColour getOpponentColour()
	{
		return this==White? Black: White;
	}
	
	public String toString()
	{
		return this==White? "White": "Black";
	}
}
