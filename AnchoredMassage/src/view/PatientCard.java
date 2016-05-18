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
	private AppointmentUpdatePanel myPatientUpdatePanel;

	/**
	 * Patient Search Panel.
	 */
	private AppointmentSearchPanel myPatientSearchPanel;

	/**
	 * Patient Table Panel.
	 */
	private AppointmentTablePanel myPatientTablePanel;
	
	/**
	 * Constructs the patient card to hold all sub panels. 
	 */
	public PatientCard() {
		setLayout(new BorderLayout());
		myPatientUpdatePanel = new AppointmentUpdatePanel();
		myPatientSearchPanel = new AppointmentSearchPanel();
		myPatientTablePanel = new AppointmentTablePanel();
		add(myPatientUpdatePanel, BorderLayout.EAST);
		add(myPatientSearchPanel, BorderLayout.NORTH);
		add(myPatientTablePanel, BorderLayout.CENTER);
		myPatientTablePanel.addPropertyChangeListener(myPatientUpdatePanel);
		myPatientSearchPanel.addPropertyChangeListener(myPatientTablePanel);
		myPatientUpdatePanel.addPropertyChangeListener(myPatientTablePanel);
	}
}
