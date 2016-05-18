package view;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import view.AnchoredGUI;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Acts as a storage container for its Panel components.
 */
public class AppointmentCard extends JPanel {
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
		myAppointmentTablePanel.addPropertyChangeListener(myAppointmentUpdatePanel);
		myAppointmentUpdatePanel.addPropertyChangeListener(myAppointmentTablePanel);
		myAppointmentSearchPanel.addPropertyChangeListener(myAppointmentTablePanel);
	}

}
