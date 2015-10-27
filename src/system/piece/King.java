
package system.piece;

import java.util.ArrayList;
import java.util.List;

import system.board.Board;
import system.board.PlayerColour;
import system.move.Move;
import system.move.Moves;

public class King extends Piece {
	
	public King(int xPos, int yPos, PlayerColour player) {
		super(xPos, yPos, player);
		type = PieceType.King;
	}


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
