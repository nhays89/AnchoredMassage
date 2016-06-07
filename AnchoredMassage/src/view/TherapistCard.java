package view;

import java.awt.BorderLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JPanel;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Acts as a storage container for its Panel components.
 */
public class TherapistCard extends JPanel {
	/**
	 * Therapist Info Panel.
	 */
	TherapistUpdatePanel myTherapistUpdatePanel;
	/**
	 * Therapist Search Panel.
	 */
	TherapistSearchPanel myTherapistSearchPanel;
	/**
	 * Therapist Table Panel.
	 */
	TherapistTablePanel myTherapistTablePanel;
	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 2398946988371590567L;
	/**
	 * meta data.
	 */
	protected static PreparedStatement myTherapistInsertPS;
	/**
	 * therapist meta data
	 */
	protected static ResultSetMetaData THERAPIST_META_DATA;
	
	/**
	 * Constructs the Therapist card which will act as a container for its sub
	 * components. Initializes prepared statements, and retrieves meta data for 
	 * therapist. 
	 */
	public TherapistCard() {
		setLayout(new BorderLayout());
		myTherapistUpdatePanel = new TherapistUpdatePanel();
		myTherapistSearchPanel = new TherapistSearchPanel();
		myTherapistTablePanel = new TherapistTablePanel();
		add(myTherapistUpdatePanel, BorderLayout.EAST);
		add(myTherapistSearchPanel, BorderLayout.NORTH);
		add(myTherapistTablePanel, BorderLayout.CENTER);
		myTherapistTablePanel.addPropertyChangeListener(myTherapistUpdatePanel);
		myTherapistUpdatePanel.addPropertyChangeListener(myTherapistTablePanel);
		myTherapistSearchPanel.addPropertyChangeListener(myTherapistTablePanel);
		
		Statement therapistStmt;
		try {
			myTherapistInsertPS = AnchoredGUI.DB_CONNECTION.prepareStatement("insert into THERAPIST values(?,?,?,?,?,?,?,?,?,?)");
			therapistStmt = AnchoredGUI.DB_CONNECTION.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			therapistStmt.execute("Select * from THERAPIST");
			THERAPIST_META_DATA = therapistStmt.getResultSet().getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
