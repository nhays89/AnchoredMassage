package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;

public class MSGWindow extends WindowAdapter{
	JFrame myFrame;
	JButton btnYes, btnNo;
	boolean answer;
	Object mon;

	public MSGWindow(String message) {
		myFrame = new JFrame();
		myFrame.getContentPane().setBackground(Color.BLACK);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.black);
		myFrame.add(mainPanel);
		mainPanel.add(new JLabel(message, SwingConstants.CENTER), BorderLayout.CENTER);
		myFrame.setSize(300, 100);
		myFrame.setVisible(true);
		myFrame.setLocationRelativeTo(null);
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
			myFrame.dispose();
	}
	
}
