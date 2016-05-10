package view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Panel displayed in a tabular format which allows users to interact
 *         with the search results.
 */
public class AppointmentTablePanel extends AbstractTablePanel {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = 2114076967932626581L;

	JTable myAppointmentTable;
	TableModel myAppointmentTableModel;

	/**
	 * Constructs an Appointment Table to be a component of the appointment
	 * card.
	 */
	public AppointmentTablePanel() {
		super(Color.red);
		this.setLayout(new BorderLayout());
		myAppointmentTableModel = new AbstractTableModel() {
			/**
			 * Default serial id.
			 */
			private static final long serialVersionUID = 1560767779827580324L;

			public int getColumnCount() {
				return 10;
			}

			public int getRowCount() {
				return 100;
			}

			public Object getValueAt(int row, int col) {
				return new Integer(row * col);
			}
		};
		myAppointmentTable = new JTable(myAppointmentTableModel);
		JScrollPane myTherpaistScrollPane = new JScrollPane(myAppointmentTable);
		myAppointmentTable.setShowGrid(true);
		myAppointmentTable.setFillsViewportHeight(true);
		add(myTherpaistScrollPane, BorderLayout.CENTER);
	}

}
