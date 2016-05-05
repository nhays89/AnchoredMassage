package view;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import controller.TherapistCardListener;

public class TherapistTablePanel extends AbstractTablePanel implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1924941157603173363L;
	private TherapistCardListener myTherapistPanelListener;
	private AbstractTableModel myTherapistTableModel;
	private JTable myTherapistTable;
	private static String THERAPIST_LISTENER_PROP_NAME = "therapistPanelListener";

	public TherapistTablePanel() {
		super(new Color(133, 111, 122));
		myTherapistTableModel = new AbstractTableModel() {
	

			/**
			 * 
			 */
			private static final long serialVersionUID = 2616088246370497039L;

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
		myTherapistTable = new JTable(myTherapistTableModel);
		JScrollPane myTherapistScrollPane = new JScrollPane(myTherapistTable);
		myTherapistTable.setShowGrid(true);
		add(myTherapistScrollPane);

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if(propName.equals(THERAPIST_LISTENER_PROP_NAME)) {
			System.out.println(" in " + this.getClass().getSimpleName()+ ", getting the" + propName + "object");
			myTherapistPanelListener = (TherapistCardListener) evt.getNewValue();
			System.out.println("in " + this.getClass().getSimpleName()+ ", setting button action listener");
			myTherapistTableModel.addTableModelListener(myTherapistPanelListener);
		}
		
	}
	
	
	
}
