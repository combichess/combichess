package main.control;

public enum PlayerStatus {
	NO_STATUS,
	STALE_MATE,
	CHECK,
	CHECK_MATE,
	UNDEFINED;
	
	public static PlayerStatus createFromString(String fromString)
	{
		if (fromString.equals(NO_STATUS.toString()))
			return NO_STATUS;
		if (fromString.equals(STALE_MATE.toString()))
			return STALE_MATE;
		if (fromString.equals(CHECK.toString()))
			return CHECK;
		if (fromString.equals(CHECK_MATE.toString()))
			return CHECK_MATE;
		return UNDEFINED;
	}
	
	public String toString()
	{
		return this.name();
	}
}
