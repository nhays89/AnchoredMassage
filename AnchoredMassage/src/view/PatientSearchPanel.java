package view;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;

import controller.PatientCardListener;

public class PatientSearchPanel extends AbstractSearchPanel implements PropertyChangeListener{

	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2549419250238970948L;
	private PatientCardListener myPatientPanelListener;
	private JButton myPatientSearchBtn;
	private static String PATIENT_LISTENER_PROP_NAME = "patientPanelListener";
	
	
	public PatientSearchPanel() {
		super(Color.yellow);
		 myPatientSearchBtn = new JButton("Patient Search Button");
		 myPatientSearchBtn.setName("patientSearchBtn");
		this.add(myPatientSearchBtn);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if(propName.equals(PATIENT_LISTENER_PROP_NAME)) {
			System.out.println(" in " + this.getClass().getSimpleName()+ ", getting the" + propName + "object");
			myPatientPanelListener = (PatientCardListener) evt.getNewValue();
			System.out.println("in " + this.getClass().getSimpleName()+ ", setting button action listener");
			myPatientSearchBtn.addActionListener(myPatientPanelListener);
		}
		
	}
	
	
}
