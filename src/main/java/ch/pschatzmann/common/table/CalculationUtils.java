package ch.pschatzmann.common.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculationUtils implements Serializable {

	public static Result calculateColTotals(ITable table) {
		Map<Integer, Double> totalCols = new HashMap();
		List<Integer> cols = new ArrayList();
		for (int col = 0; col < table.getColumnCount(); col++) {
			cols.add(col);
			double total = 0.0;
			for (int row = 0; row < table.getRowCount(); row++) {
				Object val = table.getValue(col, row);
				if (val instanceof Number) {
					total += ((Number) val).doubleValue();
				}
			}
			totalCols.put(col, total);
		}
		
		//colFieldModel.setup(cols, max, totalCols);
		return new Result(cols, totalCols);
	}

	public static Result calculateRowTotals(ITable table) {
		Map<Integer, Double> totalRows = new HashMap();
		List<Integer> rows = new ArrayList();
		for (int row = 0; row < table.getRowCount(); row++) {
			rows.add(row);
			double total = 0.0;
			for (int col = 0; col < table.getColumnCount(); col++) {
				Object val = table.getValue(col, row);
				if (val instanceof Number) {
					total += ((Number) val).doubleValue();
				}
			}
			totalRows.put(row, total);
		}
		
		//rowFieldModel.setup(rows, max, totalRows);
		return new Result(rows,totalRows);
	}
	
	static class Result {
		List<Integer> lines;
		Map<Integer, Double> totals;
		Result(List<Integer> rows,Map<Integer, Double> totals) {
			this.lines = rows;
			this.totals = totals;
		}
	}

}
