package gui;

import system.board.PlayerColour;

public class GameStatus {
	
	public static final int UNDEFINED = -1; 
	public static final int WHITE_TO_MOVE = 0;
	public static final int BLACK_TO_MOVE = 3;
	public static final int GAME_OVER = 4;
	
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
	
	public void setGameOver()
	{
		status = GAME_OVER;
	}
	
	public void setPlayerTurn(PlayerColour pc)
	{
		chosenSquareFrom = chosenSquareTo = UNDEFINED;
		status = pc == PlayerColour.White? WHITE_TO_MOVE: BLACK_TO_MOVE;
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
		return status == WHITE_TO_MOVE;
	}
	
	public boolean isGameOver()
	{
		return status == GAME_OVER;
	}
	
	public void switchPlayer()
	{
		if (status == GAME_OVER)
			return;
		
 		status = (status == WHITE_TO_MOVE? BLACK_TO_MOVE: WHITE_TO_MOVE);
 		chosenSquareFrom = UNDEFINED;
 		chosenSquareTo = UNDEFINED;
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
		case BLACK_TO_MOVE:
			str += "Black To Move";
			break;
		case GAME_OVER:
			str += "Game over";
			break;
		}
		if (chosenSquareFrom != UNDEFINED)
			str += "\tchosenSquareFrom = " + chosenSquareFrom;
		
		if (chosenSquareTo != UNDEFINED)
			str += "\tchosenSquareTo = " + chosenSquareTo;
		
		return str;
	}
}



