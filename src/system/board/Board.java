package system.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import system.board.PlayerColour;
import system.move.Move;
import system.move.Moves;
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
	protected LinkedList<Piece> blacks;
	protected LinkedList<Piece> whites;
	protected Piece squares[];
	protected Player playerBlack;
	protected Player playerWhite;
	protected int moveNumber;
	
		// setup
	protected Board()
	{
		blacks = new LinkedList<>();
		whites = new LinkedList<>();
		squares = new Piece[64];
		committedMoves = new LinkedList<Move>();
		playerWhite = new Player();
		playerBlack = new Player();
	}
	
	public Piece getPieceOnSquare(int x, int y)
	{
		return squares[(y<<3) + x];
	}
	
	public Piece getPieceOnSquare(int pos)
	{
		return squares[pos];
	}

	protected void setPieceOnSquare(int xPos, int yPos, Piece piece)
	{
		squares[(yPos<<3) + xPos] = piece;
	}
	
	public int getMoveNumber()
	{
		return moveNumber;
	}
	
	public Move getLastCommittedMove()	// anv�nds enbart i En passant-checkar
	{
		return committedMoves.getLast();
	}
	
	protected void addPiece(int xPos, int yPos, PlayerColour player, PieceType type, boolean touchedBefore) {
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
		newPiece.setPreviousMoveNumber(touchedBefore? 0: Move.PREVIOUSLY_NEVER_MOVED);
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
		
			// <------------f�r�ndra allt detta, det �r fult skrivet och helt on�digt---
		int oldXPos = moveToCommit.getPiece().getX();
		int oldYPos = moveToCommit.getPiece().getY();
		
		int dx = moveToCommit.getXmove();
		int dy = moveToCommit.getYmove();
		
		int newXPos = oldXPos + dx;
		int newYPos = oldYPos + dy;
		
		int newPosId = (newYPos<<3) + newXPos;
		int oldPosId = (oldYPos<<3) + oldXPos;
			// --------------------------------------------->
			
		
			// i fall det �r en "en passant" s� �r den tagna pj�sen inte p� samma position som den committande pj�sen vandrar till, d�rf�r utf�rs raden h�r nedan.
		Piece takenPiece = moveToCommit.getAffectedPiece();
		if (takenPiece != null) {
			//squares[(takenPiece.getY() << 3) + takenPiece.getX()] = null;
			squares[takenPiece.getPosition()] = null;
			takenPiece.setActivity(false);
		}
		
		//moveToCommit.getPiece().moveX(dx, dy);
		moveToCommit.getPiece().moveX(dx + dy*8, moveNumber);
		if (newPosId > 63 || newPosId < 0 || oldPosId > 63 || oldPosId < 0)
		{
			System.out.println("boardet now:\n" + this.toString());
			System.out.println("newPosId: " + newPosId + "\t oldPosId: " + oldPosId);
			// throw exception
			
		} 
		
		if (moveToCommit.getMoveType().isPromotion()) {
			Piece oldPawn = moveToCommit.getPiece();
			//PlayerColour pc = moveToCommit.getPiece().getPlayer();
			PlayerColour pc = oldPawn.getPlayer();
			oldPawn.setActivity(false);
			Piece newPiece = null;
			switch(moveToCommit.getMoveType())
			{
			case PROMOTION_BISHOP:
				newPiece = new Bishop(newPosId%8, newPosId/8, pc);
				break;
			case PROMOTION_KNIGHT:
				newPiece = new Knight(newPosId%8, newPosId/8, pc);
				break;
			case PROMOTION_ROOK:
				newPiece = new Rook(newPosId%8, newPosId/8, pc);
				break;
			default:
				newPiece = new Queen(newPosId%8, newPosId/8, pc);
				break;
			}
			squares[newPosId] = newPiece; 
			(pc == PlayerColour.White? whites: blacks).add(newPiece);	// byt till addLast f�r att vara mer specifik, nya pj�ser l�ggs o tas bort sist i k�n.
		} else {
			squares[newPosId] = moveToCommit.getPiece();
		}
		squares[oldPosId] = null;
		
		moveNumber++;
		return 0;
	}
	
	protected int uncommitLastMove()
	{
		Move toUncommit = committedMoves.removeLast();
		
		Piece uncommittingPiece = toUncommit.getPiece();
		Piece takenPiece = toUncommit.getAffectedPiece();
		int posPostCommit = uncommittingPiece.getPosition(); 
		int posPreCommit = posPostCommit - toUncommit.getPositionChange();
		
		
		//System.out.println("nuvarande position: " + (toUncommit.getPiece().getPosition()%8) + ", " + (toUncommit.getPiece().getPosition()/8));
		//System.out.println("f�r�ndring: " + (toUncommit.getPositionChange()%8) + ", " + (toUncommit.getPositionChange()/8));
		//System.out.println("det �ndras med: " + (gammalPosition%8) + ", " + (gammalPosition/8));

		if (toUncommit.getMoveType().isPromotion()) {
			//Piece promotedPiece = squares[uncommittingPiece.getPosition()];
			// sist i listan �r den nya pj�sen och den ska tas bort.
			(uncommittingPiece.getPlayer() == PlayerColour.White? whites: blacks).removeLast();
			uncommittingPiece.setActivity(true);
			
			int positionChangeBack = -toUncommit.getPositionChange();
			uncommittingPiece.moveX(positionChangeBack, toUncommit.getPreviousMoveNumber());
			squares[posPreCommit] = uncommittingPiece;
			squares[posPostCommit] = null;
			
		} else {
			int positionChangeBack = -toUncommit.getPositionChange();		
			uncommittingPiece.moveX(positionChangeBack, toUncommit.getPreviousMoveNumber());
			squares[posPreCommit] = uncommittingPiece;
			squares[posPostCommit] = null;
		}
			
		if (takenPiece != null) {
			takenPiece.setActivity(true);
			squares[takenPiece.getPosition()] = takenPiece; 
		}
		
		moveNumber--;
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
			addPiece(i, 1, PlayerColour.White, PieceType.Pawn, false);
			addPiece(i, 6, PlayerColour.Black, PieceType.Pawn, false);
		}
		for (int i=0; i<2; i++)
		{
			PlayerColour plr = i==0? PlayerColour.White: PlayerColour.Black;
			
			addPiece(0, i*7, plr, PieceType.Rook, false);
			addPiece(1, i*7, plr, PieceType.Knight, false);
			addPiece(2, i*7, plr, PieceType.Bishop, false);
			addPiece(3, i*7, plr, PieceType.Queen, false);
			addPiece(4, i*7, plr, PieceType.King, false);
			addPiece(5, i*7, plr, PieceType.Bishop, false);
			addPiece(6, i*7, plr, PieceType.Knight, false);
			addPiece(7, i*7, plr, PieceType.Rook, false);	
		}
		
	}
	
	protected Piece getPiece(int horizontal, int vertical) 
	{
		return squares[horizontal - 9 + 8*vertical];
	}
	
	@Override
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
	
	protected Moves getAllPossibleMovesFor(PlayerColour colour) {
		Moves possibleMoves = new Moves();
			
			//s�tt upp spelarens valueTable(pj�sv�rden) i PieceType.java s� att det snabbt kan returnera r�tt v�rde p� 
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
	
	protected Moves getAllPossibleAllowedMovesFor(PlayerColour colour) {
		Moves possibleMoves = new Moves();
			
			//s�tt upp spelarens valueTable(pj�sv�rden) i PieceType.java s� att det snabbt kan returnera r�tt v�rde p� 
		PieceType.setPieceValues((colour == PlayerColour.White? playerWhite: playerBlack).getValueTable());
		
		List<Piece> playerPieces = (colour == PlayerColour.White)? whites: blacks;
		for (Piece piece : playerPieces) {
			if (piece != null && piece.getActivity()) {
				possibleMoves.addAll(piece.getPossibleMoves(this));
			}
		}

		
		PlayerColour opponentColour = colour.getOpponentColour();
		
		Iterator<Move> iter = possibleMoves.iterator();
		
		
		while (iter.hasNext())
		{
			// flytta �ver detta till n�gon av isPlayerChecked, isPlayerCheckMate, draw 
			commitMove(iter.next());
			Move bestOpponentMove = getAllPossibleMovesFor(opponentColour).getBestMoveFromList();
			
			if (PieceType.isMoveValueKingSize(bestOpponentMove.getValue()))
				iter.remove();	// om kungen kan tas s� �r colour schackad i f�reg�ende drag, d�rf�r f�r inte move vara med i Moves				
			
			uncommitLastMove();
		}
		
		return possibleMoves;
	}
	
	
		// returnerar alla m�jliga till�tna positioner colour kan flytta fr�n
		// fixa denna senare, kolla upp om spelare st�r schack eller om flytt av pj�s kan flytta utan att st�lla sig sj�lv schack
	protected List<Integer> getAllPossibleAllowedSquaresToMoveFrom(PlayerColour colour)
	{
		List<Integer> listWithMovesFrom = new ArrayList<>();
		Set<Integer> setWithMovesFrom = getAllPossibleAllowedMovesFor(colour).getAllPossibleSquaresToMoveFrom();
		listWithMovesFrom.addAll(setWithMovesFrom);
		return listWithMovesFrom;
		//return getAllPossibleAllowedMovesFor(colour).getAllPossibleSquaresToMoveFrom();
	}
	
		// returnerar alla m�jliga platser pj�s kan flytta till.
		// fixa denna senare p� samma s�tt som ovan.
	protected List<Integer> getAllPossibleAllowedSquaresToMoveToWith(Piece piece)
	{
		List<Integer> listWithMovesFrom = new ArrayList<>();
		Set<Integer> setWithMovesFrom = getAllPossibleAllowedMovesFor(piece.getPlayer()).getMovesByPiece(piece).getAllPossibleSquaresToMoveTo();
		listWithMovesFrom.addAll(setWithMovesFrom);
		return listWithMovesFrom;
		//return getAllPossibleAllowedMovesFor(piece.getPlayer()).getMovesByPiece(piece).getAllPossibleSquaresToMoveTo();
	}
	
	private Move findBestMove(PlayerColour colour)
	{
		return getAllPossibleMovesFor(colour).getBestMoveFromList();
	}
	
	private Move findBestMove(PlayerColour colour, int N)
	{
		Move rekordMove = null;

		if (N == 1)
			return findBestMove(colour);
		
		Moves moves = getAllPossibleMovesFor(colour);
		int rekordMoveValue = -100*PieceType.King.getValue();	// tabort, �ndra detta till en konstant i PieceType
		
		for (Move thisMove : moves)
		{
			
			//tabort			
			if (thisMove == null)
			{
				thisMove = null;
			} else if (thisMove.getValue() == PieceType.King.getValue()) {
				// forts�tt inte leta djupare om kungen blir tagen, d� �r spelet avslutat
				// annars s� forts�tter den byta kungarna f�r att sen forts�tta spelet (strictly foh-bid'n)
				return thisMove;
			}
			
				// utf�r move
			commitMove(thisMove);

			Move nextMove = findBestMove(colour.getOpponentColour(), N-1);
			
			//tabort
			if (nextMove == null)
			{
				Move nextMove2 = findBestMove(colour.getOpponentColour(), N-1);
				System.out.println(nextMove2);
			}
			
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
		//tabort	
		if (rekordMove == null)
		{
			rekordMove = null;
		}

		return rekordMove;
	}
	
	//protected Move findBestMoveFor(Player player, int N)
	protected Move findBestMoveFor(PlayerColour colour, int N)
	{		
			// s�tt v�rdering av pj�serna �t spelaren som spelar.
		PieceType.setPieceValues((colour == PlayerColour.White? playerWhite: playerBlack).getValueTable());
		
		//tabort
		if (whites.size() != 16 || blacks.size() != 16)
		{
			
			//System.out.println(whites);
			//System.out.println(blacks);
		}
		
		Move bestMove = findBestMove(colour, N);
		
		// h�mta alla drag som man faktiskt f�r utf�ra, och sp�na vidare d�rifr�n.
		// detta f�r att undvika att den st�ller sig schack.
		//getAllPossibleMovesFor(colour)
			
		// nollst�ll v�rderingen av pj�serna n�r spelaren letat f�rdigt
		PieceType.unsetPieceValues();
		return bestMove;
	}
	
		// ange endast fromPos och toPos, f�r att beskriva ett drag.
	protected Move createMoveFromPositions(int fromPos, int toPos)
	{
		Piece piece = squares[fromPos];
		if (piece == null)
			return null;
		
		Moves moves = piece.getPossibleMoves(this).getMovesToPos(toPos);
		if (moves == null || moves.size() == 0)
			return null;
		
		return moves.get(0);
		// vid castling kr�vs mer information
	}
	
	public boolean isSquareThreatnedBy(int pos, PlayerColour pc)
	{
		List<Piece> pieces = ((pc == PlayerColour.White)? whites: blacks);
		
		for (Piece piece: pieces)
		{
			if (piece.isPieceThreateningPosition(pos, squares))
				return true;
		}
		
		return false;
	}
}




