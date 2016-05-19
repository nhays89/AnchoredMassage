package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


import model.AnchoredTableModel;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Panel displayed in a tabular format which allows users to interact
 *         with the search results.
 */
public class TherapistTablePanel extends JPanel
		implements TableModelListener, ListSelectionListener, PropertyChangeListener {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = -1924941157603173363L;

	/**
	 * Default table model.
	 */
	private AnchoredTableModel myTherapistTableModel;

	/**
	 * My Table Component.
	 */
	private JTable myTherapistTable;
	/**
	 * Database Connection string.
	 */
	private Connection myDBConn;

	/**
	 * Constructs the Therapist table panel which will hold all the sub
	 * components.
	 */
	public TherapistTablePanel() {
		this.setLayout(new BorderLayout());
		myDBConn = AnchoredGUI.DB_CONNECTION;
		myTherapistTable = new JTable();
		createTableModel();
		JScrollPane myTherapistScrollPane = new JScrollPane(myTherapistTable);
		myTherapistTable.setShowGrid(true);
		add(myTherapistScrollPane, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(600, 600));
		this.setVisible(true);
	}

	private void createTableModel() {
		try {
			Statement stmt;
			stmt = myDBConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery(TherapistSearchPanel.CURRENT_QUERY);
			myTherapistTableModel = new AnchoredTableModel(rs);
			myTherapistTableModel.addTableModelListener(this);
			myTherapistTable.setModel(myTherapistTableModel);
			myTherapistTable.getSelectionModel().addListSelectionListener(this);
		} catch (Exception e) {
			new MSGWindow("error");
			e.printStackTrace();
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
		int colNumber = myTherapistTableModel.getColumnCount();
		String[] result = new String[colNumber];

		for (int i = 0; i < colNumber; i++) {
			result[i] = (String) myTherapistTable.getModel().getValueAt(row, i);
		}
		return result;
	}
	
	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if (propName.equals("createResultSet")) {
			if (TherapistSearchPanel.CURRENT_QUERY != null) {
				createTableModel();
			}
		}
	}

	/**
	 * List selection handler. Notifies the update panel of current selection.
	 * 
	 * @param evt
	 *            user selection event on a row in the Table.
	 */
	@Override
	public void valueChanged(ListSelectionEvent evt) {
		if (evt.getValueIsAdjusting() == false && myTherapistTable.getSelectedRow() != -1) {
			String[] rowData = getRowAt(myTherapistTable.getSelectedRow());
			firePropertyChange("tableSelectionChanged", null, rowData);
		}
	}


	@Override
	public void tableChanged(TableModelEvent arg0) {
		// TODO Auto-generated method stub

	}

}
