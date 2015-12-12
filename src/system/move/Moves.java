package system.move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import system.piece.King;
import system.piece.Piece;
import system.piece.PieceType;

// Allting hanteras med ArrayLists
// Kan vara intressant att se en tic-toc jämförelse mellan ArrayList och LinkedList 

public class Moves extends ArrayList<Move> {
	private static final long serialVersionUID = 1L;

	public Moves() {
		super();
	}
	
	public Moves getMovesByPiece(Piece piece) {
		Moves newMoves = new Moves();
		for (Move move: this)
		{
			if (move.getPiece() == piece)
				newMoves.add(move);
		}
		return newMoves;
	}
	
	public Moves getMovesByPieceType(@SuppressWarnings("rawtypes") Class type)
	{
		Moves newMoves = new Moves();
		for (Move move: this)
		{
			if (move.getPiece().getClass() == type)
				newMoves.add(move);
		}
		return newMoves;
	}
	
	public Move getBestMoveFromList() {
		Move recordMove = null;
		int recordValue = -2*PieceType.getValue(King.class);
		for (Move move: this)
		{
			if (move.getValue() > recordValue)
			{
				recordValue = move.getValue();
				recordMove = move;
			}
		}
		
		return recordMove;
	}
	
	public Set<Integer> getAllPossibleSquaresToMoveFrom()
	{
		Set<Integer> positions = new HashSet<>();
		for(Move move: this)
			positions.add(move.getPiece().getPosition());
		return positions;
	}

	public Set<Integer> getAllPossibleSquaresToMoveTo() {
		Set<Integer> positions = new HashSet<>();
		for(Move move: this)
			positions.add(move.getToPos());
		return positions;
	}
	
	
	
	public Set<Piece> getAffectedPieces()    // unique results
    {
	    Set<Piece> uniquePieces = new HashSet<>();
	    for (Move pis: this)
	        uniquePieces.add(pis.getAffectedPiece());
	    return uniquePieces;
    }
    
	public Set<Piece> getMovingPieces()        // unique results
    {
		Set<Piece> uniquePieces = new HashSet<>();
	    for (Move pis: this)
	        uniquePieces.add(pis.getPiece());
	    return uniquePieces;
    }
    
	public Moves getMovesFromPos(int pos)
    {
		Moves moves = new Moves();
		for (Move move: this)
		{
			if (move.getFromPos() == pos)
				moves.add(move);
		}
    	return moves;
    }
	
	public Moves getMovesToPos(String str)
	{
		if (str == null || str.length() != 2)
			return null;
		
		int x = str.toLowerCase().charAt(0) - 'a';
		int y = str.charAt(1) - '1';
		if (x<0 || x>7 || y<0 || y>7)
			return null;
		
		return getMovesToPos(x + y*8);
	}
	
	public Moves getMovesByValue(int value)
	{
		Moves moves = new Moves();
		for (Move move: this)
		{
			if (move.getValue() == value)
				moves.add(move);
		}
		return moves; 
	}
    
	public Moves getMovesToPos(int pos)
    {
		Moves moves = new Moves();
		for (Move move: this)
		{
			if (move.getToPos() == pos)
				moves.add(move);
		}
    	return moves;
    }
    
	public Move getRandomMove()
    {
		return get((int) (Math.random() * size()));
    }
    
	public Moves orderByValue()
    {
		Moves moves = new Moves();
		moves.addAll(this);
		
		//Sorting
		Collections.sort(moves, new Comparator<Move>() {
			@Override
	        public int compare(Move move1, Move move2)
	        {
	        	return move1.getValue() - move2.getValue();
	        }
		});
		
    	return moves;
    }
	
	public Moves getMovesByMoveType(MoveType mt)
    {
		Moves moves = new Moves();
		for (Move move: this)
		{
			if (mt == MoveType.PROMOTION) {
				switch(move.getMoveType())
				{
				case PROMOTION:
				case PROMOTION_BISHOP:
				case PROMOTION_KNIGHT:
				case PROMOTION_QUEEN:
				case PROMOTION_ROOK:
					moves.add(move);
					break;
				default:
					break;
				}
			} else if (move.getMoveType() == mt)
				moves.add(move);
		}
    	return moves;
    }
}
