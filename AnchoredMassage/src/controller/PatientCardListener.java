package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.swing.JButton;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import view.PatientInfoPanel;
import view.PatientSearchPanel;
import view.PatientTablePanel;

public class PatientCardListener implements PropertyChangeListener, ActionListener, TableModelListener, RowSetListener {

	PatientInfoPanel myPatientInfoPanel;
	PatientSearchPanel myPatientSearchPanel;
	PatientTablePanel myPatientTablePanel;
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object theObject = e.getSource();
		if (theObject.getClass().getSimpleName().equals("JButton")) {
			JButton theButton = (JButton) theObject;
			if(theButton.getName().equals("patientSubmitBtn")) {
				theButton.setText("patientsubmitbtnchanged");
			}
			if(theButton.getName().equals("patientSearchBtn")) {
				theButton.setText("patientsearchtnchanged");
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if (propName.equals("patientInfoPanel")) {
			myPatientInfoPanel = (PatientInfoPanel) evt.getNewValue();
			System.out.println(" in PatientPanelListener, getting the" + propName + "object");
		}
		if (propName.equals("patientSearchPanel")) {
			myPatientSearchPanel = (PatientSearchPanel) evt.getNewValue();
			System.out.println(" in PatientPanelListener, getting the" + propName + "object");
		}
		if (propName.equals("patientTablePanel")) {
			myPatientTablePanel = (PatientTablePanel) evt.getNewValue();
			System.out.println(" in PatientPanelListener, getting the" + propName + "object");
		}
	}

	@Override
	public void tableChanged(TableModelEvent evt) {
		System.out.println("table model changed");
		
	}

	@Override
	public void cursorMoved(RowSetEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rowChanged(RowSetEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rowSetChanged(RowSetEvent event) {
		// TODO Auto-generated method stub
		
	}

}
