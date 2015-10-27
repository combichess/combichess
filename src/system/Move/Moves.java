package system.move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	public Move getBestMoveFromList() {
		Move recordMove = null;
		int recordValue = -2*PieceType.King.getValue();
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
			if (move.getMoveType() == mt)
				moves.add(move);
		}
    	return moves;
    }
	
	
	/*
	 * 		För att hitta rätt drag.	 
	 * 
	 * public static HashMap<String, Integer> getHighest(HashMap<String, Integer> map)
    {
        int topValue = map.values().iterator().next().intValue();
        for (Integer val: map.values())
            topValue = topValue>val? topValue: val;
        
        HashMap<String, Integer> toReturn = new HashMap<>();
        for (Entry<String, Integer> en: map.entrySet())
            if (en.getValue() == topValue)
                toReturn.put(en.getKey(), en.getValue());
        
        return toReturn;
    }
    
    public static String getHighest2(HashMap<String, Integer> map)
    {
        int topValue = map.values().iterator().next().intValue();
        int numOfValue = 0;
        for (Integer val: map.values())
        {
            if (val == topValue)
                numOfValue++;
            else if (val > topValue)
            {
                numOfValue = 1;
                topValue = val;
            }
        }
        
        int index = (int)(Math.random()*numOfValue);
        for (Entry<String, Integer> en: map.entrySet()) {
            if (en.getValue() == topValue && --index < 0)
                return en.getKey();
            
        }
        return null;
    }
    
    
    public static String getRandomHighest2(final HashMap<String, Integer> map)
    {
        int topValue = map.values().iterator().next().intValue();
        int bottomValue = topValue;
        double totalValue = 0;
        for (Integer val: map.values()) {
            topValue = topValue>val? topValue: val;
            bottomValue = bottomValue<val? bottomValue: val;
        }
        Set<Integer> hej = new HashSet<>();
            
        HashMap<String, Double> doubleMap = new HashMap<>();
        for (Entry<String, Integer> en: map.entrySet())
        {
            double value = Math.exp((en.getValue()-topValue)*2);
            totalValue += value;
            doubleMap.put(en.getKey(), value);
        }
        
        double catchValue = Math.random()*totalValue;

        for (Entry<String, Double> en: doubleMap.entrySet())
        {
            catchValue -= en.getValue();
            if (catchValue < 0)
                return en.getKey();
        }
        
        return null;
    }
	 * */
	
	
}
