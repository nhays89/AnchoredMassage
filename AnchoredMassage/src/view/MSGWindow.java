package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Provies support for error messages. 
 * @author Nicholas A. Hays
 */
public class MSGWindow extends WindowAdapter{
	/**
	 * Main window.
	 */
	JFrame myFrame;
	/**
	 * Text area to display error message. 
	 */
	JTextArea textArea;
	public MSGWindow(String message) {
		myFrame = new JFrame();
		myFrame.getContentPane().setBackground(Color.BLACK);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setMaximumSize(new Dimension(300,300));
		myFrame.add(mainPanel);
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.append(message + " date format is YYYY-MM-DD");
		textArea.setPreferredSize(new Dimension(300,150));
		mainPanel.add(textArea, BorderLayout.CENTER);
		myFrame.setVisible(true);
		myFrame.setLocationRelativeTo(null);
		myFrame.pack();
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
			myFrame.dispose();
	}
	
}
