package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
	private JButton myTherapistSearchBtn;
	/**
	 * therapist search labels.
	 */
	private JLabel lblTherapistID, lblTherapistFName, lblTherapistLName;
	/**
	 * therapist text fields.
	 */
	private JTextField txtTherapistID, txtTherapistFName, txtTherapistLName;
	/**
	 * JButton to launch create dialog.
	 */
	private JButton myTherapistCreateBtn;
	/**
	 * JButton to submit therapist data to database. 
	 */
	private CreateEntity myTherapistEntity;
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
	/**
	 * Add components to therapist search panel. 
	 */
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
		myTherapistSearchBtn = new JButton(" Search ");
		myTherapistCreateBtn = new JButton(" Create ");
		myTherapistSearchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateSearchResults();

			}
		});
		this.add(myTherapistSearchBtn);
		myTherapistCreateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myTherapistEntity = new CreateEntity(TherapistCard.THERAPIST_META_DATA, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						insertTherapistTuple(myTherapistEntity.getTextFields());
						myTherapistEntity.clearFields();
					}
				});
			}
		});
		this.add(myTherapistCreateBtn);
	}
	/**
	 * Builds SQL expression from the search fields.
	 */
	private void updateSearchResults() {
		StringBuilder searchExp = new StringBuilder("Select * from THERAPIST ");
		if(!txtTherapistID.getText().isEmpty()) searchExp.append("where [Patient ID] = '" + txtTherapistID.getText() + "'");
		if(!txtTherapistFName.getText().isEmpty()) {
			if(!txtTherapistID.getText().isEmpty()) {
				searchExp.append(" AND [First Name] = '" + txtTherapistFName.getText()  + "'");
			} else {
				searchExp.append("where [First Name] LIKE '%" + txtTherapistFName.getText() + "%'");
			}
		}
		if(!txtTherapistLName.getText().isEmpty()) {
			if(!txtTherapistID.getText().isEmpty() || !(txtTherapistFName.getText().isEmpty())) {
				searchExp.append(" AND [Last Name] = '" + txtTherapistFName.getText()  + "'");
			} else {
				searchExp.append("where [Last Name] LIKE '%" + txtTherapistLName.getText() + "%'");
			}
		}
		CURRENT_QUERY = searchExp.toString(); 
		this.firePropertyChange("createResultSet", null, null);
	}
	/**
	 * Inserts a therapist tuple using prepared statement. 
	 * @param dataFields the data to add to the therapist tuple. 
	 */
	private void insertTherapistTuple(String[] dataFields) {
		try {
			TherapistCard.myTherapistInsertPS.setString(1, dataFields[1]);
			TherapistCard.myTherapistInsertPS.setString(2, dataFields[2]);
			TherapistCard.myTherapistInsertPS.setString(3, dataFields[3]);
			TherapistCard.myTherapistInsertPS.setString(4, dataFields[4]);
			TherapistCard.myTherapistInsertPS.setString(5, dataFields[5]);
			TherapistCard.myTherapistInsertPS.setDate(6, Date.valueOf(dataFields[6]));
			TherapistCard.myTherapistInsertPS.setInt(7, Integer.parseInt(dataFields[7]));
			TherapistCard.myTherapistInsertPS.setString(8, dataFields[8]);
			TherapistCard.myTherapistInsertPS.setString(9, dataFields[9]);
			TherapistCard.myTherapistInsertPS.setDate(10, Date.valueOf(dataFields[10]));
			TherapistCard.myTherapistInsertPS.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			new MSGWindow(e.getLocalizedMessage());
		}
		this.firePropertyChange("createResultSet", null, null);

	}

	
	
	/**
	 * Provides main dialog and components for therapist. 
	 * 
	 * @author Nicholas A. Hays
	 */
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
		 * Creates the therapist entity. 
		 * @param theData
		 *            the result set meta data
		 */
		public CreateEntity(ResultSetMetaData theData, ActionListener theListener) {
			myMetaData = theData;
			this.setLocationRelativeTo(null);
			myActionListener = theListener;
			myDisplay = new JPanel(new ParagraphLayout());
			createComponents();
		}
		
		/**
		 * Creates the components to dispaly on the dialog. 
		 */
		private void createComponents() {
			try {
				NUM_OF_ATTRIBUTES = myMetaData.getColumnCount();
				myLabels = new JLabel[NUM_OF_ATTRIBUTES];
				myTextFields = new JTextField[NUM_OF_ATTRIBUTES];
				myAttribute = new String[NUM_OF_ATTRIBUTES];
				for (int i = 0; i < myMetaData.getColumnCount(); i++) {
					myTextFields[i] = new JTextField(TEXT_FIELD_SIZE);
					myAttribute[i] = myMetaData.getColumnLabel(i + 1);
					myLabels[i] = new JLabel(myMetaData.getColumnLabel(i + 1));
					if( i > 0) {
						myDisplay.add(myLabels[i], ParagraphLayout.NEW_PARAGRAPH);
						myDisplay.add(myTextFields[i], ParagraphLayout.NEW_LINE);
					}
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
		 * Retreives text fields. 
		 * @return the text fields data. 
		 */
		protected String[] getTextFields() {
			String[] textFields = new String[NUM_OF_ATTRIBUTES];
			for (int i = 1; i < textFields.length; i++) {
				textFields[i] = myTextFields[i].getText();
			}
			return textFields;
		}
		
		/**
		 * Clears text fields. 
		 */
		protected void clearFields() {
			for (JTextField f : myTextFields) {
				f.setText(null);
			}
		}
	}

	

}
