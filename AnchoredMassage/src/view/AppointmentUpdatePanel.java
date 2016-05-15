package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.Appointment;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Panel that will display and update information relating to each tuple
 *         of the appointment table.
 */
public class AppointmentUpdatePanel extends AbstractUpdatePanel {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 6342333683011977975L;

	/**
	 * Submit Button.
	 */
	private JButton myAppointmentSubmitBtn;

	/**
	 * Constructs the Appointment Info Panel.
	 */
	public AppointmentUpdatePanel() {
		super(Color.white);
		myAppointmentSubmitBtn = new JButton("Appointment Submit");
		myAppointmentSubmitBtn.setName("appointmentSubmitBtn");
		this.add(myAppointmentSubmitBtn);
		myAppointmentSubmitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateApptInfo();
			}
		});
	}
	/**
	 * Fires a property change event to notify the Appointment Card. 
	 */
	public void updateApptInfo() {
		firePropertyChange("apptSubmitBtn", null, null);
	}
	/**
	 * Retrieves data from text fields to be used a query. 
	 * @return the Appointment object. 
	 */
	public Appointment getApptInfo() {
		return new Appointment("afsd", "afsd", "afsd", "afsd", "afsd", "afsd", "afsd", "afsd");
	}

}
