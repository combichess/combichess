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
	private int value;	// värdet på detta draget och alla efterföljande drag.
						// om draget ger 4 poäng, motspelares bästa drag ger 5 poäng och
						// efterföljande drag ger 3 poäng blir alltså value = 4 - 5 + 3 = 2 poäng
						// detta görs med addValueFromNextMove()
	
		// konstruktor utan givet dragvärde
	public Move(Piece pieceThatMoves, Piece affectedPiece, int toX, int toY) {
		create(pieceThatMoves, affectedPiece, toX + toY*8, 0, MoveType.STANDARD);
	}
		
		// konstruktor med dragets värde angivet som value
	public Move(Piece pieceThatMoves, Piece affectedPiece, int toX, int toY, int value) {
		create(pieceThatMoves, affectedPiece, toX + toY*8, value, MoveType.STANDARD);
	}
	
	public Move(Piece pieceThatMoves, Piece affectedPiece, int toX, int toY, MoveType moveType) {
		create(pieceThatMoves, affectedPiece, toX + toY*8, 0, moveType);
		//System.out.println("Move.java\t Hit ska den inte komma!");
	}
	public Move(Piece pieceThatMoves, Piece affectedPiece, int toX, int toY, int value, MoveType moveType) {
		create(pieceThatMoves, affectedPiece, toX + toY*8, value, moveType);
		//System.out.println("Move.java\t Hit ska den inte komma!");
	}
	
	private void create(Piece pieceThatMoves, Piece affectedPiece, int posTo, int value, MoveType moveType)
	{
		this.previousMoveNumber = pieceThatMoves.getPreviousMoveNumber();
		this.pieceThatMoves = pieceThatMoves;
		this.posFrom = pieceThatMoves.getPosition();
		this.posTo = posTo;
		this.affectedPiece = affectedPiece;
		this.moveType = moveType;
		this.value = value;		
	}
	
	public boolean setPromotionType(MoveType moveType)
	{
		// kolla så att man kan välja promotiontype
		if (!this.moveType.isPromotion())
			return false;
		
		// se till att man valt rätt 
		switch(moveType)
		{
		case PROMOTION_QUEEN:
		case PROMOTION_BISHOP:
		case PROMOTION_KNIGHT:
		case PROMOTION_ROOK:
			this.moveType = moveType;
			return true;
		default:
			return false;
		}

	}
	
	public MoveType getMoveType() 
	{
		return moveType;
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
	
	public String toString(ChessNotation not)	// det ska även innehålla drag-nummer
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
		return toString(ChessNotation.ALGEBRAIC);	// det ska även innehålla drag-nummer
	}
}
