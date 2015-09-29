package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

	// dum klass. Används enbart för att åter få kontakt med Gui:et efter en knapptryckning, men fattar inte hur jag annars ska göra :( 
public class StartupAlert extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String CPU_OPPONENT_LEVEL_1 = "Åke (3/5)";	// tänker 3 drag framåt
	private static final String CPU_OPPONENT_LEVEL_2 = "Sixten (4/5)";	// tänker 4 drag framåt
	private static final String CPU_OPPONENT_LEVEL_3 = "Elisabeth (5/5)";	// tänker 5 drag framåt
	
	private boolean whitePlayerIsHuman;
	private boolean blackPlayerIsHuman;
	private String whitePlayerName;
	private String blackPlayerName;
	private long whitePlayerMillisecondsLeft;
	private long blackPlayerMillisecondsLeft;
	
	public StartupAlert()
	{
		whitePlayerIsHuman = false;
		blackPlayerIsHuman = false;
		whitePlayerName = "";
		blackPlayerName = "";
		whitePlayerMillisecondsLeft = 300000;
		blackPlayerMillisecondsLeft = 300000;
	}
	
	
	
	public void askQuestions() {
	
			// http://stackoverflow.com/questions/6555040/multiple-input-in-joptionpane-showinputdialog
		String[] possibilities = {CPU_OPPONENT_LEVEL_1, CPU_OPPONENT_LEVEL_2, CPU_OPPONENT_LEVEL_3};
		String result = (String) JOptionPane.showInputDialog(this, "Message", "Title", JOptionPane.PLAIN_MESSAGE, null, possibilities, possibilities[0]);
	
		//int result = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
		System.out.println(result);
    }



	public boolean whitePlayerIsHuman() {
		return whitePlayerIsHuman;
	}



	public boolean isBlackPlayerIsHuman() {
		return blackPlayerIsHuman;
	}


	public String getWhitePlayerName() {
		return whitePlayerName;
	}


	public String getBlackPlayerName() {
		return blackPlayerName;
	}



	public long getWhitePlayerMillisecondsLeft() {
		return whitePlayerMillisecondsLeft;
	}



	public long getBlackPlayerMillisecondsLeft() {
		return blackPlayerMillisecondsLeft;
	}
}

