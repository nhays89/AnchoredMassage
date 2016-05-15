package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.jhlabs.awt.ParagraphLayout;

import model.Patient;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Panel that will allow users to query and filter the patient table.
 */
public class PatientSearchPanel extends AbstractSearchPanel {

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
	 * current query string.
	 */
	public static String CURRENT_QUERY;
	private static int TEXT_FIELD_SIZE = 17;
	/**
	 * Constructs the Patient search panel.
	 */
	public PatientSearchPanel() {
		
		super(Color.yellow);
		addComponents();
		
	}

	private void addComponents() {
		lblPatientID = new JLabel("Patient ID:");
		add(lblPatientID, ParagraphLayout.NEW_PARAGRAPH);
		txtPatientID = new JTextField(TEXT_FIELD_SIZE);
		add(txtPatientID, ParagraphLayout.NEW_LINE);
		lblPatientFName= new JLabel("Patient First Name:");
		add(lblPatientFName, ParagraphLayout.NEW_PARAGRAPH);
		txtPatientFName = new JTextField(TEXT_FIELD_SIZE);
		add(txtPatientFName, ParagraphLayout.NEW_LINE);
		lblPatientLName = new JLabel("Patient Last Name:");
		add(lblPatientLName, ParagraphLayout.NEW_PARAGRAPH);
		txtPatientLName = new JTextField(TEXT_FIELD_SIZE);
		add(txtPatientLName, ParagraphLayout.NEW_LINE);
		myPatientSearchBtn = new JButton("Patient Search Button");
		myPatientSearchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateSearchResults();

			}
		});
		myPatientSearchBtn.setName("patientSearchBtn");
		this.add(myPatientSearchBtn);
		
	}

	/**
	 * Generates an sql string from the search fields, sends the string to the Patient card,
	 * Card then sends the string to the table panel, where the table panel
	 * will take the string, turn it to a statement, then execute the statement. 
	 */
	private void updateSearchResults() {
		 CURRENT_QUERY = "SELECT * FROM PATIENT";
		this.firePropertyChange("patientSearchBtn", null, null);
	}

	/**
	 * Retrieves the patient search results from the textfields to be used for
	 * the query to the database.
	 * 
	 * @return the Patient object.
	 */
	public Patient getPatientSearch() {
		return new Patient("IID", "ETHAN", "VICTORIA", null, null, null, null, null);
	}

}
