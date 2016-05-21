package view;

import java.awt.Color;
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
	/**
	 * current selected value.
	 */
	private String[] currentSelection;
	/**
	 * pre-compiles sql query for update, insert, and delete.
	 */
	private PreparedStatement prepDeleteAppt, prepUpdateAppt, prepInsertAppt;

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
			String deleteString = "delete from APPOINTMENT "
					+ "WHERE patientID = ? AND therapistID = ? AND date = ? AND startTime = ? AND endTime = ?";
			String updateString = "update dbo.APPOINTMENT SET serviceName = ? where patientID = ? AND therapistID = ? AND date = ? AND "
					+ "startTime = ? AND endTime = ?";
			String insertString = "insert into dbo.APPOINTMENT values(?,?,?,?,?,?)";
			prepDeleteAppt = AnchoredGUI.DB_CONNECTION.prepareStatement(deleteString);
			prepUpdateAppt = AnchoredGUI.DB_CONNECTION.prepareStatement(updateString);
			prepInsertAppt = AnchoredGUI.DB_CONNECTION.prepareStatement(insertString);
			NUM_OF_COLS = rsmd.getColumnCount();
			myApptLbl = new JLabel[NUM_OF_COLS];
			myApptTxt = new JTextField[NUM_OF_COLS];
			myTxtFieldNames = new String[NUM_OF_COLS];
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				myApptTxt[i] = new JTextField(TEXT_FIELD_SIZE);
				myTxtFieldNames[i] = rsmd.getColumnLabel(i + 1);
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
				deleteApptDB(currentSelection);
				String[] fields = getFields();
				insertApptDB(fields);
			}

		});
		myApptDeleteBtn = new JButton("Delete");
		myApptDeleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deleteApptDB(currentSelection);
			}

		});
		add(myApptUpdateBtn, ParagraphLayout.NEW_PARAGRAPH);
		add(myApptDeleteBtn);

	}

	private String[] getFields() {
		String[] fields = new String[myApptTxt.length];
		for (int i = 0; i < myApptTxt.length; i++) {
			fields[i] = myApptTxt[i].getText();
			System.out.println(fields[i]);
			System.out.println("here");
		}
		return fields;
	}

	/**
	 * Inserts a tuple into the appointment Table.
	 */
	private void insertApptDB(String[] dataFields) {
		// to do
		// if the fields are not empty run this

		try {
			prepInsertAppt.setString(1, dataFields[0]);
			prepInsertAppt.setString(2, dataFields[1]);
			prepInsertAppt.setDate(3, Date.valueOf(dataFields[2]));
			prepInsertAppt.setTime(4, Time.valueOf(dataFields[3]));
			prepInsertAppt.setTime(5, Time.valueOf(dataFields[4]));
			prepInsertAppt.setString(6, dataFields[5]);
			prepInsertAppt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (JTextField t : myApptTxt) {
			t.setText(null);
		}
		firePropertyChange("createResultSet", null, null);
	}

	/**
	 * Deletes a appointment from the appointment Table.
	 */
	private void deleteApptDB(String[] dataFields) {
		try {
			prepDeleteAppt.setString(1, dataFields[0]);
			prepDeleteAppt.setString(2, dataFields[1]);
			prepDeleteAppt.setDate(3, Date.valueOf(dataFields[2]));
			prepDeleteAppt.setTime(4, Time.valueOf(dataFields[3]));
			prepDeleteAppt.setTime(5, Time.valueOf(dataFields[4]));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (!myApptTxt[0].getText().equals("")) {
			try {
				prepDeleteAppt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			firePropertyChange("createResultSet", null, null);
		}
		/*
		 * for (JTextField t : myApptTxt) { t.setText(null); }
		 */

	}

	/**
	 * generates a prepared statement query to update db. Then fire a property
	 * change event to refresh the appointment table.
	 * 
	 */
	public void updateApptDB() {
		try {
			/*
			 * System.out.println(myApptTxt[5].getText() + " " +
			 * myApptTxt[0].getText() + " " + myApptTxt[1].getText() + " " +
			 * myApptTxt[2].getText() + " " + myApptTxt[3].getText() + "" + " "
			 * + myApptTxt[4].getText());
			 * 
			 * 
			 * System.out.println(Date.valueOf(myApptTxt[2].getText()));
			 * prepUpdateAppt.setString(1, myApptTxt[5].getText());
			 * prepUpdateAppt.setString(2, myApptTxt[0].getText());
			 * prepUpdateAppt.setString(3, myApptTxt[1].getText());
			 * prepUpdateAppt.setDate(4, Date.valueOf(myApptTxt[2].getText()));
			 * prepUpdateAppt.setTime(5, Time.valueOf(myApptTxt[3].getText()));
			 * prepUpdateAppt.setTime(6, Time.valueOf(myApptTxt[4].getText()));
			 */

			prepUpdateAppt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		firePropertyChange("createResultSet", null, null);
		for (JTextField t : myApptTxt) {
			t.setText(null);
		}
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
		this.firePropertyChange("createResultSet", null, null);
		for (JTextField t : myApptTxt) {
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
			currentSelection = ((String[]) evt.getNewValue());
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
}
