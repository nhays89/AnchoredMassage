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

import view.AppointmentInfoPanel;
import view.AppointmentSearchPanel;
import view.AppointmentTablePanel;

public class AppointmentCardListener
		implements PropertyChangeListener, ActionListener, TableModelListener, RowSetListener {

	AppointmentInfoPanel myApptInfoPanel;
	AppointmentSearchPanel myApptSearchPanel;
	AppointmentTablePanel myApptTablePanel;

	@Override
	public void actionPerformed(ActionEvent e) {
		Object theObject = e.getSource();
		if (theObject.getClass().getSimpleName().equals("JButton")) {
			JButton theButton = (JButton) theObject;
			if (theButton.getName().equals("appointmentSubmitBtn")) {
				theButton.setText("appointmentSubmitBtnCHANGED");
			}
			if (theButton.getName().equals("appointmentSearchBtn")) {
				theButton.setText("appointmentSearchBtnCHANGED");
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if (propName.equals("appointmentInfoPanel")) {
			myApptInfoPanel = (AppointmentInfoPanel) evt.getNewValue();
			System.out.println(" in AppoitmentPanelListener, getting the" + propName + "object");
		}
		if (propName.equals("appointmentSearchPanel")) {
			myApptSearchPanel = (AppointmentSearchPanel) evt.getNewValue();

			System.out.println(" in AppoitmentPanelListener, getting the" + propName + "object");
		}
		if (propName.equals("appointmentTablePanel")) {
			myApptTablePanel = (AppointmentTablePanel) evt.getNewValue();
			System.out.println(" in AppoitmentPanelListener, getting the" + propName + "object");
		}

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

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub

	}

}
