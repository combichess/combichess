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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import main.Communicator;
import main.control.ControlValue;
import main.control.Message;
import main.control.MessageType;
import main.control.PlayerStatus;
import main.control.ProcessType;
import system.board.PlayerColour;


//exempelkod fr�n:
//http://zetcode.com/tutorials/javaswingtutorial/firstprograms/

public class Gui extends JFrame {// implements Runnable {
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
	private JLabel[] verticalCoordLabels = null;
	private JLabel[] horizontalCoordLabels = null;
	private JTextArea textAreaRight = null;
	
	private static final Color WHITE_SQUARE_COLOUR = Color.lightGray;
	private static final Color BLACK_SQUARE_COLOUR = Color.darkGray;
	
	private ProcessType processType = ProcessType.Gui_1;
	private Timer idleTimer;
	private static final int UPDATE_GUI_IDLE_MILLISECONDS = 10; 
	private HashMap<String, ImageIcon> imageIcons = null;
	private GameStatus gameStatus = null;
	
	private String saveAsFileName = null;	
	private GameSettings gameSettings = null;

	public Gui() {
		
		loadImages();
		
		gameSettings = new GameSettings();
		Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.STANDARD_SETUP, null));
		
				// S�tt upp hela GUI:et
			// referens hur till "./schacket ska se ut.jpg"
		windowPanel = new JPanel();
    	windowPanel.setLayout(new BoxLayout(windowPanel, BoxLayout.Y_AXIS));
    	
    	topPanel = new JPanel();
    	topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        boardPanel = new JPanel();
        informationPanel = new JPanel();
        bottomPanel = new JPanel();

        squares = new JButton[64];
        //whiteVisibleAtBottom = true;
        bottomPanel.setLayout(new GridLayout(3,3,3,3));
        
        for (Buttons butt: new Buttons[]{Buttons.Nytt, Buttons.Get, Buttons.Save, Buttons.SaveAs, Buttons.Info, Buttons.Difficult,
        		Buttons.Suggest, Buttons.Resign, Buttons.Exit})
        {
        	JButton nyKnapp = new JButton(butt.toString());
        	nyKnapp.setPreferredSize(new Dimension(40, 50));
        	nyKnapp.setActionCommand("" + butt.getValue());
        	nyKnapp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent buttonId) {
					buttonIdClick(Integer.parseInt(buttonId.getActionCommand()));
				}
        	});
        	bottomPanel.add(nyKnapp);
        }
        
        boardPanel.setLayout(new GridLayout(9, 9, 1, 1));
        verticalCoordLabels = new JLabel[9];
        horizontalCoordLabels = new JLabel[9];
        
        int b = 0;
        for (int i=0; i<81; i++) {
        	if (i>72) {
        		horizontalCoordLabels[i-73] = new JLabel();
        		horizontalCoordLabels[i-73].setHorizontalAlignment(SwingConstants.CENTER);
        		horizontalCoordLabels[i-73].setVerticalAlignment(SwingConstants.TOP);// SwingConstants.CENTER);
        		boardPanel.add(horizontalCoordLabels[i-73]);
        		
        	} else if (i==72) {
        		boardPanel.add(new JLabel());
        	}  else if ((i%9) == 0)	{ // l�gg till 8,7,6...1
        		
        			// g�r dessa labels till klassmedlemmar s� att man kan byta sida 
        		int verticalCoord = i/9;
        		verticalCoordLabels[verticalCoord] = new JLabel();
	    		verticalCoordLabels[verticalCoord].setHorizontalAlignment(SwingConstants.RIGHT);
	    		verticalCoordLabels[verticalCoord].setVerticalAlignment(SwingConstants.CENTER);
	    		boardPanel.add(verticalCoordLabels[verticalCoord]);
	    	} else {
        		squares[b] = new JButton();
            	squares[b].setMargin(new Insets(0, 0, 0, 0));
            	squares[b].setForeground(Color.red);
            	squares[b].setBackground(((9*b / 8) % 2) == 0? WHITE_SQUARE_COLOUR: BLACK_SQUARE_COLOUR);
    			squares[b].setBorder(null);
    			squares[b].setActionCommand("" + b);
    			squares[b].addActionListener(new ActionListener() {
    				@Override
    				public void actionPerformed(ActionEvent buttonId) {
    					buttonIdClick(Integer.parseInt(buttonId.getActionCommand()));
    				}
            	});
    			boardPanel.add(squares[b]);
    			b++;
        	}
        }
        updateCoordinateLabelTexts();
        topPanel.add(boardPanel);
        
        textAreaRight = new JTextArea("");
        textAreaRight.setEditable(false);
        JScrollPane sp = new JScrollPane(textAreaRight);
        sp.setPreferredSize(new Dimension(200, 500));
        informationPanel.add(sp);
        topPanel.add(informationPanel);
        
        windowPanel.add(topPanel);
        windowPanel.add(bottomPanel);
        add(windowPanel);

        // 	k�r kontinuerlig uppdatering p� timern f�r att se om board:en uppdateras
        idleTimer = new Timer(UPDATE_GUI_IDLE_MILLISECONDS, new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {
				idleFunction();
			}
		});
        idleTimer.setRepeats(true);
        idleTimer.start();
        
        	// http://docs.oracle.com/javase/7/docs/api/java/awt/doc-files/AWTThreadIssues.html
        addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent event) 
        	{
        		closeGUI();
        	}
        });
        
        setTitle("*** Combichess ***");
        setSize(750, 770);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
			// S�tt upp Board:en, l�t detta g�ras efter hur anv�ndaren v�ljer f�rvald setup. 
			// I f�rvald setup v�ljs vilken f�rg spelaren ska ha, bet�nketid, standardsetup eller n�got annat magiskt.
	}
	
	public void start()
	{
		setVisible(true);
		setAllSquaresEnabled(false);
		Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.GET_MOVABLE_PIECES, "" + ControlValue.WHITE));
		
		gameStatus = new GameStatus();
		startGameSettingsAlert();
	}
	
	private void startGameSettingsAlert()
	{
		boolean isWhitePlayerAtBottomOfScreenBefore = gameSettings.isWhitePlayerAtBottomOfScreen();
		gameSettings.runAlert();
		
		//boolean isWhitePlayerAtBottomOfScreenAfter = !(gameSettings.isBlackPlayerHuman() == true && gameSettings.isWhitePlayerHuman() == false);
		boolean isWhitePlayerAtBottomOfScreenAfter = gameSettings.isWhitePlayerAtBottomOfScreen();
		
		if (isWhitePlayerAtBottomOfScreenAfter != isWhitePlayerAtBottomOfScreenBefore)
		{
			// redraw board
			updateCoordinateLabelTexts();
			
			// g�r alla squares disabled
			setAllSquaresEnabled(false);

			// skicka ett h�mta-alla-pj�ser fr�n boardet
			Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.GET_BOARD_PIECES, null));
			
			updateMove();
		}
	}
	
	private void updateCoordinateLabelTexts()
	{
		boolean whiteDown = gameSettings.isWhitePlayerAtBottomOfScreen();
		for (int i=0; i<8; i++)
		{
			//verticalCoordLabels[i].setText(Integer.toString(whiteDown? i+1: 8-i));
			verticalCoordLabels[i].setText(Integer.toString(whiteDown? 8-i: i+1));
			horizontalCoordLabels[i].setText("" + (char)(whiteDown? ('A' + i): ('H' - i)));
		}
	}
	
	private void setAllSquaresEnabled(boolean enabled)
	{
		for (JButton square: squares)
			square.setEnabled(enabled);
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
			case SET_MOVE_AS_STRING: {		// detta �r bekr�ftelse p� att ett drag �r flyttat och turen flyttas �ver till n�sta spelare.
				//textAreaRight.setText(textAreaRight.getText() + mess.getMessageData());// + '\n');
				textAreaRight.setText(mess.getMessageData());// + '\n');
				gameStatus.switchPlayer();
				updateMove();
				break;}
			case ANNOUNCE_PLAYER_STATUS: {
				//System.out.println(mess.getMessageData());
				String[] arr = mess.getMessageData().split(",");
				PlayerStatus ps = PlayerStatus.createFromString(arr[0]);
				 
				//System.out.println(arr);
				if (ps == PlayerStatus.CHECK_MATE || ps == PlayerStatus.STALE_MATE)
					CheckAlert.createCheckAlert(mess.getMessageData());
				break;
			}
			case SET_PLAYERS_TURN: {
				gameStatus.setPlayerTurn(mess.getMessageData().equals("W")? PlayerColour.White: PlayerColour.Black);
				break;}
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
		//System.out.println("Nu avslutas GUI:et och d�rf�r s�nds avslutningsmeddelande till Boarden");
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
			String toLoad = "images/" + imageStrings[i] + ".png";
			
			InputStream input = classLoader.getResourceAsStream(toLoad);
			Image img = null;
			try {
				img = ImageIO.read(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ImageIcon imageIcon = new ImageIcon(img);
			if (imageIcon != null)
				imageIcons.put(imageStrings[i], new ImageIcon(img));
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
	private void updateBoardPieces(String messData) 
	{
		String boardSetupAsString[] = messData.split(",");
		
		int squareId=0;
		for (String controlValueStr: boardSetupAsString)
		{
			// g�r schackpj�sknapparna till klassmedlemmar och stoppa in r�tt imageIcon h�r.
			int buttonId = getButtonIdFromSquareId(squareId);
			ControlValue controlValue = new ControlValue(controlValueStr);
			ImageIcon icon = imageIcons.get(controlValue.toString());
			if (icon != null) {
				squares[buttonId].setText("");
				squares[buttonId].setIcon(icon);
				squares[buttonId].setDisabledIcon(icon);
			} else {
				squares[buttonId].setText("");
				squares[buttonId].setIcon(null);
			}
        	squareId++;
		}
	}
	
	private void updateBoardAvailability(String messData)
	{
		String squareAvailStr[] = messData.split(",");
		if (squareAvailStr.length != 64)
		{
			System.out.println("updateBoardAvailability failed");
			return;
		}
		
		for (int squareId=0; squareId<squareAvailStr.length; squareId++) {
			boolean squareEnabled = squareAvailStr[squareId].contains("1");
			int buttonId = getButtonIdFromSquareId(squareId);
			squares[buttonId].setEnabled(squareEnabled);
			if (squares[buttonId].getIcon() == null && squareEnabled)
				squares[buttonId].setText("*");
		}
	}
	
	
		// denna metoden ska ge r�tt pj�s oavsett hur bordet �r v�nt.
	private int getSquareIdFromButtonId(int buttonId) {
		int buttonIdX = buttonId%8;
		int buttonIdY = buttonId/8;
		if (gameSettings.isWhitePlayerAtBottomOfScreen())
			return (7-buttonIdY)*8 + buttonIdX;
		else
			return 8*buttonIdY + 7 - buttonIdX;
	}
	
	private int getButtonIdFromSquareId(int squareId) {
		int squareIdX = squareId%8;
		int squareIdY = squareId/8;
		if (gameSettings.isWhitePlayerAtBottomOfScreen())
			return (7-squareIdY)*8 + squareIdX;
		else
			return 8*squareIdY + 7 - squareIdX;
	}
	
	public void buttonIdClick(int buttonId)		// man f�r anta att det �r en m�nniska f�r annars �r inte knapparna enabled
	{
		if (buttonId >= 0 && buttonId < 64) {
			int squareId = getSquareIdFromButtonId(buttonId);
			int status = gameStatus.getStatus();
			switch (status)
			{
			case GameStatus.GAME_OVER:
				return;
			case GameStatus.WHITE_TO_MOVE:
			case GameStatus.BLACK_TO_MOVE:
				if (gameStatus.getChosenSquareFrom() == GameStatus.UNDEFINED)
					gameStatus.setChosenSquareFrom(squareId);
				else if (gameStatus.getChosenSquareTo() == GameStatus.UNDEFINED)
					gameStatus.setChosenSquareTo(squareId);
				else 
					System.out.println("B�de chosenSquareFrom och To �r defined :(");
				updateMove();
				break;
			}
		} else if (buttonId >= 100) {
			
			if (buttonId == Buttons.Nytt.getValue()){
				gameStatus.setGameOver();
				System.out.println("Nu �r det game over");
				Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.STANDARD_SETUP, null));
				startGameSettingsAlert();
				System.out.println("Nu �r status = " + gameStatus);
				gameStatus.setPlayerTurn(PlayerColour.White);
				saveAsFileName = null;
				textAreaRight.setText("");
				updateMove();
			}
			
			if (buttonId == Buttons.Exit.getValue())
				closeGUI();
			
			if (buttonId == Buttons.Difficult.getValue())
				startGameSettingsAlert();
			
			if (buttonId == Buttons.Save.getValue() || buttonId == Buttons.SaveAs.getValue())
			{
				saveGame(buttonId == Buttons.SaveAs.getValue());
			}
			
			if (buttonId == Buttons.Get.getValue())
				loadGame();
		}
	}
	
	public void updateMove()
	{
		if (gameStatus.isGameOver())
		{
			System.out.println("updateMove(): gameStatus = GAME_OVER");
			return;
		}
		
		boolean isWhite = gameStatus.isWhitesTurn();
		String stringColour = "" + (isWhite? ControlValue.WHITE: ControlValue.BLACK);
		boolean isHuman = isWhite? gameSettings.isWhitePlayerHuman(): gameSettings.isBlackPlayerHuman();
		
		if (gameStatus.getStatus() == GameStatus.BLACK_TO_MOVE || gameStatus.getStatus() == GameStatus.WHITE_TO_MOVE) {
			if (isHuman) {
				int sqrFrom = gameStatus.getChosenSquareFrom();
				int sqrTo = gameStatus.getChosenSquareTo();
				
					// h�r ska alla squares vara disabled
				if (sqrFrom == GameStatus.UNDEFINED) {
					Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.GET_MOVABLE_PIECES, stringColour));
				} else if (sqrTo == GameStatus.UNDEFINED) {
					Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.GET_POSSIBLE_MOVES_FROM_SQUARE, Integer.toString(sqrFrom)));
				} else {
					Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.COMMIT_MOVE, Integer.toString(sqrFrom) + "," + Integer.toString(sqrTo)));
					Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.GET_BOARD_PIECES, null));
				}
				
				setAllSquaresEnabled(false);
			} else {
				int numMovesForward = isWhite? gameSettings.getNumberOfMovesForwardForWhite(): gameSettings.getNumberOfMovesForwardForBlack();
				Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.PROPOSE_MOVE, stringColour + "," + numMovesForward));
				Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.GET_BOARD_PIECES, null));
			}
			
		} else {
			System.out.println("Gui.java r.467\tDen ska inte komma hit f�r d� �r gameStatus ett v�rde som det inte ska ha.");
		}
	}
	
	
	private void loadGame()
	{
		String fileName = FileSelector.load();
		if (fileName != null)
			Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.LOAD_GAME, fileName));
		startGameSettingsAlert();
	}
	
	private void saveGame(boolean saveAs) 
	{
		String fileName = null;
		if ((saveAs) || (!saveAs && saveAsFileName == null))
			fileName = FileSelector.save();
		else
			fileName = saveAsFileName;
		
		if (fileName != null)
			Communicator.addMessage(new Message(processType, ProcessType.Board_1, MessageType.SAVE_GAME, fileName));
	}
}