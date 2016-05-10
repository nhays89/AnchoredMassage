package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * 
 * @author Nicholas A. Hays Provides default implementation for each TablePanel.
 */
public abstract class AbstractTablePanel extends JPanel {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = -6016010290024724009L;

	/**
	 * Sets the Color and dimensions of the Panel.
	 * 
	 * @param theColor
	 *            the Color of the Background.
	 */
	public AbstractTablePanel(Color theColor) {
		this.setPreferredSize(new Dimension(600, 600));
		this.setVisible(true);
		this.setBackground(theColor);
	}

}
