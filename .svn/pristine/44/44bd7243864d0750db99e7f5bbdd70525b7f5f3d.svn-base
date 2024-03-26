package ch.pschatzmann.common.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.pschatzmann.common.table.CalculationUtils.Result;
import ch.pschatzmann.common.table.TablePercentChange.PercentDimension;

/**
 * Calculates the percentage of the total. The total value can be defined either
 * across rows or across columns.
 * 
 * @author pschatzmann
 *
 */

public class TablePercent implements ITable<Number> {
	private ITable<Number> table;
	private PercentDimension percentDimension;
	private Map<Integer, Double> totals;

	public TablePercent(ITable table, PercentDimension percentDimension) {
		this.table = table;
		this.percentDimension = percentDimension;
		calculateTotals(table, percentDimension);
	}

	private void calculateTotals(ITable table, PercentDimension percentDimension) {
		if (percentDimension == PercentDimension.OnColumns) {
			Result r = CalculationUtils.calculateColTotals(table);
			totals = r.totals;
		} else {
			Result r = CalculationUtils.calculateRowTotals(table);
			totals = r.totals;
		}
	}

	@Override
	public List<String> getRowFieldNames() {
		List<String> result = new ArrayList(table.getRowFieldNames());
		return result;
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
		Number result = null;
		if (percentDimension == PercentDimension.OnColumns) {
			result = calculateValue(col, row, col);
		} else {
			result = calculateValue(col, row, row);
		}
		return result;
	}

	private Number calculateValue(int col, int row, int totalFromRowOrCol) {
		Number obj1 = table.getValue(col, row);
		Number result=null;
		if (obj1 instanceof Number) {
			try {
				Double total = totals.get(totalFromRowOrCol);
				double value = ((Number) obj1).doubleValue() / total.doubleValue() * 100.0;				
				result = Math.round(value * 10.0) / 10.0;
			} catch (Exception ex) {
			}
		} else {
			result = obj1;
		}
		return result;
	}

	@Override
	public ITable getBaseTable() {
		return this.table;
	}
}
