package main.control;

public enum MessageType {
    COMMIT_MOVE(0),        // int[2] {from, to}, vid castling så måste flera commits skickas.
    PROPOSE_MOVE(1),        // int {player}, messSentTo = Gui_1
    GET_PIECE_VALUES(2),    // int[7], int[0]=plr, int[1..6] = 
    SET_PIECE_VALUES(3),	// int[7]
    KILL_PROCESS_IMMEDIATELY(4),	// null, döda tråd om denna erhålls
    STANDARD_SETUP(5),
    GET_POSSIBLE_MOVES_FROM_SQUARE(6),
    GET_BOARD_PIECES(7),
    SET_BOARD_PIECES(8),
    AVAILABLE_SQUARES(9);
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
    		return "Get Player Piece Values";
    	case 3:
    		return "Set Player Piece Values";
    	case 4:
    		return "Kill Process immediately";
    	case 5:
    		return "Standard Setup";
    	case 6:
    		return "Get Possible Moves From";
    	case 7:
    		return "Get Board pIECES";
    	case 8:
    		return "Set Board Pieces";
    	case 9:
    		return "Available Squares";
    		
    	}
    	return null;
    }
}
