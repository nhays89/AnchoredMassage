package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
public class PatientUpdatePanel extends JPanel implements PropertyChangeListener {

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
	private static int PATIENT_TEXT_SIZE = 16, INSURANCE_TEXT_SIZE = 14, AUTHORIZATION_TEXT_SIZE = 13, TEXT_FIELD_SIZE = 10;
	/**
	 * Labels
	 */
	private JLabel[] myLabels;
	/**
	 * TextFields
	 */
	private ArrayList<JTextField> myPatientTxt, myInsuranceTxt, myAuthorizationTxt;
	/**
	 * Display Panel
	 */
	private JPanel myDisplayPanel;
	/**
	 * Number of Columns.
	 */
	private int NUM_OF_ATTRIBUTES;
	/**
	 * lists of attriubtes
	 */
	private ArrayList<String> patientAttributes, insuranceAttributes, authAttributes;
	/**
	 * from current selected patient (get from table model)
	 */
	private static boolean hasInsurance, hasAuthorization;
	/**
	 * checkboxes
	 */
	private static JCheckBox myInsuranceBox, myAuthorizationBox;
	/**
	 * current Patient ID.
	 */
	private static int CURRENT_PATIENT_ID;

	/**
	 * Constructor for Patient Information Panel.
	 */
	public PatientUpdatePanel() {
		myDisplayPanel = new JPanel(new GridBagLayout());
		determineGroup();
		createComponents();
		setLayout(new ParagraphLayout(00, 10, 10, 10, 10, 10));
		this.setPreferredSize(new Dimension(400, 1000));
		this.setVisible(true);
	}
	
	/**
	 * Creates components for update panel.
	 */
	private void createComponents() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
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
				notifyAuthorization();
				toggleComponents((JCheckBox) evt.getSource(), authPanel);
			}
		});

		myInsuranceBox = new JCheckBox();
		myInsuranceBox.setText("Add Insurance");
		toggleComponents(myInsuranceBox, insurancePanel);
		myInsuranceBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent evt) {
				notifyInsurance();
				myAuthorizationBox.setEnabled(myInsuranceBox.isSelected());
				toggleComponents((JCheckBox) evt.getSource(), insurancePanel);
				if (!myInsuranceBox.isSelected()) {
					myAuthorizationBox.setSelected(false);
					toggleComponents(myAuthorizationBox, authPanel);
				}
			}
		});

		gbc.gridy++;
		myDisplayPanel.add(myInsuranceBox, gbc);
		gbc.gridy++;
		myDisplayPanel.add(insurancePanel, gbc);
		gbc.gridy++;
		myDisplayPanel.add(myAuthorizationBox, gbc);
		gbc.gridy++;
		myDisplayPanel.add(authPanel, gbc);
		gbc.gridy++;
		JButton update = new JButton(" Update ");
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
					if(!checkValidation()) {
						return;
					}
					updatePatient();
					checkInsurance();
					checkAuthorization();
					firePropertyChange("createResultSet", null, null);
				} 
		});
		gbc.gridy++;
		gbc.gridwidth = 1;
		myDisplayPanel.add(update, gbc);
		JButton deleteBtn = new JButton(" Delete Patient ");
		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this Patient?",
						"Delete Patient", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					System.out.println("in delete");
					deletePatient();
					firePropertyChange("createResultSet", null, null);
				}
			}
		});
		gbc.gridx++;
		myDisplayPanel.add(deleteBtn, gbc);
		add(myDisplayPanel);
		this.setVisible(true);
	}
	
	/**
	 * Checks to see if the user is updating authorization. 
	 */
	protected void checkAuthorization() {
		if (myAuthorizationBox.isSelected()) {
			if (hasAuthorization) {
				updateAuthorization();
			} else {
				insertAuthorization();
				hasAuthorization = true;
			}
		} else {
			if (hasAuthorization) {
				deleteAuthorization();
			}
		}
	}
	
	/**
	 * Checks to see if the user is updating insurance. 
	 */
	protected void checkInsurance() {
		if (myInsuranceBox.isSelected()) {
			if (hasInsurance) {
				updateInsurance();
			} else {
				insertInsurance();
				hasInsurance = true;
			}
		} else {
			if (hasInsurance) {
				deleteInsurance();
			}
		}
	}
	
	/**
	 * Tests to make sure fields are valid. 
	 * 
	 * @return boolean that tests the fields validity. 
	 */
	protected boolean checkValidation() {
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
		} catch(SQLException e ) {
			new MSGWindow(e.getLocalizedMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Notifys user of authorization changes.
	 */
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
	
	/**
	 * Notifys user of insurance changes.
	 */
	protected void notifyInsurance() {
		if (hasInsurance) {
			if (!myInsuranceBox.isSelected()) {
				myInsuranceBox.setText(" Insurance will be deleted upon update ");
			} else {
				myInsuranceBox.setText(" Insurance will be updated upon update ");
			}
		} else {
			myInsuranceBox.setText(" Add Insurance ");
		}
	}
	
	/**
	 * Notifys user of authorization changes.
	 */
	protected void notifyAuthorization() {
		if (hasInsurance) {
			if (hasAuthorization) {
				if (myAuthorizationBox.isSelected()) {
					myAuthorizationBox.setText(" Authorization data will be updated upon update ");
				} else {
					myAuthorizationBox.setText(" Authorization data will be deleted upon update ");
				}
			} else {
				myAuthorizationBox.setText(" Add Authorization ");
			}
		} else {
			if (myAuthorizationBox.isSelected()) {
				myAuthorizationBox.setText(" Add Authorization ");
			}
		}
	}

	/**
	 * Update patient prepared statement. 
	 */
	protected void updatePatient() {
		try {
			System.out.println("in insert");
			PatientCard.myPatientUpdatePS.setString(1, myPatientTxt.get(1).getText());
			PatientCard.myPatientUpdatePS.setString(2, myPatientTxt.get(2).getText());
			PatientCard.myPatientUpdatePS.setString(3, myPatientTxt.get(3).getText());
			PatientCard.myPatientUpdatePS.setString(4, myPatientTxt.get(4).getText());
			PatientCard.myPatientUpdatePS.setString(5, myPatientTxt.get(5).getText());
			PatientCard.myPatientUpdatePS.setInt(6, Integer.parseInt(myPatientTxt.get(6).getText()));
			PatientCard.myPatientUpdatePS.setDate(7, Date.valueOf(myPatientTxt.get(7).getText()));
			PatientCard.myPatientUpdatePS.setString(8, (myPatientTxt.get(8).getText()));
			PatientCard.myPatientUpdatePS.setString(9, myPatientTxt.get(9).getText());
			PatientCard.myPatientUpdatePS.setInt(10, Integer.parseInt(myPatientTxt.get(0).getText()));
			PatientCard.myPatientUpdatePS.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			new MSGWindow("Please check that all Patient Fields are entered correctly.");
		}
	}
	
	/**
	 * Update patient insurance prepared statment. 
	 */
	protected void updateInsurance() {
		try {

			PatientCard.myInsuranceUpdatePS.setString(1, myInsuranceTxt.get(0).getText());
			PatientCard.myInsuranceUpdatePS.setString(2, myInsuranceTxt.get(1).getText());
			PatientCard.myInsuranceUpdatePS.setString(3, myInsuranceTxt.get(2).getText());
			PatientCard.myInsuranceUpdatePS.setString(4, myInsuranceTxt.get(3).getText());
			PatientCard.myInsuranceUpdatePS.setInt(5, CURRENT_PATIENT_ID);
			PatientCard.myInsuranceUpdatePS.executeUpdate();
		} catch (SQLException sql) {
			new MSGWindow("Please check that all Insurance Fields are entered correctly.");
		}
	}
	
	/**
	 * Update patient authorization prepared statement.
	 */
	private void updateAuthorization() {
		try {

			PatientCard.myAuthorizationUpdatePS.setString(1, myAuthorizationTxt.get(0).getText());
			PatientCard.myAuthorizationUpdatePS.setString(2, myAuthorizationTxt.get(1).getText());
			PatientCard.myAuthorizationUpdatePS.setString(3, myAuthorizationTxt.get(2).getText());
			PatientCard.myAuthorizationUpdatePS.setString(4, myAuthorizationTxt.get(3).getText());
			PatientCard.myAuthorizationUpdatePS.setInt(5, CURRENT_PATIENT_ID);
			PatientCard.myAuthorizationUpdatePS.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			new MSGWindow("Please check that all Insurance authorization fields are entered correctly");
		}
	}
	
	/**
	 * Insert patient insurance prepared statement. 
	 */
	protected void insertInsurance() {
		try {
			PatientCard.myInsuranceInsertPS.setInt(1, CURRENT_PATIENT_ID);
			PatientCard.myInsuranceInsertPS.setString(2, myInsuranceTxt.get(0).getText());
			PatientCard.myInsuranceInsertPS.setString(3, myInsuranceTxt.get(1).getText());
			PatientCard.myInsuranceInsertPS.setString(4, myInsuranceTxt.get(2).getText());
			PatientCard.myInsuranceInsertPS.setString(5, myInsuranceTxt.get(3).getText());
			PatientCard.myInsuranceInsertPS.executeUpdate();
		} catch (SQLException sql) {
			sql.printStackTrace();
			new MSGWindow("Please check that all Insurance Fields are entered correctly.");
		}
	}
	/**
	 * Insert patient authorization prepared statement. 
	 */
	private void insertAuthorization() {
		try {
			PatientCard.myAuthorizationInsertPS.setInt(1, CURRENT_PATIENT_ID);
			PatientCard.myAuthorizationInsertPS.setString(2, myAuthorizationTxt.get(0).getText());
			PatientCard.myAuthorizationInsertPS.setString(3, myAuthorizationTxt.get(1).getText());
			PatientCard.myAuthorizationInsertPS.setString(4, myAuthorizationTxt.get(2).getText());
			PatientCard.myAuthorizationInsertPS.setString(5, myAuthorizationTxt.get(3).getText());
			PatientCard.myAuthorizationInsertPS.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			new MSGWindow("Please check that all Insurance authorization fields are entered correctly");
		}
	}
	
	/**
	 * Delete all patient data using prepared statement. 
	 */
	private void deletePatient() {
		try {
			PatientCard.myPatientDeletePS.setInt(1, CURRENT_PATIENT_ID);
			PatientCard.myPatientDeletePS.executeUpdate();
			ArrayList<JTextField> temp = new ArrayList<JTextField>();
			temp.addAll(myPatientTxt);
			temp.addAll(myInsuranceTxt);
			temp.addAll(myAuthorizationTxt);
			for (JTextField textfield : temp) {
				textfield.setText("");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			new MSGWindow("Please check that all Insurance authorization fields are entered correctly");
		}
	}
	
	/**
	 * Delete prepared statment for patient insurance. 
	 */
	private void deleteInsurance() {
		try {
			PatientCard.myInsuranceDeletePS.setInt(1, CURRENT_PATIENT_ID);
			PatientCard.myInsuranceDeletePS.executeUpdate();
			for (JTextField text : myInsuranceTxt) {
				text.setText("");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			new MSGWindow("Please check that all Insurance authorization fields are entered correctly");

		}
	}
	
	/**
	 * Delete prepared statment for patients. 
	 */
	private void deleteAuthorization() {
		try {
			PatientCard.myAuthorizationDeletePS.setInt(1, CURRENT_PATIENT_ID);
			PatientCard.myAuthorizationDeletePS.executeUpdate();
			for (JTextField text : myAuthorizationTxt) {
				text.setText("");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			new MSGWindow("Please check that all Insurance authorization fields are entered correctly");

		}
	}
	
	/**
	 * Determines the group each attribute is in. 
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

				if (i == 0 && PatientCard.PATIENT_JOIN_META_DATA.getColumnLabel(i + 1).equals("Patient ID")
						|| i != 0 && !PatientCard.PATIENT_JOIN_META_DATA.getColumnLabel(i + 1).equals("Patient ID")) {
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
	 * Creates components for the Panel. 
	 * 
	 * @param theGroup the group of attributes.
	 * @param tableName the table name.
	 * @param txtFields the TextFields.
	 * @return a panel with components.
	 */
	private JPanel createPanel(ArrayList<String> theGroup, String tableName, ArrayList<JTextField> txtFields) {
		JPanel borderPanel = new JPanel(new ParagraphLayout());
		NUM_OF_ATTRIBUTES = theGroup.size();
		myLabels = new JLabel[NUM_OF_ATTRIBUTES];
		int size;
		if(tableName.equals(" Patient ")) {
			size = PATIENT_TEXT_SIZE;
		} else if(tableName.equals(" Insurance ")) {
			size = INSURANCE_TEXT_SIZE;
		} else {
			size = AUTHORIZATION_TEXT_SIZE;
		}
		
		for (int i = 0; i < NUM_OF_ATTRIBUTES; i++) {
			txtFields.add(new JTextField(size));
			myLabels[i] = new JLabel(theGroup.get(i));
			borderPanel.add(myLabels[i], ParagraphLayout.NEW_PARAGRAPH);
			borderPanel.add(txtFields.get(i), ParagraphLayout.NEW_LINE);
		}
		borderPanel.setBorder(BorderFactory.createTitledBorder(tableName));
		return borderPanel;
	}

	private void toggleComponents(JCheckBox theCheckBox, JPanel thePanel) {
		for (Component jc : thePanel.getComponents()) {
			jc.setEnabled(theCheckBox.isSelected());
		}
	}

	/**
	 * Receives a table selection event to set the text fields in the update
	 * panel.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("tableSelectionChanged")) {
			setFields((String[]) evt.getNewValue());
		}
	}

	/**
	 * Set Patient Fields.
	 */
	private void setFields(String[] rowData) {
		int rowDataCounter = 0;
		CURRENT_PATIENT_ID = Integer.parseInt(rowData[0]);
		for (int i = 0; i < myPatientTxt.size(); i++) {
			myPatientTxt.get(i).setText(rowData[rowDataCounter++]);
		}
		for (int j = 0; j < myInsuranceTxt.size(); j++) {
			myInsuranceTxt.get(j).setText(rowData[rowDataCounter++]);
		}
		if (!myInsuranceTxt.get(0).getText().isEmpty()) {
			hasInsurance = true;
			myInsuranceBox.setSelected(true);
		} else {
			hasInsurance = false;
			myInsuranceBox.setSelected(false);
		}
		for (int k = 0; k < myAuthorizationTxt.size(); k++) {
			myAuthorizationTxt.get(k).setText(rowData[rowDataCounter++]);
		}
		if (!myAuthorizationTxt.get(0).getText().isEmpty()) {
			hasAuthorization = true;
			myAuthorizationBox.setSelected(true);
		} else {
			hasAuthorization = false;
			myAuthorizationBox.setSelected(false);
		}
	}
}
