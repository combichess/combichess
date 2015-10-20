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

	/*
	 * Junit-test för bonden av schackbonden (pawn-tests, double step, en passant och uncommit)

	kolla möjliga drag från B2, B3 och B4 ska existera
	kolla möjliga drag från A7, A6 och A5 ska existera
	B2->B4        A7->A5
	kolla möjliga drag från B4, B5 och A5 ska existera
	B4->B5        C7->C5
	kolla möjliga drag från B5, B4 och C4 men inte A4 ska existera
	B5->C6
	kolla att C5 inte existerar
	uncommitMove
	kolla att B5 och C5 är identisk med tidigare.
	B5->C6        C8->A6
	C6->C7        A6->B5
	kolla att det finns 3 lägen gånger 4 möjligheter till promotion
	kolla så att det höga värden på dragen
	C7->C8        D8->xC8
	uncommit moves tillbaka till startposition och jämför med startposition
	 * */
	
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
			testBoard.standardSetup();
			PieceType.setPieceValues(new int[] {1, 3, 4, 5, 9, 100});
			setupIsExecuted = true;
		}
	}
	
	@Test
	public void testVarious01() {
		/*skicka följande sträng från Board till Gui: 

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
