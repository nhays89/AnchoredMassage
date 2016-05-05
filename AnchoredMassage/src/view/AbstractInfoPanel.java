package view;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public abstract class AbstractInfoPanel extends JPanel {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4800493041989411395L;
	ArrayList<String> myColNames;
	
	public AbstractInfoPanel(Color theColor) {
		
		
		init(theColor);
	}

	private void init(Color theColor) {
		this.setPreferredSize(new Dimension(400, 1000));
		this.setVisible(true);
		this.setBackground(theColor);
	}
	
	
}
