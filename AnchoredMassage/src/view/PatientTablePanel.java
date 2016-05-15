package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javax.sql.*;

import model.PatientTableModel;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Panel displayed in a tabular format which allows users to interact
 *         with the search results.
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
	 * Default table model.
	 */
	AbstractTableModel defaultTableModel;

	/**
	 * Column Names.
	 */
	String[] colNames = { "PatientID", "First Name", "Last Name", "DOB", "street", "city", "state", "zip" };

	/**
	 * Patient Table Model.
	 */
	PatientTableModel myPatientTableModel;
	/**
	 * Database connection object.
	 */
	SQLServerDataSource ds;

	/**
	 * Constructs the Table Panel. Displays a tabular grid of cells that
	 * correspond to data in the table model.
	 */
	public PatientTablePanel() {
		super(Color.CYAN);
		ds = AnchoredGUI.DATA_SOURCE;
		this.setLayout(new BorderLayout());

		/*defaultTableModel = new AbstractTableModel() {

			private static final long serialVersionUID = 8361210862913337326L;

			public int getColumnCount() {
				return 8;
			}

			public int getRowCount() {
				return 100;
			}

			public String getColumnName(int column) {
				return colNames[column];
			}

			public Object getValueAt(int row, int col) {
				return "";
			}
		};*/

		myPatientTable = new JTable();
		JScrollPane myPatientScrollPane = new JScrollPane(myPatientTable);
		myPatientTable.setShowGrid(true);
		add(myPatientScrollPane, BorderLayout.CENTER);

	}

	public void updateTableModel() {

		try {
			Statement stmt;
			Connection con = null;
			con = ds.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery(PatientSearchPanel.CURRENT_QUERY);
			myPatientTableModel = new PatientTableModel(rs);
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

	}

	
	
	/**
	 * List selection handler.
	 * @param evt
	 */
	@Override
	public void valueChanged(ListSelectionEvent evt) {
		if (evt.getValueIsAdjusting() == false && myPatientTable.getSelectedRow() != -1) {
			System.out.println(myPatientTable.getSelectedRow());
			String[] rowData = getRowAt(myPatientTable.getSelectedRow());
			firePropertyChange("tableSelectionChanged", null, rowData);
		}
	}

	public String[] getRowAt(int row) {
		int colNumber = myPatientTableModel.getColumnCount();
		String[] result = new String[colNumber];

		for (int i = 0; i < colNumber; i++) {
			result[i] = (String) myPatientTable.getModel().getValueAt(row, i);
		}
		return result;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if (propName.equals("patientUpdateBtn")) {
			if (PatientSearchPanel.CURRENT_QUERY != null) {
				updateTableModel();
			}
		}
		if(propName.equals("patientSearchBtn")) {
			updateTableModel();
		}

	}
}
