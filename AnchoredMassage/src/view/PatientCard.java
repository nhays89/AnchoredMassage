package view;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Acts as a storage container for its Panel components.
 */
public class PatientCard extends JPanel {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 4063258991323443984L;

	/**
	 * Patient Info Panel.
	 */
	private PatientUpdatePanel myPatientUpdatePanel;

	/**
	 * Patient Search Panel.
	 */
	private PatientSearchPanel myPatientSearchPanel;

	/**
	 * Patient Table Panel.
	 */
	private PatientTablePanel myPatientTablePanel;
	
	/**
	 * Constructs the patient card to hold all sub panels. 
	 */
	public PatientCard() {
		setLayout(new BorderLayout());
		myPatientUpdatePanel = new PatientUpdatePanel();
		myPatientSearchPanel = new PatientSearchPanel();
		myPatientTablePanel = new PatientTablePanel();
		add(myPatientUpdatePanel, BorderLayout.EAST);
		add(myPatientSearchPanel, BorderLayout.NORTH);
		add(myPatientTablePanel, BorderLayout.CENTER);
		myPatientTablePanel.addPropertyChangeListener(myPatientUpdatePanel);
		myPatientSearchPanel.addPropertyChangeListener(myPatientTablePanel);
		myPatientUpdatePanel.addPropertyChangeListener(myPatientTablePanel);
	}
}
