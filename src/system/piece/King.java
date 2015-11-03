
package system.piece;

import system.board.Board;
import system.board.PlayerColour;
import system.move.Move;
import system.move.Moves;

public class King extends Piece {
	
	public King(int xPos, int yPos, PlayerColour player) {
		super(xPos, yPos, player);
		type = PieceType.King;
	}


	@Override
	public Moves getPossibleMoves(Board board)
	{
		Moves nyLista = new Moves();
		int maxX = Math.min(8, xPos + 2);
		int maxY = Math.min(8, yPos + 2);
		
		for (int y=Math.max(yPos-1, 0); y<maxY; y++) 
		{
			for (int x=Math.max(xPos-1, 0); x<maxX; x++)
			{
				Piece pieceOnNewSquare = board.getPieceOnSquare(x, y);
				if (pieceOnNewSquare == null)
					nyLista.add(new Move(this, pieceOnNewSquare, x, y));
				else if (pieceOnNewSquare.getPlayer() != player)
					nyLista.add(new Move(this, pieceOnNewSquare, x, y, pieceOnNewSquare.type.getValue()));
				
			}
		}
		
		return nyLista;
	}
}


/*
public class King extends Piece {
    
    private static int[][] SQRS_TO_CHECK = {
        {1, 8, 9},
        {-1, 1, 7, 8, 9},
        {-1, 7, 8},
        
        {-7, -8, 1, 8, 9},
        {-7, -8, -9, -1, 1, 7, 8, 9},
        {-8, -9, -1, 7, 8},

        {-7, -8, 1}, 
        {-7, -8, -9, -1, 1}, 
        {-8, -9, -1}};
    
    public King(int xPos, int yPos, PlayerColour player) {
        super(xPos, yPos, player);
        type = PieceType.King;
    }


    public List<Move> getPossibleMoves(Board board)
    {
        Moves nyLista = new Moves();
        int val = (xPos>0? 1: 0) + (xPos==7? 1: 0) + (yPos>0? 3: 0) + (yPos==7? 3: 0);
        int presentPos = xPos + 8*yPos;
        for (int dPos: SQRS_TO_CHECK[val])
        {
            Piece pieceOnNewSquare = board.getPieceOnSquare(presentPos + dPos);
            if (pieceOnNewSquare == null)
                nyLista.add(new Move(this, pieceOnNewSquare, x, y));
            else if (pieceOnNewSquare.getPlayer() != player)
                nyLista.add(new Move(this, pieceOnNewSquare, x, y, pieceOnNewSquare.type.getValue()));
                
        }
        
        return nyLista;
    }
*/