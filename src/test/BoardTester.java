package test;

import system.board.Board;
import system.board.PlayerColour;
import system.move.Move;
import system.piece.PieceType;

public class BoardTester extends Board{
	BoardTester() {
		super();
		
	}
	
	public void clearAllData()
	{
		clearSetup();
	}
	
	public void addPiece(int xPos, int yPos, PlayerColour player, PieceType type)
	{
		super.addPiece(xPos, yPos, player, type);
	}
	
	public Move findBestMoveFor(PlayerColour col, int N)
	{
		return super.findBestMoveFor(col, N);
	}
}
