package Board;

public enum PlayerColour {
	White,
	Black;
	
	
	public PlayerColour getOpponentColour()
	{
		return this==White? Black: White;
	}
	
}
