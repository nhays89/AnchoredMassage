package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
	private static int TEXT_FIELD_SIZE = 10;

	/**
	 * Constructs the Patient search panel.
	 */
	public PatientSearchPanel() {
		CURRENT_QUERY = "SELECT * FROM PATIENT_VIEW";
		addComponents();
		this.setPreferredSize(new Dimension(1000, 50));
		this.setVisible(true);
	}

	/**
	 * adds components to the search panel.
	 */
	private void addComponents() {
		setLayout(new ParagraphLayout(10,10,10,10,10,10));
		TitledBorder title;
		title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder());
		title.setTitlePosition(TitledBorder.TOP);
		this.setBorder(title);
		lblPatientID = new JLabel("Patient ID:");
		add(lblPatientID, ParagraphLayout.STRETCH_H);
		txtPatientID = new JTextField(TEXT_FIELD_SIZE);
		add(txtPatientID);
		lblPatientFName = new JLabel("Patient First Name:");
		add(lblPatientFName, ParagraphLayout.STRETCH_H);
		txtPatientFName = new JTextField(TEXT_FIELD_SIZE);
		add(txtPatientFName);
		lblPatientLName = new JLabel("Patient Last Name:");
		add(lblPatientLName, ParagraphLayout.STRETCH_H);
		txtPatientLName = new JTextField(TEXT_FIELD_SIZE);
		add(txtPatientLName);
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
				new CreateEntity();
			}
		});

		this.add(myPatientSearchBtn, ParagraphLayout.STRETCH_H);
		this.add(myPatientCreateBtn, ParagraphLayout.STRETCH_H);

	}

	/**
	 * Executes a update query statement to the appointment Table.
	 * 
	 * @param queryString
	 */
	private void updateTableModel() {
		System.out.println("in update table model");
		firePropertyChange("createResultSet", null, null);
	}

	/**
	 * Builds SQL expression from the search fields.
	 */
	private void updateSearchResults() {
		StringBuilder searchExp = new StringBuilder("Select * from PATIENT_VIEW ");
		if(!txtPatientID.getText().isEmpty()) searchExp.append("where [Patient ID] = '" + txtPatientID.getText() + "'");
		if(!txtPatientFName.getText().isEmpty()) {
			if(!txtPatientID.getText().isEmpty()) {
				searchExp.append(" AND [First Name] = '" + txtPatientFName.getText()  + "'");
			} else {
				searchExp.append("where [First Name] LIKE '%" + txtPatientFName.getText() + "%'");
			}
		}
		if(!txtPatientLName.getText().isEmpty()) {
			if(!txtPatientID.getText().isEmpty() || !(txtPatientFName.getText().isEmpty())) {
				searchExp.append(" AND [Last Name] = '" + txtPatientFName.getText()  + "'");
			} else {
				searchExp.append("where [Last Name] LIKE '%" + txtPatientLName.getText() + "%'");
			}
		}
		CURRENT_QUERY = searchExp.toString(); 
		this.firePropertyChange("createResultSet", null, null);
	}

	public class CreateEntity extends JFrame {
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
		private ArrayList<JTextField> myPatientTxt, myInsuranceTxt, myAuthorizationTxt;
		/**
		 * TextFieldSize
		 */
		private int TEXT_FIELD_SIZE = 15;
		/**
		 * Display Panel
		 */
		private JPanel myDisplayPanel;
		/**
		 * Number of Columns.
		 */
		private int NUM_OF_ATTRIBUTES;
		/**
		 * lists
		 */
		private ArrayList<String> patientAttributes, insuranceAttributes, authAttributes;
		/**
		 * Check boxes to add additional patient information
		 */
		private JCheckBox myInsuranceBox, myAuthorizationBox;

		/**
		 * 
		 * @param theData
		 *            the result set meta data
		 */
		public CreateEntity() {
			this.setLocationRelativeTo(null);
			myDisplayPanel = new JPanel(new GridBagLayout());
			determineGroup();
			createComponents();
		}
		/**
		 * Determines the attributes table.
		 */
		private void determineGroup() {
			patientAttributes = new ArrayList<String>();
			insuranceAttributes = new ArrayList<String>();
			authAttributes = new ArrayList<String>();
			try {
				NUM_OF_ATTRIBUTES = PatientCard.PATIENT_JOIN_META_DATA.getColumnCount();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			for (int i = 0; i < NUM_OF_ATTRIBUTES; i++) {
				try {
					if (!PatientCard.PATIENT_JOIN_META_DATA.getColumnLabel(i + 1).equals("Patient ID")) {
						if (PatientCard.PATIENT_JOIN_META_DATA.getTableName(i + 1).equals("PATIENT")) {
							patientAttributes.add(PatientCard.PATIENT_JOIN_META_DATA.getColumnLabel(i + 1));
						} else if (PatientCard.PATIENT_JOIN_META_DATA.getTableName(i + 1).equals("INSURANCE")) {
							insuranceAttributes.add(PatientCard.PATIENT_JOIN_META_DATA.getColumnLabel(i + 1));
						} else {
							authAttributes.add(PatientCard.PATIENT_JOIN_META_DATA.getColumnLabel(i + 1));
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		/**
		 * Creates components for JFrame. 
		 */
		private void createComponents() {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(10, 10, 10, 10);
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridheight = 4;
			gbc.anchor = GridBagConstraints.NORTHWEST;
			myPatientTxt = new ArrayList<JTextField>(10);
			myInsuranceTxt = new ArrayList<JTextField>(10);
			myAuthorizationTxt = new ArrayList<JTextField>(10);
			myDisplayPanel.add(createPanel(patientAttributes, " Patient ", myPatientTxt), gbc);
			JPanel insurancePanel = createPanel(insuranceAttributes, " Insurance ", myInsuranceTxt);
			JPanel authPanel = createPanel(authAttributes, " Authorization ", myAuthorizationTxt);
			myAuthorizationBox = new JCheckBox();
			myAuthorizationBox.setEnabled(false);
			myAuthorizationBox.setText("Add Authorization");
			toggleComponents(myAuthorizationBox, authPanel);
			myAuthorizationBox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent evt) {
					toggleComponents((JCheckBox) evt.getSource(), authPanel);
				}
			});
			myInsuranceBox = new JCheckBox();
			myInsuranceBox.setText("Add Insurance");
			toggleComponents(myInsuranceBox, insurancePanel);
			myInsuranceBox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent evt) {
					myAuthorizationBox.setEnabled(myInsuranceBox.isSelected());
					toggleComponents((JCheckBox) evt.getSource(), insurancePanel);
					if (!myInsuranceBox.isSelected()) {
						myAuthorizationBox.setSelected(false);
						toggleComponents(myAuthorizationBox, authPanel);
					}
				}
			});
			gbc.gridy = 0;
			gbc.gridheight = 1;
			gbc.gridx++;
			myDisplayPanel.add(insurancePanel, gbc);
			gbc.gridy++;
			myDisplayPanel.add(myInsuranceBox, gbc);
			gbc.gridy++;
			myDisplayPanel.add(authPanel, gbc);
			gbc.gridy++;
			myDisplayPanel.add(myAuthorizationBox, gbc);
			gbc.gridy++;
			JButton submit = new JButton(" Submit ");
			addSubmitBtn(submit);
			gbc.gridy++;
			myDisplayPanel.add(submit, gbc);
			this.getContentPane().add(myDisplayPanel, BorderLayout.CENTER);
			pack();
			this.setVisible(true);
		}

		/**
		 * Adds a Jbutton to the panel which initiates the insert.
		 * 
		 * @param sumbitBtn the JButton. 
		 */
		private void addSubmitBtn(JButton sumbitBtn) {
			sumbitBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					long patientKey = 0;
					try {
						if (isValid()) {
							insertPatient();
							ResultSet myPatientGenKeys = PatientCard.myPatientInsertPS.getGeneratedKeys();
							if (myPatientGenKeys.next()) {
								patientKey = myPatientGenKeys.getLong(1);
							}
							if (myInsuranceBox.isSelected()) {
								insertInsurance(patientKey);
							}
							if (myAuthorizationBox.isSelected()) {
								insertInsAuthorization(patientKey);
							}
							System.out.println("getting here");
							PatientSearchPanel.this.updateTableModel();
						}
					} catch (SQLException ex) {
						new MSGWindow(ex.getLocalizedMessage());
						ex.printStackTrace();
					}
				}
				/**
				 * Validates each field that the user specified. 
				 *  
				 * @return boolean value of the fields validity.
				 */
				private boolean isValid() {
					try {
						if (!validateFields(PatientCard.PATIENT_META_DATA, myPatientTxt)) {
							new MSGWindow("Please make sure all Patient Fields are not empty");
							return false;
						}
						if (myInsuranceBox.isSelected()) {
							if (!validateFields(PatientCard.INSURANCE_META_DATA, myInsuranceTxt)) {
								new MSGWindow("Please enter all Insurance Fields correctly!");
								return false;
							}
						}
						if (myAuthorizationBox.isSelected()) {
							if (!validateFields(PatientCard.AUTH_META_DATA, myAuthorizationTxt)) {
								new MSGWindow("Please enter all Authorization Fields");
								return false;
							}
						}
					} catch (SQLException e) {
						new MSGWindow(e.getLocalizedMessage());
						e.printStackTrace();
					}
					return true;
				}
			});
			
		}

	

		/**
		 * Validates user data before data is sent to the database by comparing
		 * atrributes with non null fields with text from the JTextField.
		 * 
		 * @param rsmd
		 *            the meta data of the table's attributes.
		 * @param txtFields
		 *            the JTextFields to retrive data from.
		 * @return the truth value of the validation check.
		 * @throws SQLException
		 *             if result set meta data is null or invalid.
		 */
		private boolean validateFields(ResultSetMetaData rsmd, ArrayList<JTextField> txtFields) throws SQLException {
			for (int i = 0; i < txtFields.size(); i++) {
				if (rsmd.isNullable(i + 2) == ResultSetMetaData.columnNoNulls) {
					if (txtFields.get(i).getText().isEmpty()) {
						return false;
					}
				}
			}
			return true;
		}

		/**
		 * Constructs the JPanel to represent the attributes in the Patient
		 * Database.
		 * 
		 * @param theGroup
		 *            the group of attributes.
		 * @param tableName
		 *            the name of the table.
		 * @param txtFields
		 *            a list of text fields to associate names with.
		 * @return JPanel with components.
		 */
		private JPanel createPanel(ArrayList<String> theGroup, String tableName, ArrayList<JTextField> txtFields) {
			JPanel borderPanel = new JPanel(new ParagraphLayout());
			NUM_OF_ATTRIBUTES = theGroup.size();
			myLabels = new JLabel[NUM_OF_ATTRIBUTES];
			for (int i = 0; i < NUM_OF_ATTRIBUTES; i++) {
				JTextField txtField = new JTextField(TEXT_FIELD_SIZE);
				txtFields.add(txtField);
				myLabels[i] = new JLabel(theGroup.get(i));
				borderPanel.add(myLabels[i], ParagraphLayout.NEW_PARAGRAPH);
				borderPanel.add(txtFields.get(i), ParagraphLayout.NEW_LINE);
			}
			borderPanel.setBorder(BorderFactory.createTitledBorder(tableName));
			return borderPanel;
		}

		/**
		 * Patient Prepared Statement.
		 */
		protected void insertPatient() {
			try {
				PatientCard.myPatientInsertPS.setString(1, myPatientTxt.get(0).getText());
				PatientCard.myPatientInsertPS.setString(2, myPatientTxt.get(1).getText());
				PatientCard.myPatientInsertPS.setString(3, myPatientTxt.get(2).getText());
				PatientCard.myPatientInsertPS.setString(4, myPatientTxt.get(3).getText());
				PatientCard.myPatientInsertPS.setString(5, myPatientTxt.get(4).getText());
				PatientCard.myPatientInsertPS.setInt(6, Integer.parseInt(myPatientTxt.get(5).getText()));
				PatientCard.myPatientInsertPS.setDate(7, Date.valueOf(myPatientTxt.get(6).getText()));
				PatientCard.myPatientInsertPS.setString(8, (myPatientTxt.get(7).getText()));
				PatientCard.myPatientInsertPS.setString(9, myPatientTxt.get(8).getText());
				PatientCard.myPatientInsertPS.executeUpdate();
			} catch (SQLException e) {
				new MSGWindow("Please check that all Patient Fields are entered correctly.");
			}
		}

		/**
		 * Insurance Prepared Statement.
		 * 
		 * @param thePatientKey
		 *            the autogenerated patient id.
		 */
		protected void insertInsurance(long thePatientKey) {
			try {
				PatientCard.myInsuranceInsertPS.setLong(1, thePatientKey);
				PatientCard.myInsuranceInsertPS.setString(2, myInsuranceTxt.get(0).getText());
				PatientCard.myInsuranceInsertPS.setString(3, myInsuranceTxt.get(1).getText());
				PatientCard.myInsuranceInsertPS.setString(4, myInsuranceTxt.get(2).getText());
				PatientCard.myInsuranceInsertPS.setString(5, myInsuranceTxt.get(3).getText());
				PatientCard.myInsuranceInsertPS.executeUpdate();
			} catch (SQLException sql) {
				new MSGWindow("Please check that all Insurance Fields are entered correctly.");
			}
		}

		/**
		 * Insurance Authroization prepared statement.
		 * 
		 * @param thePatientKey
		 *            the auto generated patient id.
		 */
		private void insertInsAuthorization(long thePatientKey) {
			try {
				PatientCard.myAuthorizationInsertPS.setLong(1, thePatientKey);
				PatientCard.myAuthorizationInsertPS.setString(2, myAuthorizationTxt.get(0).getText());
				PatientCard.myAuthorizationInsertPS.setString(3, myAuthorizationTxt.get(1).getText());
				PatientCard.myAuthorizationInsertPS.setString(4, myAuthorizationTxt.get(2).getText());
				PatientCard.myAuthorizationInsertPS.setString(5, myAuthorizationTxt.get(3).getText());
				PatientCard.myAuthorizationInsertPS.executeUpdate();
			} catch (SQLException ex) {
			}
		}

		/**
		 * Toggles Components to determine which textfields are included on
		 * Insert of Patient.
		 * 
		 * @param theCheckBox
		 *            decides on the toggle state.
		 * @param thePanel
		 *            the panel to toggle components on.
		 */
		private void toggleComponents(JCheckBox theCheckBox, JPanel thePanel) {
			for (Component jc : thePanel.getComponents()) {
				jc.setEnabled(theCheckBox.isSelected());
			}
		}
	}
}
