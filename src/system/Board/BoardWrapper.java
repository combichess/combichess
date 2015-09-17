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
		System.out.println("running tråd BoardWrapper");
		boolean stopRunning = false;
		Message retrieved;
		
		do {
				// hämta meddelanden till mig som ligger i kommunikatorn från andra världar.
			while((retrieved = Communicator.getMessage(processType)) != null) {
				
				System.out.println("Board har tagit emot ett meddelande som lyder: " + retrieved.toString());
				
				switch(retrieved.getMessageType())
				{
				case CommitMove:
					break;
				case GetPlayerValues:
					break;
				case KillProcessImmediately:
					stopRunning = true;
					break;
				case ProposeMove:
					break;
				case SetPlayerValues:
					break;
				case StandardSetup:
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
	
	
		// jag orkar inte med InterruptionExceptions när jag ska sova :( 
	public void sleepWithLessEffort(long numOfMilliseconds)
	{
		try {
			Thread.sleep(numOfMilliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
