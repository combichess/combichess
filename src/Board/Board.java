package Board;

import java.util.ArrayList;
import java.util.List;

import Move.Move;
import piece.Bishop;
import piece.ChessNotation;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.PieceType;
import piece.Queen;
import piece.Rook;

public class Board { 
	private List<Move> committedMoves;
	private Piece blacks[];
	private Piece whites[];
	private Piece squares[];
	private Player playerBlack;
	private Player playerWhite;
	
		// setup
	public Board(Player white, Player black)
	{
		blacks = new Piece[16];
		whites = new Piece[16];
		squares = new Piece[64];
		committedMoves = new ArrayList<Move>();
		playerWhite = white;
		playerBlack = black;
	}
	
	public Piece getPieceOnSquare(int x, int y)
	{
		return squares[(y<<3) + x];
	}

	public void setPieceOnSquare(int xPos, int yPos, Piece piece)
	{
		squares[(yPos<<3) + xPos] = piece;
	}
	
	
	public void addPiece(int xPos, int yPos, PlayerColour player, PieceType type) {
		Piece newPiece = null;
		
		if ((xPos & (~7)) != 0 || (yPos & (~7)) != 0)
			return;
		
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
		
		boolean thereIsNoMorePieces = true;
		Piece playerPieces[] = (player == PlayerColour.White)? whites: blacks;
		for (int i=0; i<playerPieces.length; i++)
		{
			if (playerPieces[i] == null)
			{
				playerPieces[i] = newPiece;
				thereIsNoMorePieces = false;
				break;
			}
		}
		
		if (thereIsNoMorePieces)
			return;
		
		// annars har allt g�tt bra
		setPieceOnSquare(xPos, yPos, newPiece);
	}
	
	public int commitMove(Move moveToCommit)
	{
		committedMoves.add(moveToCommit);
		
		int oldXPos = moveToCommit.getPiece().getX();
		int oldYPos = moveToCommit.getPiece().getY();
		
		int newXPos = oldXPos + moveToCommit.getXmove();
		int newYPos = oldYPos + moveToCommit.getYmove();
		
		int newPosId = (newYPos<<3) + newXPos;
		int oldPosId = (oldYPos<<3) + oldXPos;
		
			// i fall det �r en "en passant" s� �r den tagna pj�sen inte p� samma position som den committande pj�sen vandrar till, d�rf�r utf�rs raden h�r nedan.
		Piece takenPiece = moveToCommit.getAffectedPiece();
		if (takenPiece != null) {
			squares[(takenPiece.getY() << 3) + takenPiece.getX()] = null;
			takenPiece.setActivity(false);
		}
		squares[newPosId] = moveToCommit.getPiece();
		squares[oldPosId] = null;
		
		return 0;
	}
	
	public Move getHighestValueMove(List<Move> moves)
	{
		return null;
	}
	
	public Move getLowestValueMove(List<Move> moves)
	{
		return null;
	}
	
	public int uncommitLastMove()
	{
		return 0;
	}
	
	public void standardSetup()
	{
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
	
	public Piece getPiece(int horizontal, int vertical) 
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
	
	public List<Move> getAllPossibleMovesFor(PlayerColour player) {
		List<Move> possibleMoves = new ArrayList<Move>();
		Piece[] playerPieces = (player == PlayerColour.White)? whites: blacks;
		for (Piece piece : playerPieces) {
			if (piece != null && piece.getActivity())
				possibleMoves.addAll(piece.getPossibleMoves(this));
		}
		
		return possibleMoves;
	}
	
	static public Move getBestMoveFromList(List<Move> moves)
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
		Player player = (colour == PlayerColour.White? playerWhite: playerBlack);
		
			// s�tt v�rdering av pj�serna �t spelaren som spelar.
		PieceType.setPieceValues(player.getValueTable());

		List<Move> moves = getAllPossibleMovesFor(colour);
		commitMove(moves.get(0));
		
			// nollst�ll v�rderingen av pj�serna n�r spelaren letat f�rdigt
		PieceType.unsetPieceValues();
		
		return getBestMoveFromList(moves);
	}
	
	public Move findBestMove(PlayerColour colour, int N)
	{
		//static int numberOfSteps = 0;
		
		if (N == 1) {
			return findBestMove(colour);
		} else {
			List<Move> moves = getAllPossibleMovesFor(colour);
			
			for (Move move : moves)
			{
				commitMove(move);
				findBestMove(colour.getOpponent(), N-1);
				uncommitLastMove();
			}
		}
		
		//System.out.println("number of steps: " + numberOfSteps);
		
		return null;
	}
}
