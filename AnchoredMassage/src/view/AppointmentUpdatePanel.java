package view;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jhlabs.awt.ParagraphLayout;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Panel that will display and update information relating to each tuple
 *         of the appointment table.
 */
public class AppointmentUpdatePanel extends JPanel implements PropertyChangeListener {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 9106446617813863696L;

	/**
	 * Update and delete buttons.
	 */
	JButton myApptUpdateBtn, myApptDeleteBtn;
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
	JTextField myApptTxt[];
	/**
	 * JLabel objects.
	 */
	JLabel[] myApptLbl;
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
	 * Constructor for Appointment Information Panel.
	 */
	public AppointmentUpdatePanel() {
		setLayout(new ParagraphLayout(40, 30, 10, 10, 10, 10));
		myDBConn = AnchoredGUI.DB_CONNECTION;
		createComponents();
		addListeners();
		this.setPreferredSize(new Dimension(400, 1000));
		this.setVisible(true);
	}

	/**
	 * Creates components from table meta data. Meta data returned is 1 based.
	 */
	private void createComponents() {
		try {
			Statement stmt = myDBConn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM APPOINTMENT");
			ResultSetMetaData rsmd = rs.getMetaData();
			NUM_OF_COLS = rsmd.getColumnCount();
			myApptLbl = new JLabel[NUM_OF_COLS];
			myApptTxt = new JTextField[NUM_OF_COLS];
			myTxtFieldNames = new String[NUM_OF_COLS];
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				myApptTxt[i] = new JTextField(TEXT_FIELD_SIZE);
				myTxtFieldNames[i] = rsmd.getColumnLabel(i + 1);
				if (i == 0 || i == 1) {
					myApptTxt[i].setEditable(false);
					myApptTxt[i].setToolTipText("cannot edit primary key");
				}
				myApptLbl[i] = new JLabel(rsmd.getColumnLabel(i + 1));
				add(myApptLbl[i], ParagraphLayout.NEW_PARAGRAPH);
				add(myApptTxt[i], ParagraphLayout.NEW_LINE);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds listeners to buttons.
	 */
	private void addListeners() {
		myApptUpdateBtn = new JButton("Update");
		myApptUpdateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateApptDB();
			}
		});
		myApptDeleteBtn = new JButton("Delete");
		myApptDeleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteApptDB();
			}
		});
		add(myApptUpdateBtn, ParagraphLayout.NEW_PARAGRAPH);
		add(myApptDeleteBtn);

	}

	/**
	 * Deletes a appointment from the appointment Table.
	 */
	private void deleteApptDB() {
		if (!myApptTxt[0].getText().equals("")) {
			String deleteString = "DELETE FROM APPOINTMENT WHERE appointmentID ='" + myApptTxt[0].getText() + "'";
			executeQuery(deleteString);
			firePropertyChange("createResultSet", null, myApptTxt[0]);
		}
	}

	/**
	 * generates a query to update database. Then fire a property change event
	 * to refresh the appointment table.
	 * 
	 */
	public void updateApptDB() {
		String updateString = "update dbo.appointment SET " + myTxtFieldNames[2] + "='" + myApptTxt[2].getText() + "', "
				+ myTxtFieldNames[3] + "='" + myApptTxt[3].getText() + "', " + myTxtFieldNames[4] + "='"
				+ myApptTxt[4].getText() + "' where patientID ='" + myApptTxt[0].getText() + "' and therapistID='"
				+ myApptTxt[1].getText() + "'";
		executeQuery(updateString);
	}

	/**
	 * Executes a update query statement to the appointment Table.
	 * 
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

		JTextField t : myApptTxt) {
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
	 * Set appointment Fields.
	 */
	private void setTextFields(String[] rowData) {
		for (int i = 0; i < myApptTxt.length; i++) {
			myApptTxt[i].setText(rowData[i]);
		}
	}

	// to do
	/*
	 * String insertString = "insert into dbo.appointment values('" +
	 * myappointmentTxt[1].getText() + "', '" + myappointmentTxt[2].getText() +
	 * "', '" + myappointmentTxt[3].getText() + "', '" +
	 * myappointmentTxt[4].getText() + "', '" + myappointmentTxt[5].getText() +
	 * "', '" + myappointmentTxt[6].getText() + "', '" +
	 * myappointmentTxt[7].getText() + "')";
	 */
}
