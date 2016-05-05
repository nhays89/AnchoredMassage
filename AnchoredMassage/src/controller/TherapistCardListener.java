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

import view.TherapistInfoPanel;
import view.TherapistSearchPanel;
import view.TherapistTablePanel;

public class TherapistCardListener implements PropertyChangeListener, ActionListener, TableModelListener, RowSetListener {

	TherapistInfoPanel myTherapistInfoPanel;
	TherapistSearchPanel myTherapistSearchPanel;
	TherapistTablePanel myTherapistTablePanel;
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object theObject = e.getSource();
		if (theObject.getClass().getSimpleName().equals("JButton")) {
			JButton theButton = (JButton) theObject;
			if(theButton.getName().equals("therapistSubmitBtn")) {
				theButton.setText("therapistSubmitBtnChanged");
			}
			if(theButton.getName().equals("therapistSearchBtn")) {
				theButton.setText("therapistSearchChanged");
			}
		}
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if(propName.equals("therapistInfoPanel")) {
			myTherapistInfoPanel = (TherapistInfoPanel) evt.getNewValue();
			System.out.println(" in TherapistPanelListener, getting the" + propName + "object");
		}
		if(propName.equals("therapistSearchPanel")) {
			myTherapistSearchPanel= (TherapistSearchPanel) evt.getNewValue();
			System.out.println(" in TherapistPanelListener, getting the" + propName + "object");
		}
		if(propName.equals("therapistTablePanel")) {
			myTherapistTablePanel = (TherapistTablePanel) evt.getNewValue();
			System.out.println(" in TherapistPanelListener, getting the" + propName + "object");
		}
		
	}

	@Override
	public void cursorMoved(RowSetEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rowChanged(RowSetEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rowSetChanged(RowSetEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		System.out.println("table model changed");
	}

}
