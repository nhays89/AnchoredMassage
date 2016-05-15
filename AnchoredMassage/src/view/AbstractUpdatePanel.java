package view;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Provides default implementation for each InfoPanel.
 */
public abstract class AbstractUpdatePanel extends JPanel {
	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = -4800493041989411395L;

	
	/**
	 * Sets the Color and dimensions of the Panel.
	 * @param theColor the Color of the Background.
	 */
	public AbstractUpdatePanel(Color theColor) {
		this.setPreferredSize(new Dimension(400, 1000));
		this.setVisible(true);
		this.setBackground(theColor);
	}
}
