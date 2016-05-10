package view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * 
 * @author Nicholas A. Hays
 * 
 *         Panel displayed in a tabular format which allows users to interact
 *         with the search results.
 */
public class TherapistTablePanel extends AbstractTablePanel {

	/**
	 * Default serial id.
	 */
	private static final long serialVersionUID = -1924941157603173363L;

	/**
	 * Default table model.
	 */
	private AbstractTableModel myTherapistTableModel;

	/**
	 * My Table Component.
	 */
	private JTable myTherapistTable;

	/**
	 * Constructs the Therapist table panel which will hold all the sub
	 * components.
	 */
	public TherapistTablePanel() {
		super(new Color(133, 111, 122));
		this.setLayout(new BorderLayout());
		myTherapistTableModel = new AbstractTableModel() {

			/**
			 * default serial id.
			 */
			private static final long serialVersionUID = 2616088246370497039L;

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
		myTherapistTable = new JTable(myTherapistTableModel);
		JScrollPane myTherapistScrollPane = new JScrollPane(myTherapistTable);
		myTherapistTable.setShowGrid(true);
		add(myTherapistScrollPane, BorderLayout.CENTER);

	}

}
