package view;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import controller.AppointmentCardListener;

public class AppointmentTablePanel extends AbstractTablePanel implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2114076967932626581L;
	private AppointmentCardListener myAppointmentPanelListener;
	JTable myAppointmentTable;
	TableModel myAppointmentTableModel;
	private static String APPOINTMENT_LISTENER_PROP_NAME = "appointmentPanelListener";

	public AppointmentTablePanel() {
		super(Color.red);
		myAppointmentTableModel = new AbstractTableModel() {
		
			

			/**
			 * 
			 */
			private static final long serialVersionUID = 1560767779827580324L;

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
		myAppointmentTable = new JTable(myAppointmentTableModel);
		JScrollPane myTherpaistScrollPane = new JScrollPane(myAppointmentTable);
		myAppointmentTable.setShowGrid(true);
		add(myTherpaistScrollPane);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		if (propName.equals(APPOINTMENT_LISTENER_PROP_NAME)) {
			System.out.println(" in " + this.getClass().getSimpleName() + ", getting the" + propName + "object");
			myAppointmentPanelListener = (AppointmentCardListener) evt.getNewValue();
			System.out.println("in " + this.getClass().getSimpleName() + ", setting button action listener");
			myAppointmentTableModel.addTableModelListener(myAppointmentPanelListener);
		}

	}

}
