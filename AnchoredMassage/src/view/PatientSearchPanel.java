package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
	 * Builds sql expression from the search fields.
	 */
	private void updateSearchResults() {
		CURRENT_QUERY = "SELECT * FROM PATIENT"; // to do
		this.firePropertyChange("createResultSet", null, null);
	}

}
