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
public class TherapistCard extends JPanel implements PropertyChangeListener {
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
		myTherapistUpdatePanel.addPropertyChangeListener(this);
		myTherapistSearchPanel.addPropertyChangeListener(this);
		myTherapistTablePanel.addPropertyChangeListener(this);
	}

	/**
	 * Handles all the property change events fired by the sub components of this object. 
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();

		if (propName.equals("therapistSubmitBtn")) {
			System.out.println("Therapist:" + myTherapistUpdatePanel.getTherapistData().getFName());

			// appointment object evt.getNewValue();
		}
		if (propName.equals("therapistSearchBtn")) {
			Patient findTherapist = myTherapistSearchPanel.getTherapistSearch();
			System.out.println(findTherapist.getFName());
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
