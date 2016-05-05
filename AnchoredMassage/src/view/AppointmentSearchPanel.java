package view;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;

import controller.AppointmentCardListener;

public class AppointmentSearchPanel extends AbstractSearchPanel implements PropertyChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2978537189128649888L;
	private JButton myAppointmentSearchBtn;
	private AppointmentCardListener myAppointmentListener;
	private static String APPOINTMENT_LISTENER_PROP_NAME = "appointmentPanelListener";
	
	
	public AppointmentSearchPanel() {
		super(Color.MAGENTA);
		myAppointmentSearchBtn = new JButton("Appointment Search");
		myAppointmentSearchBtn.setName("appointmentSearchBtn");
		this.add(myAppointmentSearchBtn);
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if(propName.equals(APPOINTMENT_LISTENER_PROP_NAME)) {
			System.out.println(" in " + this.getClass().getSimpleName()+ ", getting the" + propName + "object");
			myAppointmentListener = (AppointmentCardListener) evt.getNewValue();
			System.out.println("in " + this.getClass().getSimpleName()+ ", setting button action listener");
			myAppointmentSearchBtn.addActionListener(myAppointmentListener);
		}
		
	}
	

}
