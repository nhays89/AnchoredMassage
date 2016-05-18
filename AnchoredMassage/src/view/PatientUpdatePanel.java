package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.jhlabs.awt.ParagraphLayout;


/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Panel that will display and update information relating to each tuple
 *         of the patient table.
 */
public class PatientUpdatePanel extends AbstractUpdatePanel implements PropertyChangeListener {

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
	private static int TEXT_FIELD_SIZE = 17;
	/**
	 * Database connection.
	 */
	private Connection myDBConn;
	/**
	 * JTextField objects.
	 */
	JTextField myPatientTxt[];
	/**
	 * JLabel objects.
	 */
	JLabel[] myPatientLbl;
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
	 * Constructor for Patient Information Panel.
	 */
	public PatientUpdatePanel() {
		super(Color.DARK_GRAY);
		setLayout(new ParagraphLayout(40, 30, 10, 10, 10, 10));
		myDBConn = AnchoredGUI.DB_CONNECTION;
		createComponents();
		addListeners();
	}

	/**
	 * Creates components from table meta data. 
	 * Meta data returned is 1 based.
	 */
	private void createComponents() {
		try {
			Statement stmt = myDBConn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PATIENT");
			ResultSetMetaData rsmd = rs.getMetaData();
			NUM_OF_COLS = rsmd.getColumnCount();
			myPatientLbl = new JLabel[NUM_OF_COLS];
			myPatientTxt = new JTextField[NUM_OF_COLS];
			myTxtFieldNames = new String[NUM_OF_COLS];
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				myPatientTxt[i] = new JTextField(TEXT_FIELD_SIZE);
				myTxtFieldNames[i] = rsmd.getColumnLabel(i + 1);
				if (i == 0) {
					myPatientTxt[i].setEditable(false);
					myPatientTxt[i].setToolTipText("cannot edit primary key");
				}
				myPatientLbl[i] = new JLabel(rsmd.getColumnLabel(i + 1)); 
				myPatientLbl[i].setForeground(Color.lightGray);
				add(myPatientLbl[i], ParagraphLayout.NEW_PARAGRAPH);
				add(myPatientTxt[i], ParagraphLayout.NEW_LINE);
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
				updatePatientDB();
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
			String deleteString = "DELETE FROM PATIENT WHERE patientID ='" + myPatientTxt[0].getText() + "'";
			executeQuery(deleteString);
			firePropertyChange("createResultSet", null, myPatientTxt[0]);
		}
	}

	/**
	 * generates a query to update database. Then fire a property change event
	 * to refresh the patient table.
	 * 
	 */
	public void updatePatientDB() {

		String updateString = "update dbo.PATIENT SET " + myTxtFieldNames[1] + "='" + myPatientTxt[1].getText() + "', "
				+ myTxtFieldNames[2] + "='" + myPatientTxt[2].getText() + "', " + myTxtFieldNames[3] + "='"
				+ myPatientTxt[3].getText() + "', " + myTxtFieldNames[4] + "='" + myPatientTxt[4].getText() + "', "
				+ myTxtFieldNames[5] + "='" + myPatientTxt[5].getText() + "', " + myTxtFieldNames[6] + "='"
				+ myPatientTxt[6].getText() + "', " + myTxtFieldNames[7] + "='" + myPatientTxt[7].getText()
				+ "' where patientID ='" + myPatientTxt[0].getText() + "'";
		executeQuery(updateString);
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
			new MSGWindow("error");
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
			setTextFields((String[]) evt.getNewValue());
		}
	}

	/**
	 * Set Patient Fields.
	 */
	private void setTextFields(String[] rowData) {
		for (int i = 0; i < myPatientTxt.length; i++) {
			myPatientTxt[i].setText(rowData[i]);
		}
	}
	
	//to do
	/*
	 * String insertString = "insert into dbo.PATIENT values('" +
	 * myPatientTxt[1].getText() + "', '" + myPatientTxt[2].getText() +
	 * "', '" + myPatientTxt[3].getText() + "', '" +
	 * myPatientTxt[4].getText() + "', '" + myPatientTxt[5].getText() +
	 * "', '" + myPatientTxt[6].getText() + "', '" +
	 * myPatientTxt[7].getText() + "')";
	 */
}
