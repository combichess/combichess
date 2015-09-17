package main.control;

public enum MessageType {
        CommitMove(0),        // int[2] {from, to}, vid castling så måste flera commits skickas.
        ProposeMove(1),        // int {player}, messSentTo = Gui_1
        GetPlayerValues(2),    // int[7], int[0]=plr, int[1..6] = 
        SetPlayerValues(3),	// int[7]
        KillProcessImmediately(4);	// null, döda tråd om denna erhålls
        
        int value;
        
        private MessageType(int val)
        {
        	value = val;
        }
        
        public String toString()
        {
        	switch(value)
        	{
        	case 0:
        		return "Commit Move";
        	case 1:
        		return "Propose Move";
        	case 2:
        		return "Get Player Values";
        	case 3:
        		return "Set Player Values";
        	case 4:
        		return "Kill Process immediately";
        	}
        	return null;
        }
}
