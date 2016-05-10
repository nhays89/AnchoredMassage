package view;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import model.Patient;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Acts as a storage container for its Panel components.
 */
public class PatientCard extends JPanel implements PropertyChangeListener {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 4063258991323443984L;

	/**
	 * Patient Info Panel.
	 */
	private PatientInfoPanel myPatientInfoPanel;

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
		myPatientInfoPanel = new PatientInfoPanel();
		myPatientSearchPanel = new PatientSearchPanel();
		myPatientTablePanel = new PatientTablePanel();
		add(myPatientInfoPanel, BorderLayout.EAST);
		add(myPatientSearchPanel, BorderLayout.NORTH);
		add(myPatientTablePanel, BorderLayout.CENTER);
		myPatientInfoPanel.addPropertyChangeListener(this);
		myPatientSearchPanel.addPropertyChangeListener(this);
		myPatientTablePanel.addPropertyChangeListener(this);
	}
	
	
	/**
	 * Handles all property change events fired from sub components of this object. 
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();

		if (propName.equals("patientSubmitBtn")) {
			System.out.println("Patient:" + myPatientInfoPanel.getPatientData().getFName());
			// appointment object evt.getNewValue();
		}
		if (propName.equals("patientSearchBtn")) {
			Patient findPatient = myPatientSearchPanel.getPatientSearch();
			System.out.println("Patient: " + findPatient.getFName());
			// make query to database
			// get data and put data in temp storage object and then dump to
			// container
		}
		if (propName.equals("apptTable")) {
			// get data from line clicked
			// call method on temp data object

			// set patient fields from return data
			// myPatientInfoPanel.setPatientFields(findPatient);
		}
		if (propName.equals("apptDeleteBtn")) {

		}

	}
}
