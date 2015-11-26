package main.control;

public enum MessageType {
    COMMIT_MOVE(0),        // int[2] {from, to}, vid castling så måste flera commits skickas.
    PROPOSE_MOVE(1),        // int {player},{#MovesForward}
    GET_PIECE_VALUES(2),    // int[7], int[0]=plr, int[1..6] = 
    SET_PIECE_VALUES(3),	// int[7]
    KILL_PROCESS_IMMEDIATELY(4),	// null, döda tråd om denna erhålls
    STANDARD_SETUP(5),
    GET_POSSIBLE_MOVES_FROM_SQUARE(6),
    GET_BOARD_PIECES(7),
    SET_BOARD_PIECES(8),
    AVAILABLE_SQUARES(9),
    GET_MOVABLE_PIECES(10),	//ControlValue.BLACK el ControlValue.WHITE
    SET_MOVE_AS_STRING(11),		//null
	ANNOUNCE_PLAYER_STATUS(12),	// with one of the enum members in PlayerStatus.java
	SAVE_GAME(13),
	LOAD_GAME(14);
	
    int value;
    
    private MessageType(int val)
    {
    	value = val;
    }
    
    @Override
	public String toString()
    {
    	return this.name();
    }
}
