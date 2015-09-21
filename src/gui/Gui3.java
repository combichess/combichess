package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;    
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;


import main.Communicator;
import main.control.ControlValue;
import main.control.Message;
import main.control.MessageType;
import main.control.ProcessType;


//exempelkod från:
//http://zetcode.com/tutorials/javaswingtutorial/firstprograms/

public class Gui3 extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1729536400205912420L;
	
	private JPanel windowPanel = null;
	private JPanel topPanel = null;
	private JPanel bottomPanel = null;
	private JPanel boardPanel = null;
	private JPanel informationPanel = null;
	private String[] boardSetupAsString = null;		// vad varje ruta är för pjäser
	private boolean[] boardAvailabilityAsBoolean = null;	// vilka knappar du får klicka på, false = inactive, true = aktiv
	
	private ProcessType processType = ProcessType.Gui_1;
	private Timer idleTimer;
	private HashMap<String, ImageIcon> imageIcons = null;
	//private ImageIcon imageIcons[] = null; 
	
	@Override
	public void run() {
		
		loadImages();
		
				// Sätt upp hela GUI:et
			// referens hur till "./schacket ska se ut.jpg"
		windowPanel = new JPanel();
    	windowPanel.setLayout(new BoxLayout(windowPanel, BoxLayout.Y_AXIS));
    	
    	topPanel = new JPanel();
    	topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        boardPanel = new JPanel();
        informationPanel = new JPanel();
        bottomPanel = new JPanel();


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
        boardSetupAsString = new String[] {
            "BR", "BB", "BN", "BQ", "BK", "BN", "BB", "BR", 
            "BP", "BP", "BP", "BP", "BP", "BP", "BP", "BP",
            "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ",
            "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", 
            "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", 
            "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ",
            "WP", "WP", "WP", "WP", "WP", "WP", "WP", "WP",
            "WR", "WB", "WN", "WQ", "WK", "WN", "WB", "WR"
        };
        
        boardAvailabilityAsBoolean = new boolean[64];
        for (int i=0; i<boardAvailabilityAsBoolean.length; i++)
        	boardAvailabilityAsBoolean[i] = true;
        
        updateBoardPanel();

        topPanel.add(boardPanel);
        

        JTextArea textAreaRight = new JTextArea("text area");
        textAreaRight.setPreferredSize(new Dimension(100, 400));
        informationPanel.add(textAreaRight);
        topPanel.add(informationPanel);
        
        windowPanel.add(topPanel);
        windowPanel.add(bottomPanel);
        add(windowPanel);

        	// 	kör kontinuerlig uppdatering på timern för att se om board:en uppdateras
        idleTimer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateTimer();
			}
		});
        idleTimer.setRepeats(true);
        idleTimer.start();
        
        	// http://docs.oracle.com/javase/7/docs/api/java/awt/doc-files/AWTThreadIssues.html
        addWindowListener(new WindowAdapter() 
        {
        	@Override
        	public void windowClosing(WindowEvent event) 
        	{
        		closeGUI();
        	}
        });
        

        setTitle("*** Combichess ***");
        setSize(800, 500);
        
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// nu ska windowClosing köras om programmet avslutas.
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

		setVisible(true);
		
			// Sätt upp Board:en, låt detta göras efter hur användaren väljer förvald setup. 
			// I förvald setup väljs vilken färg spelaren ska ha, betänketid, standardsetup eller något annat magiskt.
		Message setupStandardBoard = new Message(ProcessType.Gui_1, ProcessType.Board_1, MessageType.STANDARD_SETUP, null);
		Communicator.addMessage(setupStandardBoard);
	}
	
	
	private void updateTimer()
	{
		handleMessages();
	}
	
	private void handleMessages()
	{
		Message mess = Communicator.getMessage(processType);
		while (mess != null) {
			switch (mess.getMessageType())
			{
			case KILL_PROCESS_IMMEDIATELY:
				closeGUI();
				break;
			case PROPOSE_MOVE:
				break;
			case SET_PIECE_VALUES:
				break;
			case SET_BOARD_DATA:
				boardSetupAsString = mess.getMessageData().split(",");
				updateBoardPanel();
				break;
			default:
				break;
			}
			mess = Communicator.getMessage(processType);
		};
	}
	
	private void closeGUI()
	{
		if (idleTimer != null)
			idleTimer.stop();
		System.out.println("Nu avslutas GUI:et och därför sänds avslutningsmeddelande till Boarden");
		Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.KILL_PROCESS_IMMEDIATELY, null));
		setVisible(false);
		dispose();
	}
	
	private void loadImages()
	{
		String imageStrings[] = new String[] {	"BB", "BK", "BN", "BP", "BQ", "BR", 
												"WB", "WK", "WN", "WP", "WQ", "WR"};
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		//for (int i=0; i<imageStrings.length; i++)
		//	imageStrings[i] = imageStrings[i] + ".bmp";
			
		//imageIcons = new ImageIcon[imageStrings.length];
		imageIcons = new HashMap<String, ImageIcon>();
		
		for (int i=0; i<imageStrings.length; i++)
		{
			System.out.print(imageStrings[i]);
			InputStream input = classLoader.getResourceAsStream(imageStrings[i] + ".bmp");
			Image img = null;
			try {
				img = ImageIO.read(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//imageIcons[i] = new ImageIcon(img);
			ImageIcon imageIcon = new ImageIcon(img);
			if (imageIcon != null)
				imageIcons.put(imageStrings[i], new ImageIcon(img));
			System.out.println(imageStrings[i] + "\t" + (imageIcon == null? "null": "icke null"));
		}
	}
	
		// 
	private void updateBoardPanel() 
	{
		if (boardSetupAsString.length != 64)
		{
			System.out.println("strs.length " + boardSetupAsString.length + "!= 64");
			return;
		}
		

        boardPanel.removeAll();
        boardPanel.setLayout(new GridLayout(8, 8, 1, 1));
		int i=0;
		for (String controlValueStr: boardSetupAsString)
		{
			// gör schackpjäsknapparna till klassmedlemmar och stoppa in rätt imageIcon här.
			ControlValue controlValue = new ControlValue(controlValueStr);
			ImageIcon icon = imageIcons.get(controlValue.toString());
			
			
			JButton nyKnapp = (icon != null)? new JButton(icon): new JButton("none");
			//nyKnapp.setEnabled(boardAvailabilityAsBoolean[i]);
			nyKnapp.setEnabled((i%3) == 0);
			nyKnapp.setMargin(new Insets(0, 0, 0, 0));
    		nyKnapp.setBackground(Color.blue);
    		nyKnapp.setBorder(null);

        	AL asdf = new AL(i%8, i/8);
        	i++;
            nyKnapp.addActionListener(asdf);
			boardPanel.add(nyKnapp);
		}
		boardPanel.revalidate();	// revalidate, repaint, reset eller vad?
	}
}

