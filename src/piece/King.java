
package piece;

import java.util.ArrayList;
import java.util.List;

import Board.Board;
import Board.PlayerColour;
import Move.Move;

public class King extends Piece {
	
	public King(int xPos, int yPos, PlayerColour player) {
		super(xPos, yPos, player);
		type = PieceType.King;
	}


	public List<Move> getPossibleMoves(Board board)
	{
		List<Move> nyLista = new ArrayList<Move>();
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
