package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.jhlabs.awt.ParagraphLayout;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Panel that will allow users to query and filter the patient table.
 */
public class AppointmentSearchPanel extends JPanel {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 2549419250238970948L;

	/**
	 * patient search button.
	 */
	private JButton myApptSearchBtn, myApptCreateBtn;
	/**
	 * patient search labels.
	 */
	private JLabel lblPatientID, lblTherapistID, lblFromDate, lblToDate;
	/**
	 * patient text fields.
	 */
	private JTextField txtPatientID, txtTherapistID, txtFromDate, txtToDate;
	/**
	 * current query string.
	 */
	public static String CURRENT_QUERY;
	/**
	 * text field length.
	 */
	private static int TEXT_FIELD_SIZE = 10;
	/**
	 * incorrect Date string.
	 */
	private static String incorrectDateString = "Incorrect date format. All dates must be entered as YYYY-MM-DD"
			+ "\n For instance, January 15th, 2016 is 2016-01-15";
	/**
	 * Add appointment.
	 */
	private CreateEntity myAppointmentEntity;
	/**
	 * Pre compiles insert statement.
	 */
	private PreparedStatement prepInsertAppt;

	/**
	 * Constructs the Patient search panel.
	 */
	public AppointmentSearchPanel() {
		this.setLayout(new ParagraphLayout(10,10,10,10,10,10));
		CURRENT_QUERY = "SELECT * FROM APPOINTMENT";
		setPreparedStatement();
		addSearchComps();
		this.setPreferredSize(new Dimension(600, 50));
		this.setVisible(true);
	}

	private void setPreparedStatement() {
		String insertString = "insert into APPOINTMENT values(?,?,?,?,?,?,?)";
		prepInsertAppt = null;
		try {
			prepInsertAppt = AnchoredGUI.DB_CONNECTION.prepareStatement(insertString);
		} catch (SQLException e) {
			new MSGWindow(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	/**
	 * adds components to the search panel.
	 */
	private void addSearchComps() {
		TitledBorder title;
		title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder());
		title.setTitlePosition(TitledBorder.TOP);
		this.setBorder(title);
		lblPatientID = new JLabel("Patient ID: ");
		add(lblPatientID, ParagraphLayout.STRETCH_H);
		txtPatientID = new JTextField(TEXT_FIELD_SIZE);
		add(txtPatientID);
		lblTherapistID = new JLabel("Therapist ID: ");
		add(lblTherapistID, ParagraphLayout.STRETCH_H);
		txtTherapistID = new JTextField(TEXT_FIELD_SIZE);
		add(txtTherapistID);
		lblFromDate = new JLabel("Date From: ");
		add(lblFromDate, ParagraphLayout.STRETCH_H);
		txtFromDate = new JTextField(TEXT_FIELD_SIZE);
		add(txtFromDate);
		lblToDate = new JLabel("To: ");
		add(lblToDate, ParagraphLayout.STRETCH_H);
		txtToDate = new JTextField(TEXT_FIELD_SIZE);
		add(txtToDate);
		myApptSearchBtn = new JButton("Search");
		myApptCreateBtn = new JButton("Create");

		myApptSearchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateSearchResults();
			}
		});
		this.add(myApptSearchBtn);
		myApptCreateBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				myAppointmentEntity = new CreateEntity(AppointmentCard.APPOINTMENT_META_DATA, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						insertApptTuple(myAppointmentEntity.getTextFields());
						myAppointmentEntity.clearFields();
					}
				});
			}
		});
		this.add(myApptCreateBtn);
	}

	private void insertApptTuple(String[] dataFields) {
		try {
			prepInsertAppt.setString(1, dataFields[0]);
			prepInsertAppt.setString(2, dataFields[1]);
			prepInsertAppt.setDate(3, Date.valueOf(dataFields[2]));
			prepInsertAppt.setTime(4, Time.valueOf(dataFields[3]));
			prepInsertAppt.setTime(5, Time.valueOf(dataFields[4]));
			prepInsertAppt.setString(6, dataFields[5]);
			prepInsertAppt.setString(7, dataFields[6]);
			prepInsertAppt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			new MSGWindow(e.getLocalizedMessage());
		}
		this.firePropertyChange("createResultSet", null, null);

	}

	/**
	 * Builds sql expression from the search fields.
	 */
	private void updateSearchResults() {
		StringBuilder searchExp = new StringBuilder("Select * from APPOINTMENT ");
		String fromDate = "", toDate = "";
		boolean patientData = txtPatientID.getText().isEmpty() == true ? false : true;
		boolean therapistData = txtTherapistID.getText().isEmpty() == true ? false : true;
		boolean dateFromData = txtFromDate.getText().isEmpty() == true ? false : true;
		boolean dateToData = txtToDate.getText().isEmpty() == true ? false : true;

		if (patientData) {
			String patient = "'" + txtPatientID.getText() + "'";
			searchExp.append("where [Patient ID] = " + patient);
		}
		if (therapistData) {
			String therapist = "'" + txtTherapistID.getText() + "'";
			if (patientData) {
				searchExp.append("AND [Therapist ID] = " + therapist);
			} else {
				searchExp.append("where [Therapist ID] = " + therapist);
			}
		}
		if (dateFromData) {
			try {
				fromDate = "'" + Date.valueOf(txtFromDate.getText()) + "'";
			} catch (IllegalArgumentException e) {
				new MSGWindow(incorrectDateString);
				e.printStackTrace();
				return;
			}
		}
		if (dateToData) {
			try {
				toDate = "'" + Date.valueOf(txtToDate.getText()) + "'";
			} catch (IllegalArgumentException e) {
				new MSGWindow(incorrectDateString);
				e.printStackTrace();
				return;
			}
		}
		if (dateFromData == true && dateToData == true) {
			if (therapistData == true || patientData == true) {
				searchExp.append(" AND [Date] BETWEEN " + fromDate + " AND " + toDate);
			} else {
				searchExp.append("where [Date] BETWEEN " + fromDate + " AND " + toDate);
			}
		} else if (dateFromData == true && dateToData == false) {
			if (therapistData == true || patientData == true) {
				searchExp.append(" AND [Date] >= " + fromDate);
			} else {
				searchExp.append("where [Date] >= " + fromDate);
			}
		} else if (dateFromData == false && dateToData == true) {
			if (therapistData == true || patientData == true) {
				searchExp.append(" AND [Date] <= " + toDate);
			} else {
				searchExp.append("where [Date] <= " + toDate);
			}
		}

		CURRENT_QUERY = searchExp.toString();
		this.firePropertyChange("createResultSet", null, null);
	}

	public static class CreateEntity extends JFrame {

		/**
		 * serial id.
		 */
		private static final long serialVersionUID = -5515533409438170331L;
		/**
		 * Labels
		 */
		private JLabel[] myLabels;
		/**
		 * TextFields
		 */
		private JTextField[] myTextFields;
		/**
		 * TextFieldNames
		 */
		private String[] myAttribute;
		/**
		 * TextFieldSize
		 */
		private static int TEXT_FIELD_SIZE = 15;
		/**
		 * The Meta Data
		 */
		ResultSetMetaData myMetaData;
		/**
		 * Display Panel
		 */
		private JPanel myDisplay;
		/**
		 * Number of Columns.
		 */
		private int NUM_OF_ATTRIBUTES;
		/**
		 * The action listener.
		 */
		private ActionListener myActionListener;

		/**
		 * 
		 * @param theData
		 *            the result set meta data
		 */
		public CreateEntity(ResultSetMetaData theData, ActionListener theListener) {
			myMetaData = theData;
			myActionListener = theListener;
			myDisplay = new JPanel(new ParagraphLayout());
			create();
		}

		private void create() {
			try {
				NUM_OF_ATTRIBUTES = myMetaData.getColumnCount();
				myLabels = new JLabel[NUM_OF_ATTRIBUTES];
				myTextFields = new JTextField[NUM_OF_ATTRIBUTES];
				myAttribute = new String[NUM_OF_ATTRIBUTES];
				for (int i = 0; i < myMetaData.getColumnCount(); i++) {
					myTextFields[i] = new JTextField(TEXT_FIELD_SIZE);
					myAttribute[i] = myMetaData.getColumnLabel(i + 1);
					myLabels[i] = new JLabel(myMetaData.getColumnLabel(i + 1));
					myDisplay.add(myLabels[i], ParagraphLayout.NEW_PARAGRAPH);
					myDisplay.add(myTextFields[i], ParagraphLayout.NEW_LINE);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			JButton b = new JButton("Submit");
			b.addActionListener(myActionListener);
			myDisplay.add(b, ParagraphLayout.NEW_PARAGRAPH);
			this.getContentPane().add(myDisplay, BorderLayout.CENTER);
			pack();
			this.setVisible(true);

		}
		
		/**
		 * Retrieves the text data from the text fields in the search panel.
		 * @return
		 */
		protected String[] getTextFields() {
			String[] textFields = new String[NUM_OF_ATTRIBUTES];
			for (int i = 0; i < textFields.length; i++) {
				textFields[i] = myTextFields[i].getText();
			}
			return textFields;
		}
		/**
		 * Clears out the text fields
		 */
		protected void clearFields() {
			for (JTextField f : myTextFields) {
				f.setText(null);
			}
		}
	}
}
