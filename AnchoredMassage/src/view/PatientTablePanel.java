package view;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import controller.PatientCardListener;

public class PatientTablePanel extends AbstractTablePanel implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6538944986110021190L;
	private PatientCardListener myPatientPanelListener;
	JTable myPatientTable;
	TableModel myPatientTableModel;

	public PatientTablePanel() {
		super(Color.CYAN);

		myPatientTableModel = new AbstractTableModel() {
		

			/**
			 * 
			 */
			private static final long serialVersionUID = 8361210862913337326L;

			public int getColumnCount() {
				return 10;
			}

			public int getRowCount() {
				return 10;
			}

			public Object getValueAt(int row, int col) {
				return new Integer(row * col);
			}
		};
		myPatientTable = new JTable(myPatientTableModel);
		JScrollPane myPatientScrollPane = new JScrollPane(myPatientTable);
		myPatientTable.setShowGrid(true);
		add(myPatientScrollPane);

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if (propName.equals("patientTablePanel")) {
			System.out.println(" in " + this.getClass().getSimpleName()+ ", getting the" + propName + "object");
			myPatientPanelListener = (PatientCardListener) evt.getNewValue();
			System.out.println("in " + this.getClass().getSimpleName()+ ", setting button action listener");
			myPatientTableModel.addTableModelListener(myPatientPanelListener);
		}
	}

}
