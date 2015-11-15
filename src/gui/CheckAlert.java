package gui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.control.PlayerStatus;

public class CheckAlert extends JFrame {
	private static final long serialVersionUID = 7846665767726817162L;
	public static final String BLACK_MATE = "Black is checkmate. White wins the game";
	public static final String WHITE_MATE = "White is checkmate. Black wins the game";
	public static final String BLACK_STALE_MATE = "White in stalemate. Game ends with draw.";
	public static final String WHITE_STALE_MATE = "Black in stalemate. Game ends with draw.";
	
	private JPanel panel = null; 
	private JLabel topText = null;
	
	public static void createCheckAlert(String textMessage)
	{
		(new CheckAlert()).runAlert(textMessage);
	}
	
	public static void createCheckAlert(PlayerStatus ps, String colour)
	{
		String opponent = (colour.equals("White")? "Black": "White"); 
		String textMessage = "";
		switch(ps)
		{
		case CHECK:
			textMessage = colour + " is checked by " + opponent + ".";
			break;
		case CHECK_MATE:
			textMessage = colour + " is checkmate. " + opponent + " wins the game.";
			break;
		case STALE_MATE:
			textMessage = colour + " in stalemate. Game ends with draw.";
			break;
		default:
			break;
		}
		createCheckAlert(textMessage);
	}
	
	public CheckAlert()
	{
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		topText = new JLabel("Ingen har vunnit ännu");
		topText.setHorizontalAlignment(SwingConstants.CENTER);
		
	    panel.add(topText);
	}

	public void runAlert(String textMessage)
	{
		topText.setText(textMessage);
		JOptionPane.showConfirmDialog(null, panel, "Settings: ", JOptionPane.PLAIN_MESSAGE);
	}
}
