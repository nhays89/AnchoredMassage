package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Appointment;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Panel that will allow users to query and filter the appointment
 *         table.
 */
public class AppointmentSearchPanel extends AbstractSearchPanel {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 2978537189128649888L;

	/**
	 * Search Button.
	 */
	private JButton myAppointmentSearchBtn;

	/**
	 * Text fields used for patient modification.
	 */
	private JTextField myPatientTxt, myTherapistTxt, myTimeTxt;

	/**
	 * Constructs an Appointment Search Panel for the appointment card.
	 */
	public AppointmentSearchPanel() {
		super(Color.MAGENTA);
		myAppointmentSearchBtn = new JButton("Appointment Search");
		myAppointmentSearchBtn.setName("appointmentSearchBtn");
		add(new JLabel("Patient: "));
		add(new JLabel("Therapist: "));
		add(new JLabel("Time: "));
		this.add(myAppointmentSearchBtn);
		myAppointmentSearchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fireSearchNotification();
			}
		});
	}

	/**
	 * Fires a property change event to alert the appoitment card of
	 * notifications.
	 */
	private void fireSearchNotification() {
		this.firePropertyChange("apptSearchBtn", null, null);
	}

	/**
	 * Retrieves the data from the search fields to generate a query.
	 * 
	 * @return the Appointment details.
	 */
	public Appointment getApptSearch() {
		System.out.println("in here");
		Appointment p = new Appointment("ID", "name", "nassme", "namedd", "naetme", "na2me", "nagme", "namxe");
		System.out.println(p.getApptDate());
		return p;
	}

}
