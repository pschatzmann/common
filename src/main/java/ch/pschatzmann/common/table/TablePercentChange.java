package ch.pschatzmann.common.table;

import java.util.ArrayList;
import java.util.List;

/**
 * Calculates the percentage change between the prior value. The prior value can 
 * be defined either across rows or across columns.
 * 
 * @author pschatzmann
 *
 */

public class TablePercentChange implements ITable {	
	private static final long serialVersionUID = 1L;

	public enum PercentDimension {OnRows, OnColumns};
	private ITable<Number> table;
	private PercentDimension percentDimension;

	public TablePercentChange(ITable table, PercentDimension percentDimension) {
		this.table = table;
		this.percentDimension = percentDimension;
	}

	@Override
	public List<String> getRowFieldNames() {
		List<String> result = new ArrayList(table.getRowFieldNames());
		return result;
	}

	@Override
	public int getColumnCount() {
		return percentDimension==PercentDimension.OnColumns ? table.getColumnCount()-1 : table.getColumnCount();
	}

	@Override
	public String getColumnTitle(int col) {
		return percentDimension==PercentDimension.OnColumns ? table.getColumnTitle(col+1) : table.getColumnTitle(col);
	}

	@Override
	public int getRowCount() {
		return percentDimension==PercentDimension.OnRows ? table.getRowCount()-1 : table.getRowCount();
	}

	@Override
	public List<String> getRowValue(int row) {
		return percentDimension==PercentDimension.OnRows ? table.getRowValue(row+1) : table.getRowValue(row);
	}

	@Override
	public Number getValue(int col, int row) {
		int currentCol = col;
		int currentRow = row;
		Number result = null;
		if (percentDimension==PercentDimension.OnColumns ) {
			Number obj1 = table.getValue(col,row);
			Number obj2 = table.getValue(col+1,row);
			result = determineValue(obj1, obj2);
			
		} else {
			Number obj1 = table.getValue(col,row);
			Number obj2 = table.getValue(col,row+1);
			result = determineValue( obj1, obj2);			
		}
		return result;
	}

	private Number determineValue( Number obj1, Number obj2) {
		Number result;
			try {
				Number val1 = (Number) obj1;
				Number val2 = (Number) obj2;
				double value = (val2.doubleValue() - val1.doubleValue()) / val1.doubleValue() * 100;	
				if (Double.isNaN(value)||Double.isInfinite(value)) {
					result = null;
				} else {
					result = Math.round(value * 100.0) / 100.0;
				}
			} catch(Exception ex) {
				result = null;
			}
		 
		return result;
	}

	@Override
	public ITable getBaseTable() {
		return this.table;
	}

}
