package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.UIDefaults;
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
	 *  Global Font style
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
		UIManager.put("nimbusBase", Color.darkGray);
		UIManager.put("nimbusDisabledText", Color.lightGray);
		UIManager.put("Table.font", FONT);
		UIManager.put("ToggleButton.font", FONT);
		UIManager.put("Label.font", FONT);
		UIManager.put("Button.font", FONT);
		UIManager.put("FormattedTextField.font"	, FONT);
		UIManager.put("TextArea.font", FONT);
		UIManager.put("Menu.font", FONT);
		UIManager.put("CheckBox.font", FONT);
		UIManager.put("TableHeader.font", FONT);
		UIManager.put("ToolTip.font", FONT);
		UIManager.put("ScrollPane.font", FONT);
		UIManager.put("TextField.font", FONT);
		UIManager.put("nimbusBase", Color.darkGray);
		UIManager.put("nimbusDisabledText", Color.lightGray);
		UIManager.put("textForeground", Color.lightGray);
		UIManager.put("nimbusSelection", Color.green);
		UIManager.put("nimbusSelectionBackground", Color.black);
		UIManager.put("textHighlight", Color.black);
		UIManager.put("textBackground", Color.darkGray);
		UIManager.put("TextField.background", Color.black);
		UIManager.put("Button.foreground", Color.pink);
		UIManager.put("Button.disabledText", Color.pink);
		UIManager.put("Button.disabled", Color.pink);
		UIManager.put("Button[Disabled].textForeground", Color.pink);
		
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new AnchoredGUI();
			}
		});
	}
}
