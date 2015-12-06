
package system.piece;

import system.board.Board;
import system.board.PlayerColour;
import system.move.Move;
import system.move.MoveType;
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
        //type = PieceType.King;
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
                nyLista.add(new Move(this, pieceOnNewSquare, pieceOnNewSquare.xPos, pieceOnNewSquare.yPos, pieceOnNewSquare.getValue()));
        }
        
        // check castling
        int castlingY = getPlayer() == PlayerColour.White? 0: 7; 
        PlayerColour opponent = getPlayer().getOpponentColour();
        int kingPos = getPosition();
        if (yPos == castlingY && super.getPreviousMoveNumber() == Move.PREVIOUSLY_NEVER_MOVED
        		&& !board.isSquareThreatnedBy(kingPos, opponent))
        {
        	// Kingside castling
        	Piece rook = board.getPieceOnSquare(kingPos+3);
        	if (board.getPieceOnSquare(kingPos+1) == null && 
        			board.getPieceOnSquare(kingPos+2) == null && 
        			rook != null &&
        			rook.getPreviousMoveNumber() == Move.PREVIOUSLY_NEVER_MOVED &&
        			!board.isSquareThreatnedBy(kingPos+1, opponent) && // <- time consuming comparison is done last
        			!board.isSquareThreatnedBy(kingPos+2, opponent))
        	{
        		nyLista.add(new Move(this, null, xPos+2, yPos, MoveType.KING_SIDE_CASTLING));
        	}
        	
        	// Queenside castling
        	rook = board.getPieceOnSquare(kingPos-4);
        	if (board.getPieceOnSquare(kingPos-1) == null && 
        			board.getPieceOnSquare(kingPos-2) == null &&
        			board.getPieceOnSquare(kingPos-3) == null && 
        			rook != null &&
        			rook.getPreviousMoveNumber() == Move.PREVIOUSLY_NEVER_MOVED &&
        			!board.isSquareThreatnedBy(kingPos-1, opponent) && // <- time consuming comparison is done in the end
        			!board.isSquareThreatnedBy(kingPos-2, opponent))
        	{
        		nyLista.add(new Move(this, null, xPos-2, yPos, MoveType.QUEEN_SIDE_CASTLING));
        	}
        }
        
        return nyLista;
    }
    
    @Override
    public String getLetter()
    {
    	return "K";
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