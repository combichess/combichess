package system.piece;

import system.board.Board;
import system.board.PlayerColour;
import system.move.Move;
import system.move.Moves;

public abstract class Piece implements PieceInterface {

	protected int xPos;
	protected int yPos;
	int previousMoveNumber;
	PlayerColour player;
	protected boolean active;
	

	protected Piece(int xPos, int yPos, PlayerColour player)
	{
		this.active = true;
		this.xPos = xPos;
		this.yPos = yPos;
		this.player = player;
		this.previousMoveNumber = Move.PREVIOUSLY_NEVER_MOVED;
	}
	
	
	protected boolean isThreateningSquareDiagonally(int squarePos, Piece[] squares)
	{
		int pos = xPos + 8*yPos;
		

		if (pos == squarePos)
			return false;
		int xSquarePos = squarePos&7; 
		int ySquarePos = squarePos/8;
		
		int xDiff = xPos - xSquarePos;
		int yDiff = yPos - ySquarePos;
		
		
		if (xDiff == yDiff) {
			int dPos = xDiff>0? 9: -9;

			if (squarePos < 0 || squarePos > 63)
			{
				squarePos ++;
				squarePos--;
			}
			
			for (squarePos += dPos; squarePos != pos; squarePos += dPos)
			{
				if (squares[squarePos] != null)
					return false;
			}
			return true;
		} else if(xDiff == -yDiff) {
			int dPos = xDiff>0? -7: 7;
			for (squarePos += dPos; squarePos != pos; squarePos += dPos)
			{
				if (squarePos < 0 || squarePos > 63)
				{
					squarePos ++;
					squarePos--;
				}
				
				if (squares[squarePos] != null)
					return false;
			}
			return true;
		}
		return false;
	}
	
	protected boolean isThreateningSquareStraight(int squarePos, Piece[] squares)
	{
		int pos = xPos + 8*yPos;
		if (pos == squarePos)
			return false;
		
		int xSquarePos = squarePos&7; 
		int ySquarePos = squarePos/8;
		
		if (xSquarePos == xPos) {
			int dPos = ySquarePos<yPos? 8: -8;
			for (squarePos += dPos; squarePos != pos; squarePos += dPos)
			{
				if (squarePos > 63 || squarePos < 0)
				{
					squarePos = squarePos + 1;
					squarePos--;
				}
				if (squares[squarePos] != null)
					return false;
			}
			return true;
		} else if(ySquarePos == yPos) {
			int dPos = xSquarePos<xPos? 1: -1;
			for (squarePos += dPos; squarePos != pos; squarePos += dPos)
			{
				if (squares[squarePos] != null)
					return false;
			}
			return true;
		}
		return false;
	}
	
	protected String getPieceCoordsAsString()
	{
		char xCharPos = 'a';
		xCharPos += xPos;
		return (player==PlayerColour.White? "W": "B") + " " + getLetter() + xCharPos + yPos;
	}
	
	public PlayerColour getPlayer()
	{
		return player;
	}
	
	public int getPosition()	// position definierat som int
	{
		return xPos + yPos*8;
	}

	public int getX()
	{
		return xPos;
	}
	
	public int getY()
	{
		return yPos;
	}
	
	public void setPreviousMoveNumber(int previousMoveNumber) 
	{
		this.previousMoveNumber = previousMoveNumber;
	}
	
	public int getPreviousMoveNumber()
	{
		return this.previousMoveNumber;
	}
	
	public void moveTo(int pos)
	{
		xPos = pos&7;
		yPos = pos/8;
	}
	
	public void moveTo(int pos, int previousMoveNumber)
	{
		this.previousMoveNumber = previousMoveNumber;
		xPos = pos&7;
		yPos = pos/8;
	}
	
	public String toString(boolean useColour)
	{
		return toString(xPos + yPos*8, useColour);  
	}
	
	public String toString(int customPos)
	{
		return toString(customPos, true); 
	}
	
	public String toString(int customPos, boolean useColour)
	{
		
		String toReturn = (customPos<0 || customPos>63)? "err": "";
		toReturn = (useColour? (player==PlayerColour.White? "W": "B"): "") + "" + getLetter() + Move.getCo(customPos);
		return toReturn;
	}
	
	protected void getPossibleDiagonalMoves(Moves possibleMoves, Board board)
	{
		for (int direction=0; direction<4; direction++) {
			int dx = ((direction&1) == 1)? 1: -1;
			int dy = ((direction&2) == 2)? 1: -1;
			
			int xPosNew = xPos + dx;
			int yPosNew = yPos + dy;
			Piece pieceOnNewSquare = null;
			
			while((xPosNew&(~7)) == 0 && (yPosNew&(~7)) == 0) {
				pieceOnNewSquare = board.getPieceOnSquare(xPosNew, yPosNew);
				if (pieceOnNewSquare != null)
				{
					if (pieceOnNewSquare.player != player)
						possibleMoves.add(new Move(this, pieceOnNewSquare, xPosNew, yPosNew, pieceOnNewSquare.getValue()));
					break;
				} else
					possibleMoves.add(new Move(this, null, xPosNew, yPosNew));
	
				xPosNew += dx;
				yPosNew += dy;
			};
		}
	}
	
	protected void getPossibleStraightMoves(Moves possibleMoves, Board board)
	{
		for (int direction=0; direction<4; direction++) {
			int dx = (((direction&2)==0)? 1: -1) * (((direction & 1)==0)? 1: 0); // -1, 0, 1, 0
			int dy = (((direction&2)==0)? 1: -1) * (((direction & 1)==0)? 0: 1); //  0, 1, 0, -1 
			
			int xPosNew = xPos + dx;
			int yPosNew = yPos + dy;
			Piece pieceOnNewSquare = null;
			
			while((xPosNew&(~7)) == 0 && (yPosNew&(~7)) == 0) {	// kontrollera att pjäsen är kvar på brädet
				pieceOnNewSquare = board.getPieceOnSquare(xPosNew, yPosNew);
				if (pieceOnNewSquare != null)
				{
					if (pieceOnNewSquare.getPlayer() != player)
						possibleMoves.add(new Move(this, pieceOnNewSquare, xPosNew, yPosNew, pieceOnNewSquare.getValue()));
					break;
				} else 
					possibleMoves.add(new Move(this, pieceOnNewSquare, xPosNew, yPosNew));
	
				xPosNew += dx;
				yPosNew += dy;
			};
		}
	}
	
	public abstract String getLetter();
	
	public int getValue()
	{
		return PieceType.getValue(this.getClass());
	}
	
	public boolean getActivity()
	{
		return active;
	}

	public void setActivity(boolean newActivity)
	{
		active = newActivity;
	}
}
