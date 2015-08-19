package Board;

public enum PlayerColour {
	White,
	Black;
	
	
	public PlayerColour getOpponent()
	{
		return this==White? Black: White;
	}
	
}
