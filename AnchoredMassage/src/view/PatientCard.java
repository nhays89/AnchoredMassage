package view;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import controller.PatientCardListener;

public class PatientCard extends JPanel implements PropertyChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4063258991323443984L;
	
	private PatientInfoPanel myPatientInfoPanel;
	private PatientSearchPanel myPatientSearchPanel;
	private PatientTablePanel myPatientTablePanel;
	
	
	
	private PatientCardListener myPatientCardListener;
	
	public PatientCard() {
		setLayout(new BorderLayout());
		myPatientInfoPanel = new PatientInfoPanel();
		myPatientSearchPanel = new PatientSearchPanel();
		myPatientTablePanel = new PatientTablePanel();
		add(myPatientInfoPanel, BorderLayout.EAST);
		add(myPatientSearchPanel, BorderLayout.NORTH);
		add(myPatientTablePanel, BorderLayout.CENTER);
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if(propName.equals("patientCardListener")) {
			myPatientCardListener = (PatientCardListener) evt.getNewValue();
			System.out.println(" in " + this.getClass().getSimpleName()+ ", getting the " + propName + " object");
			myPatientInfoPanel.addPropertyChangeListener(myPatientCardListener);
			myPatientSearchPanel.addPropertyChangeListener(myPatientCardListener);
			myPatientTablePanel.addPropertyChangeListener(myPatientCardListener);
		}
		
		
		
	}

}
