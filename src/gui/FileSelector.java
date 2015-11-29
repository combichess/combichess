package gui;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FileSelector extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String FILE_ENDING = ".txt";
	
	private JPanel panel = null;
	List<String> fileNames = null;
	
	private JPanel midPanel = null;		// Mitten
	private JList<String> filenameList = null;	// Uppe
	private JLabel filenameLabel = null;	// Mitten <-
	private JTextField filenameTextfield = null;	// Mitten ->
	
	
	private FileSelector()
	{
		
		panel = new JPanel();
		fileNames = new ArrayList<>();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
	
	
	        // rad 1
	    DefaultListModel<String> listModel = new DefaultListModel<String>();
	    fileNames = getFileNames();
	    for (String fileName: fileNames)
	    	listModel.addElement(fileName);
		
		
	    filenameList = new JList<String>(listModel);
	    filenameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    filenameList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
	    filenameList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting())
					filenameTextfield.setText(fileNames.get(filenameList.getSelectedIndex()));
			}
		});
        JScrollPane sp = new JScrollPane(filenameList);

        sp.setPreferredSize(new Dimension(100, 100));
	    panel.add(sp);
	

	
	        // rad 2
		midPanel = new JPanel();
		midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.LINE_AXIS));
		filenameLabel = new JLabel("Filnamn: ");
		midPanel.add(filenameLabel);
		filenameTextfield = new JTextField();
		midPanel.add(filenameTextfield);
	    panel.add(midPanel);
	}
	
	private List<String> getFileNames()
	{
		List<String> filenamesToReturn = new ArrayList<>();
		File folder = new File("./saved games/");
		File[] listOfFiles = folder.listFiles();
		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    		// Fixa detta, för här kommer det bli fel väldigt ofta.
		    	String fullFilename = file.getName();
		    	String toCompareWith = fullFilename.substring(fullFilename.length() - FILE_ENDING.length()); 
		    	if (toCompareWith.equals(FILE_ENDING))
		    		filenamesToReturn.add(fullFilename.substring(0, fullFilename.length() - FILE_ENDING.length()));
		    }
		}
		return filenamesToReturn;
	}
	
	static private String start(boolean isSaving)
	{
		FileSelector fileSelector = new FileSelector();
		
		fileSelector.filenameTextfield.setEnabled(isSaving);
		int answer = JOptionPane.showConfirmDialog(null, fileSelector.panel, "FileSelector: ", JOptionPane.OK_CANCEL_OPTION);
		
		//System.out.println("Result: " + fileSelector.filenameTextfield.getText());
		return answer == JOptionPane.OK_OPTION? (fileSelector.filenameTextfield.getText() + FILE_ENDING): null;
	}
	
	static public String load()
	{
		return start(false);
	}
	
	static public String save()
	{		
		return start(true);
	}
}
