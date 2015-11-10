package test;

import org.testng.annotations.*;

import java.util.List;

import system.board.PlayerColour;
import system.move.Move;
import system.move.MoveType;
import system.piece.Piece;
import system.piece.PieceType;

public class IsPieceThreateningPosition {
	
	private BoardTester testBoard = null;
	private Piece blackPawn = null;
	private Piece whiteKing = null;
	private static boolean printStatus = true;
	
	
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
			new boolean[] {f, t, f, t, f, t, f, f},
			new boolean[] {f, f, t, f, f, f, t, f}, 
			new boolean[] {t, t, f, t, f, t, f, f}, 
			new boolean[] {f, f, t, f, t, t, f, t}, 
			new boolean[] {f, f, t, t, f, f, f, t}, 
			new boolean[] {f, f, t, f, t, f, t, f}, 
			new boolean[] {f, f, t, t, f, f, f, t}, 
			new boolean[] {f, f, t, f, f, f, f, f}
		};
		
		PlayerColour black = PlayerColour.Black;
		for (int x = 0; x<8; x++)
		{
			for (int y=0; y<8; y++)
			{
				int pos = x + y*8;
				boolean isThreatened = testBoard.isSquareThreatnedBy(pos, black);
				if (shouldBeThreatened[y][x] != isThreatened)
					System.out.println("Kolla upp ruta " + x + ", " + y);
			}
		}
	}
}
