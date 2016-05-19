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
 *         of the patient table.
 */
public class TherapistUpdatePanel extends JPanel implements PropertyChangeListener {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 9106446617813863696L;

	/**
	 * Update and delete buttons.
	 */
	JButton myTherapistUpdateBtn, myTherapistDeleteBtn;
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
	JTextField myTherapistTxt[];
	/**
	 * JLabel objects.
	 */
	JLabel[] myTherapistLbl;
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
	 * Constructor for Therapist Information Panel.
	 */
	public TherapistUpdatePanel() {
		//super(Color.DARK_GRAY);
		setLayout(new ParagraphLayout(40, 30, 10, 10, 10, 10));
		myDBConn = AnchoredGUI.DB_CONNECTION;
		createComponents();
		addListeners();
		this.setPreferredSize(new Dimension(400, 1000));
		this.setVisible(true);
	}

	/**
	 * Creates components from table meta data. 
	 * Meta data returned is 1 based.
	 */
	private void createComponents() {
		try {
			Statement stmt = myDBConn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM THERAPIST");
			ResultSetMetaData rsmd = rs.getMetaData();
			NUM_OF_COLS = rsmd.getColumnCount();
			myTherapistLbl = new JLabel[NUM_OF_COLS];
			myTherapistTxt = new JTextField[NUM_OF_COLS];
			myTxtFieldNames = new String[NUM_OF_COLS];
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				myTherapistTxt[i] = new JTextField(TEXT_FIELD_SIZE);
				myTxtFieldNames[i] = rsmd.getColumnLabel(i + 1);
				if (i == 0) {
					myTherapistTxt[i].setEditable(false);
					myTherapistTxt[i].setToolTipText("cannot edit primary key");
				}
				myTherapistLbl[i] = new JLabel(rsmd.getColumnLabel(i + 1)); 
				add(myTherapistLbl[i], ParagraphLayout.NEW_PARAGRAPH);
				add(myTherapistTxt[i], ParagraphLayout.NEW_LINE);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds listeners to buttons.
	 */
	private void addListeners() {
		myTherapistUpdateBtn = new JButton("Update");
		myTherapistUpdateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateTherapistDB();
			}
		});
		myTherapistDeleteBtn = new JButton("Delete");
		myTherapistDeleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteTherapistDB();
			}
		});
		add(myTherapistUpdateBtn, ParagraphLayout.NEW_PARAGRAPH);
		add(myTherapistDeleteBtn);

	}
	
	/**
	 * Deletes a therapist from the Therapist Table. 
	 */
	private void deleteTherapistDB() {
		if (!myTherapistTxt[0].getText().equals("")) {
			String deleteString = "DELETE FROM THERAPIST WHERE therapistID ='" + myTherapistTxt[0].getText() + "'";
			executeQuery(deleteString);
			firePropertyChange("createResultSet", null, myTherapistTxt[0]);
		}
	}

	/**
	 * generates a query to update database. Then fire a property change event
	 * to refresh the therapist table.
	 * 
	 */
	public void updateTherapistDB() {

		String updateString = "update dbo.THERAPIST SET " + myTxtFieldNames[1] + "='" + myTherapistTxt[1].getText() + "', "
				+ myTxtFieldNames[2] + "='" + myTherapistTxt[2].getText() + "', " + myTxtFieldNames[3] + "='"
				+ myTherapistTxt[3].getText() + "', " + myTxtFieldNames[4] + "='" + myTherapistTxt[4].getText() + "', "
				+ myTxtFieldNames[5] + "='" + myTherapistTxt[5].getText() + "', " + myTxtFieldNames[6] + "='"
				+ myTherapistTxt[6].getText() + "', " + myTxtFieldNames[7] + "='" + myTherapistTxt[7].getText()
				+ "' where therapistID ='" + myTherapistTxt[0].getText() + "'";
		executeQuery(updateString);
	}

	/**
	 * Executes a update query statement to the THERAPIST Table. 
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

		JTextField t : myTherapistTxt) {
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
	 * Set THERAPIST Fields.
	 */
	private void setTextFields(String[] rowData) {
		for (int i = 0; i < myTherapistTxt.length; i++) {
			myTherapistTxt[i].setText(rowData[i]);
		}
	}
	
	//to do
	/*
	 * String insertString = "insert into dbo.THERAPIST values('" +
	 * myTherapistTxt[1].getText() + "', '" + myTherapistTxt[2].getText() +
	 * "', '" + myTherapistTxt[3].getText() + "', '" +
	 * myTherapistTxt[4].getText() + "', '" + myTherapistTxt[5].getText() +
	 * "', '" + myTherapistTxt[6].getText() + "', '" +
	 * myTherapistTxt[7].getText() + "')";
	 */
}
