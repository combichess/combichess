package test;

import org.testng.annotations.*;

import main.control.PlayerStatus;
import system.board.PlayerColour;
import system.piece.PieceType;

public class IsPieceThreateningPosition {
	
	private BoardTester testBoard = null;
	
	//@BeforeTest
	@BeforeMethod
	public void initObjects()
	{
		testBoard = new BoardTester();
		testBoard.addPiece(5,  3, PlayerColour.Black, PieceType.Bishop, true);
		testBoard.addPiece(6,  4, PlayerColour.Black, PieceType.Pawn, true);
		testBoard.addPiece(6,  5, PlayerColour.Black, PieceType.Pawn, true);
		testBoard.addPiece(2,  5, PlayerColour.Black, PieceType.Rook, true);
		testBoard.addPiece(4,  5, PlayerColour.Black, PieceType.Knight, true);
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
		String str1 = "";
		String str2 = "";
		for (int y = 7; y>=0; y--)
		{
			for (int x=0; x<8; x++)
			{
				int pos = x + y*8;
				boolean isThreatened = testBoard.isSquareThreatnedBy(pos, black);
				assert(shouldBeThreatened[7-y][x] == isThreatened);
				if (shouldBeThreatened[7-y][x] != isThreatened)
					System.out.println("Kolla upp ruta " + x + ", " + y);
				
				str1 += isThreatened? "x": " ";
				str2 += shouldBeThreatened[7-y][x]? "x": " ";
			}
			str1 += "\n";
			str2 += "\n";
		}
		System.out.println(str1);
		System.out.println("\n\n" + str2);
		System.out.println(testBoard);
	}
	
	@Test
	public void isPieceThreateningPositionTest02() {
		testBoard = new BoardTester();
		testBoard.addPiece(0,  0, PlayerColour.White, PieceType.Rook, false);
		testBoard.addPiece(1,  0, PlayerColour.White, PieceType.Knight, false);
		testBoard.addPiece(2,  0, PlayerColour.White, PieceType.Bishop, false);
		testBoard.addPiece(4,  0, PlayerColour.White, PieceType.King, false);
		testBoard.addPiece(5,  0, PlayerColour.White, PieceType.Bishop, false);
		testBoard.addPiece(6,  0, PlayerColour.White, PieceType.Knight, false);
		testBoard.addPiece(7,  0, PlayerColour.White, PieceType.Rook, false);
		
		testBoard.addPiece(0, 1, PlayerColour.White, PieceType.Pawn, false);
		testBoard.addPiece(1, 2, PlayerColour.White, PieceType.Pawn, true);
		testBoard.addPiece(2, 1, PlayerColour.White, PieceType.Pawn, false);
		testBoard.addPiece(5, 1, PlayerColour.White, PieceType.Pawn, false);
		testBoard.addPiece(6, 1, PlayerColour.White, PieceType.Pawn, false);
		testBoard.addPiece(7, 1, PlayerColour.White, PieceType.Pawn, false);
		
		testBoard.addPiece(1, 6, PlayerColour.White, PieceType.Queen, true);
		
		// ----------------------------
		
		testBoard.addPiece(0,  7, PlayerColour.Black, PieceType.Rook, false);
		testBoard.addPiece(1,  7, PlayerColour.Black, PieceType.Knight, false);
		testBoard.addPiece(6,  7, PlayerColour.Black, PieceType.Knight, false);
		testBoard.addPiece(7,  7, PlayerColour.Black, PieceType.Rook, false);
		
		testBoard.addPiece(0,  6, PlayerColour.Black, PieceType.Pawn, false);
		testBoard.addPiece(5,  6, PlayerColour.Black, PieceType.Pawn, false);
		testBoard.addPiece(7,  6, PlayerColour.Black, PieceType.Pawn, false);
		
		testBoard.addPiece(3, 5, PlayerColour.Black, PieceType.Bishop, false);
		testBoard.addPiece(4, 5, PlayerColour.Black, PieceType.King, true);
		testBoard.addPiece(6, 5, PlayerColour.Black, PieceType.Pawn, false);
		
		testBoard.addPiece(5, 4, PlayerColour.Black, PieceType.Queen, false);
		
		// -----------------------------------------
		PlayerStatus whiteStatus = testBoard.getPlayerStatus(PlayerColour.White);
		PlayerStatus blackStatus = testBoard.getPlayerStatus(PlayerColour.Black);
		
		System.out.println("white: " + whiteStatus);
		System.out.println("black: " + blackStatus);
		
	}
}
