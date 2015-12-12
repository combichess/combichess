package test;

import org.testng.annotations.*;

import system.board.PlayerColour;
import system.move.Move;
import system.move.Moves;
import system.piece.King;
import system.piece.Pawn;
import system.piece.Piece;
import system.piece.PieceType;
import system.piece.Rook;

public class CheckAndPromotionTest {

	private BoardTester testBoard = null;
	private Piece blackPawn = null;
	private Piece whiteKing = null;
	
	//@BeforeTest
	@BeforeMethod
	public void initObjects()
	{
		testBoard = new BoardTester();
		testBoard.clearAllData();
		
		testBoard.addPiece(2, 1, PlayerColour.White, Pawn.class, false);
		testBoard.addPiece(3, 1, PlayerColour.White, Pawn.class, false);
		testBoard.addPiece(4, 1, PlayerColour.White, Pawn.class, false);
		testBoard.addPiece(5, 1, PlayerColour.White, Pawn.class, false);
		testBoard.addPiece(6, 1, PlayerColour.White, Pawn.class, false);
		testBoard.addPiece(7, 1, PlayerColour.White, Pawn.class, false);
		testBoard.addPiece(3, 0, PlayerColour.White, King.class, true);
		testBoard.addPiece(5, 0, PlayerColour.White, Rook.class, true);
		testBoard.addPiece(2, 5, PlayerColour.White, Rook.class, true);
		
		testBoard.addPiece(0, 1, PlayerColour.Black, Pawn.class, true);
		testBoard.addPiece(6, 7, PlayerColour.Black, King.class, true);
		testBoard.addPiece(7, 7, PlayerColour.Black, Rook.class, true);
		testBoard.addPiece(3, 6, PlayerColour.Black, Pawn.class, false);
		testBoard.addPiece(4, 6, PlayerColour.Black, Pawn.class, false);
		testBoard.addPiece(5, 6, PlayerColour.Black, Pawn.class, false);
		testBoard.addPiece(6, 6, PlayerColour.Black, Pawn.class, false);
		testBoard.addPiece(7, 6, PlayerColour.Black, Pawn.class, false);

		PieceType.setPieceValues(new int[] {1, 3, 4, 5, 9, 100});
		blackPawn = testBoard.getPieceOnSquare(0, 1);
		whiteKing = testBoard.getPieceOnSquare(3, 0);
	}
	
	
	/**
	 * kolla möjliga drag från B2, B3 och B4 ska existera, flytta till B4
	 */
	@Test
	public void testPromotionAndCheck01() {
		
		Move blackMove3 = testBoard.findBestMoveFor(PlayerColour.Black, 3);
		//print();
		
		assert(blackMove3.getMoveType().isPromotion());
		testBoard.commitMove_(blackMove3);
		//print();
		
		Moves allWhiteMoves = testBoard.getAllPossibleAllowedMovesFor(PlayerColour.White);
		assert(allWhiteMoves.size() == 0);	// == 0 means check mate or stale mate.
	}

	/**
	 * kolla så att man inte kan gå o sätta sig i schackat läge
	 */
	@Test
	public void testPromotionAndCheck02() {
		//print();
		//Moves moves = whiteKing.getPossibleMoves(testBoard);
		Moves moves = testBoard.getAllPossibleAllowedMovesFor(whiteKing.getPlayer()).getMovesByPiece(whiteKing);
		
		
		assert(moves.size() == 2);
		int xMove = (moves.get(0).getToPos()%7) - (moves.get(0).getFromPos()%7);
		Move moveLeft = moves.get(xMove == -1? 0: 1);
		testBoard.commitMove_(moveLeft);
		//print();


		moves = testBoard.getAllPossibleAllowedMovesFor(whiteKing.getPlayer()).getMovesByPiece(whiteKing);
		assert(moves.size() == 2);
		xMove = (moves.get(0).getToPos()%7) - (moves.get(0).getFromPos()%7);
		moveLeft = moves.get(xMove == -1? 0: 1);
		testBoard.commitMove_(moveLeft);
		//print();


		moves = blackPawn.getPossibleMoves(testBoard);
		Move promotionMove = moves.getRandomMove();
		testBoard.commitMove_(promotionMove);
		//print();
		
		moves = testBoard.getAllPossibleAllowedMovesFor(PlayerColour.White);
		assert(moves.size() == 3);
		
		Moves whiteKingMoves = moves.getMovesByPiece(whiteKing);
		assert(whiteKingMoves.size() == 2);
	}
	
	@Test
	public void testPromotionAndCheck03()
	{
		testBoard.standardSetup();
		final String[] movesStr = "d4,a5,a4,Ra6,e4,Rb6,f4,d6,g4,Nd7,h4,Rc6,b4,xb4,c3,Nh6,xb4,f6,g5,xg5,hxg5,Nf7,f5,Nb6,d5,Nxd5,xd5,Rb6,f6,Rxb4,g6,Bg4,Qc2,exf6,xf7,Kxf7,Rh4,Qe8,Kd2,Bf5,Qxf5,Rxh4,Nf3,Rh1,Bg2,g6,Qc2,Bh6,Kc3,Rxc1,Na3,Qe3,Kb2,Qb6,Qb3,Qxb3,Kxb3,Rxa1,a5,g5,Nb5,Rb1,Kc4,Rb2,Bf1,g4,Nfd4,Rb1,Be2,Re8,Bxg4,f5,Bf3,c5,Nxf5,Rb4,Kd3,c4,Kc3,Rb3,Kxc4,Rxf3,Nbxd6,Kg6,Nxe8,Rxf5,Nd6,Rg5,Nxb7,Kh5,a6,Bf8,a7,Rg4,Kb5,Bd6,a8,Rb4,Kc6,Bg3,Qa3,Rc4".split(",");
		PlayerColour pc = PlayerColour.White;
		
		for (String moveStr: movesStr)
		{
			//System.out.print(moveStr + ",");
			Move move = testBoard.getMoveFromString(moveStr, pc);
			testBoard.commitMove_(move);
			String boardBefore = testBoard.toString();
			
			testBoard.getMoveHistory();
			String boardAfter = testBoard.toString();
			assert(boardBefore.equals(boardAfter));
			pc = pc.getOpponentColour();
			
			if (!testBoard.securityCheck())
			{
				System.out.println("securityCheck = false");
			}
		}
	}
	
	@AfterTest
	public void destroy()
	{
	}
}
