package view;

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
 *         Panel that will allow users to query and filter the therapist table.
 */
public class TherapistSearchPanel extends JPanel {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 6187373824017488881L;
	/**
	 * therapist search button.
	 */
	private JButton myTherapistSubmitBtn;
	/**
	 * therapist search labels.
	 */
	private JLabel lblTherapistID, lblTherapistFName, lblTherapistLName;
	/**
	 * therapist text fields.
	 */
	private JTextField txtTherapistID, txtTherapistFName, txtTherapistLName;
	/**
	 * current query string.
	 */
	public static String CURRENT_QUERY;
	/**
	 * text field length.
	 */
	private static int TEXT_FIELD_SIZE = 17;

	public TherapistSearchPanel() {
		CURRENT_QUERY = "SELECT * FROM THERAPIST";
		addComponents();
		this.setPreferredSize(new Dimension(1000, 75));
		this.setVisible(true);
	}
	private void addComponents() {
		TitledBorder title;
		title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Search");
		title.setTitlePosition(TitledBorder.TOP);
		this.setBorder(title);
		lblTherapistID = new JLabel("Therapist ID:");
		add(lblTherapistID, ParagraphLayout.NEW_PARAGRAPH);
		txtTherapistID = new JTextField(TEXT_FIELD_SIZE);
		add(txtTherapistID, ParagraphLayout.NEW_LINE);
		lblTherapistFName = new JLabel("Therapist First Name:");
		add(lblTherapistFName, ParagraphLayout.NEW_PARAGRAPH);
		txtTherapistFName = new JTextField(TEXT_FIELD_SIZE);
		add(txtTherapistFName, ParagraphLayout.NEW_LINE);
		lblTherapistLName = new JLabel("Therapist Last Name:");
		add(lblTherapistLName, ParagraphLayout.NEW_PARAGRAPH);
		txtTherapistLName = new JTextField(TEXT_FIELD_SIZE);
		add(txtTherapistLName, ParagraphLayout.NEW_LINE);
		myTherapistSubmitBtn = new JButton(" Search ");

		myTherapistSubmitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateSearchResults();

			}
		});
		myTherapistSubmitBtn.setName("therapistSearchBtn");
		this.add(myTherapistSubmitBtn);
		
	}
	/**
	 * Builds sql expression from the search fields.
	 */
	private void updateSearchResults() {
		CURRENT_QUERY = "SELECT * FROM THERAPIST"; // to do
		this.firePropertyChange("createResultSet", null, null);
	}

}
