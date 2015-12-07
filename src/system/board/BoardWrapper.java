package system.board;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import system.move.Move;
import system.move.Moves;
import system.piece.Bishop;
import system.piece.King;
import system.piece.Knight;
import system.piece.Pawn;
import system.piece.Piece;
import system.piece.Queen;
import system.piece.Rook;
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
				case LOAD_GAME:
					loadGame(retrieved.getMessageData());
					break;
				case SAVE_GAME:
					saveGame(retrieved.getMessageData());
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
		String boardSetup = "";
		for (int i=0; i<squares.length; i++)
		{
			boardSetup += (i==0? "": ",");
			if (squares[i] == null)
				boardSetup += "  ";
			else {
				switch(squares[i].getPlayer())
				{
				case White:
					boardSetup += ControlValue.WHITE;
					break;
				case Black:
					boardSetup += ControlValue.BLACK;
					break;
				default:
					boardSetup += ControlValue.UNDEFINED;
					break;
				}
				
				@SuppressWarnings("rawtypes")
				Class classType = squares[i].getClass();
				if (classType == Pawn.class)
					boardSetup += ControlValue.PAWN;
				else if (classType == Knight.class)
					boardSetup += ControlValue.KNIGHT;
				else if (classType == Bishop.class)
					boardSetup += ControlValue.BISHOP;
				else if (classType == Rook.class)
					boardSetup += ControlValue.ROOK;
				else if (classType == Queen.class)
					boardSetup += ControlValue.QUEEN;
				else if (classType == King.class)
					boardSetup += ControlValue.KING;
			}	
		}
		
		Message mess = new Message(processType, ProcessType.Gui_1, MessageType.SET_BOARD_PIECES, boardSetup);
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
		
		Moves pieceMoves = getAllPossibleAllowedMovesFor(pc).getMovesFromPos(square);
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
		//returnCommittedMovesAsText(move);
		returnCommittedMovesAsText();
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
		//returnCommittedMovesAsText(mov);
		returnCommittedMovesAsText();
		
		
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
	
	//private void returnCommittedMovesAsText(Move move)
	private void returnCommittedMovesAsText()
	{
		List<String> strs = getMoveHistory();
		String returnStr = "";
		
		int moveNum = 1;
		PlayerColour pc = PlayerColour.White;
		
		for (String str: strs)
		{
			returnStr += (pc == PlayerColour.White)? (moveNum++ + ". " + str): ("\t" + str + "\n"); 
			pc = pc.getOpponentColour();
			
		}
		
		Communicator.addMessage(new Message(processType, ProcessType.Gui_1, MessageType.SET_MOVE_AS_STRING, returnStr));
	}
	
	private void loadGame(String filePath)
	{
		String[] movesAsStr = null;
		try {
			Scanner in = new Scanner(new FileReader("./saved games/" + filePath));
			if (in.hasNext())
				movesAsStr = in.next().split(",");
			
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if (movesAsStr == null)
		{
			System.out.println("Misslyckades med att hämta filen: " + filePath);
			return;
		}
		
		standardSetup();
		PlayerColour pc = PlayerColour.White;
		for (String moveAsStr: movesAsStr)
		{
			Move move = getMoveFromString(moveAsStr, pc);
			if (move == null)
				System.out.println("Move is null");
			
			commitMove(move);
			System.out.println("Move: " + move + "\tMoveString: " + moveAsStr + '\n' + this);
			pc = pc.getOpponentColour();
		}
		setPlayersTurn(pc.getOpponentColour());
		returnBoardSetup();
		returnCommittedMovesAsText();
	}
	
	private void setPlayersTurn(PlayerColour pc)
	{
		Message mess = new Message(processType, ProcessType.Gui_1, MessageType.SET_PLAYERS_TURN, pc == PlayerColour.White? "W": "B");
		System.out.println("Nu blir det " + pc + "s tur att spela");
		Communicator.addMessage(mess);
	}
	
	private void saveGame(String filename)
	{
		List<String> movesToSave = super.getMoveHistory();
		String stringToSave = "";
			// bygg string av movesen
		for (int i=0; i<movesToSave.size(); i++)
			stringToSave += (i>0? ",": "") + movesToSave.get(i);
		
		try {
			 PrintWriter out = new PrintWriter("saved games/" + filename);
			 out.print(stringToSave);
			 out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
