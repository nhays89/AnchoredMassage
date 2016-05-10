package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.Patient;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Panel that will allow users to query and filter the patient table.
 */
public class PatientSearchPanel extends AbstractSearchPanel {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 2549419250238970948L;

	/**
	 * patient search button.
	 */
	private JButton myPatientSearchBtn;

	/**
	 * Constructs the Patient search panel.
	 */
	public PatientSearchPanel() {
		super(Color.yellow);
		myPatientSearchBtn = new JButton("Patient Search Button");
		myPatientSearchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateSearchResults();

			}
		});
		myPatientSearchBtn.setName("patientSearchBtn");
		this.add(myPatientSearchBtn);
	}

	/**
	 * Fires a property change event to notify the paitent card.
	 */
	private void updateSearchResults() {
		this.firePropertyChange("patientSearchBtn", null, "search");
	}

	/**
	 * Retrieves the patient search results from the textfields to be used for
	 * the query to the database.
	 * 
	 * @return the Patient object.
	 */
	public Patient getPatientSearch() {
		return new Patient("IID", "ETHAN", "VICTORIA", null, null, null, null, null);
	}

}
