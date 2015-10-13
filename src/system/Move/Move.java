package system.move;

import system.piece.ChessNotation;
import system.piece.Piece;

public class Move {
	public static final int PREVIOUSLY_NEVER_MOVED = -1;
	
	private Piece pieceThatMoves;
	private Piece affectedPiece;
	private int posFrom;
	private int posTo;
	private MoveType moveType;
	private int previousMoveNumber;	// previously moveNumber for moving piece
	private int value;	// v�rdet p� detta draget och alla efterf�ljande drag.
						// om draget ger 4 po�ng, motspelares b�sta drag ger 5 po�ng och
						// efterf�ljande drag ger 3 po�ng blir allts� value = 4 - 5 + 3 = 2 po�ng
						// detta g�rs med addValueFromNextMove()
	
		// konstruktor utan givet dragv�rde
	public Move(Piece pieceThatMoves, Piece affectedPiece, int toX, int toY) {
		create(pieceThatMoves, affectedPiece, toX + toY*8, 0);
	}
		
		// konstruktor med dragets v�rde angivet som value
	public Move(Piece pieceThatMoves, Piece affectedPiece, int toX, int toY, int value) {
		create(pieceThatMoves, affectedPiece, toX + toY*8, value);
	}
	
	private void create(Piece pieceThatMoves, Piece affectedPiece, int posTo, int value)
	{
		this.previousMoveNumber = pieceThatMoves.getPreviousMoveNumber();
		this.pieceThatMoves = pieceThatMoves;
		this.posFrom = pieceThatMoves.getPosition();
		this.posTo = posTo;
		this.affectedPiece = affectedPiece;
		this.value = value;		
	}
	
	public int getPreviousMoveNumber()
	{
		return previousMoveNumber;
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
	
	public String toString(ChessNotation not)	// det ska �ven inneh�lla drag-nummer
	{
		String str = pieceThatMoves.toString(posFrom, not);
		str += " -> ";
		if (affectedPiece != null)
			str += affectedPiece.toString(not);
		else 
			str += not.getCo(posTo);
		
		str += ", val: " + value;
		return str; 
	}
	
	public void addValueFromNextMove(Move nextMove)
	{
		value -= nextMove.value;
	}
	
	public String toString()
	{
		return toString(ChessNotation.ALGEBRAIC);	// det ska �ven inneh�lla drag-nummer
	}
}
