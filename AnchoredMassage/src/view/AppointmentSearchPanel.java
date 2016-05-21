package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
public class AppointmentSearchPanel extends JPanel implements PropertyChangeListener {

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
	private JLabel lblPatientID, lblTherapistID, lblDate;
	/**
	 * patient text fields.
	 */
	private JTextField txtPatientID, txtTherapistID, txtDate;
	/**
	 * current query string.
	 */
	public static String CURRENT_QUERY;
	/**
	 * text field length.
	 */
	private static int TEXT_FIELD_SIZE = 17;
	/**
	 * current selected value.
	 */
	private String[] currentSelection;
	/**
	 * Add appointment.
	 */
	private CreateEntity myAppointmentEntity;
	/**
	 * Pre compiles insert statement.
	 */
	private PreparedStatement prepInsertAppt, prepSearchAppt;

	/**
	 * Constructs the Patient search panel.
	 */
	public AppointmentSearchPanel() {
		CURRENT_QUERY = "SELECT * FROM APPOINTMENT";
		setPreparedStatement();
		addComponents();
		this.setPreferredSize(new Dimension(1000, 75));
		this.setVisible(true);
	}

	private void setPreparedStatement() {
		String insertString = "insert into APPOINTMENT values(?,?,?,?,?,?)";
		//String searchString = "select * from APPOINTMENT where patientID = ? OR therapistID = ? OR date = ?";
		prepInsertAppt = null;
		try {
			prepInsertAppt = AnchoredGUI.DB_CONNECTION.prepareStatement(insertString);
			//prepSearchAppt = AnchoredGUI.DB_CONNECTION.prepareStatement(searchString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * adds components to the search panel.
	 */
	private void addComponents() {
		TitledBorder title;
		title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Search");
		title.setTitlePosition(TitledBorder.TOP);
		this.setBorder(title);
		lblPatientID = new JLabel("Patient ID: ");
		add(lblPatientID, ParagraphLayout.NEW_PARAGRAPH);
		txtPatientID = new JTextField(TEXT_FIELD_SIZE);
		add(txtPatientID, ParagraphLayout.NEW_LINE);
		lblTherapistID = new JLabel("Therapist ID: ");
		add(lblTherapistID, ParagraphLayout.NEW_PARAGRAPH);
		txtTherapistID = new JTextField(TEXT_FIELD_SIZE);
		add(txtTherapistID, ParagraphLayout.NEW_LINE);
		lblDate = new JLabel("Date: ");
		add(lblDate, ParagraphLayout.NEW_PARAGRAPH);
		txtDate = new JTextField(TEXT_FIELD_SIZE);
		add(txtDate, ParagraphLayout.NEW_LINE);
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
			prepInsertAppt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			new MSGWindow("error");
		}
		this.firePropertyChange("createResultSet", null, null);
		
	}

	/**
	 * Builds sql expression from the search fields.
	 */
	private void updateSearchResults() {
		String patientExp = null, therapistExp = null, dateExp = null;
		if(txtPatientID.getText().equals("")) {
			patientExp = "BETWEEN '1' AND '50'";
		} else {
			
			patientExp = "= '" + txtPatientID.getText() + "'";
		}
		if(txtTherapistID.getText().equals("")) {
			therapistExp = "BETWEEN '1' AND '50'";
		} else {
			therapistExp = "= '" + txtTherapistID.getText()+ "'";
		}
		if(txtDate.getText().equals("")) {
			dateExp = "> '1995-05-01'";
		} else {
			dateExp = "= '" + txtDate.getText() + "'";
		}
		CURRENT_QUERY = "Select * from APPOINTMENT where patientID " + patientExp + " AND therapistID " + therapistExp + " AND date " + dateExp + "";
		System.out.println(CURRENT_QUERY);
		
		txtPatientID.setText("");
		txtTherapistID.setText("");
		txtDate.setText("");
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

		protected String[] getTextFields() {
			String[] textFields = new String[NUM_OF_ATTRIBUTES];
			for (int i = 0; i < textFields.length; i++) {
				textFields[i] = myTextFields[i].getText();
			}
			return textFields;
		}
		protected void clearFields() {
			for (JTextField f: myTextFields) {
				f.setText(null);
			}
		}
	}

	/**
	 * Receives a table selection event to set the text fields in the update
	 * panel.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("tableSelectionChanged")) {
			currentSelection = (String[]) evt.getNewValue();
		}
	}
}
