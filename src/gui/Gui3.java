package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;    
import javax.swing.JPanel;
import javax.swing.JTextArea;

//exempelkod från:
//http://zetcode.com/tutorials/javaswingtutorial/firstprograms/

public class Gui3 extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1729536400205912420L;

	public Gui3() {
        initUI();
    }
	
    public final void initUI() {
    	
    	JPanel windowPanel = new JPanel();
    	windowPanel.setLayout(new BoxLayout(windowPanel, BoxLayout.Y_AXIS));
    	
    	JPanel topPanel = new JPanel();
    	topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        JPanel boardPanel = new JPanel();
        JPanel informationPanel = new JPanel();
        JPanel bottomPanel = new JPanel();


        bottomPanel.setLayout(new GridLayout(3,3,3,3));
        String[] bottomButtons = {
        		"Nytt", "Hämta", "Spara", "Spara som", "Lägesinfo", "Ange svårighetsgrad", "Be om förslag", "Ge upp", "Avsluta"
        };
        
        for (String bottomButton : bottomButtons)
        {
        	JButton nyKnapp = new JButton(bottomButton);
        	nyKnapp.setPreferredSize(new Dimension(40, 50));
        	bottomPanel.add(nyKnapp);
        }
        
        	// sätter in en kant runt 
        //panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        boardPanel.setLayout(new GridLayout(8, 8, 1, 1));

        String[] buttons = {
            "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", 
            "G1", "5", "6", "*", "1", "2", "3", "-", 
            "F1", ".", "=", "+", "0", ".", "=", "+",
            "E1", "Bck", "Tjnea", "Close", "7", "8", "9", "/", 
            "D1", "5", "6", "*", "1", "2", "3", "-", 
            "C1", ".", "=", "+", "0", ".", "=", "+",
            "B1", "Bck", "Tjnea", "Close", "7", "8", "9", "/", 
            "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8",
        };

        for (int i = 0; i < buttons.length; i++) {

        	JButton nyKnapp = new JButton(buttons[i]);

        	AL asdf = new AL(i%8, i/8); 
        	
            nyKnapp.addActionListener(asdf);
            boardPanel.add(nyKnapp);
        }

        topPanel.add(boardPanel);
        

        JTextArea textAreaRight = new JTextArea("text area");
        textAreaRight.setPreferredSize(new Dimension(100, 400));
        informationPanel.add(textAreaRight);
        topPanel.add(informationPanel);
        
        windowPanel.add(topPanel);
        windowPanel.add(bottomPanel);
        add(windowPanel);

        setTitle("GridLayout");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void starta() {

        EventQueue.invokeLater(new Runnable() {
        
            @Override
            public void run() {
            	Gui3 ex = new Gui3();
                ex.setVisible(true);
            }
        });
    }
}

