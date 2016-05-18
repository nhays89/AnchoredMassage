package view;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Acts as a storage container for its Panel components.
 */
public class TherapistCard extends JPanel {
	/**
	 * Therapist Info Panel.
	 */
	TherapistUpdatePanel myTherapistUpdatePanel;
	/**
	 * Therapist Search Panel.
	 */
	TherapistSearchPanel myTherapistSearchPanel;
	/**
	 * Therapist Table Panel.
	 */
	TherapistTablePanel myTherapistTablePanel;
	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 2398946988371590567L;

	/**
	 * Constructs the Therapist card which will act as a container for its sub
	 * components.
	 */
	public TherapistCard() {
		setLayout(new BorderLayout());
		myTherapistUpdatePanel = new TherapistUpdatePanel();
		myTherapistSearchPanel = new TherapistSearchPanel();
		myTherapistTablePanel = new TherapistTablePanel();
		add(myTherapistUpdatePanel, BorderLayout.EAST);
		add(myTherapistSearchPanel, BorderLayout.NORTH);
		add(myTherapistTablePanel, BorderLayout.CENTER);
		myTherapistTablePanel.addPropertyChangeListener(myTherapistUpdatePanel);
		myTherapistUpdatePanel.addPropertyChangeListener(myTherapistTablePanel);
		myTherapistSearchPanel.addPropertyChangeListener(myTherapistTablePanel);
	
	}
}
