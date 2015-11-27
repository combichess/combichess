package gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
	
	private JPanel midPanel = null;		// Mitten
	private JPanel btmPanel = null;		// Nere
	
	private JList filenameList = null;	// Uppe
	private JLabel filenameLabel = null;	// Mitten <-
	private JTextArea filenameTextarea = null;	// Mitten ->
	private JButton okButton = null;	// Nere <-
	private JButton cancelButton = null;	// Nere ->
	
	
	private FileSelector()
	{
		
		panel = new JPanel();
	    //panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
	
	
	        // rad 1
	    filenameList = new JList<String>();
	    filenameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    filenameList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
	    //filenameList.setVisibleRowCount(-1);
	    
	    filenameList = new JList<String>(getFileNames());

        //filenameList.setPreferredSize(new Dimension(200, 300));
	    panel.add(filenameList);
	
	    
		


	
	        // rad 2
		midPanel = new JPanel();
		midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.LINE_AXIS));
		filenameLabel = new JLabel("Filnamn: ");
		midPanel.add(filenameLabel);
		filenameTextarea = new JTextArea();
		midPanel.add(filenameTextarea);
	    panel.add(midPanel);
	    
	    /*checkWhite.addActionListener(new ActionListener() {
	    	@Override
	        public void actionPerformed(ActionEvent actionEvent)
	        {
	            AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
	            boolean selected = abstractButton.getModel().isSelected();
	            System.out.println("actionPerformed on vit checkbox");
	        }
	    });
	    panel.add(checkWhite);*/

		
		    // rad 3
	    btmPanel = new JPanel();
		btmPanel.setLayout(new BoxLayout(btmPanel, BoxLayout.LINE_AXIS));
		
		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");
		btmPanel.add(okButton);
		btmPanel.add(cancelButton);
		panel.add(btmPanel);
	}
	
	private DefaultListModel<String> getFileNames()
	{
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();

		File folder = new File("./saved games/");
		File[] listOfFiles = folder.listFiles();
		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
			    listModel.addElement(file.getName());
		    }
		}
	    return listModel;
	}
	
	static public String saveAs()
	{
		return null;
	}
	
	static public String load()
	{
		FileSelector fileSelector = new FileSelector();
		int result = JOptionPane.showConfirmDialog(null, fileSelector.panel, "Settings: ", JOptionPane.OK_CANCEL_OPTION);
		return null;
	}
	
	static public String save()
	{
		return null;
	}
	
}
