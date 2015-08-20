package Board;

import java.util.ArrayList;
import java.util.LinkedList;
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
	private LinkedList<Move> committedMoves;
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
		committedMoves = new LinkedList<Move>();
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
		
		// annars har allt gått bra
		setPieceOnSquare(xPos, yPos, newPiece);
	}
	
	public int commitMove(Move moveToCommit, List<Move> moves)
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
	
	public int uncommitLastMove(List<Move> moves)
	{
		Move toUncommit = committedMoves.pop();
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
	
	public List<Move> getAllPossibleMovesFor(PlayerColour colour) {
		List<Move> possibleMoves = new ArrayList<Move>();
			
			//sätt upp spelarens valueTable(pjäsvärden) i PieceType.java så att det snabbt kan returnera rätt värde på 
		PieceType.setPieceValues((colour == PlayerColour.White? playerWhite: playerBlack).getValueTable());
		
		Piece[] playerPieces = (colour == PlayerColour.White)? whites: blacks;
		for (Piece piece : playerPieces) {
			if (piece != null && piece.getActivity()) {
				possibleMoves.addAll(piece.getPossibleMoves(this));
			}
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
		
			// sätt värdering av pjäserna åt spelaren som spelar.
		PieceType.setPieceValues(player.getValueTable());

		List<Move> moves = getAllPossibleMovesFor(colour);
		
		//for (Move mov : moves)
		//	System.out.println(j++ + ": " + mov);
		
			// nollställ värderingen av pjäserna när spelaren letat färdigt
		PieceType.unsetPieceValues();
		
		return getBestMoveFromList(moves);
	}
	
	public Move findBestMove(PlayerColour colour, int N)
	{
		Move rekordMove = null;

		if (N == 1)
			return findBestMove(colour);

		List<Move> moves = getAllPossibleMovesFor(colour);
		int rekordMoveValue = -100000000;
		
		for (Move move : moves)
		{
				// utför move
			commitMove(move, moves);
			Move move2 = findBestMove(colour.getOpponentColour(), N-1);
			int nyttMoveValue = move.getValue() - move2.getValue(); 
			if (nyttMoveValue > rekordMoveValue)
			{
				rekordMove = move;
				rekordMoveValue = nyttMoveValue;
			}
			
				// ångra move
			uncommitLastMove(moves);
		}

		return rekordMove;
	}
}
