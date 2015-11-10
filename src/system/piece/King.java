
package system.piece;

import system.board.Board;
import system.board.PlayerColour;
import system.move.Move;
import system.move.Moves;

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


    public Moves getPossibleMoves(Board board)
    {
        Moves nyLista = new Moves();
        int val = (xPos>0? 1: 0) + (xPos==7? 1: 0) + (yPos>0? 3: 0) + (yPos==7? 3: 0);
        int presentPos = xPos + 8*yPos;
        
        for (int dPos: SQRS_TO_CHECK[val])
        {
        	int newPos = presentPos + dPos;
            Piece pieceOnNewSquare = board.getPieceOnSquare(newPos);
            if (pieceOnNewSquare == null)
                nyLista.add(new Move(this, pieceOnNewSquare, newPos&7, newPos/8));
            else if (pieceOnNewSquare.getPlayer() != player)
                nyLista.add(new Move(this, pieceOnNewSquare, pieceOnNewSquare.xPos, pieceOnNewSquare.yPos, pieceOnNewSquare.type.getValue()));
        }
        
        // check castling
        int castlingY = getPlayer() == PlayerColour.White? 0: 7; 
        if (castlingY == yPos && super.getPreviousMoveNumber() == Move.PREVIOUSLY_NEVER_MOVED)
        {
        	// Kingside castling
        	
        	// Queenside castling
        }
        
        return nyLista;
    }


	@Override
	public boolean isPieceThreateningPosition(int squarePos, Piece[] squares) {
		switch(((squarePos&7) - xPos) + ((squarePos/8) - yPos)*8)
		{
		case 9:
		case 8:
		case 7:
		case 1:
		case -1:
		case -7:
		case -8:
		case -9:
			return true;
		}
		return false;
	}
} 