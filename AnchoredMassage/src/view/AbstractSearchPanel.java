package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public abstract class AbstractSearchPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8276141654168742096L;

	public AbstractSearchPanel(Color theColor) {
		this.setPreferredSize(new Dimension(1000, 400));
		this.setVisible(true);
		this.setBackground(theColor);
	}
	
	
	
	
	
	
}
