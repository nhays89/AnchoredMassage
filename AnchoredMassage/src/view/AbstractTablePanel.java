package view;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

public abstract class AbstractTablePanel extends JPanel implements PropertyChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6016010290024724009L;

	public AbstractTablePanel(Color theColor) {
		this.setPreferredSize(new Dimension(600, 600));
		this.setVisible(true);
		this.setBackground(theColor);
	}
	
	
	
	
	
}
