package view;


import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Kicks off the GUI in a new thread.
 */
public class AnchoredMain {
	/**
	 * Global Font style
	 */
	protected static Font FONT = new Font("Courier New", Font.PLAIN, 15);

	/**
	 * Main method.
	 * 
	 * @param args
	 *            command line args.
	 */
	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equalsIgnoreCase(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception ex) {
		}
	
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new AnchoredGUI();
			}
		});
	}
}
