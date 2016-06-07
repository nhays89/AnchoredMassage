package model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class AnchoredTableModel implements TableModel {
	/**
	 * Result set that represents the data in the model. 
	 */
	private ResultSet resultSet;
	/**
	 * Result set meta data.
	 */
	private ResultSetMetaData metadata;
	/**
	 * Stores the number of columns in the result set. 
	 */
	private int numCols;
	/**
	 * Stores the number of rows in the result set. 
	 */
	private int numRows;
	
	/**
	 * Constructs the table model that will be used for all TableCards. 
	 * 
	 * @param rs the result set. 
	 * @throws SQLException 
	 */
	public AnchoredTableModel (ResultSet rs) throws SQLException {
		if(rs == null) return;
		 this.resultSet = rs;
		    this.metadata = this.resultSet.getMetaData();
		    numCols = metadata.getColumnCount();
		    this.resultSet.beforeFirst();
		    this.numRows = 0;
		    while (this.resultSet.next()) {
		        this.numRows++;
		    }
		    this.resultSet.beforeFirst();
	}

	@Override
	public void addTableModelListener(TableModelListener tabelListener) {
		return;
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return numCols;
	}

	@Override
	public String getColumnName(int column) {
		   try {
		        return this.metadata.getColumnLabel(column + 1);
		    } catch (SQLException e) {
		        return e.toString();
		    }
	}

	@Override
	public int getRowCount() {
		return numRows;
	}

	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
	    try {
	        this.resultSet.absolute(rowIndex + 1);
	        Object o = this.resultSet.getObject(colIndex + 1);
	        if (o == null)
	            return null;
	        else
	            return o.toString();
	    } catch (SQLException e) {
	        return e.toString();
	    }
	}
	

	@Override
	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}

}
