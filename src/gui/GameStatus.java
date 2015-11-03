package gui;


public class GameStatus {
	
	public static final int UNDEFINED = -1; 
	public static final int WHITE_TO_MOVE = 0;
	//public static final int WHITE_HAS_CHOSEN_PIECE = 1;
	@Deprecated
	public static final int WHITE_COMMITTED_MOVE = 2;
	public static final int BLACK_TO_MOVE = 3;
	//public static final int BLACK_HAS_CHOSEN_PIECE = 4;
	@Deprecated
	public static final int BLACK_COMMITTED_MOVE = 5;
	
	private int status;
	private int chosenSquareFrom;
	private int chosenSquareTo;
	
	public GameStatus()
	{
		status = WHITE_TO_MOVE;
		chosenSquareFrom = UNDEFINED;
		chosenSquareTo = UNDEFINED;
	}
	
	public int getStatus()
	{
		return status;
	}
	
	public void updateStatus(int newStatus)
	{
		if (!isStatusUpdatePossible(status, newStatus))
		{
			System.out.println("Changed status from " + status + " to " + newStatus + " is not possible");
			return;
		}
		chosenSquareFrom = UNDEFINED;
		chosenSquareTo = UNDEFINED;
		status = newStatus;
	}
	
	public void setChosenSquareFrom(int chosenSquareFrom)
	{
		this.chosenSquareFrom = chosenSquareFrom;
	}
	
	public int getChosenSquareFrom()
	{
		return (status == WHITE_TO_MOVE || status == BLACK_TO_MOVE)? chosenSquareFrom: UNDEFINED;
	}
	
	public void setChosenSquareTo(int chosenSquareTo)
	{
		this.chosenSquareTo = chosenSquareTo;
	}
	
	public int getChosenSquareTo()
	{
		return (status == WHITE_TO_MOVE || status == BLACK_TO_MOVE)? chosenSquareTo: UNDEFINED;
	}
	
	public boolean isWhitesTurn()
	{
		return (status == WHITE_TO_MOVE || status == WHITE_COMMITTED_MOVE);
	}
	
	public void switchPlayer()
	{
 		status = (status == WHITE_TO_MOVE? BLACK_TO_MOVE: WHITE_TO_MOVE);
 		chosenSquareFrom = UNDEFINED;
 		chosenSquareTo = UNDEFINED;
	}
	
	private static boolean isStatusUpdatePossible(int preStatus, int postStatus)
	{
		switch(preStatus)
		{
		case WHITE_TO_MOVE:
			return postStatus == WHITE_COMMITTED_MOVE;
		case WHITE_COMMITTED_MOVE:
			return postStatus == BLACK_TO_MOVE;
		case BLACK_TO_MOVE:
			return postStatus == BLACK_COMMITTED_MOVE;
		case BLACK_COMMITTED_MOVE:
			return postStatus == WHITE_TO_MOVE;
		}
		
		return false;
	}
	
	@Override
	public String toString()
	{
		String str = "status = ";
		switch(status)
		{
		case WHITE_TO_MOVE:
			str += "White To Move";
			break;
		case WHITE_COMMITTED_MOVE:
			str += "White Committed a move";
			break;
		case BLACK_TO_MOVE:
			str += "Black To Move";
			break;
		case BLACK_COMMITTED_MOVE:
			str += "Black Committed a move";
			break;
		}
		
		if (chosenSquareFrom != UNDEFINED)
			str += "\tchosenSquareFrom = " + chosenSquareFrom;
		
		if (chosenSquareTo != UNDEFINED)
			str += "\tchosenSquareTo = " + chosenSquareTo;
		
		return str;
	}
}



