package system.board;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import system.board.PlayerColour;
import system.move.Move;
import system.piece.Bishop;
import system.piece.ChessNotation;
import system.piece.King;
import system.piece.Knight;
import system.piece.Pawn;
import system.piece.PieceType;
import system.piece.Queen;
import system.piece.Rook;

import system.piece.Piece;



public class Board { 
	
	protected LinkedList<Move> committedMoves;
	protected List<Piece> blacks;
	protected List<Piece> whites;
	protected Piece squares[];
	protected Player playerBlack;
	protected Player playerWhite;
	
		// setup
	protected Board(Player white, Player black)
	{
		blacks = new LinkedList<Piece>();
		whites = new LinkedList<Piece>();
		squares = new Piece[64];
		committedMoves = new LinkedList<Move>();
		playerWhite = white;
		playerBlack = black;
	}
	
	public Piece getPieceOnSquare(int x, int y)
	{
		return squares[(y<<3) + x];
	}

	protected void setPieceOnSquare(int xPos, int yPos, Piece piece)
	{
		squares[(yPos<<3) + xPos] = piece;
	}
	
	
	protected void addPiece(int xPos, int yPos, PlayerColour player, PieceType type) {
		Piece newPiece = null;
		
		if ((xPos & (~7)) != 0 || (yPos & (~7)) != 0)
			return;	// throw exception
		
		if (getPieceOnSquare(xPos, yPos) != null)
			return;
		
		switch(type) 
		{
		case Pawn:
			newPiece = new Pawn(xPos, yPos, player);
			break;
		case Knight:
			newPiece = new Knight(xPos, yPos, player);
			break;
		case Bishop:
			newPiece = new Bishop(xPos, yPos, player);
			break;
		case Rook:
			newPiece = new Rook(xPos, yPos, player);
			break;
		case Queen:
			newPiece = new Queen(xPos, yPos, player);
			break;
		case King:
			newPiece = new King(xPos, yPos, player);
			break;
		}
		
		(player == PlayerColour.White? whites: blacks).add(newPiece);
		setPieceOnSquare(xPos, yPos, newPiece);
	}
	
	protected boolean fullTest()
	{
		int numOfErrors = 0;
		List<Piece> allPieces = new LinkedList<Piece>();
		for (Piece white :whites)
			if (white.getActivity())
				allPieces.add(white);
		for (Piece black :blacks)
			if (black.getActivity())
				allPieces.add(black);
		
		int numOfNullSquares = 0;
		
		for (int i=0; i<64; i++)
		{
			Piece square = squares[i];
			if (square == null)
				numOfNullSquares++;
					
			int numOfPiecesOnI = 0;
			for (Piece piece: allPieces)
			{
				int x = piece.getX();
				int y = piece.getY();
				int pos = x + 8*y;

				if (pos == i && piece != null)
					numOfPiecesOnI++;
				
				if (pos == i && square != piece)
				{
					System.out.println("Full Test Error(" + (++numOfErrors) + "): squares[" + i + "] = " + square + "\t!= piece " + piece);
				}
			}
			if (numOfPiecesOnI > 1)
				System.out.println("Full Test Error(" + (++numOfErrors) + "): " + numOfPiecesOnI + " pieces on square " + i);
		}
		
		
		for (Piece piece: allPieces) 
		{
			int x = piece.getX();
			int y = piece.getY();
			if (x < 0 || x > 7 || y < 0 || y > 7)
				System.out.println("Full Test Error(" + (++numOfErrors) + "): Illegal positioning on piece: " + piece + "\tx = " + x + "\ty = " + y);
		}
		
		if (64 - numOfNullSquares != allPieces.size())
		{
			System.out.println("Full Test Error(" + (++numOfErrors) + "): Occupied squares = " + (64-numOfNullSquares) + " != num of pieces = " + allPieces.size());
		}
		
		
		return numOfErrors == 0;
	}
	
	protected int commitMove(Move moveToCommit)
	{
		committedMoves.add(moveToCommit);
		
		int oldXPos = moveToCommit.getPiece().getX();
		int oldYPos = moveToCommit.getPiece().getY();
		
		int dx = moveToCommit.getXmove();
		int dy = moveToCommit.getYmove();
		
		int newXPos = oldXPos + dx;
		int newYPos = oldYPos + dy;
		
		int newPosId = (newYPos<<3) + newXPos;
		int oldPosId = (oldYPos<<3) + oldXPos;
		
			// i fall det är en "en passant" så är den tagna pjäsen inte på samma position som den committande pjäsen vandrar till, därför utförs raden här nedan.
		Piece takenPiece = moveToCommit.getAffectedPiece();
		if (takenPiece != null) {
			squares[(takenPiece.getY() << 3) + takenPiece.getX()] = null;
			takenPiece.setActivity(false);
		}
		
		moveToCommit.getPiece().moveX(dx, dy);
		if (newPosId > 63 || newPosId < 0 || oldPosId > 63 || oldPosId < 0)
		{
			System.out.println("boardet now: " + this.toString());
			System.out.println("newPosId: " + newPosId + "\t oldPosId: " + oldPosId);
			// throw exception
			
		} 
		squares[newPosId] = moveToCommit.getPiece();
		squares[oldPosId] = null;
		
		return 0;
	}
	
	protected Move getHighestValueMove(List<Move> moves)
	{
		return null;
	}
	
	protected Move getLowestValueMove(List<Move> moves)
	{
		return null;
	}
	
	protected int uncommitLastMove()
	{
		//Move toUncommit = committedMoves.pop();
		Move toUncommit = committedMoves.removeLast();
		//System.out.println(toUncommit);
		
		Piece uncommittingPiece = toUncommit.getPiece();
		Piece takenPiece = toUncommit.getAffectedPiece();
		int presentPosition = uncommittingPiece.getPosition(); 
		int gammalPosition = presentPosition - toUncommit.getPositionChange();
		
		
		//System.out.println("nuvarande position: " + (toUncommit.getPiece().getPosition()%8) + ", " + (toUncommit.getPiece().getPosition()/8));
		//System.out.println("förändring: " + (toUncommit.getPositionChange()%8) + ", " + (toUncommit.getPositionChange()/8));
		//System.out.println("det ändras med: " + (gammalPosition%8) + ", " + (gammalPosition/8));

		int positionChangeBack = -toUncommit.getPositionChange();		
		uncommittingPiece.moveX(positionChangeBack);
		squares[gammalPosition] = uncommittingPiece;
		squares[presentPosition] = takenPiece;
		if (takenPiece != null)
			takenPiece.setActivity(true);
			
		return 0;
	}
	
	protected void clearSetup()
	{
		committedMoves.clear();
		blacks.clear();
		whites.clear();
		for (int i=0; i<squares.length; i++)
			squares[i] = null;
	}
	
	protected void standardSetup()
	{
		clearSetup();
		
		for (int i=0; i<8; i++)
		{
			addPiece(i, 1, PlayerColour.White, PieceType.Pawn);
			addPiece(i, 6, PlayerColour.Black, PieceType.Pawn);
		}
		for (int i=0; i<2; i++)
		{
			PlayerColour plr = i==0? PlayerColour.White: PlayerColour.Black;
			
			addPiece(0, i*7, plr, PieceType.Rook);
			addPiece(1, i*7, plr, PieceType.Knight);
			addPiece(2, i*7, plr, PieceType.Bishop);
			addPiece(3, i*7, plr, PieceType.Queen);
			addPiece(4, i*7, plr, PieceType.King);
			addPiece(5, i*7, plr, PieceType.Bishop);
			addPiece(6, i*7, plr, PieceType.Knight);
			addPiece(7, i*7, plr, PieceType.Rook);	
		}
		
	}
	
	protected Piece getPiece(int horizontal, int vertical) 
	{
		return squares[horizontal - 9 + 8*vertical];
	}
	
	public String toString()
	{
		String str = "";
		char c = 'A';
		c--;
		for (int x=1; x<=8; x++)
			str += "   " + ((char)(c+x)) + ((x==8)? "\n": "\t");
		for (int y=8; y>0; y--)
		{
			str += "" + y + "[";
			for (int x=1; x<=8; x++)
			{
				Piece tjena = getPiece(x, y);
				if (tjena != null)
					str += tjena.toString(ChessNotation.ALGEBRAIC);
				str += (x<8)? "\t": "";
			}
			
			str += "\t]" + y + "\n";
		}	
		for (int x=1; x<=8; x++)
			str += "   " + ((char)(c+x)) + ((x==8)? "\n": "\t");
		return str;
	}
	
	protected List<Move> getAllPossibleMovesFor(PlayerColour colour) {
		List<Move> possibleMoves = new ArrayList<Move>();
			
			//sätt upp spelarens valueTable(pjäsvärden) i PieceType.java så att det snabbt kan returnera rätt värde på 
		PieceType.setPieceValues((colour == PlayerColour.White? playerWhite: playerBlack).getValueTable());
		
		//Piece[] playerPieces = (colour == PlayerColour.White)? whites: blacks;
		List<Piece> playerPieces = (colour == PlayerColour.White)? whites: blacks;
		for (Piece piece : playerPieces) {
			if (piece != null && piece.getActivity()) {
				possibleMoves.addAll(piece.getPossibleMoves(this));
			}
		}
		
		return possibleMoves;
	}
	
		// returnerar alla möjliga tillåtna positioner colour kan flytta från
		// fixa denna senare, kolla upp om spelare står schack eller om flytt av pjäs kan flytta utan att ställa sig själv schack
	protected List<Integer> getAllPossibleAllowedSquaresToMoveFrom(PlayerColour colour)
	{
		List<Integer> positioner = new LinkedList<Integer>();
		for (Piece piece : squares)
		{
			if (piece != null && piece.getActivity() && piece.getPlayer() == colour)
				positioner.add(piece.getPosition());
		}
		return positioner;
	}
	
		// returnerar alla möjliga platser pjäs kan flytta till.
		// fixa denna senare på samma sätt som ovan.
	protected List<Integer> getAllPossibleAllowedSquaresToMoveToWith(Piece piece)
	{
		List<Integer> positioner = new LinkedList<Integer>();
		List<Move> moves = piece.getPossibleMoves(this);
		for (Move move: moves)
			positioner.add(move.getToPos());
		
		return positioner;
	}
	
	static private Move getBestMoveFromList(List<Move> moves)
	{
		Move bestMove = moves.get(0);
		int bestValue = bestMove.getValue();
		
		for (Move move: moves)
		{
			if (move.getValue() > bestValue) {
				bestMove = move;
				bestValue = move.getValue();
			}
		}
		
		return bestMove;		
	}
	
	private Move findBestMove(PlayerColour colour)
	{
		List<Move> moves = getAllPossibleMovesFor(colour);
		return getBestMoveFromList(moves);
	}
	
	private Move findBestMove(PlayerColour colour, int N)
	{
		Move rekordMove = null;

		if (N == 1)
			return findBestMove(colour);
		
		List<Move> moves = getAllPossibleMovesFor(colour);
		int rekordMoveValue = -100000000;
		
		for (Move thisMove : moves)
		{
				// utför move
			commitMove(thisMove);

			Move nextMove = findBestMove(colour.getOpponentColour(), N-1);
			thisMove.addValueFromNextMove(nextMove);
			int nyttMoveValue = thisMove.getValue(); 

			if (nyttMoveValue > rekordMoveValue)
			{
				rekordMove = thisMove;
				rekordMoveValue = nyttMoveValue;
			}
			
				// dra tillbaka senaste move
			uncommitLastMove();
		}

		return rekordMove;
	}
	
	protected Move findBestMoveFor(Player player, int N)
	{		
			// sätt värdering av pjäserna åt spelaren som spelar.
		PieceType.setPieceValues(player.getValueTable());
		PlayerColour colour = player == playerWhite? PlayerColour.White: PlayerColour.Black;
		Move bestMove = findBestMove(colour, N);
	
		// nollställ värderingen av pjäserna när spelaren letat färdigt
		PieceType.unsetPieceValues();
		return bestMove;
	}
}
