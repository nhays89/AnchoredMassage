package view;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

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
	 * Patient Meta Data
	 */
	protected static ResultSetMetaData APPOINTMENT_META_DATA;

	/**
	 * Creates the Appointment Card components.
	 */
	public AppointmentCard() {
		setLayout(new BorderLayout());
		setApptMetaData();
		myAppointmentUpdatePanel = new AppointmentUpdatePanel();
		myAppointmentSearchPanel = new AppointmentSearchPanel();
		myAppointmentTablePanel = new AppointmentTablePanel();
		add(myAppointmentUpdatePanel, BorderLayout.EAST);
		add(myAppointmentSearchPanel, BorderLayout.NORTH);
		add(myAppointmentTablePanel, BorderLayout.CENTER);
		myAppointmentTablePanel.addPropertyChangeListener(myAppointmentUpdatePanel);
		myAppointmentTablePanel.addPropertyChangeListener(myAppointmentSearchPanel);
		myAppointmentUpdatePanel.addPropertyChangeListener(myAppointmentTablePanel);
		myAppointmentSearchPanel.addPropertyChangeListener(myAppointmentUpdatePanel);
		myAppointmentSearchPanel.addPropertyChangeListener(myAppointmentTablePanel);
	}

	private void setApptMetaData() {
		try {
			Statement stmt = AnchoredGUI.DB_CONNECTION.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM APPOINTMENT");
			APPOINTMENT_META_DATA = rs.getMetaData();
		} catch (SQLException e) {
			new MSGWindow(e.getMessage());
		}
	}

}
