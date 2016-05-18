package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.jhlabs.awt.ParagraphLayout;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Panel that will allow users to query and filter the patient table.
 */
public class AppointmentSearchPanel extends AbstractSearchPanel {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 2549419250238970948L;

	/**
	 * patient search button.
	 */
	private JButton myApptSearchBtn;
	/**
	 * patient search labels.
	 */
	private JLabel lblPatientID, lblTherapistID, lblStartTime;
	/**
	 * patient text fields.
	 */
	private JTextField txtPatientID, txtTherapistID, txtStartTime;
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
	public AppointmentSearchPanel() {
		super(Color.darkGray);
		CURRENT_QUERY = "SELECT * FROM APPOINTMENT";
		addComponents();

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
		lblStartTime = new JLabel("Start Time: ");
		add(lblStartTime, ParagraphLayout.NEW_PARAGRAPH);
		txtStartTime = new JTextField(TEXT_FIELD_SIZE);
		add(txtStartTime, ParagraphLayout.NEW_LINE);
		myApptSearchBtn = new JButton("Search");

		myApptSearchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateSearchResults();

			}
		});
		this.add(myApptSearchBtn);
	}

	/**
	 * Builds sql expression from the search fields.
	 */
	private void updateSearchResults() {
		CURRENT_QUERY = "SELECT * FROM APPOINTMENT"; // to do
		this.firePropertyChange("createResultSet", null, null);
	}

}
