package test;

import java.util.List;

import system.board.Board;
import system.board.PlayerColour;
import system.move.Move;
import system.move.Moves;
import system.piece.PieceType;

public class BoardTester extends Board{
	BoardTester() {
		super();
		
	}
	
	@Override
	public Moves getAllPossibleAllowedMovesFor(PlayerColour colour)
	{
		return super.getAllPossibleAllowedMovesFor(colour);
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
	
	@Override
	public void addPiece(int xPos, int yPos, PlayerColour player, PieceType type, boolean movedBefore)
	{
		super.addPiece(xPos, yPos, player, type, movedBefore);
	}
	
	@Override
	public Move findBestMoveFor(PlayerColour col, int N)
	{
		return super.findBestMoveFor(col, N);
	}
	
	@Override
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
