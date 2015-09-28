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

public class Gui extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1729536400205912420L;
	
	private JPanel windowPanel = null;
	private JPanel topPanel = null;
	private JPanel bottomPanel = null;
	private JPanel boardPanel = null;
	private JPanel informationPanel = null;
	private JButton[] squares = null;
	
	private ProcessType processType = ProcessType.Gui_1;
	private Timer idleTimer;
	private static final int UPDATE_GUI_IDLE_MILLISECONDS = 10; 
	private HashMap<String, ImageIcon> imageIcons = null;
	private int moveFrom;
	
		// skit i att skapa en egen tråd och låt GUI:et köra på den tråd som skapas med guiet. Byt run() till konstruktor
	@Override
	public void run() {
		
		loadImages();
		
		Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.STANDARD_SETUP, null));
		
				// Sätt upp hela GUI:et
			// referens hur till "./schacket ska se ut.jpg"
		windowPanel = new JPanel();
    	windowPanel.setLayout(new BoxLayout(windowPanel, BoxLayout.Y_AXIS));
    	
    	topPanel = new JPanel();
    	topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        boardPanel = new JPanel();
        informationPanel = new JPanel();
        bottomPanel = new JPanel();

        squares = new JButton[64];

        bottomPanel.setLayout(new GridLayout(3,3,3,3));
        
        for (Buttons butt: new Buttons[]{Buttons.Nytt, Buttons.Get, Buttons.Save, Buttons.SaveAs, Buttons.Info, Buttons.Difficult,
        		Buttons.Suggest, Buttons.Resign, Buttons.Exit})
        {
        	JButton nyKnapp = new JButton(butt.toString());
        	nyKnapp.setPreferredSize(new Dimension(40, 50));
        	nyKnapp.addActionListener(new AL(butt.getValue(), this));
        	bottomPanel.add(nyKnapp);
        }
        
        boardPanel.setLayout(new GridLayout(8, 8, 1, 1));
        for (int i=0; i<64; i++) {
        	squares[i] = new JButton("start");
        	squares[i].setMargin(new Insets(0, 0, 0, 0));
			squares[i].setBackground(Color.blue);
			squares[i].setBorder(null);
			squares[i].addActionListener(new AL(i, this));
			
        	boardPanel.add(squares[i]);
        }

        topPanel.add(boardPanel);
        
        JTextArea textAreaRight = new JTextArea("text area");
        textAreaRight.setPreferredSize(new Dimension(100, 400));
        informationPanel.add(textAreaRight);
        topPanel.add(informationPanel);
        
        windowPanel.add(topPanel);
        windowPanel.add(bottomPanel);
        add(windowPanel);

        // 	kör kontinuerlig uppdatering på timern för att se om board:en uppdateras
        idleTimer = new Timer(UPDATE_GUI_IDLE_MILLISECONDS, new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {
				idleFunction();
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
        setSize(650, 670);
        
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// nu ska windowClosing köras om programmet avslutas.
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

		setVisible(true);
		moveFrom = -1;
		
			// Sätt upp Board:en, låt detta göras efter hur användaren väljer förvald setup. 
			// I förvald setup väljs vilken färg spelaren ska ha, betänketid, standardsetup eller något annat magiskt.
		//Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.STANDARD_SETUP, null));
	}
	
	
	private void idleFunction()
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
			case SET_BOARD_PIECES:
				updateBoardPieces(mess.getMessageData());
				break;
			case AVAILABLE_SQUARES:
				updateBoardAvailability(mess.getMessageData());
				break;
			/*case GET_POSSIBLE_MOVES_FROM_SQUARE:
				
				updateWithPossibleMovesToMake(mess.getMessageData());*/
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

		imageIcons = new HashMap<String, ImageIcon>();
		
		for (int i=0; i<imageStrings.length; i++)
		{
			//System.out.print(imageStrings[i]);
			InputStream input = classLoader.getResourceAsStream(imageStrings[i] + ".bmp");
			Image img = null;
			try {
				img = ImageIO.read(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ImageIcon imageIcon = new ImageIcon(img);
			if (imageIcon != null)
				imageIcons.put(imageStrings[i], new ImageIcon(img));
			//System.out.println(imageStrings[i] + "\t" + (imageIcon == null? "null": "icke null"));
		}
	}
	/*
	 * 	0 = A1
		1 = B1
		2 = C1
		7 = H1
		8 = A2
		...
		56= A8
		57= B8
		63= H8*
	 */
		// 
	private void updateBoardPieces(String messData) 
	{
		String boardSetupAsString[] = messData.split(",");
		if (boardSetupAsString.length != 64)
		{
			System.out.println("strs.length = " + boardSetupAsString.length + " != 64");
			return;
		}
        
		int i=0;
		for (String controlValueStr: boardSetupAsString)
		{
			// gör schackpjäsknapparna till klassmedlemmar och stoppa in rätt imageIcon här.
			ControlValue controlValue = new ControlValue(controlValueStr);
			ImageIcon icon = imageIcons.get(controlValue.toString());
			if (icon != null) {
				squares[i].setText("");
				squares[i].setIcon(icon);
			} else {
				squares[i].setText("icon=null");
				squares[i].setIcon(null);
			}
			
			//squares[i].setEnabled((i%3) == 0);
        	i++;
		}
		//boardPanel.revalidate();	// revalidate, repaint, reset eller vad?

		//boardPanel.validate();
		//boardPanel.repaint();
	}
	
	private void updateBoardAvailability(String messData)
	{
		String squareAvailStr[] = messData.split(",");
		if (squareAvailStr.length != 64)
		{
			System.out.println("updateBoardAvailability failed");
			return;
		}
		
		for (int i=0; i<squareAvailStr.length; i++) {
			squares[i].setEnabled(squareAvailStr[i].contains("1"));
			//squares[i].validate();
			//squares[i].repaint();
		}
		
		//boardPanel.validate();
		//boardPanel.repaint();
		System.out.println("updateBoardAvailability run");
	}
	
	public void buttonIdClick(int squareId)
	{
		System.out.println("Button Id: " + squareId);
		if (squareId >= 0 && squareId < 64) {
			if (moveFrom == -1) {
				moveFrom = squareId;
				Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.GET_POSSIBLE_MOVES_FROM_SQUARE, Integer.toString(squareId)));
			} else {
				Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.COMMIT_MOVE, Integer.toString(moveFrom) + "," + Integer.toString(squareId)));
				moveFrom = -1;
				Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.PROPOSE_MOVE, ControlValue.BLACK + ""));
			}
		} else if (squareId >= 100) {
			if (squareId == Buttons.Nytt.getValue())
				Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.STANDARD_SETUP, null));
			
			if (squareId == Buttons.Exit.getValue())
				closeGUI();
		}
		
	}
}


