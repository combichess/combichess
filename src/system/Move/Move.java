package system.move;

import system.piece.ChessNotation;
import system.piece.Piece;

public class Move {
	private Piece pieceThatMoves;
	private Piece affectedPiece;
	private int posFrom;
	private int posTo;
	private MoveType moveType;
	private int value;	// värdet på detta draget och alla efterföljande drag.
						// om draget ger 4 poäng, motspelares bästa drag ger 5 poäng och
						// efterföljande drag ger 3 poäng blir alltså value = 4 - 5 + 3 = 2 poäng
						// detta görs med addValueFromNextMove()
	
		// konstruktor utan givet dragvärde
	public Move(Piece pieceThatMoves, Piece affectedPiece, int toX, int toY) {
		create(pieceThatMoves, affectedPiece, toX + toY*8, 0);
	}
		
		// konstruktor med dragets värde angivet som value
	public Move(Piece pieceThatMoves, Piece affectedPiece, int toX, int toY, int value) {
		create(pieceThatMoves, affectedPiece, toX + toY*8, value);
	}
	
	private void create(Piece pieceThatMoves, Piece affectedPiece, int toPos, int value)
	{
		this.pieceThatMoves = pieceThatMoves;
		posFrom = pieceThatMoves.getPosition();
		posTo = toPos;
		this.affectedPiece = affectedPiece;
		this.value = value;		
	}
	
	public Piece getPiece()
	{
		return pieceThatMoves;
	}
	
	public int getXmove()
	{
		return (posTo%8) - (posFrom%8);
	}
	
	public int getYmove()
	{
		return (posTo/8) - (posFrom/8);
	}
	
	public int getFromPos()
	{
		return posFrom;
	}
	
	public int getToPos()
	{
		return posTo;
	}
	
	public Piece getAffectedPiece()
	{
		return affectedPiece;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public int getPositionChange()
	{
		//return dx + dy*8;
		return posTo - posFrom;
	}
	
	public String toString(ChessNotation not)
	{
		String str = pieceThatMoves.toString(posFrom, not);
		//String str = pieceThatMoves.toString(not);
		str += " -> ";
		if (affectedPiece != null)
			str += affectedPiece.toString(not);
		else 
			str += not.getCo(posTo);
			//str += not.getCo(pieceThatMoves.getX()+dx, pieceThatMoves.getY()+dy);
		
		str += ", val: " + value;
		return str; 
	}
	
	public void addValueFromNextMove(Move nextMove)
	{
		value -= nextMove.value;
	}
	
	public String toString()
	{
		return toString(ChessNotation.ALGEBRAIC);
	}
}
