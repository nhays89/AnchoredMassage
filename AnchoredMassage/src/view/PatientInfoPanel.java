package view;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;

import controller.PatientCardListener;

public class PatientInfoPanel extends AbstractInfoPanel implements PropertyChangeListener {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9106446617813863696L;
	
	PatientCardListener myPatientCardListener;
	
	JButton myPatientInfoSubmit;
	private static String PATIENT_LISTENER_PROP_NAME = "patientCardListener";
	

	public PatientInfoPanel() {
		super(Color.DARK_GRAY);
		myPatientInfoSubmit = new JButton("Patient Submit");
		myPatientInfoSubmit.setName("patientSubmitBtn");
		this.add(myPatientInfoSubmit);
		
	}







	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if(propName.equals(PATIENT_LISTENER_PROP_NAME)) {
			System.out.println(" in " + this.getClass().getSimpleName()+ ", getting the" + propName + "object");
			myPatientCardListener = (PatientCardListener) evt.getNewValue();
			System.out.println("in " + this.getClass().getSimpleName()+ ", setting button action listener");
			myPatientInfoSubmit.addActionListener(myPatientCardListener);
			
		}
		
	}
	
	
	
}
