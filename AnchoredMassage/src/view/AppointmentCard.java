package view;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import model.Appointment;
import view.AnchoredGUI;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Acts as a storage container for its Panel components.
 */
public class AppointmentCard extends JPanel implements PropertyChangeListener {
	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Info Panel.
	 */
	AppointmentUpdatePanel myAppointmentUpdatePanel;
	/**
	 * Search Panel.
	 */
	AppointmentSearchPanel myAppointmentSearchPanel;
	/**
	 * Table Panel.
	 */
	AppointmentTablePanel myAppointmentTablePanel;

	/**
	 * Creates the Appointment Card components.
	 */
	public AppointmentCard() {
		setLayout(new BorderLayout());
		myAppointmentUpdatePanel = new AppointmentUpdatePanel();
		myAppointmentSearchPanel = new AppointmentSearchPanel();
		myAppointmentTablePanel = new AppointmentTablePanel();
		add(myAppointmentUpdatePanel, BorderLayout.EAST);
		add(myAppointmentSearchPanel, BorderLayout.NORTH);
		add(myAppointmentTablePanel, BorderLayout.CENTER);
		myAppointmentUpdatePanel.addPropertyChangeListener(this);
		myAppointmentSearchPanel.addPropertyChangeListener(this);
		myAppointmentTablePanel.addPropertyChangeListener(this);
	}
	
	/**
	 * Handler for all property change events fired on subcomponents of this object. 
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if (propName.equals("apptSubmitBtn")) {
			System.out.println(myAppointmentUpdatePanel.getApptInfo());
			// appointment object evt.getNewValue();
		}
		if (propName.equals("apptSearchBtn")) {
			Appointment theApptInfo = myAppointmentSearchPanel.getApptSearch();
			System.out.println(theApptInfo.getApptServiceID());

		}
		if (propName.equals("apptTable")) {
			
		}
		if (propName.equals("apptDeleteBtn")) {

		}

	}

}
