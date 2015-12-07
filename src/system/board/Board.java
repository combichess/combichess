package system.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.control.PlayerStatus;
import system.board.PlayerColour;
import system.move.Move;
import system.move.MoveType;
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
	private final Pattern pat = Pattern.compile("^([NBRQK]?)(\\w|\\W)*([a-h][1-8])$");
	
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
	
	/**
	 * @return Totalt antal förflyttningar, Riktiga Dragnumret = (moveNumber+1)/2
	 */
	public int getMoveNumber()
	{
		return moveNumber;
	}
	
	public Move getLastCommittedMove()	// används enbart i En passant-checkar
	{
		return committedMoves.getLast();
	}
	
	protected void addPiece(int xPos, int yPos, PlayerColour player, @SuppressWarnings("rawtypes") Class classType, boolean touchedBefore) {
		Piece newPiece = null;
		
		if ((xPos & (~7)) != 0 || (yPos & (~7)) != 0)
			return;	// throw exception
		
		if (getPieceOnSquare(xPos, yPos) != null)
			return;
		
		if (classType.equals(Pawn.class)) {
			newPiece = new Pawn(xPos, yPos, player);
		} else if (classType.equals(Knight.class)) {
			newPiece = new Knight(xPos, yPos, player);
		} else if (classType.equals(Bishop.class)) {
			newPiece = new Bishop(xPos, yPos, player);
		} else if (classType.equals(Rook.class)) {
			newPiece = new Rook(xPos, yPos, player);
		} else if (classType.equals(Queen.class)) {
			newPiece = new Queen(xPos, yPos, player);
		} else if (classType.equals(King.class)) {
			newPiece = new King(xPos, yPos, player);
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
	
	public Move getMoveFromString(String moveString, PlayerColour pc)
	{
		moveString = moveString.replace("+", "").replace("x", "");
		Moves moves = getAllPossibleAllowedMovesFor(pc);
		
		if (moveString.equals("O-O-O")) {
			return moves.getMovesByMoveType(MoveType.QUEEN_SIDE_CASTLING).get(0); 
		} else if (moveString.equals("O-O")) {
			return moves.getMovesByMoveType(MoveType.KING_SIDE_CASTLING).get(0);
		}
		
		Matcher mat = pat.matcher(moveString);
		mat.find();
		int toPos = (mat.group(3).charAt(0) - 'a') + 8*(mat.group(3).charAt(1) - '1');
		moves = moves.getMovesToPos(toPos);
		
		switch(moves.size())
		{
		case 0:
			System.out.println("Board.getMoveToString()\tMove: " + moveString + " is not an alternative");
			return null;
		case 1:
			return moves.get(0);
		default:
			break;
		}
		String grupp1 = mat.group(1);
		char c = grupp1.length() == 1? mat.group(1).charAt(0): 'x'; 
		switch(c)
		{
		case 'N':
			moves = moves.getMovesByPieceType(Knight.class);
			break;
		case 'B':
			moves = moves.getMovesByPieceType(Bishop.class);
			break;
		case 'R':
			moves = moves.getMovesByPieceType(Rook.class);
			break;
		case 'Q':
			moves = moves.getMovesByPieceType(Queen.class);
			break;
		case 'K':
			moves = moves.getMovesByPieceType(King.class);
			break;
		default:
			moves = moves.getMovesByPieceType(Pawn.class);
			break;
		}
		
		if (moves.size() == 1)
			return moves.get(0);
		
		if (mat.group(2) == null)
			return null;
		
		char p = mat.group(2).charAt(0); 
		if (p >= '1' && p <= '8') {
			for (Move move: moves)
			{
				if (move.getFromPos()/8 == (p-'1'))
					return move;
			}
		} else if (p >= 'a' && p <= 'h') {
			for (Move move: moves)
			{
				if ((move.getFromPos()&7) == (p-'a'))
					return move;
			}
		}
		return null;
	}
		
	protected int commitMove(Move moveToCommit)
	{
		committedMoves.add(moveToCommit);

		//int dx = moveToCommit.getXmove();
		//int dy = moveToCommit.getYmove();

		int newPosId = moveToCommit.getToPos();
		int oldPosId = moveToCommit.getFromPos();
		
			// i fall det är en "en passant" så är den tagna pjäsen inte på samma position som den committande pjäsen vandrar till, därför utförs raden här nedan.
		Piece takenPiece = moveToCommit.getAffectedPiece();
		if (takenPiece != null) {
			//squares[(takenPiece.getY() << 3) + takenPiece.getX()] = null;
			squares[takenPiece.getPosition()] = null;
			takenPiece.setActivity(false);
		}
		
		moveToCommit.getPiece().moveTo(newPosId, moveNumber);
		//moveToCommit.getPiece().moveX(dx + dy*8, moveNumber);
		if (newPosId > 63 || newPosId < 0 || oldPosId > 63 || oldPosId < 0)
		{
			System.out.println("Hit ska den absolut inte komma");
			return -1;
		} 
		MoveType moveType = moveToCommit.getMoveType();
		if (moveType.isPromotion()) {
			Piece oldPawn = moveToCommit.getPiece();
			PlayerColour pc = oldPawn.getPlayer();
			oldPawn.setActivity(false);
			Piece newPiece = null;
			switch(moveType)
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
			(pc == PlayerColour.White? whites: blacks).add(newPiece);	// byt till addLast för att vara mer specifik, nya pjäser läggs o tas bort sist i kön.
		} else if(moveType.isCastling()) {
			switch(moveType)
			{
			case KING_SIDE_CASTLING: {
				Piece rook = squares[oldPosId + 3];
				int rookPos = rook.getPosition();				
				squares[rookPos] = null;
				rookPos -= 2;
				rook.moveTo(rookPos, moveNumber);
				squares[rookPos] = rook;
				break;}
			default: {
				Piece rook = squares[oldPosId - 4];
				int rookPos = rook.getPosition();
				squares[rookPos] = null;
				rookPos += 3;
				rook.moveTo(rookPos,moveNumber);
				squares[rookPos] = rook;
				break;}
			}
			
			squares[newPosId] = moveToCommit.getPiece();
			
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
		int posPreCommit = toUncommit.getFromPos();
		
		MoveType moveType = toUncommit.getMoveType();
		
		if (moveType.isPromotion()) {
			// sist i listan är den nya pjäsen och den ska tas bort.
			(uncommittingPiece.getPlayer() == PlayerColour.White? whites: blacks).removeLast();
			uncommittingPiece.setActivity(true);
			
			uncommittingPiece.moveTo(toUncommit.getFromPos(), toUncommit.getPreviousMoveNumber());
			squares[posPreCommit] = uncommittingPiece;
			squares[posPostCommit] = null;
			
		} else if (moveType.isCastling()) {
			if (moveType == MoveType.KING_SIDE_CASTLING) {
				Piece rook = squares[posPostCommit-1];
				int rookPos = rook.getPosition();
				int kingPos = uncommittingPiece.getPosition();
				
				rook.moveTo(rookPos + 2, Move.PREVIOUSLY_NEVER_MOVED);
				uncommittingPiece.moveTo(kingPos - 2, Move.PREVIOUSLY_NEVER_MOVED);
				
				squares[posPostCommit+1] = rook;
				squares[posPostCommit-2] = uncommittingPiece;
				squares[posPostCommit] = squares[posPostCommit-1] = null;
			} else {
				Piece rook = squares[posPostCommit+1];
				int rookPos = rook.getPosition();
				int kingPos = uncommittingPiece.getPosition();
				
				rook.moveTo(rookPos - 3, Move.PREVIOUSLY_NEVER_MOVED);
				uncommittingPiece.moveTo(kingPos + 2, Move.PREVIOUSLY_NEVER_MOVED);
				
				squares[posPostCommit-2] = rook;
				squares[posPostCommit+2] = uncommittingPiece;
				squares[posPostCommit] = squares[posPostCommit+1] = null;
			}
		} else {
			//int positionChangeBack = -toUncommit.getPositionChange();	
			uncommittingPiece.moveTo(toUncommit.getFromPos(), toUncommit.getPreviousMoveNumber());
			//uncommittingPiece.moveX(positionChangeBack, toUncommit.getPreviousMoveNumber());
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
			addPiece(i, 1, PlayerColour.White, Pawn.class, false);
			addPiece(i, 6, PlayerColour.Black, Pawn.class, false);
		}
		for (int i=0; i<2; i++)
		{
			PlayerColour plr = i==0? PlayerColour.White: PlayerColour.Black;
			
			addPiece(0, i*7, plr, Rook.class, false);
			addPiece(1, i*7, plr, Knight.class, false);
			addPiece(2, i*7, plr, Bishop.class, false);
			addPiece(3, i*7, plr, Queen.class, false);
			addPiece(4, i*7, plr, King.class, false);
			addPiece(5, i*7, plr, Bishop.class, false);
			addPiece(6, i*7, plr, Knight.class, false);
			addPiece(7, i*7, plr, Rook.class, false);	
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
				Piece piece = getPiece(x, y);
				if (piece != null)
					str += piece.toString(ChessNotation.LONG_ALGEBRAIC);
				str += (x<8)? "\t": "";
			}
			
			str += "\t]" + y + "\n";
		}	
		for (int x=1; x<=8; x++)
			str += "   " + ((char)(c+x)) + ((x==8)? "\n": "\t");
		return str;
	}

	
	public List<String> getMoveHistory()
	{
			// skapa ett helt nytt schackbräde och gör om allting från grunden
		Moves moves = new Moves();
		List<String> moveList = new ArrayList<>();
		
		moves.addAll(committedMoves);
		while (!committedMoves.isEmpty()){
			uncommitLastMove();
		}
		
		for (Move move: moves)
		{
			boolean rankNeeded = false;
			boolean fileNeeded = false;
		
			Piece piece = move.getPiece();
			
			//but the file(x-value) takes precedence over the rank
			Moves otherMoves = getAllPossibleAllowedMovesFor(piece.getPlayer()).getMovesToPos(move.getToPos());
			int fromX = move.getFromPos()&7;
			int fromY = move.getFromPos()/8;
			
			for (Move otherMove: otherMoves)
			{
				boolean sameFile = (otherMove.getFromPos()&7) == fromX;
				boolean sameRank = (otherMove.getFromPos()/8) == fromY;
				
					// Om exempelvis det finns två pjäser av samma typ som kan gå till denna rutan
				if (piece.getClass().equals(otherMove.getPiece().getClass()))
				{
					if (!sameFile)
						fileNeeded = true;
					else  if (!sameRank)
						rankNeeded = true;
				}	
			}
			String toBeAdded = move.toString(fileNeeded, rankNeeded); 
			commitMove(move);
			switch(getPlayerStatus(move.getPiece().getPlayer().getOpponentColour()))
			{
			case CHECK:
				toBeAdded += "+";
				break;
			case CHECK_MATE:
				toBeAdded += "++";
				break;
			case STALE_MATE:
				// todo tabort
				toBeAdded += "";
				break;
			default:
				break;
			}
			
			moveList.add(toBeAdded);
		}
		
		return moveList;
	}
	
	public Move moveFromString(String str)
	{
		return null;
	}
	
	protected Moves getAllPossibleMovesFor(PlayerColour colour) {
		Moves possibleMoves = new Moves();
			
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
	
	protected Moves getAllPossibleAllowedMovesFor(PlayerColour colour) {
		Moves possibleMoves = new Moves();
			
			//sätt upp spelarens valueTable(pjäsvärden) i PieceType.java så att det snabbt kan returnera rätt värde på 
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
			// flytta över detta till någon av isPlayerChecked, isPlayerCheckMate, draw 
			commitMove(iter.next());
			Move bestOpponentMove = getAllPossibleMovesFor(opponentColour).getBestMoveFromList();
			
			if (PieceType.isMoveValueKingSize(bestOpponentMove.getValue()))
				iter.remove();	// om kungen kan tas så är colour schackad i föregående drag, därför får inte move vara med i Moves				
			
			uncommitLastMove();
		}
		
		return possibleMoves;
	}
	
	
		// returnerar alla möjliga tillåtna positioner colour kan flytta från
		// fixa denna senare, kolla upp om spelare står schack eller om flytt av pjäs kan flytta utan att ställa sig själv schack
	protected List<Integer> getAllPossibleAllowedSquaresToMoveFrom(PlayerColour colour)
	{
		List<Integer> listWithMovesFrom = new ArrayList<>();
		Set<Integer> setWithMovesFrom = getAllPossibleAllowedMovesFor(colour).getAllPossibleSquaresToMoveFrom();
		listWithMovesFrom.addAll(setWithMovesFrom);
		return listWithMovesFrom;
		//return getAllPossibleAllowedMovesFor(colour).getAllPossibleSquaresToMoveFrom();
	}
	
		// returnerar alla möjliga platser pjäs kan flytta till.
		// fixa denna senare på samma sätt som ovan.
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
		int rekordMoveValue = -100*PieceType.getValue(King.class);	// tabort, ändra detta till en konstant i PieceType
		
		for (Move thisMove : moves)
		{
			
			//tabort			
			if (thisMove == null)
			{
				thisMove = null;
			} else if (thisMove.getValue() == PieceType.getValue(King.class)) {
				// fortsätt inte leta djupare om kungen blir tagen, då är spelet avslutat
				// annars så fortsätter den byta kungarna för att sen fortsätta spelet (strictly foh-bid'n)
				return thisMove;
			}
			
				// utför move
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
	/*protected Move findBestMoveFor(PlayerColour colour, int N)
	{		
			// sätt värdering av pjäserna åt spelaren som spelar.
		PieceType.setPieceValues((colour == PlayerColour.White? playerWhite: playerBlack).getValueTable());
		
		Move bestMove = findBestMove(colour, N);
		
		// hämta alla drag som man faktiskt får utföra, och spåna vidare därifrån.
		// detta för att undvika att den ställer sig schack.
		//getAllPossibleMovesFor(colour)
			
		// nollställ värderingen av pjäserna när spelaren letat färdigt
		PieceType.unsetPieceValues();
		return bestMove;
	}*/
	
	protected Move findBestMoveFor(PlayerColour colour, int N)
	{
		if (N <= 0)
			return null;
		
			// sätt värdering av pjäserna åt spelaren som spelar.
		PieceType.setPieceValues((colour == PlayerColour.White? playerWhite: playerBlack).getValueTable());
		Moves allMoves = getAllPossibleAllowedMovesFor(colour);
		
		if (allMoves.size() == 0)
			return null;
		
		for (Move move: allMoves)
		{
			commitMove(move);
			if (N > 1)
				move.addValueFromNextMove(findBestMove(colour.getOpponentColour(), N-1));
			
			uncommitLastMove();
		}
		
		Move oneOfTheBestMoves = allMoves.getMovesByValue(allMoves.getBestMoveFromList().getValue()).getRandomMove();
		return oneOfTheBestMoves;
	}
	
		// ange endast fromPos och toPos, för att beskriva ett drag.
	protected Move createMoveFromPositions(int fromPos, int toPos)
	{
		Piece piece = squares[fromPos];
		if (piece == null)
			return null;
		
		Moves moves = piece.getPossibleMoves(this).getMovesToPos(toPos);
		if (moves == null || moves.size() == 0)
			return null;
		
		return moves.get(0);
		// vid castling krävs mer information
	}
	
	public boolean isSquareThreatnedBy(int pos, PlayerColour pc)
	{
		List<Piece> pieces = ((pc == PlayerColour.White)? whites: blacks);
		
		for (Piece piece: pieces)
		{
			if (piece.getActivity() && piece.isPieceThreateningPosition(pos, squares))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Returns if player is check, stalemate, checkmate or nothing
	 * 
	 * @param pc
	 * @return situation
	 */
	public PlayerStatus getPlayerStatus(PlayerColour pc)
	{
		LinkedList<Piece> pieces = (pc == PlayerColour.White)? whites: blacks;
		Piece king = null;
		for(Piece piece: pieces)
		{
			if (piece instanceof King)
			{
				king = piece;
				break;
			}
		}
		if (king == null)
		{
			System.out.println("Det gick fel för att den inte hittar kungen bland " + pc + " pjäser. Board->isPlayerCheck()");
			return PlayerStatus.UNDEFINED;
		}
		
		boolean playerIsChecked = isSquareThreatnedBy(king.getPosition(), pc.getOpponentColour());
		boolean playerCantMove = getAllPossibleAllowedMovesFor(pc).isEmpty();
		PlayerStatus ps;
		if (playerIsChecked)
		{
			if (playerCantMove)
				ps = PlayerStatus.CHECK_MATE;
			else
				ps = PlayerStatus.CHECK;
		} else {
			if (playerCantMove)
				ps = PlayerStatus.STALE_MATE;
			else
				ps = PlayerStatus.NO_STATUS;
		}
		
		return ps;
	}
	
	/**
	 * @return true if everything is alright
	 */
	protected boolean securityCheck()
	{
		boolean test = true;
		
		for (int pos=0; pos<64; pos++)
		{
			Piece piece = squares[pos];
			if (piece != null && piece.getPosition() != pos)
			{
				System.out.println("squares[" + pos + "].getPosition() = " + piece.getPosition());
				test = false;
			}
		}
		
		//////////////////
		List<Piece> allPieces = new ArrayList<>();
		allPieces.addAll(whites);
		allPieces.addAll(blacks);
		
		List<Piece> activePieces = new ArrayList<>();
		for (Piece piece : allPieces)
		{
			if (piece.getActivity())
				activePieces.add(piece);
		}
		
		for (int i=0; i<activePieces.size(); i++)
		{
			for (int j=i+1; j<activePieces.size(); j++)
			{
				int iPos = activePieces.get(i).getPosition();
				int jPos = activePieces.get(j).getPosition();
				if (iPos == jPos)
				{
					System.out.println("Samma position på " + activePieces.get(i) + " och " + activePieces.get(j) + ", squares[" + iPos + "] = " + squares[iPos]);
					test = false;
				}
			}
		}
		
		
		return test;
	}
}




