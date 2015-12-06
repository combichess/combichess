package test;

import org.testng.annotations.*;

import main.control.PlayerStatus;
import system.board.PlayerColour;
import system.piece.Bishop;
import system.piece.King;
import system.piece.Knight;
import system.piece.Pawn;
import system.piece.Queen;
import system.piece.Rook;

public class IsPieceThreateningPosition {
	
	private BoardTester testBoard = null;
	
	//@BeforeTest
	@BeforeMethod
	public void initObjects()
	{
		testBoard = new BoardTester();
		testBoard.addPiece(5,  3, PlayerColour.Black, Bishop.class, true);
		testBoard.addPiece(6,  4, PlayerColour.Black, Pawn.class, true);
		testBoard.addPiece(6,  5, PlayerColour.Black, Pawn.class, true);
		testBoard.addPiece(2,  5, PlayerColour.Black, Rook.class, true);
		testBoard.addPiece(4,  5, PlayerColour.Black, Knight.class, true);
	}
	
	@Test
	public void isPieceThreateningPositionTest01()
	{
		boolean t = true;
		boolean f = false;
		
		boolean[][] shouldBeThreatened = new boolean[][] {
			new boolean[] {f, t, t, t, f, t, f, f},
			new boolean[] {f, f, t, f, f, f, t, f}, 
			new boolean[] {t, t, f, t, t, f, f, f}, 
			new boolean[] {f, f, t, f, t, t, t, t}, 
			new boolean[] {f, f, t, t, f, t, f, t}, 
			new boolean[] {f, f, t, f, t, f, t, f}, 
			new boolean[] {f, f, t, t, f, f, f, t}, 
			new boolean[] {f, f, t, f, f, f, f, f}
		};
		

		PlayerColour black = PlayerColour.Black;
		for (int y = 7; y>=0; y--)
		{
			for (int x=0; x<8; x++)
			{
				int pos = x + y*8;
				boolean isThreatened = testBoard.isSquareThreatnedBy(pos, black);
				assert(shouldBeThreatened[7-y][x] == isThreatened);
			}
		}
	}
	
	@Deprecated
	@Test
	public void isPieceThreateningPositionTest02() {
		testBoard = new BoardTester();
		testBoard.addPiece(0,  0, PlayerColour.White, Rook.class, false);
		testBoard.addPiece(1,  0, PlayerColour.White, Knight.class, false);
		testBoard.addPiece(2,  0, PlayerColour.White, Bishop.class, false);
		testBoard.addPiece(4,  0, PlayerColour.White, King.class, false);
		testBoard.addPiece(5,  0, PlayerColour.White, Bishop.class, false);
		testBoard.addPiece(6,  0, PlayerColour.White, Knight.class, false);
		testBoard.addPiece(7,  0, PlayerColour.White, Rook.class, false);
		
		testBoard.addPiece(0, 1, PlayerColour.White, Pawn.class, false);
		testBoard.addPiece(1, 2, PlayerColour.White, Pawn.class, true);
		testBoard.addPiece(2, 1, PlayerColour.White, Pawn.class, false);
		testBoard.addPiece(5, 1, PlayerColour.White, Pawn.class, false);
		testBoard.addPiece(6, 1, PlayerColour.White, Pawn.class, false);
		testBoard.addPiece(7, 1, PlayerColour.White, Pawn.class, false);
		
		testBoard.addPiece(1, 6, PlayerColour.White, Queen.class, true);
		
		// ----------------------------
		
		testBoard.addPiece(0,  7, PlayerColour.Black, Rook.class, false);
		testBoard.addPiece(1,  7, PlayerColour.Black, Knight.class, false);
		testBoard.addPiece(6,  7, PlayerColour.Black, Knight.class, false);
		testBoard.addPiece(7,  7, PlayerColour.Black, Rook.class, false);
		
		testBoard.addPiece(0,  6, PlayerColour.Black, Pawn.class, false);
		testBoard.addPiece(5,  6, PlayerColour.Black, Pawn.class, false);
		testBoard.addPiece(7,  6, PlayerColour.Black, Pawn.class, false);
		
		testBoard.addPiece(3, 5, PlayerColour.Black, Bishop.class, false);
		testBoard.addPiece(4, 5, PlayerColour.Black, King.class, true);
		testBoard.addPiece(6, 5, PlayerColour.Black, Pawn.class, false);
		
		testBoard.addPiece(5, 4, PlayerColour.Black, Queen.class, false);
		
		// -----------------------------------------
		PlayerStatus whiteStatus = testBoard.getPlayerStatus(PlayerColour.White);
		PlayerStatus blackStatus = testBoard.getPlayerStatus(PlayerColour.Black);
		
		System.out.println("white: " + whiteStatus);
		System.out.println("black: " + blackStatus);
		
	}
}
