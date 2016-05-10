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
public class PatientTablePanel extends AbstractTablePanel {

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
	TableModel myPatientTableModel;
	
	/**
	 * Constructs the Table Panel. Displays a tabular grid of cells that correspond to 
	 * data in the table model. 
	 */
	public PatientTablePanel() {
		super(Color.CYAN);
		this.setLayout(new BorderLayout());
		myPatientTableModel = new AbstractTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8361210862913337326L;

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
		myPatientTable = new JTable(myPatientTableModel);
		JScrollPane myPatientScrollPane = new JScrollPane(myPatientTable);
		myPatientTable.setShowGrid(true);
		add(myPatientScrollPane, BorderLayout.CENTER);

	}
}
