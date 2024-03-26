package ch.pschatzmann.common.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.pschatzmann.common.table.CalculationUtils.Result;

/**
 * Returns a table with a limited number of rows and columns: We sort the rows
 * and columns by their total in order to get the biggest values. Then we
 * summarize the rows and columns which are exceeding the max number or rows and
 * columns as OTHERS.
 * 
 * @author pschatzmann
 *
 */

public class TableTopN implements ITable {
	private static final String OTHERS = "[OTHERS]";
	private ITable table;
	private int max;
	private IFieldModel colFieldModel = null;
	private IFieldModel rowFieldModel = null;

	public TableTopN(ITable table, int n) {
		this(table, n, new FieldModelTopN(), new FieldModelTopN());
	}
	
	public TableTopN(ITable table, int n, IFieldModel colFieldModel, IFieldModel rowFieldModel) {
		this.table = table;
		this.max = n;
		this.colFieldModel = colFieldModel;
		this.rowFieldModel = rowFieldModel;
		Result rCol = CalculationUtils.calculateColTotals(this.table);
		colFieldModel.setup(rCol.lines, max, rCol.totals);

		Result rRow = CalculationUtils.calculateRowTotals(this.table);
		rowFieldModel.setup(rRow.lines, max, rRow.totals);
	}


	@Override
	public List<String> getRowFieldNames() {
		return table.getRowFieldNames();
	}

	@Override
	public int getColumnCount() {
		return getCols().size() + (this.colFieldModel.getOtherValues().isEmpty() ? 0 : 1);
	}

	private List<Integer> getCols() {
		return this.colFieldModel.getValues();
	}

	@Override
	public String getColumnTitle(int col) {
		return col < getCols().size() ? table.getColumnTitle(this.getCols().get(col)) : OTHERS;
	}

	@Override
	public int getRowCount() {
		return getRows().size() + (this.getOtherRows().isEmpty() ? 0 : 1);
	}

	private List<Integer> getOtherRows() {
		return this.rowFieldModel.getOtherValues();
	}

	@Override
	public List<String> getRowValue(int row) {
		List<String> result = null;
		if (row > getRows().size() - 1) {
			result = Arrays.asList(OTHERS);
		} else {
			result = table.getRowValue(getRows().get(row));
		}
		return result;
	}

	private List<Integer> getRows() {
		return this.rowFieldModel.getValues();
	}

	@Override
	public Number getValue(int col, int row) {
		List<Integer> cols = getBaseCols(col);
		List<Integer> rows = getBaseRows(row);
		Number result = getTotal(cols, rows);

		return result;
	}

	private List<Integer> getBaseRows(int row) {
		List<Integer> rows = new ArrayList();
		if (row < this.getRows().size()) {
			rows.add(this.getRows().get(row));
		} else {
			rows.addAll(getOtherRows());
		}
		return rows;
	}

	private List<Integer> getBaseCols(int col) {
		List<Integer> cols = new ArrayList();
		if (col < this.getCols().size()) {
			cols.add(this.getCols().get(col));
		} else {
			cols.addAll(getOtherCols());
		}
		return cols;
	}

	private Collection<Integer> getOtherCols() {
		return this.colFieldModel.getOtherValues();
	}

	private Number getTotal(List<Integer> cols, List<Integer> rows) {
		Double result = 0.0;
		for (int xrow : rows) {
			for (int xcol : cols) {
				Object obj = table.getValue(xcol, xrow);
				if (obj != null) {
					if (obj instanceof Number) {
						Number n = (Number) obj;
						result += n.doubleValue();
					}
				}
			}
		}
		return result;
	}

	@Override
	public ITable getBaseTable() {
		return this.table;
	}


}
