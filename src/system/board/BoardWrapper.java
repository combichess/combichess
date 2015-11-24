package system.board;

import java.util.List;

import system.move.Move;
import system.move.Moves;
import system.piece.Piece;


import main.Communicator;
import main.control.ControlValue;
import main.control.Message;
import main.control.MessageType;
import main.control.PlayerStatus;
import main.control.ProcessType;

public class BoardWrapper extends Board implements Runnable { 

	private ProcessType processType = ProcessType.Board_1;  
	private static final int UPDATE_BOARD_IDLE_MILLISECONDS = 10;
	
	public BoardWrapper() {
		super();
	}

	@Override
	public void run() {
		System.out.println("running tråd BoardWrapper");
		boolean stopRunning = false;	// denna ska vara false som standard
		Message retrieved;
		
		do {
				// hämta meddelanden till mig som ligger i kommunikatorn från andra världar.
			while((retrieved = Communicator.getMessage(processType)) != null) {
				switch(retrieved.getMessageType())
				{
				case COMMIT_MOVE:
					commitMove(retrieved.getMessageData());
					break;
				case GET_POSSIBLE_MOVES_FROM_SQUARE:
					sendPossibleMovesFromSquare(Integer.parseInt(retrieved.getMessageData()));
					break;
				case GET_PIECE_VALUES:
					break;
				case KILL_PROCESS_IMMEDIATELY:
					stopRunning = true;
					break;
				case PROPOSE_MOVE:
					proposeMove(retrieved.getMessageData());
					break;
				case SET_PIECE_VALUES:
					break;
				case STANDARD_SETUP:
					standardSetup();
					returnBoardSetup();
					break;
				case GET_BOARD_PIECES:
					returnBoardSetup();
					break;
				case GET_MOVABLE_PIECES:
					sendPossibleMovesForPlayer(retrieved.getMessageData());
					break;
				default:
					break;
				}
			}
			
			if (!stopRunning)
				sleepWithLessEffort(UPDATE_BOARD_IDLE_MILLISECONDS);
			
		} while(!stopRunning);
	}
	
	
		// jag orkar inte med InterruptionExceptions när jag ska sova :( 
	public void sleepWithLessEffort(long numOfMilliseconds)
	{
		try {
			Thread.sleep(numOfMilliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public void returnBoardSetup()
	{
		String tjena = "";
		for (int i=0; i<squares.length; i++)
		{
			tjena += (i==0? "": ",");
			if (squares[i] == null)
				tjena += "  ";
			else {
				switch(squares[i].getPlayer())
				{
				case White:
					tjena += ControlValue.WHITE;
					break;
				case Black:
					tjena += ControlValue.BLACK;
					break;
				default:
					tjena += ControlValue.UNDEFINED;
					break;
				}
				
				switch(squares[i].getType())
				{
				case Pawn:
					tjena += ControlValue.PAWN;
					break;
				case Knight:
					tjena += ControlValue.KNIGHT;
					break;
				case Bishop:
					tjena += ControlValue.BISHOP;
					break;
				case Rook:
					tjena += ControlValue.ROOK;
					break;
				case Queen:
					tjena += ControlValue.QUEEN;
					break;
				case King:
					tjena += ControlValue.KING;
					break;
				default:
					tjena += ControlValue.UNDEFINED;
					break;
				}
			}	
		}
		
		Message mess = new Message(processType, ProcessType.Gui_1, MessageType.SET_BOARD_PIECES, tjena);
		Communicator.addMessage(mess);
	}
	
	private void sendPossibleMovesFromSquare(int square)
	{
		Piece piece = squares[square];
		PlayerColour pc = piece.getPlayer();
		if (piece == null || piece.getActivity() == false)
		{
			System.out.println("error in sendPossibleMovesFromSquare(" + square + ")");
			return;
		}
		
		// todo
		Moves pieceMoves = getAllPossibleAllowedMovesFor(pc).getMovesFromPos(square);
		//List<Move> pieceMoves = piece.getPossibleMoves(this);
		int val[] = new int[64];
		for (int i=0; i<64; i++)
			val[i] = 0;
		
		for (Move move: pieceMoves)
			val[move.getToPos()] = 1;
		
		String str = "";
		for (int i=0; i<64; i++)
			str += (i==0?"":",") + val[i];
		
		Communicator.addMessage(new Message(processType, ProcessType.Gui_1, MessageType.AVAILABLE_SQUARES, str));
	}
	
	/*
	@Deprecated
	public void returnBoardAvailability()
	{
		List<Integer> possiblePositions = this.getAllPossibleAllowedSquaresToMoveFrom(PlayerColour.White);
		
		boolean[] availables = new boolean[64];
		for (int i=0; i<64; i++)
			availables[i] = false;
		
		for (int pos : possiblePositions)
			availables[pos] = true;
		
		String tjena = "";
		for (int i=0; i<squares.length; i++)
			tjena += (i==0? "": ",") + (availables[i]? "1": "0");
		
		Communicator.addMessage(new Message(processType, ProcessType.Gui_1, MessageType.AVAILABLE_SQUARES, tjena));
	}*/
	
	public void commitMove(String strMove)
	{
		String[] sqrs = strMove.split(",");
		if (sqrs.length != 2) {
			System.out.println("BoardWrapper.commitMove failed, sqrs = " + sqrs);
			return;
		}
		
		int fr = Integer.parseInt(sqrs[0]);
		int to = Integer.parseInt(sqrs[1]);
		
		Move move = createMoveFromPositions(fr, to);
		PlayerColour pc = move.getPiece().getPlayer();
		
		super.commitMove(move);
		
		returnBoardSetup();
		returnCommittedMoveAsText(move);
		//returnBoardAvailability();
		
		PlayerStatus ps = super.getPlayerStatus(pc.getOpponentColour());
		if (ps != PlayerStatus.NO_STATUS) {
			returnPlayerStatus(ps, pc.getOpponentColour());
		}
	}
	
	public void proposeMove(String messData)
	{
		String[] datas = messData.split(",");
		
		PlayerColour pc = getPlayerColourFromString(datas[0]);
		int numberOfMovesForward = Integer.parseInt(datas[1]);
		
			// ska inte denna vara kvar?
		/*PlayerStatus ps = super.getPlayerStatus(pc);
		if (ps != PlayerStatus.NO_STATUS) {
			returnPlayerStatus(ps, pc);
			return;
		}*/

		if (messData == null || datas.length != 2)
		{
			System.out.println("BoardWrapper.proposeMove failed, playerChar == " + messData);
			return;
		}
		
		Move mov = findBestMoveFor(pc, numberOfMovesForward);
		
		if (mov == null)
		{
			System.out.println("BoardWrapper.proposeMove failed, mov == null");
			return;
		}
		
		super.commitMove(mov);
		returnCommittedMoveAsText(mov);
		
		
		PlayerStatus ps = super.getPlayerStatus(pc.getOpponentColour());
		if (ps != PlayerStatus.NO_STATUS) {
			returnPlayerStatus(ps, pc.getOpponentColour());
			return;
		}
		//returnBoardSetup();
		//returnBoardAvailability();
	}
	
	private void returnPlayerStatus(PlayerStatus ps, PlayerColour pc)
	{
		String string = ps.toString() + "," + pc.toString();
		Communicator.addMessage(new Message(processType, ProcessType.Gui_1, MessageType.ANNOUNCE_PLAYER_STATUS, string));
	}
	
	/**
	 * Sends available piece positions to move from as a list of integers
	 * 
	 * @param playerColourAsString
	 */
	private void sendPossibleMovesForPlayer(String playerColourAsString)
	{
		PlayerColour pc = getPlayerColourFromString(playerColourAsString);
		
		List<Integer> possiblePositions = getAllPossibleAllowedSquaresToMoveFrom(pc);
		
		boolean[] availables = new boolean[64];
		for (int i=0; i<64; i++)
			availables[i] = false;
		
		for (int pos : possiblePositions)
			availables[pos] = true;
		
		String availableSquaresAsString = "";
		for (int i=0; i<squares.length; i++)
			availableSquaresAsString += (i==0? "": ",") + (availables[i]? "1": "0");
		
		Communicator.addMessage(new Message(processType, ProcessType.Gui_1, MessageType.AVAILABLE_SQUARES, availableSquaresAsString));
	}
	
	private PlayerColour getPlayerColourFromString(String messData)
	{
		char c = messData.charAt(0);
		PlayerColour pc = null;
		switch(c)
		{
		case ControlValue.WHITE:
			pc = PlayerColour.White;
			break;
		case ControlValue.BLACK:
			pc = PlayerColour.Black;
			break;
		default:
			System.out.println("BoardWrapper.java FEL: fattade inte messData i sendPossibleMovesForPlayer");
		}
		return pc;
	}
	
	private void returnCommittedMoveAsText(Move move)
	{
		String returnStr = "";
		String moveStr = move.toString();
		
		switch(getPlayerStatus(move.getPiece().getPlayer().getOpponentColour()))
		{
		case CHECK:
			moveStr += "+";
			break;
		case CHECK_MATE:
			moveStr += "++";
			break;
		case STALE_MATE:
			moveStr += ", ½ - ½";
			break;
		default:
			break;
		}
		
		if (move.getPiece().getPlayer() == PlayerColour.White)
			returnStr = "" + (getMoveNumber()+1)/2 + ". " + moveStr;
		else
			returnStr = "\t " + moveStr + "\n";
		
		Communicator.addMessage(new Message(processType, ProcessType.Gui_1, MessageType.SET_MOVE_AS_STRING, returnStr));
	}
}
