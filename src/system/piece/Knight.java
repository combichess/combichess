package system.piece;

import system.board.Board;
import system.board.PlayerColour;
import system.move.Move;
import system.move.Moves;

public class Knight extends Piece {

	public Knight(int xPos, int yPos, PlayerColour player) {
		super(xPos, yPos, player);
	}

	@Override
	public Moves getPossibleMoves(Board board)
	{
		Moves possibleMoves = new Moves();

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
						possibleMoves.add(new Move(this, pieceOnNewSquare, xPosNew, yPosNew, pieceOnNewSquare.getValue()));
				} else 
					possibleMoves.add(new Move(this, pieceOnNewSquare, xPosNew, yPosNew));
			}
		}
		return possibleMoves;
	}

	@Override
	public boolean isPieceThreateningPosition(int squarePos, Piece[] squares) {
		
		switch(((squarePos&7) - xPos) + ((squarePos/8) - yPos)*8)
		{
		case 17:
		case 15:
		case 10:
		case 6:
		case -6:
		case -10:
		case -15:
		case -17:
			return true;
		}
		return false;
	}

	@Override
	public String getLetter() {
		return "N";
	}
}
