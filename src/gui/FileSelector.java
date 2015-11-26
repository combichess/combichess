package gui;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

public class FileSelector extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static final String CPU_OPPONENT_LEVEL_1 = "Åke (3/5)";	// tänker 3 drag framåt
	private static final String CPU_OPPONENT_LEVEL_2 = "Sixten (4/5)";	// tänker 4 drag framåt
	private static final String CPU_OPPONENT_LEVEL_3 = "Elisabeth (5/5)";	// tänker 5 drag framåt
		
	private static final String STARTUP_HUMAN = "human";
	private static final String STARTUP_CPU = "cpu";
	
	private JPanel panel = null;
	private JList filenameList = null; 
	private JTextArea filenameTextarea = null;
	private JButton okButton = null;
	private JButton cancelButton = null;
	
	
	private FileSelector()
	{
		
		panel = new JPanel();
	    panel.setLayout(new GridLayout(3, 1, 1, 1));
	
	
	        // rad 1
	    filenameList = new JList<String>();
	    filenameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    filenameList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
	    filenameList.setVisibleRowCount(-1);
	    
	    DefaultListModel listModel = new DefaultListModel();
	    listModel.addElement("Jane Doe");
	    listModel.addElement("John Smith");
	    listModel.addElement("Kathy Green");
	    


	    filenameList = new JList(listModel);
	
	
	
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
		    }
		});
		panel.add(checkBlack);
	}
	
	private void 
	
	static public String saveAs()
	{
		return null;
	}
	
	static public String load()
	{
		return null;
	}
	
	static public String save()
	{
		return null;
	}
	
}
