package system.piece;

import java.util.List;

import system.board.Board;
import system.board.PlayerColour;
import system.move.Move;

public abstract class Piece implements PieceInterface {

	protected int xPos;
	protected int yPos;
	//protected String descriptiveLetter; 
	PieceType type;
	int numberOfMoves;
	PlayerColour player;
	protected boolean active;
	//static private int valueTable[] = new int[] {0, 0, 0, 0, 0, 0};
	

	protected Piece(int xPos, int yPos, PlayerColour player)
	{
		numberOfMoves = 0;
		active = true;
		this.xPos = xPos;
		this.yPos = yPos;
		this.player = player;
	}
	
	protected String getPieceCoordsAsString()
	{
		char xCharPos = 'a';
		xCharPos += xPos;
		return (player==PlayerColour.White? "W": "B") + " " + type.getLetter() + xCharPos + "" + yPos;
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
	
	public void moveX(int dPos)
	{
		//xPos += dPos%8;
		//yPos += dPos/8;
		int nyPos = (xPos + 8*yPos) + (dPos);
		xPos = nyPos%8;
		yPos = nyPos/8; 
	}
	
	public void moveX(int dxPos, int dyPos)	
	{
		xPos += dxPos;
		yPos += dyPos;
	}
	
	public String toString()
	{
		return toString(xPos + yPos*8, ChessNotation.ALGEBRAIC); 
	}
	
	public String toString(ChessNotation not)
	{
		return toString(xPos + yPos*8, not);  
	}
	
	public String toString(int customPos)
	{
		return toString(customPos, ChessNotation.ALGEBRAIC); 
	}
	
	public String toString(int customPos, ChessNotation not)
	{
		
		String toReturn = (customPos<0 || customPos>63)? 
				"err": 
					"";
		switch (not) 
		{
		case ALGEBRAIC:
			toReturn = (player==PlayerColour.White? "W": "B") + "" + type.getLetter() + not.getCo(customPos);
			break;
		case MY_OWN_FULL_NOTATION:
			toReturn = "Piece: xxx, [" + (customPos%8) + ", " + customPos/8 + "]";
			break;
		default:
			toReturn = "No real piece-notation in Piece.toString(int, ChessNotation)";
			break;
		}
		
		return toReturn;
	}
	
	protected void getPossibleDiagonalMoves(List<Move> possibleMoves, Board board)
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
						possibleMoves.add(new Move(this, pieceOnNewSquare, xPosNew, yPosNew, pieceOnNewSquare.type.getValue()));
					break;
				} else
					possibleMoves.add(new Move(this, null, xPosNew, yPosNew));
	
				xPosNew += dx;
				yPosNew += dy;
			};
		}
	}
	
	protected void getPossibleStraightMoves(List<Move> possibleMoves, Board board)
	{
		for (int direction=0; direction<4; direction++) {
			int dx = (((direction&2)==0)? 1: -1) * (((direction & 1)==0)? 1: 0); // -1, 0, 1, 0
			int dy = (((direction&2)==0)? 1: -1) * (((direction & 1)==0)? 0: 1); //  0, 1, 0, -1 
			
			int xPosNew = xPos + dx;
			int yPosNew = yPos + dy;
			Piece pieceOnNewSquare = null;
			
			while((xPosNew&(~7)) == 0 && (yPosNew&(~7)) == 0) {	// kontrollera att pj�sen �r kvar p� br�det
				pieceOnNewSquare = board.getPieceOnSquare(xPosNew, yPosNew);
				if (pieceOnNewSquare != null)
				{
					if (pieceOnNewSquare.getPlayer() != player)
						possibleMoves.add(new Move(this, pieceOnNewSquare, xPosNew, yPosNew, pieceOnNewSquare.type.getValue()));
					break;
				} else 
					possibleMoves.add(new Move(this, pieceOnNewSquare, xPosNew, yPosNew));
	
				xPosNew += dx;
				yPosNew += dy;
			};
		}
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
