package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jhlabs.awt.ParagraphLayout;


/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Panel that will display and update information relating to each tuple
 *         of the patient table.
 */
public class PatientUpdatePanel extends JPanel implements PropertyChangeListener {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 9106446617813863696L;

	/**
	 * Update and delete buttons.
	 */
	JButton myPatientUpdateBtn, myPatientDeleteBtn;
	/**
	 * TextField Size
	 */
	private static int TEXT_FIELD_SIZE = 15;
	/**
	 * Database connection.
	 */
	private Connection myDBConn;
	/**
	 * JTextField objects.
	 */
	JTextField[] myPatientTxt, myInsuranceTxt, myAuthorizationTxt;
	/**
	 * JLabel objects.
	 */
	JLabel[] myPatientLbl, myInsuranceLbl, myAuthorization;
	/**
	 * Field names.
	 */
	String[] myTxtFieldNames;
	/**
	 * Display Names.
	 */
	String[] myLblFieldNames;
	/*
	 * Number of attributes in table. 
	 */
	private static int NUM_OF_COLS;
	/**
	 * current Patient ID.
	 */
	private static String CURRENT_PATIENT_ID;
	/**
	 * Prepared Statement
	 */
	private PreparedStatement patientPS, insurancePS, authorizationPS;
	/**
	 * Constructor for Patient Information Panel.
	 */
	public PatientUpdatePanel() {
		setLayout(new ParagraphLayout(00, 30, 10, 10, 10, 10));
		myDBConn = AnchoredGUI.DB_CONNECTION;
		setPreparedStatement();
		createPatient();
		createPatientInsurance();
		addListeners();
		this.setPreferredSize(new Dimension(400, 1000));
		this.setVisible(true);
	}
	/** 
	 * Sets the prepared statement format 
	 */
	private void setPreparedStatement() {
		String patientInsertString = "insert into PATIENT values(?,?,?,?,?,?,?,?,?)";
		String patientInsuranceString = "insert into INSURANCE values(?,?,?,?,?,?)";
		patientPS = null;
		try {
			patientPS = AnchoredGUI.DB_CONNECTION.prepareStatement(patientInsertString, Statement.RETURN_GENERATED_KEYS);
			insurancePS = AnchoredGUI.DB_CONNECTION.prepareStatement(patientInsuranceString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inserts a tuple into the appointment Table.
	 */
	private void insertPatientDB(String[] dataFields) {
		// to do
		// if the fields are not empty run this
		
		try {
			patientPS.setString(1, dataFields[1]);
			patientPS.setString(2, dataFields[2]);
			patientPS.setString(3, dataFields[3]);
			patientPS.setString(4, dataFields[4]);
			patientPS.setString(5, dataFields[5]);
			patientPS.setInt(6, Integer.parseInt(dataFields[6]));
			patientPS.setDate(7, Date.valueOf(dataFields[7]));
			patientPS.setString(8, dataFields[8]);
			patientPS.setString(9, dataFields[9]);
			patientPS.executeUpdate();
		} catch (Exception e) {
			new MSGWindow(e.getLocalizedMessage());
			e.printStackTrace();
		}
		for (JTextField t : this.myPatientTxt) {
			t.setText(null);
		}
		firePropertyChange("createResultSet", null, null);
	}
	/**
	 * Inserts a tuple into the appointment Table.
	 */
	private void insertInsuranceDB(String[] dataFields) {
		// to do
		// if the fields are not empty run this
		
		try {
			insurancePS.setInt(1, Integer.parseInt(dataFields[0]));
			insurancePS.setString(2, dataFields[1]);
			insurancePS.setString(3, dataFields[2]);
			insurancePS.setString(4, dataFields[3]);
			insurancePS.setInt(5, Integer.parseInt(dataFields[4]));
			insurancePS.setString(6, dataFields[5]);
	
			insurancePS.executeUpdate();
		} catch (Exception e) {
			new MSGWindow(e.getLocalizedMessage());
			e.printStackTrace();
		}
		for (JTextField t : this.myPatientTxt) {
			t.setText(null);
		}
		firePropertyChange("createResultSet", null, null);
	}
	/**
	 * Creates components from table meta data. 
	 * Meta data returned is 1 based.
	 */
	private void createPatient() {
		try {
			Statement stmt = myDBConn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PATIENT");
			ResultSetMetaData rsmd = rs.getMetaData();
			NUM_OF_COLS = rsmd.getColumnCount();
			myPatientLbl = new JLabel[NUM_OF_COLS];
			myPatientTxt = new JTextField[NUM_OF_COLS];
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				myPatientTxt[i] = new JTextField(TEXT_FIELD_SIZE);
				if (i == 0) {
					myPatientTxt[i].setEditable(false);
					myPatientTxt[i].setToolTipText("cannot edit primary key");
				}
				myPatientLbl[i] = new JLabel(rsmd.getColumnLabel(i + 1)); 
				add(myPatientLbl[i], ParagraphLayout.NEW_PARAGRAPH);
				add(myPatientTxt[i], ParagraphLayout.NEW_LINE);
			}

		} catch (SQLException e) {
			new MSGWindow(e.getLocalizedMessage());
		}
	}
	/**
	 * Creates components from table meta data. 
	 * Meta data returned is 1 based.
	 */
	private void createPatientInsurance() {
		try {
			
			Statement stmt = myDBConn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM INSURANCE");
			ResultSetMetaData rsmd = rs.getMetaData();
			NUM_OF_COLS = rsmd.getColumnCount();
			myInsuranceLbl = new JLabel[NUM_OF_COLS];
			myInsuranceTxt = new JTextField[NUM_OF_COLS];
			for (int i = 1; i < rsmd.getColumnCount(); i++) {
				myInsuranceTxt[i] = new JTextField(TEXT_FIELD_SIZE);
				myInsuranceLbl[i] = new JLabel(rsmd.getColumnLabel(i + 1)); 
				add(myInsuranceLbl[i], ParagraphLayout.NEW_PARAGRAPH);
				add(myInsuranceTxt[i], ParagraphLayout.NEW_LINE);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Adds listeners to buttons.
	 */
	private void addListeners() {
		myPatientUpdateBtn = new JButton("Update");
		myPatientUpdateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertPatientDB(getPatientFields());
				insertInsuranceDB(getInsuranceFields());
			}
		});
		myPatientDeleteBtn = new JButton("Delete");
		myPatientDeleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deletePatientDB();
			}
		});
		add(myPatientUpdateBtn, ParagraphLayout.NEW_PARAGRAPH);
		add(myPatientDeleteBtn);

	}
	
	/**
	 * Deletes a patient from the Patient Table. 
	 */
	private void deletePatientDB() {
		if (!myPatientTxt[0].getText().equals("")) {
			String deleteString = "DELETE FROM PATIENT WHERE [Patient ID] ='" + myPatientTxt[0].getText() + "'";
			executeQuery(deleteString);
			firePropertyChange("createResultSet", null, myPatientTxt[0]);
		}
	}

	/**
	 * Executes a update query statement to the Patient Table. 
	 * @param queryString
	 */
	private void executeQuery(String queryString) {
		Statement stmt;
		try {
			stmt = myDBConn.createStatement();
			stmt.executeUpdate(queryString);
		} catch (Exception e) {
			new MSGWindow(e.getLocalizedMessage());
			e.printStackTrace();
		}
		// if successful?
		this.firePropertyChange("createResultSet", null, null);
		for (

		JTextField t : myPatientTxt) {
			t.setText(null);
		}
	}
	
	/**
	 * Receives a table selection event to set the text fields in the update 
	 * panel.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("tableSelectionChanged")) {
			setPatientFields((String[]) evt.getNewValue());
		}
	}
	/**
	 * Gets text in JTextFields.
	 * @return array of Strings to be inserted into the db.
	 */
	private String[] getPatientFields() {
		String[] fields = new String[myPatientTxt.length];
		for (int i = 0; i < myPatientTxt.length; i++) {
			fields[i] = myPatientTxt[i].getText();
		}
		return fields;
	}
	/**
	 * Gets text in JTextFields.
	 * @return array of Strings to be inserted into the db.
	 */
	private String[] getInsuranceFields() {
		String[] fields = new String[myInsuranceTxt.length];
		for (int i = 0; i < myInsuranceTxt.length; i++) {
			fields[i] = myInsuranceTxt[i].getText();
		}
		return fields;
	}

	/**
	 * Set Patient Fields.
	 */
	private void setPatientFields(String[] rowData) {
		for (int i = 0; i < myPatientTxt.length; i++) {
			if( i == 0) {
				CURRENT_PATIENT_ID = rowData[i];
			}
			myPatientTxt[i].setText(rowData[i]);
		}
	}
	
}
