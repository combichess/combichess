package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;    

public class Gui2 extends JFrame{
	
    public Gui2() {

        initUI();
    }

    private void initUI() {
        
        setTitle("Simple example");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void starta() {

        EventQueue.invokeLater(new Runnable() {
        
            @Override
            public void run() {
            	Gui2 ex = new Gui2();
                ex.setVisible(true);
            }
        });
    }
}
