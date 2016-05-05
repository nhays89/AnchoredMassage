package view;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import controller.AppointmentCardListener;

public class AppointmentCard extends JPanel implements PropertyChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	AppointmentInfoPanel myAppointmentInfoPanel;
	AppointmentSearchPanel myAppointmentSearchPanel;
	AppointmentTablePanel myAppointmentTablePanel;
	private static String APPOINTMENT_LISTENER_PROP_NAME = "appointmentCardListener";
	private AppointmentCardListener myAppointmentCardListener;

	public AppointmentCard() {
		setLayout(new BorderLayout());
		myAppointmentInfoPanel = new AppointmentInfoPanel();
		myAppointmentSearchPanel = new AppointmentSearchPanel();
		myAppointmentTablePanel = new AppointmentTablePanel();
		add(myAppointmentInfoPanel, BorderLayout.EAST);
		add(myAppointmentSearchPanel, BorderLayout.NORTH);
		add(myAppointmentTablePanel, BorderLayout.CENTER);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if (propName.equals(APPOINTMENT_LISTENER_PROP_NAME)) {
			System.out.println(" in " + this.getClass().getSimpleName()+ ", getting the " + propName + " object");
			myAppointmentCardListener = (AppointmentCardListener) evt.getNewValue();
		}

	}

}
