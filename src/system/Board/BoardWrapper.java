package system.board;

import main.Communicator;
import main.control.Message;
import main.control.ProcessType;

public class BoardWrapper extends Board implements Runnable { 

	private ProcessType processType = ProcessType.Board_1;  
	
	public BoardWrapper(Player white, Player black) {
		super(white, black);
		
	}

	@Override
	public void run() {
		System.out.println("running tr�d BoardWrapper");
		boolean stopRunning = false;
		Message retrieved;
		
		do {
				// h�mta meddelanden till mig som ligger i kommunikatorn fr�n andra v�rldar.
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
					break;
				default:
					break;
						
				}
			}
			
			if (!stopRunning)
				sleepWithLessEffort(100);
			
		} while(!stopRunning);
	}
	
	
		// jag orkar inte med InterruptionExceptions n�r jag ska sova :( 
	public void sleepWithLessEffort(long numOfMilliseconds)
	{
		try {
			Thread.sleep(numOfMilliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}