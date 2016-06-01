package view;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JPanel;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Acts as a storage container for its Panel components.
 */
public class PatientCard extends JPanel {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 4063258991323443984L;

	/**
	 * Patient Info Panel.
	 */
	private PatientUpdatePanel myPatientUpdatePanel;

	/**
	 * Patient Search Panel.
	 */
	private PatientSearchPanel myPatientSearchPanel;
	/**
	 * Patient, Insurance, and InsuranceAuthorization Meta Data. 
	 */
	protected static ResultSetMetaData PATIENT_META_DATA;
	

	/**
	 * Patient Table Panel.
	 */
	protected static PatientTablePanel myPatientTablePanel;
	
	/**
	 * Constructs the patient card to hold all sub panels. 
	 */
	public PatientCard() {
		setLayout(new BorderLayout());
		setPatientMetaData();
		myPatientUpdatePanel = new PatientUpdatePanel();
		myPatientSearchPanel = new PatientSearchPanel();
		myPatientTablePanel = new PatientTablePanel();
		add(myPatientUpdatePanel, BorderLayout.EAST);
		add(myPatientSearchPanel, BorderLayout.NORTH);
		add(myPatientTablePanel, BorderLayout.CENTER);
		myPatientTablePanel.addPropertyChangeListener(myPatientUpdatePanel);
		myPatientSearchPanel.addPropertyChangeListener(myPatientTablePanel);
		myPatientUpdatePanel.addPropertyChangeListener(myPatientTablePanel);
	}

	private void setPatientMetaData() {
		try {
			Statement patientStmt = AnchoredGUI.DB_CONNECTION.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			//Statement insuranceStmt = AnchoredGUI.DB_CONNECTION.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			//Statement insuranceAuthStmt = AnchoredGUI.DB_CONNECTION.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet patientRS = patientStmt.executeQuery("SELECT * FROM PATIENT");
			PATIENT_META_DATA = patientRS.getMetaData();
		} catch (SQLException e) {
			new MSGWindow(e.getMessage());
		}
	}
	
	
}
