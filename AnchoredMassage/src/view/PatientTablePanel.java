package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import model.AnchoredTableModel;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Panel displayed in a tabular format.
 */
public class PatientTablePanel extends AbstractTablePanel
		implements TableModelListener, ListSelectionListener, PropertyChangeListener {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 6538944986110021190L;

	/**
	 * The patient table.
	 */
	JTable myPatientTable;

	/**
	 * Patient Table Model.
	 */
	AnchoredTableModel myPatientTableModel;
	/**
	 * Database connection object.
	 */
	SQLServerDataSource DATA_SOURCE;
	/**
	 * Database connection.
	 */
	Connection myDBConn;

	/**
	 * Constructs the Table Panel. Displays a tabular grid of cells that
	 * correspond to data in the table model.
	 */
	public PatientTablePanel() {
		super(Color.darkGray);
		this.setLayout(new BorderLayout());
		myDBConn = AnchoredGUI.DB_CONNECTION;
		myPatientTable = new JTable();
		createTableModel();
		JScrollPane myPatientScrollPane = new JScrollPane(myPatientTable);
		myPatientTable.setShowGrid(true);
		add(myPatientScrollPane, BorderLayout.CENTER);

	}

	/**
	 * Creates a new table model based on the current Query string from the
	 * search panel.
	 */
	public void createTableModel() {
		try {
			Statement stmt;
			stmt = myDBConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery(AppointmentSearchPanel.CURRENT_QUERY);
			myPatientTableModel = new AnchoredTableModel(rs);
			myPatientTableModel.addTableModelListener(this);
			myPatientTable.setModel(myPatientTableModel);
			myPatientTable.getSelectionModel().addListSelectionListener(this);
		} catch (Exception e) {
			new MSGWindow("error");
			e.printStackTrace();
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		System.out.println("in table changed");
		// to do
	}

	/**
	 * List selection handler. Notifies the update panel of current selection.
	 * 
	 * @param evt
	 *            user selection event on a row in the Table.
	 */
	@Override
	public void valueChanged(ListSelectionEvent evt) {
		if (evt.getValueIsAdjusting() == false && myPatientTable.getSelectedRow() != -1) {
			String[] rowData = getRowAt(myPatientTable.getSelectedRow());
			firePropertyChange("tableSelectionChanged", null, rowData);
		}
	}

	/**
	 * Retrieves a record of the currently selection row in the patient table.
	 * 
	 * @param row
	 *            the row from the table.
	 * @return patient data to be inserted into the update panel.
	 */
	public String[] getRowAt(int row) {
		int colNumber = myPatientTableModel.getColumnCount();
		String[] result = new String[colNumber];

		for (int i = 0; i < colNumber; i++) {
			result[i] = (String) myPatientTable.getModel().getValueAt(row, i);
		}
		return result;
	}

	/**
	 * Handles property change events that will update the data model.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if (propName.equals("createResultSet")) {
			if (AppointmentSearchPanel.CURRENT_QUERY != null) {
				createTableModel();
			}
		}
	}
}
