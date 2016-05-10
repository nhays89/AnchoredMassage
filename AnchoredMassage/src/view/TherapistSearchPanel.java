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
public class TherapistSearchPanel extends AbstractSearchPanel {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 6187373824017488881L;

	/**
	 * Therapist search button.
	 */
	private JButton myTherapistSearchBtn;

	public TherapistSearchPanel() {
		super(Color.gray);
		myTherapistSearchBtn = new JButton("Therapist Search");
		myTherapistSearchBtn.setName("therapistSearchBtn");
		add(myTherapistSearchBtn);
		myTherapistSearchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateSearchResults();
			}
		});
	}

	/**
	 * Fires property change event to notify Therapist Card that changes may
	 * occur.
	 */
	private void updateSearchResults() {
		this.firePropertyChange("therapistSearchBtn", null, "search");
	}

	/**
	 * Gets the therapist information from the respective text fields and sends
	 * it to the therapist card.
	 * 
	 * @return the Therapist data to modify
	 */
	public Patient getTherapistSearch() {
		return new Patient("IID", "VICTORIA", "VICTORIA", null, null, null, null, null);
	}

}
