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
import java.sql.Statement;
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
	private static int TEXT_FIELD_SIZE = 17;

	/**
	 * Constructs the Patient search panel.
	 */
	public PatientSearchPanel() {
		CURRENT_QUERY = "SELECT * FROM PATIENT_VIEW";
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
				new CreateEntity();
			}
		});

		this.add(myPatientSearchBtn, ParagraphLayout.NEW_PARAGRAPH);
		this.add(myPatientCreateBtn);

	}

	/**
	 * Executes a update query statement to the appointment Table.
	 * 
	 * @param queryString
	 */
	private void updateTableModel() {
		System.out.println("firing");
		firePropertyChange("createResultSet", null, null);
	}

	/**
	 * Builds SQL expression from the search fields.
	 */
	private void updateSearchResults() {
		CURRENT_QUERY = "SELECT * FROM PATIENT_VIEW"; // to do
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

			JCheckBox authCheckBox = new JCheckBox();
			authCheckBox.setEnabled(false);
			authCheckBox.setText("Add Authorization");
			toggleComponents(authCheckBox, authPanel);
			authCheckBox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent evt) {
					toggleComponents((JCheckBox) evt.getSource(), authPanel);
				}
			});

			JCheckBox insuranceCheckBox = new JCheckBox();
			insuranceCheckBox.setText("Add Insurance");
			toggleComponents(insuranceCheckBox, insurancePanel);
			insuranceCheckBox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent evt) {
					authCheckBox.setEnabled(insuranceCheckBox.isSelected());
					toggleComponents((JCheckBox) evt.getSource(), insurancePanel);
					if (!insuranceCheckBox.isSelected()) {
						authCheckBox.setSelected(false);
						toggleComponents(authCheckBox, authPanel);
					}
				}
			});

			gbc.gridy = 0;
			gbc.gridheight = 1;
			gbc.gridx++;
			myDisplayPanel.add(insurancePanel, gbc);
			gbc.gridy++;
			myDisplayPanel.add(insuranceCheckBox, gbc);
			gbc.gridy++;
			myDisplayPanel.add(authPanel, gbc);
			gbc.gridy++;
			myDisplayPanel.add(authCheckBox, gbc);
			gbc.gridy++;
			JButton sumbit = new JButton(" Submit ");

			sumbit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					long patientKey = 0;
					try {
						Statement patientStmt = AnchoredGUI.DB_CONNECTION
								.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
						patientStmt.execute("Select * from PATIENT");
						ResultSetMetaData patientRSMD = patientStmt.getResultSet().getMetaData();
						if (!validateFields(patientRSMD, myPatientTxt)) {
							new MSGWindow("Please make sure all Patient Fields are not empty");
							return;
						}
						if (insuranceCheckBox.isSelected()) {
							if (!validateFields(PatientCard.INSURANCE_META_DATA, myInsuranceTxt)) {
								new MSGWindow("Please enter all Insurance Fields correctly!");
								return;
							}
						}
						if (authCheckBox.isSelected()) {
							if (!validateFields(PatientCard.AUTH_META_DATA, myAuthorizationTxt)) {
								new MSGWindow("Please enter all Authorization Fields");
								return;
							}
						}
						insertPatient();
						ResultSet myPatientGenKeys = PatientCard.myPatientInsertPS.getGeneratedKeys();
						if (myPatientGenKeys.next()) {
							patientKey = myPatientGenKeys.getLong(1);
						}
						if (insuranceCheckBox.isSelected()) {
							insertInsurance(patientKey);
						}
						if (authCheckBox.isSelected()) {
							insertInsAuthorization(patientKey);
						}
						PatientSearchPanel.this.updateTableModel();
					} catch (SQLException ex) {
						new MSGWindow(ex.getLocalizedMessage());
						ex.printStackTrace();
					}
				}

				private boolean validateFields(ResultSetMetaData rsmd, ArrayList<JTextField> txtFields)
						throws SQLException {
					for (int i = 0; i < txtFields.size(); i++) {
						if (rsmd.isNullable(i + 2) == ResultSetMetaData.columnNoNulls) {
							if (txtFields.get(i).getText().isEmpty()) {
								return false;
							}
						}
					}
					return true;
				}
			});
			gbc.gridy++;
			myDisplayPanel.add(sumbit, gbc);
			this.getContentPane().add(myDisplayPanel, BorderLayout.CENTER);
			pack();
			this.setVisible(true);
		}

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

		private void insertInsAuthorization(long thePatientKey) {
			try {
				PatientCard.myAuthorizationInsertPS.setLong(1, thePatientKey);
				PatientCard.myAuthorizationInsertPS.setString(2, myAuthorizationTxt.get(0).getText());
				PatientCard.myAuthorizationInsertPS.setString(3, myAuthorizationTxt.get(1).getText());
				PatientCard.myAuthorizationInsertPS.setString(4, myAuthorizationTxt.get(2).getText());
				PatientCard.myAuthorizationInsertPS.setString(5, myAuthorizationTxt.get(3).getText());
				PatientCard.myAuthorizationInsertPS.executeUpdate();
			} catch (SQLException ex) {
				new MSGWindow("Please check that all Insurance authorization fields are entered correctly");
			}
		}

		private void toggleComponents(JCheckBox theCheckBox, JPanel thePanel) {
			for (Component jc : thePanel.getComponents()) {
				jc.setEnabled(theCheckBox.isSelected());
			}
		}

		private JPanel createPanel(ArrayList<String> theGroup, String tableName, ArrayList<JTextField> txtFields) {
			JPanel borderPanel = new JPanel(new ParagraphLayout());
			NUM_OF_ATTRIBUTES = theGroup.size();
			myLabels = new JLabel[NUM_OF_ATTRIBUTES];
			for (int i = 0; i < NUM_OF_ATTRIBUTES; i++) {
				txtFields.add(new JTextField(TEXT_FIELD_SIZE));
				myLabels[i] = new JLabel(theGroup.get(i));
				borderPanel.add(myLabels[i], ParagraphLayout.NEW_PARAGRAPH);
				borderPanel.add(txtFields.get(i), ParagraphLayout.NEW_LINE);
			}
			borderPanel.setBorder(BorderFactory.createTitledBorder(tableName));
			return borderPanel;
		}
	}
}
