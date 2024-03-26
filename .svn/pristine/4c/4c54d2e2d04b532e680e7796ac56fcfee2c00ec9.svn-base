package ch.pschatzmann.common.table;

import java.util.List;

/**
 * Returns only absolute positive or negative values (if positive == false). A
 * pie chart does not accept any negative values. The class can be used to
 * present a pie chart of profits (positive values) and a pie chart of losses
 * (with negative values)
 * 
 * @author pschatzmann
 *
 */

public class TablePositive implements ITable<Number> {
	private ITable<Number> table;
	private boolean positive;

	public TablePositive(ITable<Number> table, boolean positive) {
		this.table = table;
		this.positive = positive;
	}

	@Override
	public List<String> getRowFieldNames() {
		return table.getRowFieldNames();
	}

	@Override
	public int getColumnCount() {
		return table.getColumnCount();
	}

	@Override
	public String getColumnTitle(int col) {
		return table.getColumnTitle(col);
	}

	@Override
	public int getRowCount() {
		return table.getRowCount();
	}

	@Override
	public List<String> getRowValue(int row) {
		return table.getRowValue(row);
	}

	@Override
	public Number getValue(int col, int row) {
		Number result = table.getValue(col, row);
		if (result != null) {
			if (result instanceof Number) {
				Number n = (Number) result;
				if (positive && n.doubleValue() > 0.0) {
					result = n.doubleValue();
				} else if (!positive && n.doubleValue() < 0.0) {
					result = -n.doubleValue();
				} else {
					result = 0.0;
				}
			}
		} else {
			result = 0.0;
		}
		return result;
	}

	@Override
	public ITable getBaseTable() {
		return this.table;
	}

}
