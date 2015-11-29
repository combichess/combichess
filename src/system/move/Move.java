package system.move;

import system.piece.ChessNotation;
import system.piece.Piece;
import system.piece.PieceType;

public class Move {
	public static final int PREVIOUSLY_NEVER_MOVED = -1;
	
	private Piece pieceThatMoves;
	private Piece affectedPiece;
	private int posFrom;
	private int posTo;
	private MoveType moveType;
	private static ChessNotation STANDARD_CHESS_NOTATION = ChessNotation.ALGEBRAIC;
	private int previousMoveNumber;	// previously moveNumber for moving piece
	private int value;	// v�rdet p� detta draget och alla efterf�ljande drag.
						// om draget ger 4 po�ng, motspelares b�sta drag ger 5 po�ng och
						// efterf�ljande drag ger 3 po�ng blir allts� value = 4 - 5 + 3 = 2 po�ng
						// detta g�rs med addValueFromNextMove()
	
		 
	/** konstruktor utan givet dragv�rde
	 * @param pieceThatMoves
	 * @param affectedPiece
	 * @param toX
	 * @param toY
	 */
	public Move(Piece pieceThatMoves, Piece affectedPiece, int toX, int toY) {
		create(pieceThatMoves, affectedPiece, toX + toY*8, 0, MoveType.STANDARD);
	}
		
		// konstruktor med dragets v�rde angivet som value
	/**
	 * @param pieceThatMoves
	 * @param affectedPiece
	 * @param toX
	 * @param toY
	 * @param value
	 */
	public Move(Piece pieceThatMoves, Piece affectedPiece, int toX, int toY, int value) {
		create(pieceThatMoves, affectedPiece, toX + toY*8, value, MoveType.STANDARD);
	}
	
	/**
	 * @param pieceThatMoves
	 * @param affectedPiece
	 * @param toX
	 * @param toY
	 * @param moveType
	 */
	public Move(Piece pieceThatMoves, Piece affectedPiece, int toX, int toY, MoveType moveType) {
		create(pieceThatMoves, affectedPiece, toX + toY*8, 0, moveType);
	}
	
	/**
	 * @param pieceThatMoves
	 * @param affectedPiece
	 * @param toX
	 * @param toY
	 * @param value
	 * @param moveType
	 */
	public Move(Piece pieceThatMoves, Piece affectedPiece, int toX, int toY, int value, MoveType moveType) {
		create(pieceThatMoves, affectedPiece, toX + toY*8, value, moveType);
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
		// kolla s� att man kan v�lja promotiontype
		if (!this.moveType.isPromotion())
			return false;
		
		// se till att man valt r�tt 
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
	
	/**
	 * @param not - chess notation
	 * @param withFile "The vertical columns of squares (called files) from White's left"
	 * @param withRank "The horizontal rows of squares (called ranks) are numbered 1 to 8 starting from White's side of the board"
	 * @return
	 */
	public String toString(ChessNotation not, boolean withFile, boolean withRank)
	{
		String str = "";
		switch(not)
		{
		case ALGEBRAIC: {
			if (moveType == MoveType.KING_SIDE_CASTLING)
				str = "O-O";
			else if (moveType == MoveType.QUEEN_SIDE_CASTLING)
				str = "O-O-O";
			else {
				str = pieceThatMoves.getType().getLetter();
				str += withFile? STANDARD_CHESS_NOTATION.getCo(getFromPos()).charAt(0): "";
				str += withRank? STANDARD_CHESS_NOTATION.getCo(getFromPos()).charAt(1): "";
				str += (affectedPiece == null)? "": "x";
				str += not.getCo(this.getToPos());
			}
			break;}
		
		case LONG_ALGEBRAIC: {
			str = pieceThatMoves.toString(posFrom, not);
			str += " -> ";
			if (affectedPiece != null)
				str += affectedPiece.toString(not);
			else 
				str += not.getCo(posTo);
			
			str += ", val: " + value;
			break;}
		
		default:
			str = "Finns ingen toString anpassad f�r denna chessNotation: " + not;
		}
		
		return str;
	}
	
	public String toString(ChessNotation not)	// det ska �ven inneh�lla drag-nummer
	{
		 return toString(not, false, false);
	}
	
	// special om kungen blir tagen, d� ska detta visas som konstant (+-)kunga-v�rde.
	// 
	public void addValueFromNextMove(Move nextMove)
	{

		if (nextMove == null) {
			//tabort
			nextMove = null;
		} else if (nextMove.value == PieceType.King.getValue()) {
			//tabort
			if (value == PieceType.King.getValue())
			{
				value = 100;
				System.out.println("Hit ska den inte komma alls");
			}
			value = -PieceType.King.getValue();
		} else
			value -= nextMove.value;
	}
	
	@Override
	public String toString()
	{
		return toString(STANDARD_CHESS_NOTATION);	// det ska �ven inneh�lla drag-nummer
	}
	
	
	/**
	 * @param withRank "The horizontal rows of squares (called ranks) are numbered 1 to 8 starting from White's side of the board"
	 * @param withFile "The vertical columns of squares (called files) from White's left"
	 * @return
	 */
	public String toString(boolean withFile, boolean withRank)
	{
		return toString(STANDARD_CHESS_NOTATION, withRank, withFile);	// det ska �ven inneh�lla drag-nummer
	}
}
