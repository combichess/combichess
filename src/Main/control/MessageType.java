package main.control;

public enum MessageType {
        CommitMove,        // int[2] {from, to}, vid castling s� m�ste flera commits skickas.
        ProposeMove,        // int {player}, messSentTo = Gui_1
        GetPlayerValues,    // int[7], int[0]=plr, int[1..6] = 
        SetPlayerValues;	// int[7]
}
