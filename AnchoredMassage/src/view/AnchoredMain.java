package view;

import java.awt.EventQueue;
/**
 * 
 * @author Nicholas A. Hays
 * 
 * Kicks off the GUI in a new thread.
 */
public class AnchoredMain {
	/**
	 * Main method. 
	 * @param args command line args. 
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new AnchoredGUI();
			}
		});
	}
}
