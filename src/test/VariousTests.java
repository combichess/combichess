package test;

//import static org.junit.Assert.*;

import org.testng.annotations.*;

import java.util.List;

import system.board.PlayerColour;
import system.move.Move;
import system.move.MoveType;
import system.piece.Piece;
import system.piece.PieceType;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VariousTests {

	private static BoardTester testBoard = null;
	private static Piece whiteBPawn = null;
	private static Piece blackAPawn = null;
	private static Piece blackCPawn = null;
	private static Piece blackQueen = null;
	private static boolean setupIsExecuted = false;
	
	private static boolean printStatus = true;
	
	@BeforeTest
	public void initObjects()
	{
		if (!setupIsExecuted) {
			testBoard = new BoardTester();
			testBoard.clearAllData();
			//testBoard.standardSetup();
			PieceType.setPieceValues(new int[] {1, 3, 4, 5, 9, 100});
			setupIsExecuted = true;
		}
	}
	
	@Test
	public void testVarious01() {
		testBoard.addPiece(0, 0, PlayerColour.White, PieceType.Rook, false);
		testBoard.addPiece(2, 0, PlayerColour.White, PieceType.Bishop, false);
		testBoard.addPiece(4, 0, PlayerColour.White, PieceType.King, false);
		testBoard.addPiece(5, 0, PlayerColour.White, PieceType.Bishop, false);
		testBoard.addPiece(7, 0, PlayerColour.White, PieceType.Rook, false);
		
		testBoard.addPiece(0, 1, PlayerColour.White, PieceType.Pawn, false);
		testBoard.addPiece(5, 1, PlayerColour.White, PieceType.Pawn, false);
		testBoard.addPiece(6, 1, PlayerColour.White, PieceType.Pawn, false);
		testBoard.addPiece(7, 1, PlayerColour.White, PieceType.Pawn, false);
		
		testBoard.addPiece(0, 2, PlayerColour.White, PieceType.Pawn, true);
		testBoard.addPiece(1, 2, PlayerColour.White, PieceType.Queen, true);
		testBoard.addPiece(2, 2, PlayerColour.White, PieceType.Knight, true);
		testBoard.addPiece(5, 2, PlayerColour.White, PieceType.Knight, true);

		testBoard.addPiece(2,  5, PlayerColour.White, PieceType.Pawn, true);
		testBoard.addPiece(4,  4, PlayerColour.White, PieceType.Pawn, true);
		
		/*
		BR,BN,  ,BQ,BK,BB,BN,BR
		  ,  ,  ,  ,BP,BP,BP,BP,
		  ,  ,WP,  ,  ,  ,  ,  ,
		  ,  ,  ,  ,WP,  ,  ,  ,
		  ,  ,  ,  ,  ,  ,BB,  ,
		WP,WQ,WN,  ,  ,WN,  ,  ,
		WP,  ,  ,  ,  ,WP,WP,WP,
		WR,  ,WB,  ,WK,WB,  ,WR,
		*/
		
		testBoard.addPiece(0,  7, PlayerColour.Black, PieceType.Rook, false);
		testBoard.addPiece(1,  7, PlayerColour.Black, PieceType.Knight, false);
		testBoard.addPiece(3,  7, PlayerColour.Black, PieceType.Queen, false);
		testBoard.addPiece(4,  7, PlayerColour.Black, PieceType.King, false);
		testBoard.addPiece(5,  7, PlayerColour.Black, PieceType.Bishop, false);
		testBoard.addPiece(6,  7, PlayerColour.Black, PieceType.Knight, false);
		testBoard.addPiece(7,  7, PlayerColour.Black, PieceType.Rook, false);
		
		testBoard.addPiece(4,  6, PlayerColour.Black, PieceType.Pawn, false);
		testBoard.addPiece(5,  6, PlayerColour.Black, PieceType.Pawn, false);
		testBoard.addPiece(6,  6, PlayerColour.Black, PieceType.Pawn, false);
		testBoard.addPiece(7,  6, PlayerColour.Black, PieceType.Pawn, false);
		
		testBoard.addPiece(6,  3, PlayerColour.Black, PieceType.Bishop, true);
		
		System.out.println("Vit ska räkna ut sitt drag");
		Move moveW = testBoard.findBestMoveFor(PlayerColour.White, 5);
		System.out.println("Vits drag: " + moveW);
		System.out.println("Svart ska räkna ut sitt drag");
		Move moveB = testBoard.findBestMoveFor(PlayerColour.Black, 5);
		System.out.println("Vits drag: " + moveB);
		
		System.out.println(testBoard.toString());
		/*skicka följande sträng från Board till Gui: 

			
			BR,BN,  ,BQ,BK,BB,BN,BR
			  ,  ,  ,  ,BP,BP,BP,BP,
			  ,  ,WP,  ,  ,  ,  ,  ,
			  ,  ,  ,  ,WP,  ,  ,  ,
			  ,  ,  ,  ,  ,  ,BB,  ,
			WP,WQ,WN,  ,  ,WN,  ,  ,
			WP,  ,  ,  ,  ,WP,WP,WP,
			WR,  ,WB,  ,WK,WB,  ,WR,
			
			
			
			WR,  ,WB,  ,WK,WB,  ,WR,
			WP,  ,  ,  ,  ,WP,WP,WP,
			WP,WQ,WN,  ,  ,WN,  ,  ,
			  ,  ,  ,  ,  ,  ,BB,  ,
			  ,  ,  ,  ,WP,  ,  ,  ,
			  ,  ,WP,  ,  ,  ,  ,  ,
			  ,  ,  ,  ,BP,BP,BP,BP,
			BR,BN,  ,BQ,BK,BB,BN,BR
			

			Board har tagit emot ett meddelande som lyder: From: "Gui"	To: "Board	MessageType: "Propose Move"	Data: "B,5"

			Exception in thread "t-board" java.lang.NullPointerException
				at system.move.Move.addValueFromNextMove(Move.java:137)
				at system.board.Board.findBestMove(Board.java:416)
				at system.board.Board.findBestMove(Board.java:415)
				at system.board.Board.findBestMove(Board.java:415)
				at system.board.Board.findBestMoveFor(Board.java:437)
				at system.board.BoardWrapper.proposeMove(BoardWrapper.java:234)
				at system.board.BoardWrapper.run(BoardWrapper.java:55)
				at java.lang.Thread.run(Unknown Source)
		 */

	}

	
	private void print()
	{
		if (printStatus)
			System.out.println(testBoard.toString());
	}
	 
	@AfterTest
	public void destroy()
	{
	}
}
