package system.board;

public enum PlayerColour {
	White,
	Black;
	
	
	public PlayerColour getOpponentColour()
	{
		return this==White? Black: White;
	}
	
	@Override
	public String toString()
	{
		return this==White? "White": "Black";
	}
}
