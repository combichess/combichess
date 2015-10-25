package test;

import java.util.List;

import system.board.Board;
import system.board.PlayerColour;
import system.move.Move;
import system.piece.PieceType;

public class BoardTester extends Board{
	BoardTester() {
		super();
		
	}
	
	public List<Integer> getPossibleMoves(PlayerColour colour)
	{
		return super.getAllPossibleAllowedSquaresToMoveFrom(colour);
		//return getAllPossibleMovesFor(colour);
	}
	
	public void clearAllData()
	{
		clearSetup();
	}
	
	public void addPiece(int xPos, int yPos, PlayerColour player, PieceType type, boolean movedBefore)
	{
		super.addPiece(xPos, yPos, player, type, movedBefore);
	}
	
	public Move findBestMoveFor(PlayerColour col, int N)
	{
		return super.findBestMoveFor(col, N);
	}
	
	public void standardSetup()
	{
		super.standardSetup();
	}
	
	public void commitMove_(Move moveToCommit)
	{
		super.commitMove(moveToCommit);
	}
	
	public void uncommit()
	{
		super.uncommitLastMove();
	}

}
