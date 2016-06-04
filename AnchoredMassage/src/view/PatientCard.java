package view;

import java.awt.BorderLayout;
import java.sql.PreparedStatement;
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
	protected static ResultSetMetaData PATIENT_JOIN_META_DATA, PATIENT_META_DATA, INSURANCE_META_DATA, AUTH_META_DATA;
	/**
	 * prepared statements
	 */
	protected static PreparedStatement myPatientInsertPS, myInsuranceInsertPS, myAuthorizationInsertPS, myPatientUpdatePS, myInsuranceUpdatePS, myAuthorizationUpdatePS,
	myPatientDeletePS, myInsuranceDeletePS, myAuthorizationDeletePS;
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
			
			myPatientInsertPS = AnchoredGUI.DB_CONNECTION.prepareStatement("insert into PATIENT values(?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			myInsuranceInsertPS = AnchoredGUI.DB_CONNECTION.prepareStatement("insert into INSURANCE values(?,?,?,?,?)");
			myAuthorizationInsertPS = AnchoredGUI.DB_CONNECTION.prepareStatement("insert into INS_AUTHORIZATION values(?,?,?,?,?)");
			myPatientUpdatePS = AnchoredGUI.DB_CONNECTION.prepareStatement("update PATIENT SET [First Name] = ?, [Last Name] = ?, Street = ?, City = ?, State = ?, Zip = ?, [Date of Birth] = ?, "
					+ "[E-mail] = ?, [Cell #] = ? where [Patient ID] = ?");
			myInsuranceUpdatePS = AnchoredGUI.DB_CONNECTION.prepareStatement("update INSURANCE SET [Policy Number] = ?, [Group Number] = ?, [Group Name] = ?, [Deductible] = ? where [Patient ID] = ?");
			myAuthorizationUpdatePS = AnchoredGUI.DB_CONNECTION.prepareStatement("update INS_AUTHORIZATION SET [Authorization Number] = ?, [Number of Visits Authorized] = ?, [Effective Start Date] = ?, [Effective End Date] = ? where [Patient ID] = ?");
			myPatientDeletePS = AnchoredGUI.DB_CONNECTION.prepareStatement("delete from PATIENT where [Patient ID] = ?");
			myInsuranceDeletePS = AnchoredGUI.DB_CONNECTION.prepareStatement("delete from INSURANCE where [Patient ID] = ?");
			myAuthorizationDeletePS = AnchoredGUI.DB_CONNECTION.prepareStatement("delete from INS_AUTHORIZATION where [Patient ID] = ?");
		
			Statement patientStmt = AnchoredGUI.DB_CONNECTION.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			patientStmt.execute("Select * from PATIENT");
			PATIENT_META_DATA = patientStmt.getResultSet().getMetaData();
			
			
			Statement insuranceStmt = AnchoredGUI.DB_CONNECTION.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			insuranceStmt.execute("Select * from INSURANCE");
			INSURANCE_META_DATA = insuranceStmt.getResultSet().getMetaData();
			
			
			Statement authStmt = AnchoredGUI.DB_CONNECTION.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			authStmt.executeQuery("Select * from INS_AUTHORIZATION");
			AUTH_META_DATA = authStmt.getResultSet().getMetaData();
			
			
			Statement patientJoinStmt =  AnchoredGUI.DB_CONNECTION.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet patientRS = patientJoinStmt.executeQuery("SELECT * FROM PATIENT INNER JOIN INSURANCE ON PATIENT.[Patient ID] = INSURANCE.[Patient ID] INNER JOIN INS_AUTHORIZATION ON PATIENT.[Patient ID] = INS_AUTHORIZATION.[Patient ID] ");
			PATIENT_JOIN_META_DATA = patientRS.getMetaData();
		} catch (SQLException e) {
			new MSGWindow(e.getMessage());
		}
	}
	
	
}
