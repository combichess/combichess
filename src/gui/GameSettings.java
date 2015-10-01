package gui;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


// http://stackoverflow.com/questions/6555040/multiple-input-in-joptionpane-showinputdialog
// http://docs.oracle.com/javase/tutorial/uiswing/layout/card.html

public class StartupAlert extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public static final String CPU_OPPONENT_LEVEL_1 = "Åke (3/5)";	// tänker 3 drag framåt
	public static final String CPU_OPPONENT_LEVEL_2 = "Sixten (4/5)";	// tänker 4 drag framåt
	public static final String CPU_OPPONENT_LEVEL_3 = "Elisabeth (5/5)";	// tänker 5 drag framåt
		
	private static final String STARTUP_HUMAN = "human";
	private static final String STARTUP_CPU = "cpu";
	
	
	
	private JPanel panel = null; 
	private JTextArea whiteCompHuman = null;
	private JComboBox<String> whiteCompCPU = null;
	private JTextArea blackCompHuman = null;
	private JComboBox<String> blackCompCPU = null;
	private JPanel whiteCards = null;
	private JPanel blackCards = null;
	
	private boolean whitePlayerIsHuman;
	private boolean blackPlayerIsHuman;
	private String whitePlayerName;
	private String blackPlayerName;
	
	public StartupAlert()
	{
		whitePlayerIsHuman = true;
		blackPlayerIsHuman = false;
		whitePlayerName = "Fru Vit";
		blackPlayerName = "Herr Svart";
		panel = new JPanel();
	    panel.setLayout(new GridLayout(3, 3, 1, 1));
	
	
	        // rad 1
	    panel.add(new JLabel("Sida: "));
	    panel.add(new JLabel("Namn: "));
	    panel.add(new JLabel("Human or Cpu: "));
	
	
	
	
	        // rad 2
	    panel.add(new JLabel("Vit: "));
	    
	    String cpuNames[] = new String[] {CPU_OPPONENT_LEVEL_1, CPU_OPPONENT_LEVEL_2, CPU_OPPONENT_LEVEL_3};
	    whiteCompHuman = new JTextArea("Mattias");
	    whiteCompCPU = new JComboBox<String>(cpuNames);
	    whiteCards = new JPanel(new CardLayout());
	    whiteCards.add(whiteCompHuman, STARTUP_HUMAN);
	    whiteCards.add(whiteCompCPU, STARTUP_CPU);

	    panel.add(whiteCards);
	    JCheckBox checkWhite = new JCheckBox("Human");
	    checkWhite.setSelected(whitePlayerIsHuman);
	    ((CardLayout) whiteCards.getLayout()).show(whiteCards, whitePlayerIsHuman? STARTUP_HUMAN: STARTUP_CPU);
	    
	    
	    checkWhite.addActionListener(new ActionListener() {
	    	@Override
	        public void actionPerformed(ActionEvent actionEvent)
	        {
	            AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
	            boolean selected = abstractButton.getModel().isSelected();
	            System.out.println("actionPerformed on vit checkbox");
	            setColourToHuman(true, selected);
	        }
	    });
	    panel.add(checkWhite);

		
		
		
		
		    // rad 3
		panel.add(new JLabel("Svart: "));
		
		blackCompHuman = new JTextArea("Mattias");
		blackCompCPU = new JComboBox<String>(cpuNames);
		blackCards = new JPanel(new CardLayout());
	    blackCards.add(blackCompHuman, STARTUP_HUMAN);
	    blackCards.add(blackCompCPU, STARTUP_CPU);
		panel.add(blackCards);
		
		JCheckBox checkBlack = new JCheckBox("Human: ");
		checkBlack.setSelected(blackPlayerIsHuman);
		((CardLayout) blackCards.getLayout()).show(blackCards, blackPlayerIsHuman? STARTUP_HUMAN: STARTUP_CPU);
		
		checkBlack.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent actionEvent)
		    {
		        AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
		        boolean selected = abstractButton.getModel().isSelected();
		        System.out.println("actionPerformed on svart checkbox");
		        setColourToHuman(false, selected);
		    }
		});
		panel.add(checkBlack);
	}
	
	public void runAlert()
	{
		JOptionPane.showConfirmDialog(null, panel, "Settings: ", JOptionPane.PLAIN_MESSAGE);

		whitePlayerName = whitePlayerIsHuman? whiteCompHuman.getText(): (String) (whiteCompCPU.getSelectedItem());
		blackPlayerName = blackPlayerIsHuman? blackCompHuman.getText(): (String) (blackCompCPU.getSelectedItem());
		System.out.println("white: " + whitePlayerName + "\tHuman: " + whitePlayerIsHuman);
		System.out.println("black: " + blackPlayerName + "\tHuman: " + blackPlayerIsHuman);
		//String result = (String) JOptionPane.showInputDialog(this, "Message", "Title", JOptionPane.PLAIN_MESSAGE, null, possibilities, possibilities[0]);
	}
	
	

	private void setColourToHuman(boolean isWhite, boolean isHuman)
	{
		JPanel cards = isWhite? whiteCards: blackCards;
		CardLayout cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards, isHuman? STARTUP_HUMAN: STARTUP_CPU);
		
		if (isWhite)
			whitePlayerIsHuman = isHuman;
		else 
			blackPlayerIsHuman = isHuman;
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
}

