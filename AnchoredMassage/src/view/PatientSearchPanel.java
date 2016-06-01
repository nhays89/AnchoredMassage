package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

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
public class PatientSearchPanel extends JPanel {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 2549419250238970948L;

	/**
	 * patient search button.
	 */
	private JButton myPatientSearchBtn;
	/**
	 * patient search labels.
	 */
	private JLabel lblPatientID, lblPatientFName, lblPatientLName;
	/**
	 * patient text fields.
	 */
	private JTextField txtPatientID, txtPatientFName, txtPatientLName;
	/**
	 * create patient button.
	 */
	private JButton myPatientCreateBtn;
	/**
	 * current query string.
	 */
	public static String CURRENT_QUERY;
	/**
	 * text field length.
	 */
	private static int TEXT_FIELD_SIZE = 17;
	/**
	 * Create Patient
	 */
	private CreateEntity myPatientEntity;

	/**
	 * Constructs the Patient search panel.
	 */
	public PatientSearchPanel() {
		CURRENT_QUERY = "SELECT * FROM PATIENT";
		addComponents();
		this.setPreferredSize(new Dimension(1000, 75));
		this.setVisible(true);
	}
	

	/**
	 * adds components to the search panel.
	 */
	private void addComponents() {
		TitledBorder title;
		title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Search");
		title.setTitlePosition(TitledBorder.TOP);
		this.setBorder(title);
		lblPatientID = new JLabel("Patient ID:");
		add(lblPatientID, ParagraphLayout.NEW_PARAGRAPH);
		txtPatientID = new JTextField(TEXT_FIELD_SIZE);
		add(txtPatientID, ParagraphLayout.NEW_LINE);
		lblPatientFName = new JLabel("Patient First Name:");
		add(lblPatientFName, ParagraphLayout.NEW_PARAGRAPH);
		txtPatientFName = new JTextField(TEXT_FIELD_SIZE);
		add(txtPatientFName, ParagraphLayout.NEW_LINE);
		lblPatientLName = new JLabel("Patient Last Name:");
		add(lblPatientLName, ParagraphLayout.NEW_PARAGRAPH);
		txtPatientLName = new JTextField(TEXT_FIELD_SIZE);
		add(txtPatientLName, ParagraphLayout.NEW_LINE);
		myPatientSearchBtn = new JButton(" Search ");
		myPatientCreateBtn = new JButton(" Create ");
		myPatientSearchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateSearchResults();
			}
		});

		myPatientCreateBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				myPatientEntity = new CreateEntity(PatientCard.PATIENT_META_DATA, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						insertApptTuple(myPatientEntity.getTextFields());
					}
				});
			}
		});

		this.add(myPatientSearchBtn, ParagraphLayout.NEW_PARAGRAPH);
		this.add(myPatientCreateBtn);

	}

	private void insertApptTuple(String[] dataFields) {
		StringBuilder insertString = new StringBuilder();
		insertString.append("insert into dbo.PATIENT values('");
		for (int i = 1; i < dataFields.length -1; i++) {
			insertString.append(dataFields[i] + "', '");
		}
		insertString.append(dataFields[dataFields.length - 1] + "')");
		System.out.println(insertString.toString());
		executeQuery(insertString.toString());

	}

	/**
	 * Executes a update query statement to the appointment Table.
	 * 
	 * @param queryString
	 */
	private void executeQuery(String queryString) {
		Statement stmt;
		try {
			stmt = AnchoredGUI.DB_CONNECTION.createStatement();
			stmt.executeUpdate(queryString);
		} catch (Exception e) {
			new MSGWindow("error");
			e.printStackTrace();
		}
		// if successful?
		this.firePropertyChange("createResultSet", null, null);
	}

	/**
	 * Builds sql expression from the search fields.
	 */
	private void updateSearchResults() {
		CURRENT_QUERY = "SELECT * FROM PATIENT"; // to do
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
					if (i == 0) {
						myTextFields[i].setEditable(false);
						myTextFields[i].setToolTipText("auto generated");
					}
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
			for(int i =0; i < textFields.length; i++) {
				textFields[i] = myTextFields[i].getText();
			}
			return textFields;
		}
		
	}
}
