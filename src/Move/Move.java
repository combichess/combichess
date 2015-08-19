package Move;

import piece.ChessNotation;
import piece.Piece;

public class Move {
	private Piece pieceThatMoves;
	private Piece affectedPiece;
	private int dx;
	private int dy;
	private int value;	// value of Player.White - value of Player.Black
	
		// konstruktor utan givet dragvärde
	public Move(Piece pieceThatMoves, Piece affectedPiece, int toX, int toY) {
		this.pieceThatMoves = pieceThatMoves;
		dx = toX - pieceThatMoves.getX();
		dy = toY - pieceThatMoves.getY();
		this.affectedPiece = affectedPiece;
		this.value = 0;
	}
		
		// konstruktor med dragets värde angivet som value
	public Move(Piece pieceThatMoves, Piece affectedPiece, int toX, int toY, int value) {
		this.pieceThatMoves = pieceThatMoves;
		dx = toX - pieceThatMoves.getX();
		dy = toY - pieceThatMoves.getY();
		this.affectedPiece = affectedPiece;
		this.value = value;
	}
	
	public Piece getPiece()
	{
		return pieceThatMoves;
	}
	
	public int getXmove()
	{
		return dx;
	}
	
	public int getYmove()
	{
		return dy;
	}
	
	public Piece getAffectedPiece()
	{
		return affectedPiece;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public String toString(ChessNotation not)
	{
		String str = pieceThatMoves.toString(not);
		str += " -> ";
		if (affectedPiece != null)
			str += affectedPiece.toString(not);
		else 
			str += not.getCo(pieceThatMoves.getX()+dx, pieceThatMoves.getY()+dy);
		
		str += " (" + value + ")";
		return str; 
	}
	
	public String toString()
	{
		return toString(ChessNotation.ALGEBRAIC);
	}
}
