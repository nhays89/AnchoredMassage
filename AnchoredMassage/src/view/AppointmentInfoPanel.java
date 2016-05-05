package view;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;

import controller.AppointmentCardListener;


public class AppointmentInfoPanel extends AbstractInfoPanel implements PropertyChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6342333683011977975L;
	private JButton myAppointmentSubmitBtn;
	private AppointmentCardListener myAppointmentListener;
	private static String APPOINTMENT_LISTENER_PROP_NAME = "appointmentPanelListener";
	
	
	
	public AppointmentInfoPanel() {
		super(Color.white);
		myAppointmentSubmitBtn = new JButton("Appointment Submit");
		myAppointmentSubmitBtn.setName("appointmentSubmitBtn");
		this.add(myAppointmentSubmitBtn);
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if(propName.equals(APPOINTMENT_LISTENER_PROP_NAME)) {
			System.out.println(" in " + this.getClass().getSimpleName()+ ", getting the" + propName + "object");
			myAppointmentListener = (AppointmentCardListener) evt.getNewValue();
			System.out.println("in " + this.getClass().getSimpleName()+ ", setting button action listener");
			myAppointmentSubmitBtn.addActionListener(myAppointmentListener);
			
		}
	}

}
