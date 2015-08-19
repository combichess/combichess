package piece;

import java.util.ArrayList;
import java.util.List;

import Board.Board;
import Board.PlayerColour;
import Move.Move;

public class Knight extends Piece {

	public Knight(int xPos, int yPos, PlayerColour player) {
		super(xPos, yPos, player);
		type = PieceType.Knight;
	}

	public List<Move> getPossibleMoves(Board board)
	{
		List<Move> possibleMoves = new ArrayList<Move>();

		for (int dir=0; dir<8; dir++) {
			
			/*
			 * # 2 # 6 # 
			 * 3 # # # 7 
			 * # # N # # 
			 * 1 # # # 5 
			 * # 0 # 4 # 
			 * */
			int dx = (((dir&4) == 0)? -1: 1) * (((dir&1)==0)? 1: 2);
			int dy = (((dir&2) == 0)? -1: 1) * (((dir&1)==0)? 2: 1);
			
			
			int xPosNew = xPos + dx;
			int yPosNew = yPos + dy;
			Piece pieceOnNewSquare = null;
			
			if ((xPosNew&(~7)) == 0 && (yPosNew&(~7)) == 0)
			{
				pieceOnNewSquare = board.getPieceOnSquare(xPosNew, yPosNew);
				if (pieceOnNewSquare != null)
				{
					if (pieceOnNewSquare.getPlayer() != player)
						possibleMoves.add(new Move(this, pieceOnNewSquare, xPosNew, yPosNew, pieceOnNewSquare.type.getValue()));
				} else 
					possibleMoves.add(new Move(this, pieceOnNewSquare, xPosNew, yPosNew));
			}
		}
		return possibleMoves;
	}
}
