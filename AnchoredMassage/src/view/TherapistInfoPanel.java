package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.Therapist;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Panel that will display and update information relating to each tuple
 *         of the patient table.
 */
public class TherapistInfoPanel extends AbstractInfoPanel {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = -4024841346169919998L;
	/**
	 * Therapist submit button.
	 */
	private JButton myTherapistSubmitBtn;

	/**
	 * Constructs the Therapist Info Panel which acts a container for all its
	 * sub components.
	 */
	public TherapistInfoPanel() {
		super(new Color(123, 12, 56));
		myTherapistSubmitBtn = new JButton("Therapist Submit");
		myTherapistSubmitBtn.setName("therapistSubmitBtn");
		this.add(myTherapistSubmitBtn);
		myTherapistSubmitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateTherapistInfo();
			}
		});
		myTherapistSubmitBtn.setName("therapistSearchBtn");
		this.add(myTherapistSubmitBtn);
	}

	/**
	 * Notifies the therapist card a bean's property has changed. 
	 */
	private void updateTherapistInfo() {
		firePropertyChange("therapistSubmitBtn", null,
				new Therapist("id", "nick", "hays", "dsffd", "3434 dfadf", "34", "wa", "39394"));
	}

	/**
	 * Gets the Therapist data from the text fields, which will be used to
	 * generate an update or trigger to the database.
	 * 
	 * @return the Therapist object.
	 */
	public Therapist getTherapistData() {
		return new Therapist("id", "nick", "hays", "dsffd", "3434 dfadf", "34", "wa", "39394");
	}

}
