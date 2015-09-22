package system.board;

import java.util.List;


import main.Communicator;
import main.control.ControlValue;
import main.control.Message;
import main.control.MessageType;
import main.control.ProcessType;

public class BoardWrapper extends Board implements Runnable { 

	private ProcessType processType = ProcessType.Board_1;  
	
	public BoardWrapper(Player white, Player black) {
		super(white, black);
		
	}

	@Override
	public void run() {
		System.out.println("running tråd BoardWrapper");
		boolean stopRunning = false;	// denna ska vara false som standard
		Message retrieved;
		
		do {
				// hämta meddelanden till mig som ligger i kommunikatorn från andra världar.
			while((retrieved = Communicator.getMessage(processType)) != null) {
				
				System.out.println("Board har tagit emot ett meddelande som lyder: " + retrieved.toString());
				
				switch(retrieved.getMessageType())
				{
				case COMMIT_MOVE:
					break;
				case GET_PIECE_VALUES:
					break;
				case KILL_PROCESS_IMMEDIATELY:
					stopRunning = true;
					break;
				case PROPOSE_MOVE:
					break;
				case SET_PIECE_VALUES:
					break;
				case STANDARD_SETUP:
					standardSetup();
					returnBoardAvailability();
					returnBoardSetup();
					break;
				default:
					break;
						
				}
			}
			
			if (!stopRunning)
				sleepWithLessEffort(100);
			
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
		
		System.out.println("skicka följande sträng från Board till Gui: " + tjena);
		Message mess = new Message(processType, ProcessType.Gui_1, MessageType.SET_BOARD_PIECES, tjena);
		Communicator.addMessage(mess);
	}
	
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
	}
}
