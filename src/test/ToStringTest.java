package test;

import java.util.List;

import org.testng.annotations.*;

import system.board.PlayerColour;
import system.move.Move;
import system.piece.Piece;

public class ToStringTest {

	private BoardTester testBoard = null;
	
	@BeforeMethod
	public void initObjects()
	{
		testBoard = new BoardTester();
		testBoard.clearAllData();
		testBoard.standardSetup();
	}
	
	@Test
	public void test01() {
		for (int i=0; i<4; i++)
		{
			testBoard.commitMove_(testBoard.findBestMoveFor(PlayerColour.White, 4));
			testBoard.commitMove_(testBoard.findBestMoveFor(PlayerColour.Black, 4));
		}
		
		List<String> str = testBoard.toString2();
		List<String> str2 = testBoard.toString2();
		List<String> str3 = testBoard.toString2();
		
		assert(str.equals(str2));
		assert(str.equals(str3));
	}
	
	@Test
	public void test02() {
		Piece blackPawnG = testBoard.getPieceOnSquare(6 + 6*8);
		Piece blackPawnH = testBoard.getPieceOnSquare(7 + 6*8);
		
		testBoard.commitMove_(testBoard.getAllPossibleAllowedMovesFor(PlayerColour.White).getMovesToPos(3 + 2*8).get(0));
		
		testBoard.commitMove_(blackPawnG.getPossibleMoves(testBoard).get(0));
		testBoard.commitMove_(testBoard.getAllPossibleAllowedMovesFor(PlayerColour.White).getMovesToPos(3 + 1*8).getMovesFromPos(1 + 0*8).get(0));
		testBoard.commitMove_(blackPawnG.getPossibleMoves(testBoard).get(0));
		testBoard.commitMove_(testBoard.getAllPossibleAllowedMovesFor(PlayerColour.White).getMovesToPos(5 + 2*8).getMovesFromPos(6 + 0*8).get(0));
		
		List<String> str = testBoard.toString2();
		List<String> str2 = testBoard.toString2();
		List<String> str3 = testBoard.toString2();
		
		assert(str.equals(str2));
		assert(str.equals(str3));
		assert(str.get(4).equals("Ngf3"));
		
		
		testBoard.commitMove_(blackPawnG.getPossibleMoves(testBoard).get(0));
		testBoard.commitMove_(testBoard.getAllPossibleAllowedMovesFor(PlayerColour.White).getMovesToPos(1 + 2*8).getMovesFromPos(3 + 1*8).get(0));
		
		testBoard.commitMove_(blackPawnG.getPossibleMoves(testBoard).getMovesByValue(0).get(0));
		testBoard.commitMove_(testBoard.getAllPossibleAllowedMovesFor(PlayerColour.White).getMovesToPos(6 + 2*8).getMovesFromPos(7 + 1*8).get(0));
		
		testBoard.commitMove_(blackPawnH.getPossibleMoves(testBoard).getMovesToPos(7 + 5*8).get(0));
		testBoard.commitMove_(testBoard.getAllPossibleAllowedMovesFor(PlayerColour.White).getMovesToPos(3 + 3*8).getMovesFromPos(1 + 2*8).get(0));
		
		str = testBoard.toString2();
		assert(str.get(8).equals("hxg3"));
		assert(str.get(10).equals("Nbd4"));
		
		
		
		testBoard.commitMove_(blackPawnH.getPossibleMoves(testBoard).get(0));
		testBoard.commitMove_(testBoard.getAllPossibleAllowedMovesFor(PlayerColour.White).getMovesToPos(3 + 1*8).getMovesFromPos(5 + 2*8).get(0));
		
		testBoard.commitMove_(blackPawnH.getPossibleMoves(testBoard).get(0));
		testBoard.commitMove_(testBoard.getAllPossibleAllowedMovesFor(PlayerColour.White).getMovesToPos(5 + 2*8).getMovesFromPos(3 + 1*8).get(0));
		
		str = testBoard.toString2();
		assert(str.get(14).equals("N2f3"));
		
		///// Återställ allting och gör om allting med utskriften från .toString2()
		testBoard.standardSetup();
		
		PlayerColour pc = PlayerColour.White;
		for (String moveStr: str)
		{
			Move move = testBoard.getMoveFromString(moveStr, pc);
			pc = pc.getOpponentColour();
			testBoard.commitMove_(move);
		}
		
		str2 = testBoard.toString2();
		assert(str.equals(str2));
	}
}
