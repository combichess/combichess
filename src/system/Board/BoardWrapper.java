package system.board;

import system.piece.Piece;
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
		boolean stopRunning = true;	// denna ska vara false som standard
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
					this.standardSetup();
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
				case Black:
					tjena += ControlValue.BLACK;
				default:
					tjena += ControlValue.UNDEFINED;
				}
				
				switch(squares[i].getType())
				{
				case Pawn:
					tjena += ControlValue.PAWN;
				case Knight:
					tjena += ControlValue.KNIGHT;
				case Bishop:
					tjena += ControlValue.BISHOP;
				case Rook:
					tjena += ControlValue.ROOK;
				case Queen:
					tjena += ControlValue.QUEEN;
				case King:
					tjena += ControlValue.KING;
				default:
					tjena += ControlValue.UNDEFINED;
				}
			}	
		}
		
		System.out.println("skicka följande sträng från Board till Gui: " + tjena);
		Message mess = new Message(processType, ProcessType.Gui_1, MessageType.SET_BOARD_DATA, tjena);
		Communicator.addMessage(mess);
	}
	
	public void returnBoardAvailability()
	{
		String tjena = "";
		for (int i=0; i<squares.length; i++)
		{
			tjena += (i==0? "": ",");
			if (squares[i] == null)
				tjena += "0";
			else {
				
			}
		}
	}
}
