package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;    
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

import main.Communicator;
import main.control.Message;
import main.control.MessageType;
import main.control.ProcessType;

//exempelkod fr�n:
//http://zetcode.com/tutorials/javaswingtutorial/firstprograms/

public class Gui3 extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1729536400205912420L;
	private JPanel windowPanel = null;
	private ProcessType processType = ProcessType.Gui_1;
	
	@Override
	public void run() {
				// S�tt upp hela GUI:et
		// TODO Auto-generated method stub
		windowPanel = new JPanel();
    	windowPanel.setLayout(new BoxLayout(windowPanel, BoxLayout.Y_AXIS));
    	
    	JPanel topPanel = new JPanel();
    	topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        JPanel boardPanel = new JPanel();
        JPanel informationPanel = new JPanel();
        JPanel bottomPanel = new JPanel();


        bottomPanel.setLayout(new GridLayout(3,3,3,3));
        String[] bottomButtons = {
        		"Nytt", "H�mta", "Spara", "Spara som", "L�gesinfo", "Ange sv�righetsgrad", "Be om f�rslag", "Ge upp", "Avsluta"
        };
        
        for (String bottomButton : bottomButtons)
        {
        	JButton nyKnapp = new JButton(bottomButton);
        	nyKnapp.setPreferredSize(new Dimension(40, 50));
        	bottomPanel.add(nyKnapp);
        }
        
        	// s�tter in en kant runt 
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
        
        AL guiTimerUpdater = new AL(100, 100);
        final Timer timer = new Timer(100, guiTimerUpdater);
        
        	// k�r kontinuerlig uppdatering p� timern f�r att se om board:en uppdateras
        timer.setRepeats(true);
        timer.start();
        
        	// http://docs.oracle.com/javase/7/docs/api/java/awt/doc-files/AWTThreadIssues.html
        addWindowListener(new WindowAdapter() 
        {
        	@Override
        	public void windowClosing(WindowEvent event) 
        	{
        		timer.stop();
        		System.out.println("Nu avslutas GUI:et och d�rf�r s�nds avslutningsmeddelande till Boarden");
        		Message mess = new Message(processType, ProcessType.Board_1, MessageType.KillProcessImmediately, null);
        		Communicator.addMessage(mess);
        		setVisible(false);
        		dispose();
        	}
        });
        

        setTitle("*** Combichess ***");
        setSize(800, 500);
        
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// nu ska windowClosing k�ras om programmet avslutas.
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

		setVisible(true);
		
		
			// S�tt upp Board:en, l�t detta g�ras efter hur anv�ndaren v�ljer f�rvald setup. 
			// I f�rvald setup v�ljs vilken f�rg spelaren ska ha, bet�nketid, standardsetup eller n�got annat magiskt.
		Message setupStandardBoard = new Message(ProcessType.Gui_1, ProcessType.Board_1, MessageType.StandardSetup, null);
		Communicator.addMessage(setupStandardBoard);
	}
	
}

