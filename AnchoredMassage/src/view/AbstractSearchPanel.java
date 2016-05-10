package view;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author Nicholas A. Hays
 * 
 *         Provides default implementation for each SearchPanel.
 */
public abstract class AbstractSearchPanel extends JPanel {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 8276141654168742096L;

	/**
	 * Sets the Color and dimensions of the Panel.
	 * 
	 * @param theColor
	 *            the Color of the Background.
	 */
	public AbstractSearchPanel(Color theColor) {
		this.setPreferredSize(new Dimension(1000, 400));
		this.setVisible(true);
		this.setBackground(theColor);
	}
}
