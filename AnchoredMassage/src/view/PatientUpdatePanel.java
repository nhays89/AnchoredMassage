package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.jhlabs.awt.ParagraphLayout;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import model.Patient;

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
	 * Info Submit Button.
	 */
	JButton myPatientUpdateBtn;
	/**
	 * TextField Size
	 */
	private static int TEXT_FIELD_SIZE = 17;

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
	String[] myTxtFieldNames = { "patientID", "firstName", "lastName", "dob", "street", "city", "state", "zip" };
	/**
	 * Display Names.
	 */
	String[] myLblFieldNames = { "Patient ID: ", "First Name: ", "Last Name: ", "Date of Birth: ", "City: ", "Street: ",
			"State: ", "Zip: " };
	/**
	 * Database connection object.
	 */
	SQLServerDataSource ds;

	/**
	 * Constructor for Patient Information Panel.
	 */
	public PatientUpdatePanel() {
		super(Color.DARK_GRAY);
		myPatientLbl = new JLabel[myLblFieldNames.length];
		myPatientTxt = new JTextField[myTxtFieldNames.length];
		setLayout(new ParagraphLayout(40, 30, 10, 10, 10, 10));
		ds = AnchoredGUI.DATA_SOURCE;
		addComponents();

	}

	private void addComponents() {
		System.out.println(myTxtFieldNames.length);
		for (int i = 0; i < myPatientTxt.length; i++) {
			myPatientTxt[i] = new JTextField(TEXT_FIELD_SIZE);
			if (i == 0) {
				myPatientTxt[i].setEditable(false);
				myPatientTxt[i].setToolTipText("cannot edit primary key");
			}
			myPatientLbl[i] = new JLabel(myLblFieldNames[i]);
			myPatientLbl[i].setForeground(Color.GREEN);
			myPatientTxt[i].setName(myTxtFieldNames[i]);
			add(myPatientLbl[i], ParagraphLayout.NEW_PARAGRAPH);
			add(myPatientTxt[i], ParagraphLayout.NEW_LINE);
		}
		myPatientUpdateBtn = new JButton("Update");
		myPatientUpdateBtn.setBackground(Color.black);
		myPatientUpdateBtn.setForeground(Color.green);
		myPatientUpdateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updatePatientDB();
			}
		});
		myPatientUpdateBtn.setName("patientSubmitBtn");
		add(myPatientUpdateBtn, ParagraphLayout.NEW_LINE);
	}

	/**
	 * generates a query to update database. Then fire a property change event
	 * to refresh the patient table.
	 * 
	 */
	public void updatePatientDB() {
		System.out.println(myPatientTxt[7].getText());

		/*
		 * String insertString = "insert into dbo.PATIENT values('" +
		 * myPatientTxt[0].getText() + "', '" + myPatientTxt[1].getText() +
		 * "', '" + myPatientTxt[2].getText() + "', '" +
		 * myPatientTxt[3].getText() + "', '" + myPatientTxt[4].getText() +
		 * "', '" + myPatientTxt[5].getText() + "', '" +
		 * myPatientTxt[6].getText() + "', '" + myPatientTxt[7].getText() +
		 * "')";
		 */
		String updateString = "update dbo.PATIENT SET " + myTxtFieldNames[0] + "='" + myPatientTxt[0].getText() + "', "
				+ myTxtFieldNames[1] + "='" + myPatientTxt[1].getText() + "', " + myTxtFieldNames[2] + "='"
				+ myPatientTxt[2].getText() + "', " + myTxtFieldNames[3] + "='" + myPatientTxt[3].getText() + "', "
				+ myTxtFieldNames[4] + "='" + myPatientTxt[4].getText() + "', " + myTxtFieldNames[5] + "='"
				+ myPatientTxt[5].getText() + "', " + myTxtFieldNames[6] + "='" + myPatientTxt[6].getText() + "', "
				+ myTxtFieldNames[7] + "='" + myPatientTxt[7].getText() + "' where patientID ='"
				+ myPatientTxt[0].getText() + "'";
		Statement stmt;
		try {
			Connection con = null;
			try {
				con = ds.getConnection();
				stmt = con.createStatement();
				stmt.executeUpdate(updateString);
			} finally {
				con.close();
			}
		} catch (Exception e) {
			new MSGWindow("error");
			e.printStackTrace();
		}
		// if successful?
		this.firePropertyChange("patientUpdateBtn", null, null);
		for(JTextField t: myPatientTxt) {
			t.setText("");
		}
	}

	

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
		for (int i = 0; i < myTxtFieldNames.length; i++) {
			myPatientTxt[i].setText(rowData[i]);
		}
	}
}
