package piece;

import java.util.List;

import Board.Board;
import Board.PlayerColour;
import Move.Move;

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
	}//((squares[pos].getPlayer() == Player.Black)? "B": "W") + 
	
	/*static public void setPieceValues(int valueTable_[])
	{
		for (int i=0; i<valueTable.length; i++)
			valueTable[i] = valueTable_[i];
	}
	
	static public void unsetPieceValues()
	{
		for (int i=0; i<valueTable.length; i++)
			valueTable[i] = 0;
	}*/
	
	public PlayerColour getPlayer()
	{
		return player;
	}

	public int getX()
	{
		return xPos;
	}
	
	public int getY()
	{
		return yPos;
	}
	
	public void moveX(int dxPos, int dyPos)	
	{
		xPos += dxPos;
		yPos += dyPos;
	}
	
	public String toString()
	{
		return toString(ChessNotation.MY_OWN_FULL_NOTATION); 
	}
	
	public String toString(ChessNotation not)
	{
		String toReturn = "";
		switch (not) 
		{
		case ALGEBRAIC:
			toReturn = (player==PlayerColour.White? "W": "B") + "" + type.getLetter() + not.getCo(xPos, yPos);
			break;
		case MY_OWN_FULL_NOTATION:
			toReturn = "Piece: xxx, [" + xPos + ", " + yPos + "]";
			break;
		default:
			toReturn = "No real piece-notation";
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
			
			while((xPosNew&(~7)) == 0 && (yPosNew&(~7)) == 0) {	// kontrollera att pjäsen är kvar på brädet
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
